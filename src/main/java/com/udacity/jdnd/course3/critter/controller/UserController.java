package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.mapper.UserMapper;
import com.udacity.jdnd.course3.critter.domain.Customer;
import com.udacity.jdnd.course3.critter.domain.Employee;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class.getSimpleName());

    private final CustomerService customerService;
    private final PetService petService;
    private final EmployeeService employeeService;
    private final UserMapper userMapper;

    public UserController(CustomerService customerService, PetService petService, EmployeeService employeeService, UserMapper userMapper) {
        this.customerService = customerService;
        this.petService = petService;
        this.employeeService = employeeService;
        this.userMapper = userMapper;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        logger.info("request to save a new customer");
        Customer customer = userMapper.mapCustomerDTOToCustomer(customerDTO);
        Customer newCustomer = customerService.saveCustomer(customer);
        return userMapper.mapCustomerToCustomerDTO(newCustomer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        logger.info("request to get all customers");
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        List<Customer> customers = customerService.getAllCustomers();
        customers.forEach(customer -> customerDTOS.add(userMapper.mapCustomerToCustomerDTO(customer)));
        return customerDTOS;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        Customer customer = petService.getOwnerByPet(petId);
        return userMapper.mapCustomerToCustomerDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        logger.info("request to save a new Employee");
        Employee newEmployee = employeeService.save(userMapper.mapEmployeeDTOToEmployee(employeeDTO));
        return userMapper.mapEmployeeToEmployeeDTO(newEmployee);
    }


    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        logger.info("request to get employee by id");
        Employee employee = employeeService.getEmployee(employeeId);
        return userMapper.mapEmployeeToEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        logger.info("request to set availability for employee");
        employeeService.setAvailability(userMapper.mapDayOfWeekToEmployeeAvailability(daysAvailable), employeeId);
    }


    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        logger.info("request to find employees for service");
        List<Employee> employeesForService = employeeService.findEmployeesForService(employeeDTO.getSkills(), employeeDTO.getDate().getDayOfWeek());
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        employeesForService.forEach(emp -> employeeDTOS.add(userMapper.mapEmployeeToEmployeeDTO(emp)));
        return employeeDTOS;
    }


}
