package com.twonow.springapi.service;

import com.twonow.springapi.datamodel.User;
import com.twonow.springapi.dto.UserDTO;
import com.twonow.springapi.repository.UserRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUser(UUID id) throws AccountNotFoundException {
        if(userRepository.findById(id).isPresent())
        {
            return userRepository.findById(id).get();
        }
        throw new AccountNotFoundException("Account not found");
    }

    public void UpdateUser(UserDTO dto) throws AccountNotFoundException {
        if(userRepository.findById(dto.getId()).isPresent()) {
            userRepository.deleteById(dto.getId());
            User user = new User();
            user.setUsername(dto.getUsername());
            user.setEmail(dto.getEmail());
            user.setId(dto.getId());
            userRepository.save(user);
        }
        throw new AccountNotFoundException("account not registered");
    }

    public void CreateUser(UserDTO dto)
    {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setId(dto.getId());
        userRepository.save(user);
    }

    public void testCall()
    {
        System.out.println("SERVICE CONNECTION WORKS");
    }
}
