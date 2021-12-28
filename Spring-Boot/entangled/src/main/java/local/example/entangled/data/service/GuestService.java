package local.example.entangled.data.service;

import local.example.entangled.data.entity.Guest;
import local.example.entangled.data.repository.GuestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class GuestService {

    @Autowired
    GuestRepository guestRepository;

    public Optional<Guest> get(Integer id) {
        return guestRepository.findById(id);
    }

    public Guest update(Guest entity) {
        return guestRepository.save(entity);
    }

    public void delete(Integer id) {
        guestRepository.deleteById(id);
    }

    public List<Guest> list() {
        if (guestRepository.findAll().isEmpty()) {
            return Collections.emptyList();
        }
        return guestRepository.findAll();
    }

    public Stream<Guest> stream() {
        try {
            return guestRepository.findAll().stream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Stream.empty();
    }

    public Page<Guest> pageable(Pageable pageable) {
        return guestRepository.findAll(pageable);
    }

    public int count() {
        return (int) guestRepository.count();
    }
}
