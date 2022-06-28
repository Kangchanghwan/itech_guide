package com.itech.guide.domain.member.service;

import com.itech.guide.domain.member.entity.Role;
import com.itech.guide.domain.member.entity.Roles;
import com.itech.guide.domain.member.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Transactional
public class InitRole {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void initRoleData(){
        if(roleRepository.count() == 0){
            Arrays.stream(Role.values()).forEach(role -> roleRepository.saveAndFlush(Roles.newInstance(role)));
        }
    }

}
