package com.patientportal.interfaces;

import com.patientportal.dto.LoginDTO;
import com.patientportal.dto.RegisterDTO;

public interface IAuthService {
    String login(LoginDTO loginDTO);
    String register(RegisterDTO registerDTO);
}
