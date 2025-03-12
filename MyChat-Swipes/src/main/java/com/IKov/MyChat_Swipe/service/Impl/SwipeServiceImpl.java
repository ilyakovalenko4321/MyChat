package com.IKov.MyChat_Swipe.service.Impl;

import com.IKov.MyChat_Swipe.service.KafkaService;
import com.IKov.MyChat_Swipe.service.PostgresService;
import com.IKov.MyChat_Swipe.service.RedisService;
import com.IKov.MyChat_Swipe.service.SwipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SwipeServiceImpl implements SwipeService {

    private final RedisService redisService;
    private final PostgresService postgresService;
    private final KafkaService kafkaService;

    @Override
    public void pass(String userTag) {

    }

    @Override
    public void like(String userTag, String likedUserTag) {

        if(redisService.likeExists(userTag, likedUserTag)){
            kafkaService.send(userTag, likedUserTag);
            redisService.deleteLikeMessage(userTag, likedUserTag);
            return;
        } else if(postgresService.likeExists(userTag, likedUserTag)){
            kafkaService.send(userTag, likedUserTag);
            return;
        }

        redisService.postLike(userTag, likedUserTag);
        postgresService.postLike(userTag, likedUserTag);
    }
}
