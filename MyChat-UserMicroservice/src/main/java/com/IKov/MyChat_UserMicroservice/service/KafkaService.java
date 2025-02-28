package com.IKov.MyChat_UserMicroservice.service;

import com.IKov.MyChat_UserMicroservice.domain.profiles.Profile;

public interface KafkaService {

    void send(Profile profile);

}
