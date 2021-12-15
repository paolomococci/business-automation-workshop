package local.example.demo.data.service;

import java.util.Optional;
import local.example.demo.data.entity.Address;
import local.example.demo.data.repository.AddressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private AddressRepository addressRepository;

    public AddressService(@Autowired AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Optional<Address> get(Integer id) {
        return addressRepository.findById(id);
    }

    public Address update(Address entity) {
        return addressRepository.save(entity);
    }

    public void delete(Integer id) {
        addressRepository.deleteById(id);
    }

    public Page<Address> list(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }

    public int count() {
        return (int) addressRepository.count();
    }
}