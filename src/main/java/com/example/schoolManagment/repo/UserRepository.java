package com.example.schoolManagment.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.schoolManagment.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
	User findByUsername(String username);
}
