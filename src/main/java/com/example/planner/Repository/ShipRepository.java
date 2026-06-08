package com.example.planner.Repository;

import com.example.planner.Model.VikingShip;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
public interface  ShipRepository extends JpaRepository<VikingShip, Long>{
    
}
