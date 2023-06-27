package com.likelasttime.notification.repository;


import com.likelasttime.notification.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
