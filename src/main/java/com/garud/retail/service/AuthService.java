package com.garud.retail.service;

import com.garud.retail.dao.AuthRepository;
import com.garud.retail.entity.SignupEntity;
import com.garud.retail.exception.RetailException;
import com.garud.retail.pojo.SignupRequest;
import com.garud.retail.pojo.SignupResponse;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
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

    public SignupResponse save(SignupRequest signupRequest){

      try {
          SignupEntity signupEntity = modelMapper.map(signupRequest, SignupEntity.class);

          signupEntity.setCreateDatetime(new Timestamp(System.currentTimeMillis()));
          signupEntity.setUpdateDatetime(new Timestamp(System.currentTimeMillis()));
          signupEntity.setOtpCreationTime(new Timestamp(System.currentTimeMillis()));
          signupEntity.setPassword(passwordEncoder.encode(signupEntity.getPassword()));



           SignupEntity signupEntitySaved = authRepository.save(signupEntity);
           return modelMapper.map(signupEntitySaved, SignupResponse.class);



      }catch (Exception e){
          throw new RetailException("7001", e.getCause().getMessage(), HttpStatus.BAD_REQUEST);
      }
    }


}
