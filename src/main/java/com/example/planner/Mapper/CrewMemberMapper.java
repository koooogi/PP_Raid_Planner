package com.example.planner.Mapper;

import com.example.planner.DTO.CrewMemberDto;
import com.example.planner.Model.CrewMember;
import org.springframework.stereotype.Component;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */

@Component
public class CrewMemberMapper {
    
    public CrewMemberDto toDto(CrewMember member) {
        if (member == null) return null;
        
        CrewMemberDto dto = new CrewMemberDto();
        dto.setId(member.getId());
        dto.setName(member.getName());
        dto.setClan(member.getClan());
        dto.setGender(member.getGender());
        dto.setAge(member.getAge());
        dto.setStrength(member.getStrength());
        dto.setSupplies(member.getSupplies());
        
        return dto;
    }
}
