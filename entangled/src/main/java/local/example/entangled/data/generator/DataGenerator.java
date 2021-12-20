package local.example.entangled.data.generator;

import com.vaadin.flow.spring.annotation.SpringComponent;
import local.example.entangled.data.Role;
import local.example.entangled.data.entity.User;
import local.example.entangled.data.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(
            PasswordEncoder passwordEncoder,
            UserRepository userRepository
    ) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (userRepository.count() != 0L) {
                logger.info("Using existing database.");
                return;
            }

            logger.info("Generating demo users");

            logger.info("Generating two User entities.");
            User user = new User();
            user.setName("John Under");
            user.setUsername("johnunder");
            user.setHashedPassword(passwordEncoder.encode("johnunder"));
            user.setRoles(Collections.singleton(Role.USER));
            userRepository.save(user);
            
            User admin = new User();
            admin.setName("Amy Boss");
            admin.setUsername("amyboss");
            admin.setHashedPassword(passwordEncoder.encode("amyboss"));
            admin.setRoles(Stream.of(Role.USER, Role.ADMIN).collect(Collectors.toSet()));
            userRepository.save(admin);

            logger.info("Generated demo users");
        };
    }
}
