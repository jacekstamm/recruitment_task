package com.recruitment.empik.repository;

import com.recruitment.empik.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
