package com.gonzaga.challengejava.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_rooms")
public class RoomModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private Number roomNumber;

    private boolean occupied;

    @OneToOne(mappedBy = "room", cascade = CascadeType.ALL)
    private GuestModel guest;

    public RoomModel(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Number getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Number roomNumber) {
        this.roomNumber = roomNumber;
    }

    public boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public GuestModel getGuest() {
        return guest;
    }

    public void setGuest(GuestModel guest) {
        this.guest = guest;
    }
}
