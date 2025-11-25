package com.farm2pot.scription.daemon;

import com.farm2pot.scription.entity.Subscription;
import com.farm2pot.scription.entity.SubscriptionHistory;
import com.farm2pot.scription.repository.SubscriptionHistoryRepository;
import com.farm2pot.scription.repository.SubscriptionRepository;
import com.farm2pot.scription.utils.SubscriptionConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubscriptionScheduler {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionHistoryRepository historyRepository;

    @Transactional
    @Scheduled(cron = "*/20 * * * * *") // 현재 20초마다
    public void processSubscriptions() {

        LocalDate today = LocalDate.now();
        log.info("[SubscriptionScheduler] START at {}", LocalDateTime.now());

        /*
         * 1) 만료(EXPIRE) 처리
         */
        List<Subscription> expiredList =
                subscriptionRepository.findByEndDateBeforeAndStatus(
                        today,
                        SubscriptionConstants.SUBSCRIPTION_STATUS.ACTIVE.code()
                );

        for (Subscription sub : expiredList) {
            try {
                log.info("Expiring subscription userId={}", sub.getUserId());

                // 1-1) subscription → EXPIRED
                Subscription updated = sub.toBuilder()
                        .status(SubscriptionConstants.SUBSCRIPTION_STATUS.EXPIRED.code())
                        .build();
                subscriptionRepository.save(updated);

                // 1-2) history → COMPLETED
                historyRepository.findActiveHistoryByUserId(sub.getUserId())
                        .ifPresent(history -> {
                            SubscriptionHistory h = history.toBuilder()
                                    .status(SubscriptionConstants.SUBSCRIPTION_HIST_STATUS.COMPLETED.code())
                                    .endDate(sub.getEndDate())
                                    .build();
                            historyRepository.save(h);
                        });

            } catch (Exception e) {
                log.error("Expire error userId={}, cause={}", sub.getUserId(), e.getMessage(), e);
            }
        }


        /*
         * 2) 예약된 구독 → ACTIVE 승격(PROMOTION)
         */
        List<SubscriptionHistory> promotionList =
                historyRepository.findReservedHistoryWithoutActiveSubscription(
                        SubscriptionConstants.SUBSCRIPTION_HIST_STATUS.RESERVED.code(),
                        today
                );

        for (SubscriptionHistory hist : promotionList) {

            Long userId = hist.getUserId();
            log.info("Promotion subscription userId={}", userId);
            // 1) 해당 유저의 subscription 조회
            Subscription sub = subscriptionRepository.findByUserId(userId)
                    .orElseThrow(() -> new IllegalStateException("Subscription not found for userId=" + userId));

            // 2) subscription → ACTIVE 승격
            Subscription promoted = sub.toBuilder()
                    .status(SubscriptionConstants.SUBSCRIPTION_STATUS.ACTIVE.code())
                    .startDate(hist.getStartDate())
                    .endDate(hist.getEndDate())
                    .build();
            subscriptionRepository.save(promoted);

            // 3) SubscriptionHistory → ACTIVE 승격
            SubscriptionHistory updatedHist = hist.toBuilder()
                    .status(SubscriptionConstants.SUBSCRIPTION_HIST_STATUS.ACTIVE.code())
                    .startDate(today)
                    .build();

            historyRepository.save(updatedHist);
        }

        log.info("[SubscriptionScheduler] END at {}", LocalDateTime.now());
    }
}
