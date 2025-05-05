package com.IKov.MyChat_Auth.web.controller;

import com.IKov.MyChat_Auth.domain.jwtResponse.JwtResponse;
import com.IKov.MyChat_Auth.domain.user.User;
import com.IKov.MyChat_Auth.service.AuthService;
import com.IKov.MyChat_Auth.service.JwtTokenService;
import com.IKov.MyChat_Auth.web.dto.LogoutRequest;
import com.IKov.MyChat_Auth.web.dto.RegisterRequest;
import com.IKov.MyChat_Auth.web.dto.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenService jwtTokenService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public boolean register(@Valid @RequestBody RegisterRequest registerRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new RuntimeException();
        }
        User user = userMapper.toEntity(registerRequest);
        return authService.register(user);
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
