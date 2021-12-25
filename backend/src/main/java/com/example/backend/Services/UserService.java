package com.example.backend.Services;

import com.example.backend.Beans.User;
import com.example.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService {
    @Autowired
    public final UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private FishingInstructorService fishingInstructorService;
    @Autowired
    private CottageOwnerService cottageOwnerService;
    @Autowired
    private BoatOwnerService boatOwnerService;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Collection<User> getAllNotEnabledUsers(){
        List<User> users = new ArrayList<>();
        for(User u : userRepository.findAll())
            if(!u.getEnabled() && !u.getRole().getName().equals("ROLE_CUSTOMER"))
                users.add(u);
        return users;
    }

    public User findUserById(long id){
        return userRepository.findById(id).orElse(null);
    }

    public User enableUser(long id) {
        User user = findUserById(id);

        switch (user.getRole().getName()) {
            case "ROLE_INSTRUCTOR" -> user = (User) fishingInstructorService.enableFishingInstructor(id);
            case "ROLE_COTTAGE_OWNER" -> user = (User) cottageOwnerService.enableCottageOwner(id);
            case "ROLE_BOAT_OWNER" -> user = (User) boatOwnerService.enableBoatOwner(id);
        }

        try {
            emailService.sendConfirmMailToUser(user);
        }catch (Exception e){ return null; }

        return user;
    }

    public boolean disableUser(long id, String reason){
        User user = findUserById(id);
        try {
            deleteUser(id);
            emailService.sendNegativeMailToUser(user, reason);
            return true;
        }catch (Exception e){ System.out.println(e.toString()); return false;}
    }

    public void deleteUser(long id){
        userRepository.deleteById(id);
    }
}
