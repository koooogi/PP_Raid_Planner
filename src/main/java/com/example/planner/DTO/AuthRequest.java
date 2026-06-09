package com.example.planner.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
public class AuthRequest {
    
    @JsonProperty("username")
    private String username;
    
    @JsonProperty("password")
    private String password;
    
    public AuthRequest(){}
    
    public AuthRequest(String username, String password){
        this.username = username;
        this.password = password;
    }
    
    public String getUsername(){ 
        return username; 
    }
    public void setUsername(String username){ 
        this.username = username; 
    }
    
    public String getPassword(){ 
        return password; 
    }
    public void setPassword(String password){ 
        this.password = password; 
    }
}
