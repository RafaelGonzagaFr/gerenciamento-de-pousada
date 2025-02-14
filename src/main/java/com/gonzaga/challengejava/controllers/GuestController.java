package com.gonzaga.challengejava.controllers;

import com.gonzaga.challengejava.infra.GuestNotFoundException;
import com.gonzaga.challengejava.models.GuestModel;
import com.gonzaga.challengejava.services.GuestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/guests")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    public ResponseEntity<List<GuestModel>> getGuest(){
        return ResponseEntity.status(HttpStatus.FOUND).body(guestService.getGuests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getGuest(@PathVariable UUID id){
        try {
            GuestModel guest = guestService.getGuest(id);
            return ResponseEntity.status(HttpStatus.FOUND).body(guest);
        } catch (GuestNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
