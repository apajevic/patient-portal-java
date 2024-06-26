package com.patientportal.response;

import com.patientportal.model.Gender;
import com.patientportal.model.Role;
import com.patientportal.model.User;
import com.patientportal.model.Condition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class UserResponse {
    private UUID id;
    private String name;
    private String email;
    private String photo;
    private String phone;
    private String address;
    private Gender gender;
    private Role role;
    private Set<Condition> conditions;
    private int loginCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.photo = user.getPhoto();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.gender = user.getGender();
        this.role = user.getRole();
        this.loginCount = user.getLoginCount();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.conditions = user.getConditions();
    }
}


