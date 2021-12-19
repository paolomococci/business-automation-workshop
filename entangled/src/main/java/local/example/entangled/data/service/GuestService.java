package local.example.entangled.data.service;

import java.util.List;
import java.util.Optional;
import local.example.entangled.data.entity.Guest;
import local.example.entangled.data.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GuestService {

    private GuestRepository guestRepository;

    public GuestService(@Autowired GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

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
        return guestRepository.findAll();
    }

    public Page<Guest> pageable(Pageable pageable) {
        return guestRepository.findAll(pageable);
    }

    public int count() {
        return (int) guestRepository.count();
    }
}