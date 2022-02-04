package com.shubbi.reactblogbackend.users;

import com.shubbi.reactblogbackend.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = this.userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/user")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        User savedUser = this.userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long userId){
        System.out.println(userId);
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with id "+userId+ " not found")
        );

        return ResponseEntity.ok(user);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long userId, @RequestBody User newUser){
        return this.userRepository.findById(userId)
                // Updates user properties and return updated user
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    user.setUsername(newUser.getUsername());
                    user.setPassword(newUser.getPassword());
                    User savedUser = this.userRepository.save(user);
                    return ResponseEntity.ok(savedUser);
                })
                // Saves newUser to db and returns it
                .orElseGet(() -> {
                    newUser.setId(userId);
                    User savedUser = this.userRepository.save(newUser);
                    return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
                });
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<User> removeUser(@PathVariable("id") Long userId){
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found "+ userId)
        );

        this.userRepository.delete(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user")
    ResponseEntity<User> findUser(@RequestParam(name="email", required = true) String email, @RequestParam(name="password", required = true) String password){
        return null;
    }
}
