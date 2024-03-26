package com.patientportal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @NotNull
    @Future
    private Date date;

    @Column(name = "start_time")
    @NotNull
    private String startTime;

    @Column(name = "end_time")
    @NotNull
    private String endTime;

    @NotNull
    private String location;

    @NotNull
    private String description;

    private String conclusion;

    @Column(name = "status", columnDefinition = "varchar(255) DEFAULT 'ACTIVE'")
    private String status;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Appointment(User patient, User doctor, Date date, String startTime, String endTime, String location, String description) {
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getPatient(), that.getPatient()) && Objects.equals(getDoctor(), that.getDoctor()) && Objects.equals(getDate(), that.getDate()) && Objects.equals(getStartTime(), that.getStartTime()) && Objects.equals(getEndTime(), that.getEndTime()) && Objects.equals(getLocation(), that.getLocation()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getConclusion(), that.getConclusion()) && Objects.equals(getStatus(), that.getStatus()) && Objects.equals(getCreatedAt(), that.getCreatedAt()) && Objects.equals(getUpdatedAt(), that.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPatient(), getDoctor(), getDate(), getStartTime(), getEndTime(), getLocation(), getDescription(), getConclusion(), getStatus(), getCreatedAt(), getUpdatedAt());
    }
}
