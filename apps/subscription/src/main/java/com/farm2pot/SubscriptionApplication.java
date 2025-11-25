package com.farm2pot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling /* @Scheduled 사용 시 추가해야 함 */
public class SubscriptionApplication{
    public static void main(String[] args) {
        SpringApplication.run(SubscriptionApplication.class, args);
    }
}