package com.example.backend.Services;

import com.example.backend.Beans.*;
import com.example.backend.Dtos.CustomerChangeDto;
import com.example.backend.Dtos.DeleteUserRequestDto;
import com.example.backend.Dtos.PasswordChangeDto;
import com.example.backend.Dtos.UserRegistration;
import com.example.backend.Repository.AddressRepository;
import com.example.backend.Repository.CustomerRepository;
import com.example.backend.Repository.DeleteProfileRequestRepository;
import com.example.backend.Services.Interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService implements IUserService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DeleteProfileRequestRepository deleteProfileRequestRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private EmailService emailService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.getById(id);
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findCustomerById(long id){
        Optional<Customer> instructor = customerRepository.findById(id);
        return instructor.orElse(null);
    }

    public Collection<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    @Override
    public Customer save(UserRegistration userRegistration) {
        Customer u = new Customer();

        u.setEmail(userRegistration.getEmail());
        u.setPassword(passwordEncoder.encode(userRegistration.getPassword()));

        u.setFirstname(userRegistration.getFirstname());
        u.setLastName(userRegistration.getLastname());
        u.setEnabled(false); //Za sad je true dok se ne doda slanje na mejl
        u.setEmail(userRegistration.getEmail());
        u.setPhone(userRegistration.getPhone());
        u.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));
        Address address = new Address();
            address.setCity(userRegistration.getCity());
            address.setStreet(userRegistration.getStreet());
            address.setCountry(userRegistration.getCountry());

        addressRepository.save(address);
        u.setAddress(address);
        u.setPenaltyPoints(0);
        u.setPoints(0);

        // u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
        Role roles = roleService.findByName("ROLE_CUSTOMER");
        u.setRole(roles);

        return this.customerRepository.save(u);
    }

    public void verifyUser(Customer customer){
        customer.setEnabled(true);
        this.customerRepository.save(customer);
    }

    public Customer updateCustomer(CustomerChangeDto changeDto){
        Customer customer = findCustomerById(changeDto.getId());
        customer.setFirstname(changeDto.getFirstname());
        customer.setLastName(changeDto.getLastName());
        customer.setPhone(changeDto.getPhone());
        customer.getAddress().setStreet(changeDto.getStreet());
        customer.getAddress().setCity(changeDto.getCity());
        customer.getAddress().setCountry(changeDto.getCountry());
        return customerRepository.save(customer);
    }

    public void changePasswordToCustomer(PasswordChangeDto passwordChangeDto){
        Customer customer = findCustomerById(passwordChangeDto.getUserId());
        customer.setPassword(passwordEncoder.encode(passwordChangeDto.getPassword()));
        customer.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));
        customerRepository.save(customer);
    }

    public boolean isPasswordMatching(long id, String password) {
        Customer customer = findCustomerById(id);
        String pass = passwordEncoder.encode(password);
        String pass1 = customer.getPassword();
        return Objects.equals(pass1, pass);
    }

    public int getCustomerPenaltyPoints(long id){
        Customer customer = findCustomerById(id);
        return customer.getPenaltyPoints();
    }

    public boolean sendDeleteProfileRequest(DeleteUserRequestDto deleteUserRequestDto){
        DeleteProfileRequest saveVal = getDeleteProfileRequest(deleteUserRequestDto);
        try {
            deleteProfileRequestRepository.save(saveVal);
        }
        catch(Exception e){
            return false;
        }
        return true;
    }

    public void deleteCustomer(long id){
        customerRepository.deleteById(id);
    }

    public List<FishingInstructor> getCustomerInstructorsSubscription(long userId){
        return findCustomerById(userId).getPrepaidInstructors();
    }

    public List<Cottage>getCustomerCottagesSubscription(long userId){
        return findCustomerById(userId).getPrepaidCottages();
    }

    public List<Boat>getCustomerBoatSubscription(long userId){
        return findCustomerById(userId).getPrepaidBoats();
    }

    private DeleteProfileRequest getDeleteProfileRequest(DeleteUserRequestDto deleteUserRequestDto) {
        DeleteProfileRequest saveVal = new DeleteProfileRequest();
        saveVal.setRequestText(deleteUserRequestDto.getRequestText());
        saveVal.setUser(findById(deleteUserRequestDto.getId()));
        saveVal.setReviewed(false);
        return saveVal;
    }

     public Customer addPenaltyPointToCustomer(long id) {
        Customer customer = findCustomerById(id);
        customer.setPenaltyPoints(customer.getPenaltyPoints() + 1);
        return saveCustomer(customer);
    }

    private Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public User enableCustomer(long id) {
        Customer customer = findCustomerById(id);
        customer.setEnabled(true);
        return saveCustomer(customer);
    }
}
