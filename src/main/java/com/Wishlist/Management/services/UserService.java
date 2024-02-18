package com.Wishlist.Management.services;

import com.Wishlist.Management.models.User;
import com.Wishlist.Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String addUser(String username, String password) {
        User newUser = new User();
        User user=findByName(username);
        if(user!=null) {
            return  "user already exists";
             }
        newUser.setUsername(username);
        newUser.setPassword("{noop}"+password);
        newUser.setEnabled(true);
        newUser.setRole("USER");
        userRepository.save(newUser);
        return "user added successfully";

    }

    public User findByName(String name) {
        Optional<User> optionalUser = userRepository.findByName(name);
        User user = new User();
        if(optionalUser!=null && optionalUser.isPresent()){
            user = optionalUser.get();
            return user;
        }
        else{
            return null;
        }
    }
}
