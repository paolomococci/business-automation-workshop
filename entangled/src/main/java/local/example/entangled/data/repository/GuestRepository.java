package local.example.entangled.data.repository;

import local.example.entangled.data.entity.Guest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository
        extends JpaRepository<Guest, Integer> {
}
