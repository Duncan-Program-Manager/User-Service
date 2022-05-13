package com.twonow.springapi.controller;

import com.twonow.springapi.datamodel.User;
import com.twonow.springapi.dto.UserDTO;
import com.twonow.springapi.endpoint.UserEndpoints;
import com.twonow.springapi.repository.UserRepository;
import com.twonow.springapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

@RestController
@RequestMapping(value = UserEndpoints.BASE)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = UserEndpoints.GETUSER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser(@RequestParam String id)
    {
        try {
            return new ResponseEntity<>(userService.getUser(UUID.fromString(id)), HttpStatus.OK);
        } catch (AccountNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(value = UserEndpoints.UPDATEUSER, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody UserDTO dto)
    {
        if(userService.UpdateUser(dto))
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }


}
