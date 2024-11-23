package com.garud.retail.service;

import com.garud.retail.entity.SignupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SignupEntity,Long> {
    Optional<SignupEntity> findByEmail(String email);

    Optional<SignupEntity> findByEmailAndOtp(String email, String otp);
}
