package com.garud.retail.dao;

import com.garud.retail.entity.SignupEntity;
import com.garud.retail.pojo.AdminResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<SignupEntity,Long> {

    List<SignupEntity> findByUsername( String admin);
}
