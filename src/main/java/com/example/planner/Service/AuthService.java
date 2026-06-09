package com.example.planner.Service;

import com.example.planner.DTO.AuthRequest;
import com.example.planner.DTO.AuthResponse;
import com.example.planner.Model.User;
import com.example.planner.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public AuthResponse register(AuthRequest request){
        
        if (userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists");
        }
        
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        
        userRepository.save(user);
        
        return new AuthResponse(
            user.getUsername(),
            user.getRole(),
            "User registered successfully"
        );       
    }
    
    public AuthResponse login(AuthRequest request){
    
        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }
        
        return new AuthResponse(
            user.getUsername(),
            user.getRole(),
            "Login successful"
        );
    }
}
