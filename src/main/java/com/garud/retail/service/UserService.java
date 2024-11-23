package com.garud.retail.service;

import com.garud.retail.entity.SignupEntity;
import com.garud.retail.exception.RetailException;
import com.garud.retail.pojo.UserResponse;
import com.garud.retail.util.MailUtil;
import com.garud.retail.util.OtpUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;


@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MailUtil mailUtil;

    public UserResponse getUser(Long id){

        return modelMapper.map(userRepository.findById(id),UserResponse.class);

    }

    public UserResponse updateUser(Long id, UserResponse userResponse) {

        SignupEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Update only the fields present in UserResponse
        if (userResponse.getUsername() != null) {
            existingUser.setUsername(userResponse.getUsername());
        }
        if (userResponse.getCityId() != null) {
            existingUser.setCityId(userResponse.getCityId());
        }
        if (userResponse.getName() != null) {
            existingUser.setName(userResponse.getName());
        }
        if (userResponse.getMobileNumber() != null) {
            existingUser.setMobileNumber(userResponse.getMobileNumber());
        }
        if (userResponse.getEmail() != null) {
            existingUser.setEmail(userResponse.getEmail());
        }
        if (userResponse.getGstNumber() != null) {
            existingUser.setGstNumber(userResponse.getGstNumber());
        }
        if (userResponse.getAddress() != null) {
            existingUser.setAddress(userResponse.getAddress());
        }

        // Update the timestamp for modification
        existingUser.setUpdateDatetime(new Timestamp(System.currentTimeMillis()));

        // Save the updated entity
        SignupEntity updatedUser = userRepository.save(existingUser);

        // Convert the updated entity back to UserResponse and return
        return modelMapper.map(updatedUser, UserResponse.class);
    }

    public boolean saveOTP(String email) {

       try {
           Optional<SignupEntity> userEntity = userRepository.findByEmail(email);
          if (!userEntity.isEmpty()){
              String otp = OtpUtil.generateOTP();
              System.out.println("Generated OTP: " + otp);

              // Send OTP to user's email
              String subject = "Password Reset Request for RConsole";
              String messageContent = "Your OTP for password reset is: " + otp;
              boolean emailSent = mailUtil.sendEmail(email, subject, messageContent);
              log.info("OTP email sent : " + emailSent);


              userEntity.ifPresent(user -> {
                  user.setOtp(otp);
                  user.setOtpCreationTime(new Timestamp(System.currentTimeMillis()));
                  userRepository.save(user);
              });

              return true;
          }
          return false;
       } catch (Exception e) {
           throw new RetailException("9000",e.getMessage() + "userService.class", HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    public boolean verifyOTP(String email, String otp) {
        return userRepository.findByEmailAndOtp(email, otp)
                .map(user -> {
                    Timestamp otpCreationTime = user.getOtpCreationTime();
                    long currentTime = System.currentTimeMillis();
                    long otpTime = otpCreationTime.getTime();
                    return (currentTime - otpTime) <= 15 * 60 * 1000; // 15 minutes validity
                })
                .orElse(false);
    }

    public int updatePassword(String email, String otp, String newPassword) {
        Optional<SignupEntity> userEntity = userRepository.findByEmailAndOtp(email, otp);
        if (userEntity.isPresent() && verifyOTP(email, otp)) {
            SignupEntity user = userEntity.get();
            user.setPassword(newPassword);
            user.setUpdateDatetime(new Timestamp(System.currentTimeMillis()));
            userRepository.save(user);
            return 1;
        }
        return 0;
    }
}
