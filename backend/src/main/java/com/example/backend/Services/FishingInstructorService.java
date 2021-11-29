package com.example.backend.Services;

import com.example.backend.Beans.Address;
import com.example.backend.Beans.FishingInstructor;
import com.example.backend.Dtos.UserRegistration;
import com.example.backend.Repository.FishingInstructorRepository;
import com.example.backend.Services.Interfaces.IFishingInstructorService;
import org.apache.tomcat.jni.Local;
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
    //TODO: DODATI SLANJE POTVRDNOG MEJLA
    public FishingInstructor enableFishingInstructor(long id){
        FishingInstructor instructor = findFishingInstructorById(id);
        instructor.setEnabled(true);
        return fishingInstructorRepository.save(instructor);
    }
    //TODO: DODATI SLANJE NEGATIVNOG MEJLA, SA OBRAZLOZENJEM DA PROBA PONOVO
    public void disableDisableFishingInstructor(long id){
        FishingInstructor instructor = findFishingInstructorById(id);
        fishingInstructorRepository.delete(instructor);
    }

    public void deleteFishingInstructor(long id){
        fishingInstructorRepository.deleteById(id);
    }

    //VALIDACIJA
    private FishingInstructor createFishingInstructor(UserRegistration user) {
        FishingInstructor instructor =  new FishingInstructor();
        instructor.setAddress(createAddress(user));
        instructor.setEmail(user.getEmail());
        instructor.setFirstname(user.getFirstname());
        instructor.setLastName(user.getLastname());
        instructor.setPhone(user.getPhone());
        instructor.setRole(roleService.findByName("FISHING_INSTRUCTOR_ROLE"));
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
