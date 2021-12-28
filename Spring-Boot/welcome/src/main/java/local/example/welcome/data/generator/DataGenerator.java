package local.example.welcome.data.generator;

import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import local.example.welcome.data.Role;
import local.example.welcome.data.entity.Person;
import local.example.welcome.data.entity.User;
import local.example.welcome.data.repository.PersonRepository;
import local.example.welcome.data.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(
            PasswordEncoder passwordEncoder,
            PersonRepository personRepository,
            UserRepository userRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (personRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");

            logger.info("Fifty fictitious names generated for example only!");
            ExampleDataGenerator<Person> personRepositoryGenerator = new ExampleDataGenerator<>(
                    Person.class, LocalDateTime.of(2021, 12, 10, 0, 0, 0));
            personRepositoryGenerator
                    .setData(Person::setId, DataType.ID);
            personRepositoryGenerator
                    .setData(Person::setFirstName, DataType.FIRST_NAME);
            personRepositoryGenerator
                    .setData(Person::setLastName, DataType.LAST_NAME);
            personRepositoryGenerator
                    .setData(Person::setEmail, DataType.EMAIL);
            personRepositoryGenerator
                    .setData(Person::setPhone, DataType.PHONE_NUMBER);
            personRepositoryGenerator
                    .setData(Person::setDateOfBirth, DataType.DATE_OF_BIRTH);
            personRepositoryGenerator
                    .setData(Person::setOccupation, DataType.OCCUPATION);
            personRepositoryGenerator
                    .setData(Person::setImportant, DataType.BOOLEAN_10_90);

            personRepository.saveAll(personRepositoryGenerator.create(50, seed));

            logger.info("Generating two User entitiesfor illustrative purposes only.");
            User user = new User();
            user.setName("John Guest");
            user.setUsername("johnguest");
            user.setHashedPassword(passwordEncoder.encode("guest"));
            user.setRoles(Collections.singleton(Role.USER));
            userRepository.save(user);
            User admin = new User();
            admin.setName("John Power");
            admin.setUsername("johnpower");
            admin.setHashedPassword(passwordEncoder.encode("power"));
            admin.setRoles(
                    Stream.of(Role.USER, Role.ADMIN)
                            .collect(Collectors.toSet()));
            userRepository.save(admin);

            logger.info("Generated demo data!");
        };
    }
}
