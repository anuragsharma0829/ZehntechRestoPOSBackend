package com.restopos.security.services;

import com.restopos.models.Food;
import com.restopos.models.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleService {

    List<Role> getrole();
}
