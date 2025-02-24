package com.IKov.MyChat_UserMicroservice.web.controller;


import com.IKov.MyChat_UserMicroservice.domain.preferences.Preferences;
import com.IKov.MyChat_UserMicroservice.service.PreferencesService;
import com.IKov.MyChat_UserMicroservice.service.UserService;
import com.IKov.MyChat_UserMicroservice.web.dto.PreferencesDto;
import com.IKov.MyChat_UserMicroservice.web.dto.UserProfileDto;
import com.IKov.MyChat_UserMicroservice.web.mapper.PreferencesMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/self")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PreferencesService preferencesService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void create(@Valid @ModelAttribute UserProfileDto userProfileDto){
        userService.create(userProfileDto);
    }

    @PostMapping(value = "/preferences")
    public void preferences(@Valid @RequestBody PreferencesDto preferencesDto){
        Preferences preferences = PreferencesMapper.toEntity(preferencesDto);
        preferencesService.createPreferences(preferences);
    }
}
