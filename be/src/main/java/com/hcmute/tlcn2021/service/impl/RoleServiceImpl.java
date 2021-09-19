package com.hcmute.tlcn2021.service.impl;

import com.hcmute.tlcn2021.model.Role;
import com.hcmute.tlcn2021.repository.RoleRepository;
import com.hcmute.tlcn2021.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
