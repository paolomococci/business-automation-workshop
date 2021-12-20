package local.example.demo.data.service;

import local.example.demo.data.entity.Customer;
import local.example.demo.data.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Optional<Customer> get(Integer id) {
        return customerRepository.findById(id);
    }

    public Customer update(Customer entity) {
        return customerRepository.save(entity);
    }

    public void delete(Integer id) {
        customerRepository.deleteById(id);
    }

    public List<Customer> list() {
        return customerRepository.findAll();
    }

    public Page<Customer> pageable(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public int count() {
        return (int) customerRepository.count();
    }
}
