package local.example.entangled.data.repository;

import local.example.entangled.data.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
        extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}