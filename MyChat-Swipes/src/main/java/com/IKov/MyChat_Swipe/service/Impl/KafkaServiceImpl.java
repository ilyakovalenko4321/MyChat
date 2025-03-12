package com.IKov.MyChat_Swipe.service.Impl;

import com.IKov.MyChat_Swipe.service.KafkaService;
import com.IKov.MyChat_Swipe.service.props.KafkaProps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {

    private final KafkaSender<String, String> kafkaSender;
    private final KafkaProps kafkaProps;

    @Override
    public Mono<Void> send(String userTag, String likedUserTag) {
        // Создаем SenderRecord с правильными типами (String, String)
        SenderRecord<String, String, String> senderRecord = SenderRecord.create(
                kafkaProps.getTopic(),
                0,
                System.currentTimeMillis(),
                "like",
                userTag + likedUserTag,
                null
        );

        // Асинхронно отправляем сообщение
        return kafkaSender.send(Mono.just(senderRecord))
                .doOnError(e -> System.err.println("Ошибка отправки сообщения: " + e.getMessage()))
                .then();
    }


}
