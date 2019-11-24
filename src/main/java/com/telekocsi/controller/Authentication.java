package com.telekocsi.controller;

import com.telekocsi.exception.ResourceNotFoundException;
import com.telekocsi.model.User;
import com.telekocsi.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://telekocsi-frontendv2.herokuapp.com")
@RequestMapping("auth")
public class Authentication {

    @Autowired private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<User> isAuthenticated(@RequestBody User user) throws Exception {
      Optional<User> u = userRepository.findByEmail(user.getEmail());
      if(!u.isPresent()) throw new Exception("rossz user");
      User us = u.get();
      if(!us.getPassword().equals(user.getPassword())) throw new Exception("rossz jelszo");

      return ResponseEntity.ok(us);
    }

    @PostMapping("/register")
    public User createUser(@RequestBody User user) {
      return userRepository.save(user);
    }
}
