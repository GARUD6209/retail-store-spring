package com.garud.retail.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Objects;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class SignupEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "city_id")
    private Long cityId; // Can be null, so using Integer

    @Column(nullable = false)
    private String name;

    @Column(name = "mobile_number", nullable = false, unique = true)
    private String mobileNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "gst_number")
    private String gstNumber;

    @Column(nullable = false)
    private String address;

    @Column(name = "create_datetime", nullable = false)
    private Timestamp createDatetime;

    @Column(name = "update_datetime")
    private Timestamp updateDatetime;

    @Column
    private String otp;

    @Column(name = "otp_creation_time")
    private Timestamp otpCreationTime;

    @Column(nullable = false)
    private boolean enabled;




    // toString Method
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='******'" +
                ", cityId=" + cityId +
                ", name='" + name + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", email='" + email + '\'' +
                ", gstNumber='" + gstNumber + '\'' +
                ", address='" + address + '\'' +
                ", createDatetime=" + createDatetime +
                ", updateDatetime=" + updateDatetime +
                ", otp='" + otp + '\'' +
                ", otpCreationTime=" + otpCreationTime +
                ", enabled=" + enabled +
                '}';
    }

    // equals and hashCode Methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignupEntity user = (SignupEntity) o;
        return id == user.id && enabled == user.enabled && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(cityId, user.cityId) && Objects.equals(name, user.name) && Objects.equals(mobileNumber, user.mobileNumber) && Objects.equals(email, user.email) && Objects.equals(gstNumber, user.gstNumber) && Objects.equals(address, user.address) && Objects.equals(createDatetime, user.createDatetime) && Objects.equals(updateDatetime, user.updateDatetime) && Objects.equals(otp, user.otp) && Objects.equals(otpCreationTime, user.otpCreationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, cityId, name, mobileNumber, email, gstNumber, address, createDatetime, updateDatetime, otp, otpCreationTime, enabled);
    }


}
