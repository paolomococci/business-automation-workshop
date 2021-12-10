package local.example.welcome.data.service;

import local.example.welcome.data.entity.Person;
import local.example.welcome.data.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService extends CrudService<Person, Integer> {

    private PersonRepository personRepository;

    public PersonService(@Autowired PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    protected PersonRepository abstractJpaRepository() {
        return personRepository;
    }
}
