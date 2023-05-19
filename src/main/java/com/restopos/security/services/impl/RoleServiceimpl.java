package com.restopos.security.services.impl;

import com.restopos.models.Role;
import com.restopos.repository.RoleRepository;
import com.restopos.security.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceimpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public List<Role> getrole() {
       return (List<Role>) roleRepository.findAll();
    }
}
