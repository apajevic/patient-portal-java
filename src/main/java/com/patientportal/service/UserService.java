package com.patientportal.service;

import com.patientportal.dto.UserDTO;
import com.patientportal.model.User;
import com.patientportal.repository.UserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;

    @Transactional
    public void create(UserDTO request) {
        boolean userExists = Stream.of(
                        userRepository.find("email", request.email()).firstResult(),
                        userRepository.find("phone", request.phone()).firstResult())
                .anyMatch(Objects::nonNull);

        if (userExists) {
            throw new IllegalArgumentException("A user with this email or phone number already exists");
        }

        if (!request.password().equalsIgnoreCase(request.passwordConfirm())) {
            throw new IllegalArgumentException("Password and password confirmation do not match");
        }

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(BcryptUtil.bcryptHash(request.password()));
        user.setPhone(request.phone());
        user.setAddress(request.address());
        user.setGender(request.gender());

        userRepository.persist(user);
    }

    public User getById(Long id) {
        return userRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }

    public List<User> getAll() {
        List<User> users = Optional.ofNullable(userRepository.listAll())
                .orElseThrow(() -> new NotFoundException("No users found"));

        return users.stream()
                .filter(User::isActive)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        userRepository.findByIdOptional(id)
                .ifPresentOrElse(userRepository::delete, () -> {
                    throw new NotFoundException("User with id " + id + " not found");
                });
    }
}
