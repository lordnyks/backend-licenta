package com.monitoring.documents.controllers;

import com.monitoring.documents.model.ERole;
import com.monitoring.documents.model.UserEntity;
import com.monitoring.documents.repository.UserRepository;
import com.monitoring.documents.security.jwt.JwtUtils;
import com.monitoring.documents.security.payload.request.LoginRequest;
import com.monitoring.documents.security.payload.request.SignupRequest;
import com.monitoring.documents.security.payload.response.JwtResponse;
import com.monitoring.documents.security.payload.response.MessageResponse;
import com.monitoring.documents.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest request) {
        Authentication authentication;

        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();

        List<String> role = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwtUtils.generateJwtToken(authentication), user.getUsername(), role));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest request) {
        if(userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Eroare: Numele de utilizator este existent"));
        }


        if(userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Eroare: Email-ul este deja existent"));
        }

        UserEntity user = new UserEntity(request.getFirstName(),request.getLastName(), request.getDateOfBirth(), request.getEmail(), request.getUsername(),
                passwordEncoder.encode(request.getPassword()), request.getGender(), request.getPhoneNumber());

        user.setRole(ERole.ROLE_MEMBER);
        user.setEnabled(true);

        userService.save(user);

        return ResponseEntity.ok(new MessageResponse("User-ul a fost creat cu succes!"));
    }


}
