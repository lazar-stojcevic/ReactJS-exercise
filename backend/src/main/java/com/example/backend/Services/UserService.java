package com.example.backend.Services;

import com.example.backend.Beans.DeleteProfileRequest;
import com.example.backend.Beans.User;
import com.example.backend.Dtos.AnswerOnRequestForDeletingDto;
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
    @Autowired
    private DeleteProfileRequestService deleteProfileRequestService;
    @Autowired
    private CustomerService customerService;

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
            case "ROLE_CUSTOMER" -> user = (User) customerService.enableCustomer(id);
        }

        try {
            emailService.sendConfirmMailToUser(user);
        }catch (Exception e){ return null; }

        return user;
    }

    public boolean disableUserRegistration(long id, String reason){
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

    public void answerOnRequestForDeleting(AnswerOnRequestForDeletingDto dto) {
        DeleteProfileRequest request = deleteProfileRequestService.findDeleteProfileRequestById(dto.getRequestId());
        deleteProfileRequestService.markDeleteProfileRequestAsReviewed(request);

        if(dto.isForDelete())
            deleteUser(request.getUser().getId());

        try { emailService.sendAnswerOnRequestForDeletingProfile(request.getUser(), dto.getAnswer());
        }catch (Exception e){ System.out.println(e);}
    }

    public Collection<User> getAllUsersExceptAdmins(){
        return userRepository.getAllUsersExceptAdmins();
    }

    public User disableUser(long id){
        User user = findUserById(id);
        user.setEnabled(false);
        try {
            emailService.sendMailForDisabling(user);
        }catch (Exception ignore) {}
        return save(user);
    }

    private User save(User user){
        return userRepository.save(user);
    }
}
