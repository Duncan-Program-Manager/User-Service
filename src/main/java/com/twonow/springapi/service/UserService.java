package com.twonow.springapi.service;

import com.twonow.springapi.datamodel.User;
import com.twonow.springapi.dto.UserDTO;
import com.twonow.springapi.rabbitmq.RabbitMQSender;
import com.twonow.springapi.repository.UserRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.json.simple.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RabbitMQSender rabbitMQSender;

    public User getUser(UUID id) throws AccountNotFoundException {
        if(userRepository.findById(id).isPresent())
        {
            return userRepository.findById(id).get();
        }
        throw new AccountNotFoundException("Account not found");
    }

    public void UpdateUser(UserDTO dto) throws AccountNotFoundException {
        if(userRepository.findById(dto.getId()).isPresent()) {
            User user = userRepository.findById(dto.getId()).get();
            if(!user.getUsername().equals(dto.getUsername()) || !user.getEmail().equals(dto.getEmail()))
            {
                JSONObject fullJson = new JSONObject();
                JSONObject userInfo = new JSONObject();
                fullJson.put("method", "Update User");
                userInfo.put("oldUsername", user.getUsername());
                userInfo.put("newUsername", dto.getUsername());
                userInfo.put("oldEmail", user.getEmail());
                userInfo.put("newEmail", dto.getEmail());
                fullJson.put("data", userInfo);
                rabbitMQSender.send(new Message(fullJson.toJSONString().getBytes(StandardCharsets.UTF_8)));
            }
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
        JSONObject fullJson = new JSONObject();
        JSONObject userInfo = new JSONObject();
        userInfo.put("username", dto.getUsername());
        fullJson.put("method", "New User");
        fullJson.put("data", userInfo);
        rabbitMQSender.send(new Message(fullJson.toJSONString().getBytes(StandardCharsets.UTF_8)));
    }

    public void DeleteUser(UserDTO dto)
    {
        JSONObject fulljson = new JSONObject();
        JSONObject userInfo = new JSONObject();
        fulljson.put("method", "Delete User");
        userInfo.put("username", dto.getUsername());
        fulljson.put("data", userInfo);
        rabbitMQSender.send(new Message(fulljson.toJSONString().getBytes(StandardCharsets.UTF_8)));
        userRepository.deleteById(dto.getId());
    }

    public void testCall()
    {
        System.out.println("SERVICE CONNECTION WORKS");
    }
}
