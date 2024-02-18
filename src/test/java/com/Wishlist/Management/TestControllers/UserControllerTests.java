package com.Wishlist.Management.TestControllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import com.Wishlist.Management.controllers.UserController;
import com.Wishlist.Management.models.User;
import com.Wishlist.Management.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserControllerTests {

    @MockBean
    private UserService userService;
    @Autowired
    private UserController userController;
    @Test
    public void testAddUser() {
       User user=new User();
       user.setUsername("vamsi");
       user.setPassword("password1");
       user.setRole("USER");
       user.setEnabled(true);
       when(userService.addUser("vamsi","password1")).thenReturn("user added successfully");
        ResponseEntity<String> result = userController.addUser("vamsi", "password1");
        verify(userService,times(1)).addUser("vamsi","password1");
        Assertions.assertNotNull(result, "Result object should not be null");
        Assertions.assertEquals("vamsi", user.getUsername(), "Expected name was not return");
        Assertions.assertEquals("vamsi", user.getUsername(), "Expected price was not return");

    }


}
