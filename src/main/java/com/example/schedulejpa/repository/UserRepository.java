package com.example.schedulejpa.repository;

import com.example.schedulejpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<Long> findIdByEmailAndPassword(String email, String password);
}
