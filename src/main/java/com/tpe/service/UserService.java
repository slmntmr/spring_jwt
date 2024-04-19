package com.tpe.service;

import com.tpe.domain.Role;
import com.tpe.domain.User;
import com.tpe.domain.dto.RegisterRequest;
import com.tpe.domain.enums.UserRole;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.RoleRepository;
import com.tpe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jca.cci.CciOperationNotSupportedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void registerUser(RegisterRequest request) {
        //!!! kayıt formunda girilen username unique mi ?
        if(userRepository.existsByUserName(request.getUserName())) {
            throw new ConflictException("UserName is already used");
        }
        //!!! register da otomatik olarak role kısmına Student ekliyorum
        Role role = roleRepository.findByName(UserRole.ROLE_STUDENT).orElseThrow(()->
                new ResourceNotFoundException("Role not found"));

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = new User();
        user.setUserName(request.getUserName());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRoles(roles);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);


    }
}
