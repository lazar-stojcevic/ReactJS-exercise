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

    public User enableUser(long id) {
        User user = userRepository.findById(id).orElse(null);
        assert user != null;

        //TODO: ISPOD DODATI I ZA OSTALE ROLE
        if(user.getRole().getName().equals("ROLE_INSTRUCTOR"))
            user = (User) fishingInstructorService.enableFishingInstructor(id);
        try {
            emailService.sendConfirmMailToInstructor(user);
        }catch (Exception e){ return null; }

        return user;
    }

    public boolean disableUser(long id){
        User user = userRepository.findById(id).orElse(null);
        assert user != null;

        //TODO: ISPOD DODATI I OSTALE ROLE
        if(user.getRole().getName().equals("ROLE_INSTRUCTOR"))
            fishingInstructorService.deleteFishingInstructor(id);

        try {
            emailService.sendNegativeMailToInstructor(user);
            return true;
        }catch (Exception e){ System.out.println(e.toString()); return false;}
    }
}
