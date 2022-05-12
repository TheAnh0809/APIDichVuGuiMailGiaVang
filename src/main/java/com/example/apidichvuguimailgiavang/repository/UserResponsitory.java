package com.example.apidichvuguimailgiavang.repository;

import com.example.apidichvuguimailgiavang.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserResponsitory extends JpaRepository<User, String> {
    User findUsersByEmail(String email);
    User findUserByEmailAndMaxacnhan(String email, String ma);
    void deleteUserByEmail(String email);
}
