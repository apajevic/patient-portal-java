package com.patientportal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    @Column(unique = true)
    private String phone;

    @NotBlank
    private String address;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotBlank
//  @Length(min = 8, max = 24)
    private String password;

    @Column(name = "password_confirm")
//  @Length(min = 8, max = 24)
    private String passwordConfirm;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<Appointment> appointmentsAsPatient;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private Set<Appointment> appointmentsAsDoctor;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<Prescription> prescriptionsAsPatient;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private Set<Prescription> prescriptionsAsDoctor;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserCondition> userConditions;

    @Enumerated(EnumType.STRING)
    private Role role = Role.PATIENT;

    @Column(name = "login_count")
    private int loginCount;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private boolean active = true;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getLoginCount() == user.getLoginCount() && isActive() == user.isActive() && Objects.equals(getId(), user.getId()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getName(), user.getName()) && Objects.equals(getPhone(), user.getPhone()) && Objects.equals(getAddress(), user.getAddress()) && getGender() == user.getGender() && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getPasswordConfirm(), user.getPasswordConfirm()) && Objects.equals(getAppointmentsAsPatient(), user.getAppointmentsAsPatient()) && Objects.equals(getAppointmentsAsDoctor(), user.getAppointmentsAsDoctor()) && getRole() == user.getRole() && Objects.equals(getLastLogin(), user.getLastLogin()) && Objects.equals(getCreatedAt(), user.getCreatedAt()) && Objects.equals(getUpdatedAt(), user.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getName(), getPhone(), getAddress(), getGender(), getPassword(), getPasswordConfirm(), getAppointmentsAsPatient(), getAppointmentsAsDoctor(), getRole(), getLoginCount(), getLastLogin(), getCreatedAt(), getUpdatedAt(), isActive());
    }
}
