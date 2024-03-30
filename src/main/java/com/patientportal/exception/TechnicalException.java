package com.patientportal.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TechnicalException extends Exception {
    private int status;

    public TechnicalException(int status, String message) {
        super(message);
        this.status = status;
    }
}
