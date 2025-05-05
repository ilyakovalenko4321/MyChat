package com.IKov.MyChat_Notification.server;

import com.IKov.MyChat_Notification.server.Impl.KafkaListenerImpl;

public interface MessageProcessingService {

    void process(KafkaListenerImpl.LikeKafkaDto likeKafkaDto);

}
