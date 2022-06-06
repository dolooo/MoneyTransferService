package com.crustlab.moneytransfer.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void addNewUser(User user) {
        Optional<User> userOptional = userRepository.findUserById(user.getUserId());
        if(userOptional.isPresent()) {
            throw new IllegalStateException("id already exists");
        }
        userRepository.save(user);
        System.out.println(user);
    }

    public void deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if(!exists) {
            throw new IllegalStateException("student with id " + userId + " doesn't exist");
        }
        userRepository.deleteById(userId);
    }

}
