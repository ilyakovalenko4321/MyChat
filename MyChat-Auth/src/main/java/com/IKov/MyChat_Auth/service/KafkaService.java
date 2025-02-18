package com.IKov.MyChat_Auth.service;

import com.IKov.MyChat_Auth.domain.user.User;

public interface KafkaService {

    void send(User user);

}
