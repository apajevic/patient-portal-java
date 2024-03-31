package com.patientportal.response;

import com.patientportal.model.Gender;
import com.patientportal.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Gender gender;
    private Role role;
    private int loginCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


