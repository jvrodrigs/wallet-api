package com.example.wallet.testRepository;

import com.example.wallet.Model.User;
import com.example.wallet.Repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    private static final String emailTest = "user@test.com.br";

    @Autowired
    UserRepository repository;

    @Before
    public void setUp(){
        User user = new User();
        user.setName("setUp Function");
        user.setPassword("setup123");
        user.setEmail(emailTest);

        repository.save(user);
    }

    @After
    public void tearDown(){
        repository.deleteAll();
    }

    @Test
    public void testSaveUser(){
        User user = new User();
        user.setName("Test");
        user.setPassword("123456");
        user.setEmail("testeUser@test.com");

        User res = repository.save(user);

        assertNotNull(res);
    }

    @Test
    public void testFindByEmailEquals(){
        Optional<User> res = repository.findByEmailEquals(emailTest);

        assertTrue(res.isPresent());
        assertEquals(res.get().getEmail(), emailTest);
    }
}
