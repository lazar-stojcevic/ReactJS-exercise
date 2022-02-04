package com.example.backend.service;

import com.example.backend.Beans.Role;
import com.example.backend.Beans.User;
import com.example.backend.Repository.UserRepository;
import com.example.backend.Services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private UserService userService;

    @Test
    public void getAllNotEnabledUsersTest(){
        when(userRepositoryMock.findAll()).thenReturn(List.of(makeUser(), makeUser1()));

        List<User> list = (List<User>) userService.getAllNotEnabledUsers();

        assertEquals(2, list.size());
    }

    private User makeUser(){
        User user = new User() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getUsername() {
                return null;
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        };
        Role role = new Role();
        role.setName("ROLE_BOAT_OWNER");
        user.setRole(role);
        user.setEnabled(false);
        return user;
    }

    private User makeUser1(){
        User user = new User() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getUsername() {
                return null;
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        };
        Role role = new Role();
        role.setName("ROLE_COTTAGE_OWNER");
        user.setRole(role);
        user.setEnabled(false);
        return user;
    }
}
