package com.IKov.MyChat_Recomendation.service.Impl;

import com.IKov.MyChat_Recomendation.domain.user.UserPropertiesToVectorize;
import com.IKov.MyChat_Recomendation.service.KafkaDataReceiver;
import com.IKov.MyChat_Recomendation.service.UserVectorizeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaDataReceiverImpl implements KafkaDataReceiver {

    private final KafkaReceiver<String, Object> receiver;
    private final UserVectorizeService userVectorize;

    @PostConstruct
    private void init() {
        fetch();
    }

    @Override
    public void fetch() {
        Gson gson = new GsonBuilder().create();
        receiver.receive().subscribe(record -> {
            try {
                UserPropertiesToVectorize properties = gson.fromJson(record.value().toString(), UserPropertiesToVectorize.class);
                userVectorize.vectorize(properties);

                record.receiverOffset().acknowledge();
                log.info("Обработан Kafka-сообщение для пользователя {}", properties.getUserTag());
            } catch (Exception e) {
                log.error("Ошибка при обработке Kafka-сообщения: {}", e.getMessage());
            }
        });
    }
}
