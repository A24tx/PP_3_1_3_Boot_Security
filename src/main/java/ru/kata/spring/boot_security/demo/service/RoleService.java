package ru.kata.spring.boot_security.demo.service;

import org.springframework.data.repository.query.Param;
import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

}
