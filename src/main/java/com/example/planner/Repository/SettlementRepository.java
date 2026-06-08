/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.planner.Repository;

import com.example.planner.Model.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
public interface  SettlementRepository extends JpaRepository<Settlement, Long>{
    
}
