package com.tpe.controller;

import com.tpe.domain.dto.LoginRequest;
import com.tpe.domain.dto.RegisterRequest;
import com.tpe.security.JwtUtils;
import com.tpe.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping
@AllArgsConstructor
public class UserJwtController {

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    // Not: ********* REGISTER **************
    @PostMapping("/register") // http://localhost:8080/register  + POST + JSON
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest request){
        userService.registerUser(request);

        String message = " User registered Successfully";

        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }


    // Not: ************ LOGIN  **************
    @PostMapping("/login") // http://localhost:8080/login  + POST + JSON
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request){
        //!!! kullanıcı username ve password bilgisi kontrol edilecek, bu kontrolu
            // AuthenticationManager yapiyordu.
          Authentication authentication =
                  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(),
                                                                                  request.getPassword()));
          // !!! JWT uretiliyor
        String token = jwtUtils.generateToken(authentication);

        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }
}
