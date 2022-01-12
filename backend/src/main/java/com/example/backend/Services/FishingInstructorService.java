package com.example.backend.Services;

import com.example.backend.Beans.Address;
import com.example.backend.Beans.Customer;
import com.example.backend.Beans.FishingInstructor;
import com.example.backend.Dtos.*;
import com.example.backend.Repository.FishingInstructorRepository;
import com.example.backend.Services.Interfaces.IFishingInstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Service
public class FishingInstructorService implements IFishingInstructorService {

    @Autowired
    private final FishingInstructorRepository fishingInstructorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CustomerService customerService;

    public FishingInstructorService(FishingInstructorRepository fishingInstructorRepository) {
        this.fishingInstructorRepository = fishingInstructorRepository;
    }

    public Collection<FishingInstructor> getAllFishingInstructors(){
        return fishingInstructorRepository.findAll();
    }

    public FishingInstructor saveFishingInstructor(UserRegistration user) throws Exception{
        FishingInstructor instructor = createFishingInstructor(user);
        return fishingInstructorRepository.save(instructor);
    }

    public FishingInstructor findFishingInstructorById(long id){
        Optional<FishingInstructor> instructor = fishingInstructorRepository.findById(id);
        return instructor.orElse(null);
    }

    public FishingInstructor enableFishingInstructor(long id) {
        FishingInstructor instructor = findFishingInstructorById(id);
        instructor.setEnabled(true);
        return fishingInstructorRepository.save(instructor);
    }

    public void deleteFishingInstructor(long id){
        fishingInstructorRepository.deleteById(id);
    }

    public FishingInstructor addHolidayToFishingInstructor(long id, AvailableTimespanDto holidayDto){
        FishingInstructor instructor = findFishingInstructorById(id);
        instructor.getAvailable().setFromDate(holidayDto.getFromDate());
        instructor.getAvailable().setToDate(holidayDto.getToDate());
        return fishingInstructorRepository.save(instructor);
    }

    public FishingInstructor updateFishingInstructor(FishingInstructorChangeDto changeDto){
        FishingInstructor instructor = findFishingInstructorById(changeDto.getId());
        instructor.setFirstname(changeDto.getFirstname());
        instructor.setLastName(changeDto.getLastName());
        instructor.setPhone(changeDto.getPhone());
        instructor.getAddress().setStreet(changeDto.getStreet());
        instructor.getAddress().setCity(changeDto.getCity());
        instructor.getAddress().setCountry(changeDto.getCountry());
        return fishingInstructorRepository.save(instructor);
    }

    public void changePasswordToFishingInstructor(PasswordChangeDto passwordChangeDto){
        FishingInstructor instructor = findFishingInstructorById(passwordChangeDto.getUserId());
        instructor.setPassword(passwordEncoder.encode(passwordChangeDto.getPassword()));
        instructor.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));
        fishingInstructorRepository.save(instructor);
    }

    public boolean isUserSubcribed(long instructorId, long userId){
        FishingInstructor instructor = findFishingInstructorById(instructorId);
        Customer customer = customerService.findCustomerById(userId);

        return instructor.getPrepaidCustomers().contains(customer);
    }

    public void newSubscription(NewSubcriptionDto newSubcription){
        if(isUserSubcribed(newSubcription.getAdvertiserId(), newSubcription.getUserId())){
            return;
        }

        FishingInstructor instructor = findFishingInstructorById(newSubcription.getAdvertiserId());
        Customer customer = customerService.findCustomerById(newSubcription.getUserId());

        instructor.getPrepaidCustomers().add(customer);
        fishingInstructorRepository.save(instructor);
    }

    public void unsubscribe(NewSubcriptionDto toUnsubscribe){
        if(!isUserSubcribed(toUnsubscribe.getAdvertiserId(), toUnsubscribe.getUserId())){
            return;
        }

        FishingInstructor instructor = findFishingInstructorById(toUnsubscribe.getAdvertiserId());
        Customer customer = customerService.findCustomerById(toUnsubscribe.getUserId());

        instructor.getPrepaidCustomers().remove(customer);
        fishingInstructorRepository.save(instructor);
    }

    private FishingInstructor createFishingInstructor(UserRegistration user) {
        FishingInstructor instructor =  new FishingInstructor();
        instructor.setAddress(createAddress(user));
        instructor.setEmail(user.getEmail());
        instructor.setFirstname(user.getFirstname());
        instructor.setLastName(user.getLastname());
        instructor.setPhone(user.getPhone());
        instructor.setRole(roleService.findByName("ROLE_INSTRUCTOR"));
        instructor.setPassword(passwordEncoder.encode(user.getPassword()));
        instructor.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));
        return instructor;
    }

    private Address createAddress(UserRegistration user){
        Address address = new Address();
        address.setStreet(user.getStreet());
        address.setCity(user.getCity());
        address.setCountry(user.getCountry());
        return address;
    }
}
