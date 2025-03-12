package com.IKov.MyChat_Swipe.service;

import java.util.Map;

public interface RedisService {

    boolean likeExists(String userTag, String likedUserTag);

    void postLike(String userTag, String likedUserTag);

    void deleteLikeMessage(String firstUserTag, String secondUserTag);

    Map<String, Double> getAdjustedUsers();

}
