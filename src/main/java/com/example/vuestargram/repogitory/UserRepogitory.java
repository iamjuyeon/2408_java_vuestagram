package com.example.vuestargram.repogitory;


import com.example.vuestargram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepogitory extends JpaRepository<User, Long> {
    Optional<User> findByAccount(String account);

    // user : entity class
    // long : pk의 데이터 타입


}
