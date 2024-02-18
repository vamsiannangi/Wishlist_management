package com.Wishlist.Management.TestServices;

import com.Wishlist.Management.models.User;
import com.Wishlist.Management.repository.UserRepository;
import com.Wishlist.Management.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    @Test
    public void addUserTest(){
        User user=new User();
        user.setEnabled(true);
        user.setUsername("username1");
        user.setPassword("{noop}password1");
        user.setRole("USER");

       when(userRepository.findByName("username1")).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(user);

        String result= userService.addUser("username1","password1");

       verify(userRepository,times(1)).findByName("username1");
       verify(userRepository,times(1)).save(any(User.class));
        assertNotNull(result,"result should not be null");
        assertEquals("user added successfully",result,"result was incorrect");


    }

    @Test
    public void addUser_userAlreadyExistsTest(){
        User user=new User();
        user.setId(1l);
        user.setEnabled(true);
        user.setUsername("username1");
        user.setPassword("password1");

        when(userRepository.findByName("username1")).thenReturn(Optional.of(user));

        String result= userService.addUser("username1","password1");

        verify(userRepository,times(1)).findByName("username1");
        assertNotNull(result,"result should not be null");
        assertEquals("user already exists",result,"result was incorrect");

    }

}
