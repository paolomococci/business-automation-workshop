package local.example.entangled.data.repository;

import local.example.entangled.data.entity.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository
        extends JpaRepository<Employee, Integer> {
}