package com.IKov.MyChat_UserMicroservice.web.controller;


import com.IKov.MyChat_UserMicroservice.service.UserService;
import com.IKov.MyChat_UserMicroservice.web.dto.UserProfileDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/self")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void create(@Valid @ModelAttribute UserProfileDto userProfileDto){
        userService.create(userProfileDto);
    }

}
