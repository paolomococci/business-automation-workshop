package local.example.entangled.data.service;

import local.example.entangled.data.entity.Address;
import local.example.entangled.data.repository.AddressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public Optional<Address> get(Integer id) {
        return addressRepository.findById(id);
    }

    public Address update(Address entity) {
        return addressRepository.save(entity);
    }

    public void delete(Integer id) {
        addressRepository.deleteById(id);
    }

    public List<Address> list() {
        if (addressRepository.findAll().isEmpty()) {
            return Collections.emptyList();
        }
        return addressRepository.findAll();
    }

    public Stream<Address> stream() {
        try {
            return addressRepository.findAll().stream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Stream.empty();
    }

    public Page<Address> pageable(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }

    public int count() {
        return (int) addressRepository.count();
    }
}
