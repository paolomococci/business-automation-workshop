package local.example.demo.data.generator;

import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import local.example.demo.data.Role;
import local.example.demo.data.entity.Address;
import local.example.demo.data.entity.Book;
import local.example.demo.data.entity.Customer;
import local.example.demo.data.entity.User;
import local.example.demo.data.repository.AddressRepository;
import local.example.demo.data.repository.BookRepository;
import local.example.demo.data.repository.CustomerRepository;
import local.example.demo.data.repository.UserRepository;

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
        UserRepository userRepository, 
        CustomerRepository customerRepository, 
        AddressRepository addressRepository, 
        BookRepository bookRepository
    ) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (userRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");
            logger.info("Generating two User entities.");

            /* normal user */
            User user = new User();
            user.setName("John Doe");
            user.setUsername("johndoe");
            user.setHashedPassword(
                passwordEncoder.encode("johndoe")
            );
            user.setProfilePictureUrl(null);
            user.setRoles(Collections.singleton(Role.USER));
            userRepository.save(user);

            /* administrator user */
            User admin = new User();
            admin.setName("Amy Doe");
            admin.setUsername("amydoe");
            admin.setHashedPassword(
                passwordEncoder.encode("amydoe")
            );
            admin.setProfilePictureUrl(null);
            admin.setRoles(Stream.of(Role.USER, Role.ADMIN).collect(Collectors.toSet()));
            userRepository.save(admin);

            logger.info("Generation of one hundred customers fictitious entities, for example purposes only.");
            ExampleDataGenerator<Customer> customerRepositoryGenerator = new ExampleDataGenerator<>(
                Customer.class, 
                LocalDateTime.of(2021, 12, 14, 0, 0, 0)
            );
            customerRepositoryGenerator.setData(Customer::setId, DataType.ID);
            customerRepositoryGenerator.setData(Customer::setName, DataType.FIRST_NAME);
            customerRepositoryGenerator.setData(Customer::setSurname, DataType.LAST_NAME);
            customerRepositoryGenerator.setData(Customer::setEmail, DataType.EMAIL);
            customerRepositoryGenerator.setData(Customer::setPhone, DataType.PHONE_NUMBER);
            customerRepositoryGenerator.setData(Customer::setBirthday, DataType.DATE_OF_BIRTH);
            customerRepositoryGenerator.setData(Customer::setOccupation, DataType.OCCUPATION);
            customerRepository.saveAll(customerRepositoryGenerator.create(100, seed));

            logger.info("Generation of one hundred addresses fictitious entities, for example purposes only.");
            ExampleDataGenerator<Address> addressRepositoryGenerator = new ExampleDataGenerator<>(
                Address.class, 
                LocalDateTime.of(2021, 12, 14, 0, 0, 0)
            );
            addressRepositoryGenerator.setData(Address::setId, DataType.ID);
            addressRepositoryGenerator.setData(Address::setStreet, DataType.ADDRESS);
            addressRepositoryGenerator.setData(Address::setPostalCode, DataType.ZIP_CODE);
            addressRepositoryGenerator.setData(Address::setCity, DataType.CITY);
            addressRepositoryGenerator.setData(Address::setState, DataType.STATE);
            addressRepositoryGenerator.setData(Address::setCountry, DataType.COUNTRY);
            addressRepository.saveAll(addressRepositoryGenerator.create(100, seed));

            logger.info("Generation of one hundred books fictitious entities, for example purposes only.");
            ExampleDataGenerator<Book> bookRepositoryGenerator = new ExampleDataGenerator<>(
                Book.class, 
                LocalDateTime.of(2021, 12, 14, 0, 0, 0)
            );
            bookRepositoryGenerator.setData(Book::setId, DataType.ID);
            bookRepositoryGenerator.setData(Book::setTitle, DataType.BOOK_TITLE);
            bookRepositoryGenerator.setData(Book::setAuthor, DataType.FULL_NAME);
            bookRepositoryGenerator.setData(Book::setPublication, DataType.DATE_OF_BIRTH);
            bookRepositoryGenerator.setData(Book::setPages, DataType.NUMBER_UP_TO_1000);
            bookRepositoryGenerator.setData(Book::setIsbn, DataType.EAN13);
            bookRepository.saveAll(bookRepositoryGenerator.create(100, seed));

            logger.info("Generated demo data!");
        };
    }
}
