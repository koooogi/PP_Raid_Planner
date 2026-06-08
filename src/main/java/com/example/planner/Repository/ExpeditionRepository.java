/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.planner.Repository;

import com.example.planner.Model.Expedition;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
public interface  ExpeditionRepository extends JpaRepository<Expedition, Long>{
    List<Expedition> findByUserIdOrderByIdDesc(Long userId);
}
