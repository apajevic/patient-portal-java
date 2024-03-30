package com.patientportal.service;

import com.patientportal.dto.RegisterDTO;
import com.patientportal.dto.UpdateUserDTO;
import com.patientportal.exception.BusinessException;
import com.patientportal.exception.TechnicalException;
import com.patientportal.model.User;
import com.patientportal.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;

    @Inject
    PasswordService passwordService;

    @Transactional
    public User create(RegisterDTO request) throws BusinessException, TechnicalException {
        boolean userExists = Stream.of(
                        userRepository.find("email", request.email()).firstResult(),
                        userRepository.find("phone", request.phone()).firstResult())
                .anyMatch(Objects::nonNull);

        if (userExists) {
            throw new BusinessException(
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    "A user with this email or phone number already exists"
            );
        }

        if (!request.password().equalsIgnoreCase(request.passwordConfirm())) {
            throw new BusinessException(
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    "Password and password confirmation do not match"
            );
        }

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordService.hashPassword(request.password()));
        user.setPhone(request.phone());
        user.setAddress(request.address());
        user.setGender(request.gender());

        userRepository.persist(user);
        if(userRepository.isPersistent(user)){
            return user;
        }
        throw new TechnicalException(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                "User could not be created"
        );
    }

    public User getById(Long id) throws BusinessException {
        return userRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException(
                        Response.Status.NOT_FOUND.getStatusCode(),
                        "User with id " + id + " not found"
                ));
    }

    public List<User> getAll() throws BusinessException {
        List<User> users = userRepository.listAll();

        if (users == null || users.isEmpty()) {
            throw new BusinessException(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    "No users found"
            );
        }

        return users.stream()
                .filter(User::isActive)
                .collect(Collectors.toList());
    }

    @Transactional
    public User update(Long id, UpdateUserDTO request) throws BusinessException, TechnicalException {
        User user = userRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException(
                        Response.Status.NOT_FOUND.getStatusCode(),
                        "User with id " + id + " not found"
                ));

        if (request.name() != null) {
            user.setName(request.name());
        }
        if (request.phone() != null) {
            user.setPhone(request.phone());
        }
        if (request.address() != null) {
            user.setAddress(request.address());
        }
        if (request.gender() != null) {
            user.setGender(request.gender());
        }

        user.setUpdatedAt(LocalDateTime.now());
        userRepository.persist(user);
        if(userRepository.isPersistent(user)){
            return user;
        }
        throw new TechnicalException(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                "User could not be updated"
        );
    }

    @Transactional
    public void delete(Long id) {
        userRepository.findByIdOptional(id)
                .ifPresentOrElse(userRepository::delete, () -> {
                    throw new NotFoundException("User with id " + id + " not found");
                });
    }
}
