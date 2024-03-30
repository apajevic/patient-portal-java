package com.patientportal.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BusinessException extends Exception {
    private int status;

    public BusinessException(int status, String message) {
        super(message);
        this.status = status;
    }
}
