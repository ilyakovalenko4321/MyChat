package com.IKov.MyChat_Swipe;

import com.IKov.MyChat_Swipe.service.props.AdjustProps;
import com.IKov.MyChat_Swipe.service.props.KafkaProps;
import com.IKov.MyChat_Swipe.service.props.PostgresProps;
import com.IKov.MyChat_Swipe.service.props.RedisProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({KafkaProps.class, AdjustProps.class, RedisProps.class, PostgresProps.class})
@EnableScheduling
public class MyChatSwipeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyChatSwipeApplication.class, args);
	}

}
