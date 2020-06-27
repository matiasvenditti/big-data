package com.bigdata.bigdata.controller;

import com.bigdata.bigdata.model.Employee;
import com.bigdata.bigdata.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public List<Employee> getEmployees()
    {
        Iterable<Employee> result = employeeRepository.findAll();
        List<Employee> employeesList = new ArrayList<Employee>();
        result.forEach(employeesList::add);
        return employeesList;
    }
    @GetMapping("/employee/{id}")
    public Optional<Employee> getEmployee(@PathVariable int id) {
        return employeeRepository.findById(id);
    }

    @PutMapping("/employee/{id}")
    public Optional<Employee> updateEmployee(@RequestBody Employee newEmployee, @PathVariable int id) {
        Optional<Employee> optionalEmp = employeeRepository.findById(id);
        if (optionalEmp.isPresent()) {
            Employee emp = optionalEmp.get();
            emp.setFirstName(newEmployee.getFirstName());
            emp.setLastName(newEmployee.getLastName());
            emp.setEmail(newEmployee.getEmail());
            employeeRepository.save(emp);
        }
        return optionalEmp;
    }

    @DeleteMapping(value = "/employee/{id}", produces = "application/json; charset=utf-8")
    public String deleteEmployee(@PathVariable int id) {
        boolean result = employeeRepository.existsById(id);
        employeeRepository.deleteById(id);
        return "{ \"success\" : "+ (result ? "true" : "false") +" }";
    }

    @PostMapping("/employee")
    public Employee addEmployee(@RequestBody Employee newEmployee)
    {
        int id = new Random().nextInt();
        Employee emp = new Employee(id, newEmployee.getFirstName(), newEmployee.getLastName(), newEmployee.getEmail());
        employeeRepository.save(emp);
        return emp;
    }
}
