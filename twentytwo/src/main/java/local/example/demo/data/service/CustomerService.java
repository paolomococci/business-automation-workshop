package local.example.demo.data.service;

import java.util.Optional;
import local.example.demo.data.entity.Customer;
import local.example.demo.data.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(@Autowired CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> get(Integer id) {
        return customerRepository.findById(id);
    }

    public Customer update(Customer entity) {
        return customerRepository.save(entity);
    }

    public void delete(Integer id) {
        customerRepository.deleteById(id);
    }

    public Page<Customer> list(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public int count() {
        return (int) customerRepository.count();
    }
}
