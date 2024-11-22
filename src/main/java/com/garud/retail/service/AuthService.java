package com.garud.retail.service;

import com.garud.retail.dao.AuthRepository;
import com.garud.retail.entity.SignupEntity;
import com.garud.retail.pojo.SignupRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class AuthService {
    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private JdbcUserDetailsManager userDetailsManager; // Inject JdbcUserDetailsManager

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public String save(SignupRequest signupRequest){

        SignupEntity signupEntity = modelMapper.map(signupRequest, SignupEntity.class);

        signupEntity.setCreateDatetime(new Timestamp(System.currentTimeMillis()));
        signupEntity.setUpdateDatetime(new Timestamp(System.currentTimeMillis()));
        signupEntity.setOtpCreationTime(new Timestamp(System.currentTimeMillis()));


        SignupEntity save = authRepository.save(signupEntity);

        return "signUpRequest" + save;
    }
}
