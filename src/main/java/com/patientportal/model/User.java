package com.patientportal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
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

    @JsonIgnore
    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Appointment appointmentAsPatient;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Appointment> appointmentsAsDoctor;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Prescription> prescriptionsAsDoctor;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
}
