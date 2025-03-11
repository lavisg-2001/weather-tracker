package com.weather.tracker.controller;

import com.weather.tracker.dao.RegisteredUser;
import com.weather.tracker.exceptions.CustomException;
import com.weather.tracker.service.JwtService;
import com.weather.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @PostMapping("/register")
    public RegisteredUser registerUser(@RequestBody RegisteredUser registeredUser) throws CustomException {
        registeredUser.setPassword(encoder.encode(registeredUser.getPassword()));
        return userService.saveUser(registeredUser);
    }

    @PostMapping("/login")
    public String login(@RequestBody RegisteredUser user){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        } else{
            return "Login Failure";
        }
    }

    //OAuth2
//    @GetMapping("/login")
//    public String home(){
//        return "hey there...Welcome to WeatherTracker!";
//    }

}
