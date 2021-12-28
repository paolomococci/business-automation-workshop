package local.example.entangled.data.repository;

import local.example.entangled.data.entity.Address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository
        extends JpaRepository<Address, Integer> {
}