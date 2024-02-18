package com.Wishlist.Management.TestRepository;

import com.Wishlist.Management.models.User;
import com.Wishlist.Management.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.Assert.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;
    @BeforeEach
    void setup() {
        User user=new User();
        user.setId(1l);
        user.setUsername("vamsi");
        user.setPassword("password1");
        user.setRole("USER");
        user.setEnabled(true);
        entityManager.merge(user);

    }
    @Test
    public void findByNameTest(){
        String username="vamsi";
        entityManager.clear();
        Optional<User> user=userRepository.findByName(username);
        Assertions.assertNotNull(user, "Result object should not be null");
        Assertions.assertEquals(true, user.isPresent(), "The result list should have at least one element");
    }

}
