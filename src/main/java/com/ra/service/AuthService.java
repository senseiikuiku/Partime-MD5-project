package com.ra.service;

import com.ra.model.dto.req.LoginRequestDTO;
import com.ra.model.dto.req.RegisterRequestDTO;
import com.ra.model.dto.resp.LoginResponseDTO;
import com.ra.model.dto.resp.RegisterResponseDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

    RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO);
}
