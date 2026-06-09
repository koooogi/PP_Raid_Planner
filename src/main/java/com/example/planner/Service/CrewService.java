package com.example.planner.Service;

import com.example.planner.DTO.CrewMemberDto;
import com.example.planner.Mapper.CrewMemberMapper;
import com.example.planner.Repository.CrewRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */

@Service
public class CrewService {
    
    @Autowired
    private CrewRepository crewRepository;
    
    @Autowired
    private CrewMemberMapper crewMapper;
    
    public List<CrewMemberDto> getAllCrew(){
        return crewRepository.findAll().stream()
            .map(crewMapper::toDto)
            .collect(Collectors.toList());
    }
    
    public CrewMemberDto getCrewMemberById(Long id){
        return crewRepository.findById(id)
            .map(crewMapper::toDto)
            .orElseThrow(() -> new RuntimeException("Crew member not found"));
    }
}
