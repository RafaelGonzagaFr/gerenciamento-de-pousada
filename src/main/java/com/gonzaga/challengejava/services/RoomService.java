package com.gonzaga.challengejava.services;

import com.gonzaga.challengejava.dtos.CheckinDTO;
import com.gonzaga.challengejava.dtos.RoomDTO;
import com.gonzaga.challengejava.infra.CPFConflictException;
import com.gonzaga.challengejava.infra.RoomNotFoundException;
import com.gonzaga.challengejava.infra.RoomNumberAlreadyExistsException;
import com.gonzaga.challengejava.infra.RoomOccupiedException;
import com.gonzaga.challengejava.models.RoomModel;
import com.gonzaga.challengejava.repositories.GuestRepository;
import com.gonzaga.challengejava.repositories.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final GuestService guestService;

    public RoomService(RoomRepository roomRepository, GuestRepository guestRepository, GuestService guestService) {
        this.roomRepository = roomRepository;
        this.guestService = guestService;
    }

    public List<RoomModel> getAllRooms(){
        return roomRepository.findAll();
    }

    public List<RoomModel> getAllOccupiedRooms(){
        return roomRepository.findOccupiedRooms();
    }

    public List<RoomModel> getAllUnoccupiedRooms(){
        return roomRepository.findUnoccupiedRooms();
    }

    public RoomModel getRoomById(UUID id){
        Optional<RoomModel> roomById = roomRepository.findById(id);
        if(roomById.isPresent()){
            return roomById.get();
        }
        throw new RoomNotFoundException("Quarto não encontrado");//Tenho que retornar algo para dizer que esse quarto não existe
    }

    @Transactional
    public RoomModel createRoom(RoomDTO roomDTO){
        RoomModel toCheck = roomRepository.findByRoomNumber(roomDTO.roomNumber());
        if(toCheck == null){
            RoomModel room = new RoomModel();
            room.setRoomNumber(roomDTO.roomNumber());
            return roomRepository.save(room);
        }
         throw new RoomNumberAlreadyExistsException("Número do quarto já existe");

    }

    @Transactional
    public RoomModel checkIn(UUID id, CheckinDTO checkinDTO){
        System.out.println(checkinDTO);
        Optional<RoomModel> roomById = roomRepository.findById(id);
        if(roomById.isPresent()){
            RoomModel room = roomById.get();
            if(room.getOccupied()){
                System.out.println("Quarto ocupado");
                throw new RoomOccupiedException("Quarto ocupado pelo hóspede " + room.getGuest().getName());
            }

            try{
                room.setGuest(guestService.createGuest(checkinDTO, room));
            } catch (CPFConflictException e){
                throw new CPFConflictException(e.getMessage());
            }
            room.setOccupied(true);
            return roomRepository.save(room);
        }
        throw new RoomNotFoundException("Quarto não encontrado");
    }

    @Transactional
    public RoomModel checkOut(UUID id){
        Optional<RoomModel> roomById = roomRepository.findById(id);
        if(roomById.isPresent()){
            RoomModel room = roomById.get();

            guestService.deleteGuest(room.getGuest().getId());
            room.setGuest(null);
            room.setOccupied(false);

            return roomRepository.save(room);
        }
        throw new RoomNotFoundException("Quarto não encontrado");//Tenho que retornar algo para dizer que esse quarto não existe
    }
}
