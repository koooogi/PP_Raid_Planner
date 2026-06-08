package com.example.planner.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 50)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    private String role = "USER";
    
    public User(){}
    
        public User(String username, String password){
        this.username = username;
        this.password = password;
    }
        
    public Long getId(){ 
        return id; 
    }
    public String getUsername(){ 
        return username; 
    }
    public String getPassword(){ 
        return password; 
    }
    public String getRole(){ 
        return role; 
    }
    
    public void setId(Long id){ 
        this.id = id; 
    }
    public void setUsername(String username){ 
        this.username = username; 
    }
    public void setPassword(String password){ 
        this.password = password; 
    }
    public void setRole(String role){ 
        this.role = role; 
    }
}
