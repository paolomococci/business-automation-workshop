package local.example.welcome.data.service;

import local.example.welcome.data.entity.User;
import local.example.welcome.data.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends CrudService<User, Integer> {

    private UserRepository userRepository;

    public UserService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected UserRepository abstractJpaRepository() {
        return userRepository;
    }
}
