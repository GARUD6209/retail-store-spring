package com.garud.retail.dao;

import com.garud.retail.entity.SignupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<SignupEntity,Long> {


}
