package com.example.planner.Controller;

import com.example.planner.DTO.AuthRequest;
import com.example.planner.DTO.AuthResponse;
import com.example.planner.Service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "User registration and login")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<AuthResponse> register(@RequestParam String username, @RequestParam String password) {
        AuthRequest request = new AuthRequest(username, password);
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/login")
    @Operation(summary = "Login user")
    public ResponseEntity<AuthResponse> login(@RequestParam String username, @RequestParam String password, HttpServletRequest request, HttpServletResponse response) {
        AuthRequest authRequest = new AuthRequest(username, password);
        AuthResponse authResponse = authService.login(authRequest);
    
        if (authResponse.getMessage().equals("Login successful")) {
            HttpSession session = request.getSession(true);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, List.of());
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
            session.setAttribute("SPRING_SECURITY_CONTEXT", context);
        }
        
        return ResponseEntity.ok(authResponse);
    }
}