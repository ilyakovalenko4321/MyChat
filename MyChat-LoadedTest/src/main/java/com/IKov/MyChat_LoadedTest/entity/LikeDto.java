package com.IKov.MyChat_LoadedTest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeDto {
    private String userTag;
    private String likedUserTag;
}
