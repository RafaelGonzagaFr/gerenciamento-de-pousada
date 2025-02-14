package com.gonzaga.challengejava.repositories;

import com.gonzaga.challengejava.models.GuestModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GuestRepository extends JpaRepository<GuestModel, UUID> {
    GuestModel findGuestByCPF(String CPF);
}
