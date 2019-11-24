package com.telekocsi.controller;

import com.telekocsi.exception.ResourceNotFoundException;
import com.telekocsi.model.User;
import com.telekocsi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/users")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/{id}")
  public ResponseEntity<User> getUsersById(@PathVariable(value = "id") Long userId)
      throws ResourceNotFoundException {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with ID:  " + userId));
    return ResponseEntity.ok().body(user);
  }

  @PutMapping("/credentials/{id}")
  public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId, @RequestBody User userDetails) throws ResourceNotFoundException {
    User user = userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
    user.setEmail(userDetails.getEmail());
    user.setLastname(userDetails.getLastname());
    user.setFirstname(userDetails.getFirstname());
    final User updatedUser = userRepository.save(user);
    return ResponseEntity.ok(updatedUser);
  }

  @PutMapping("/password/{id}")
  public ResponseEntity<User> updateUserPassword(@PathVariable(value = "id") Long userId, @RequestBody String password) throws ResourceNotFoundException {
    User user = userRepository
        .findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
    user.setPassword(password);
    final User updatedUser = userRepository.save(user);
    return ResponseEntity.ok(updatedUser);
  }
}
