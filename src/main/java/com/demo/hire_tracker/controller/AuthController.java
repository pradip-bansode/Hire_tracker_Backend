package com.demo.hire_tracker.controller;

import com.demo.hire_tracker.entity.User;
import com.demo.hire_tracker.repository.UserRepository;
import com.demo.hire_tracker.security.Jwtunit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173",
        "https://hire-tracker-frontend.vercel.app")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Jwtunit jwtunit;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<Map<String , String>> signup(@RequestBody User user){
        Map<String , String> response = new HashMap<>();

        //validation if all ready login
        if(userRepository.existsByEmail(user.getEmail())){
            response.put("message", "email allready register");
            return new ResponseEntity<>(response , HttpStatus.BAD_REQUEST);
        }
        // pasword encrypt kro
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        //generate token and return
        String token = jwtunit.generateToken(user.getEmail());
        response.put("token",token);
        response.put("name" , user.getName());
        response.put("email",user.getEmail());
        response.put("message","signup succefully");

        return new ResponseEntity<>(response , HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody Map<String , String> request){
        Map<String , String> response = new HashMap<>();

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.get("email"),
                            request.get("password")
                    )
            );
            User user = userRepository.findByEmail(request.get("email")).get();
            String token = jwtunit.generateToken(user.getEmail());

            response.put("token", token);
            response.put("name", user.getName());
            response.put("email", user.getEmail());
            response.put("message", "login successful");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
                response.put("message" , "Something wrong try again");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}
