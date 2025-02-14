package com.gonzaga.challengejava.services;

import com.gonzaga.challengejava.dtos.CheckinDTO;
import com.gonzaga.challengejava.infra.CPFConflictException;
import com.gonzaga.challengejava.infra.GuestNotFoundException;
import com.gonzaga.challengejava.models.GuestModel;
import com.gonzaga.challengejava.models.RoomModel;
import com.gonzaga.challengejava.repositories.GuestRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GuestService {

    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public GuestModel getGuest(UUID id){
        Optional<GuestModel> guest = guestRepository.findById(id);
        if(guest.isPresent()){
            return guest.get();
        }
        throw new GuestNotFoundException("Hóspede não encontrado");
    }
    
    public GuestModel createGuest(CheckinDTO checkinDTO, RoomModel room){
        GuestModel toCheck = guestRepository.findGuestByCPF(checkinDTO.CPF());
        if(toCheck == null){
            GuestModel guest = new GuestModel();
            guest.setCPF(checkinDTO.CPF());
            guest.setName(checkinDTO.name());
            guest.setGender(checkinDTO.gender());
            guest.setRoom(room);
            return guestRepository.save(guest);
        }

        throw new CPFConflictException("CPF Já existe");
    }

    public void deleteGuest(UUID id){
        GuestModel guest = guestRepository.findById(id).get();
        guestRepository.delete(guest);
    }

}
