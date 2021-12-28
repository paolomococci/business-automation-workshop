package local.example.demo.data.repository;

import local.example.demo.data.entity.Address;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
