package com.IKov.MyChat_Auth.web.controller;

import com.IKov.MyChat_Auth.domain.jwtResponse.JwtResponse;
import com.IKov.MyChat_Auth.domain.user.User;
import com.IKov.MyChat_Auth.service.AuthService;
import com.IKov.MyChat_Auth.service.JwtTokenService;
import com.IKov.MyChat_Auth.web.dto.LogoutRequest;
import com.IKov.MyChat_Auth.web.dto.RegisterRequest;
import com.IKov.MyChat_Auth.web.dto.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenService jwtTokenService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest registerRequest){
        User user = userMapper.toEntity(registerRequest);
        authService.register(user);
    }

    @GetMapping("/login")
    public JwtResponse login(@RequestParam String email, @RequestParam String password){
        return jwtTokenService.login(email, password);
    }

    @DeleteMapping("/logout")
    public void logout(@RequestBody LogoutRequest logoutRequest){
        jwtTokenService.logout(logoutRequest);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody LogoutRequest refreshRequest){
        JwtResponse newTokenPair = jwtTokenService.refresh(refreshRequest.getRefreshToken());
        logout(refreshRequest);
        return newTokenPair;
    }


}
