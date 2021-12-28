package local.example.entangled.data.service;

import java.util.List;
import java.util.Optional;
import local.example.entangled.data.entity.Employee;
import local.example.entangled.data.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(@Autowired EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Optional<Employee> get(Integer id) {
        return employeeRepository.findById(id);
    }

    public Employee update(Employee entity) {
        return employeeRepository.save(entity);
    }

    public void delete(Integer id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> list() {
        return employeeRepository.findAll();
    }

    public Page<Employee> pageable(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public int count() {
        return (int) employeeRepository.count();
    }

}
