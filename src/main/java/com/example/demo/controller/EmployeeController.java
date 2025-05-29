package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @PostMapping("/insert")
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        return repository.save(employee);
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee updated) {
        return repository.findById(id).map(employee -> {
            employee.setName(updated.getName());
            employee.setAge(updated.getAge());
            employee.setEmail(updated.getEmail());
            employee.setDepartment(updated.getDepartment());
            employee.setSalary(updated.getSalary());
            return repository.save(employee);
        }).orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        repository.delete(employee);
    }

    @GetMapping("/paymentSuccess")
    public String testEndpoint() {
        return "Payment Reeceived";
    }
}
