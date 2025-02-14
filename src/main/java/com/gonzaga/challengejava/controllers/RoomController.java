package com.gonzaga.challengejava.controllers;

import com.gonzaga.challengejava.dtos.CheckinDTO;
import com.gonzaga.challengejava.dtos.RoomDTO;
import com.gonzaga.challengejava.infra.CPFConflictException;
import com.gonzaga.challengejava.infra.RoomNotFoundException;
import com.gonzaga.challengejava.infra.RoomNumberAlreadyExistsException;
import com.gonzaga.challengejava.infra.RoomOccupiedException;
import com.gonzaga.challengejava.models.RoomModel;
import com.gonzaga.challengejava.services.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<RoomModel>> getAllRooms(){
        return ResponseEntity.status(HttpStatus.FOUND).body(roomService.getAllRooms());
    }

    @GetMapping("/available")
    public ResponseEntity<List<RoomModel>> getAllUnoccupiedRooms(){
        return ResponseEntity.status(HttpStatus.FOUND).body(roomService.getAllUnoccupiedRooms());
    }

    @GetMapping("/occupied")
    public ResponseEntity<List<RoomModel>> getAllOccupiedRooms(){
        return ResponseEntity.status(HttpStatus.FOUND).body(roomService.getAllOccupiedRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRoomById(@PathVariable UUID id){
        try {
            RoomModel room = roomService.getRoomById(id);
            return ResponseEntity.status(HttpStatus.FOUND).body(room);
        } catch (RoomNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> createRoom(@RequestBody RoomDTO roomDTO){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(roomDTO));
        } catch (RoomNumberAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/check-in/{id}")
    public ResponseEntity<Object> checkIn(@PathVariable UUID id, @RequestBody CheckinDTO checkinDTO){
        try{
            RoomModel room = roomService.checkIn(id, checkinDTO);
            return ResponseEntity.status(HttpStatus.OK).body(room);
        } catch(RoomOccupiedException | CPFConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RoomNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/check-out/{id}")
    public ResponseEntity<Object> checkOut(@PathVariable UUID id){
        try {
            RoomModel room = roomService.checkOut(id);
            return ResponseEntity.status(HttpStatus.OK).body(room);
        } catch (RoomNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
