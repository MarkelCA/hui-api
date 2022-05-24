package com.grupo5.huiapi.modules.user.repository;

import com.grupo5.huiapi.modules.event.entity.Event;
import com.grupo5.huiapi.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT user FROM User user WHERE user.email = ?1")
    Optional<User> findUserByEmail(String email);

    @Query("SELECT user FROM User user WHERE user.username = ?1")
    Optional<User> findUserByUsername(String username);

}
