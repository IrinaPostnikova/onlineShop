package com.example.onlineshop.repository;

import com.example.onlineshop.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  public User findByLogin(String login);
  public User getUserByLogin(String login);

}

