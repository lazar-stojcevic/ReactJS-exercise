package com.example.backend.Services;

import com.example.backend.Beans.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserService {

    public Collection<User> getAllUsers(){
        User user1 = new User("1", "Pera", "pp@gmail.com");
        User user2 = new User();
        user2.setId("2");
        user2.setName("Mika");
        user2.setEmail("mm@gmail.com");
        ArrayList<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        return users;
    }
}
