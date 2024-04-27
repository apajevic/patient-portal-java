package com.patientportal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "conditions")
public class Condition {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NonNull
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "conditions")
    @JsonIgnore
    private Set<User> users;

    @NonNull
    private String description;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
