package com.gonzaga.challengejava.repositories;

import com.gonzaga.challengejava.models.GuestModel;
import com.gonzaga.challengejava.models.RoomModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<RoomModel, UUID> {

    RoomModel findByRoomNumber(Number roomNumber);

    @Query(value = "SELECT * FROM tb_rooms WHERE occupied = true", nativeQuery = true)
    List<RoomModel> findOccupiedRooms();

    @Query(value = "SELECT * FROM tb_rooms WHERE occupied = false", nativeQuery = true)
    List<RoomModel> findUnoccupiedRooms();
}
