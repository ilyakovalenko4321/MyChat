package com.IKov.MyChat_Auth.service;

import com.IKov.MyChat_Auth.domain.jwtResponse.JwtResponse;
import com.IKov.MyChat_Auth.web.dto.LogoutRequest;

public interface JwtTokenService {

    JwtResponse login(String email, String password);

    void logout(LogoutRequest logoutRequest);

    JwtResponse refresh(String refreshToken);

}
