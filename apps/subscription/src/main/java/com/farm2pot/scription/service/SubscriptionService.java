package com.farm2pot.scription.service;

import com.farm2pot.common.exception.BaseException;
import com.farm2pot.common.http.response.ApiResponse;
import com.farm2pot.common.webclient.client.UserClient;
import com.farm2pot.scription.controller.dto.SubscriptionRequest;
import com.farm2pot.scription.entity.Subscription;
import com.farm2pot.scription.entity.SubscriptionHistory;
import com.farm2pot.scription.mapper.SubscriptionMapper;
import com.farm2pot.scription.repository.SubscriptionHistoryRepository;
import com.farm2pot.scription.repository.SubscriptionRepository;
import com.farm2pot.scription.service.dto.SubscriptionResponse;
import com.farm2pot.scription.utils.SubscriptionConstants;
import com.farm2pot.scription.utils.SubscriptionUtils;
import com.farm2pot.scription.utils.exception.SubscriptionErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionHistoryRepository historyRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final SubscriptionUtils subscriptionUtils;
    private final UserClient userClient;

    /**
     * 현재 활성(또는 최신) 구독 조회
     */
    public SubscriptionResponse getCurrentSubscription(Long userId) {
        Subscription subscription = subscriptionRepository
                .findByUserId(userId)
                .orElseThrow(() -> new BaseException(SubscriptionErrorCode.NOT_FOUND_ACTIVE_SUBSCRIPTION));

        return subscriptionMapper.toDto(subscription);
    }

    /**
     * 구독 추가
     * 1) ACTIVE 구독 없음 → 바로 ACTIVE 구독 생성
     * 2) ACTIVE 구독 있음 → 신규 구독은 RESERVED 로 history 에만 기록
     */
    public SubscriptionResponse createSubscribe(SubscriptionRequest request) {
        Subscription current = subscriptionRepository.findByUserId(request.userId()).orElse(null);

        // 1) 구독 있는 경우
        if (current != null){
            //1-1) ACTIVE → 예약만 등록
            if (SubscriptionConstants.SUBSCRIPTION_STATUS.ACTIVE.code().equals(current.getStatus())) {

                SubscriptionHistory reserved = SubscriptionHistory.builder()
                        .userId(request.userId())
                        .price(request.price())
                        .startDate(request.startDate())
                        .endDate(request.startDate().plusMonths(request.months()))   // 기간 계산
                        .status(SubscriptionConstants.SUBSCRIPTION_HIST_STATUS.RESERVED.code())
                        .build();

                historyRepository.save(reserved);

                return new SubscriptionResponse(
                        reserved.getId(),
                        reserved.getUserId(),
                        reserved.getPrice(),
                        reserved.getStartDate(),
                        reserved.getEndDate(),
                        reserved.getStatus()
                );

            }else{
                //1-2) EXPIRED, CANCELD → 예약만 등록
                String status = SubscriptionConstants.SUBSCRIPTION_HIST_STATUS.RESERVED.code();
                if(request.startDate() == LocalDate.now()){
                    status = SubscriptionConstants.SUBSCRIPTION_HIST_STATUS.ACTIVE.code();
                }

                SubscriptionHistory reserved = SubscriptionHistory.builder()
                        .userId(request.userId())
                        .price(request.price())
                        .startDate(request.startDate())
                        .endDate(request.startDate().plusMonths(request.months()))   // 기간 계산
                        .status(status) //
                        .build();

                historyRepository.save(reserved);

                return new SubscriptionResponse(
                        reserved.getId(),
                        reserved.getUserId(),
                        reserved.getPrice(),
                        reserved.getStartDate(),
                        reserved.getEndDate(),
                        reserved.getStatus()
                );
            }
        }
        // 2) ACTIVE 구독 없음 → 바로 활성 구독 생성
        Subscription newSubscription = Subscription.builder()
                .userId(request.userId())
                .price(request.price())
                .startDate(request.startDate())
                .endDate(request.startDate().plusMonths(request.months()))
                .status(SubscriptionConstants.SUBSCRIPTION_STATUS.ACTIVE.code())
                .build();

        subscriptionRepository.save(newSubscription);

        // History INSERT (해당 구독에 대한 ACTIVE 이력 1건)
        SubscriptionHistory newHistory = SubscriptionHistory.builder()
                .userId(request.userId())
                .price(newSubscription.getPrice())
                .startDate(newSubscription.getStartDate())
                .endDate(newSubscription.getEndDate())
                .status(SubscriptionConstants.SUBSCRIPTION_HIST_STATUS.ACTIVE.code())
                .build();

        historyRepository.save(newHistory);

        return subscriptionMapper.toDto(newSubscription);
    }

    /**
     * (수동) 구독 종료 처리 - 즉시 EXPIRED 처리
     * 1) Subscription UPDATE (EXPIRED)
     * 2) SubscriptionHistory UPDATE (COMPLETED)
     * → 여기서는 조기 종료(환불) 대신 "강제 만료" 개념으로 본다.
     */
    public void endSubscription(Long userId) {

        Subscription current = subscriptionRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(SubscriptionErrorCode.NOT_FOUND_ACTIVE_SUBSCRIPTION));

        // 이미 ACTIVE 가 아니면 종료 불가
        if (!SubscriptionConstants.SUBSCRIPTION_STATUS.ACTIVE.code().equals(current.getStatus())) {
            throw new BaseException(SubscriptionErrorCode.SUBSCRIPTION_ALREADY_EXPIRED);
        }

        LocalDate today = LocalDate.now();

        // 1) 현재 구독 종료 처리 (오늘부로 EXPIRED)
        current = current.toBuilder()
                .status(SubscriptionConstants.SUBSCRIPTION_STATUS.EXPIRED.code())
                .build();
        subscriptionRepository.save(current);

        // 2) History UPDATE
        SubscriptionHistory history =
                historyRepository.findActiveHistoryByUserId(userId)
                        .orElseThrow(() -> new BaseException(SubscriptionErrorCode.NOT_FOUND_ACTIVE_SUBSCRIPTION_HISTORY));

        history = history.toBuilder()
                .endDate(today)
                .status(SubscriptionConstants.SUBSCRIPTION_HIST_STATUS.COMPLETED.code())
                .build();

        historyRepository.save(history);

        // endSubscription 은 "관리자 강제종료" 용도라고 가정 → 승격은 여기서 안 태움

    }

    /**
     * 조기 종료 (사용자 취소)
     * 1) 환불 금액 계산
     * 2) Subscription 을 CANCELED 로 업데이트
     * 3) History 를 CANCELED 로 업데이트
     * 4) 예약 구독 중 startDate == today 인 것이 있으면 승격
     */
    public SubscriptionResponse cancelSubscription(Long userId) {

        Subscription current = subscriptionRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(SubscriptionErrorCode.NOT_FOUND_ACTIVE_SUBSCRIPTION));

        // 이미 ACTIVE가 아니면 조기 종료 불가
        if (!SubscriptionConstants.SUBSCRIPTION_STATUS.ACTIVE.code().equals(current.getStatus())) {
            throw new BaseException(SubscriptionErrorCode.SUBSCRIPTION_ALREADY_EXPIRED);
        }

        LocalDate today = LocalDate.now();

        // 이미 만료일이 지난 경우 → 조기 종료가 아닌 "만료 처리" 대상
        if (today.isAfter(current.getEndDate())) {
            throw new BaseException(SubscriptionErrorCode.SUBSCRIPTION_ALREADY_EXPIRED);
        }

        // 1) 사용일/전체일 계산
        long usedDays = today.toEpochDay() - current.getStartDate().toEpochDay();
        long totalDays = current.getEndDate().toEpochDay() - current.getStartDate().toEpochDay();

        if (totalDays <= 0) {
            // 비정상 기간(데이터 오류) 방어
            throw new BaseException(SubscriptionErrorCode.INVALID_SUBSCRIPTION_PERIOD);
        }

        // 2) 환불금 계산 (단순 일할 계산)
        long dailyCost = current.getPrice() / totalDays;
        long usedCost = dailyCost * usedDays;
        long refundAmount = current.getPrice() - usedCost;

        // 3) Subscription 종료 처리 (CANCELED)
        current = current.toBuilder()
                .status(SubscriptionConstants.SUBSCRIPTION_STATUS.CANCELED.code())
                .endDate(today)
                .build();

        subscriptionRepository.save(current);

        // 4) 히스토리 업데이트 (ACTIVE -> CANCELED)
        SubscriptionHistory history = historyRepository
                .findActiveHistoryByUserId(userId)
                .orElseThrow(() -> new BaseException(SubscriptionErrorCode.NOT_FOUND_ACTIVE_SUBSCRIPTION_HISTORY));

        history = history.toBuilder()
                .status(SubscriptionConstants.SUBSCRIPTION_HIST_STATUS.CANCELED.code())
                .endDate(today)
                .build();

        historyRepository.save(history);

        // 5) 예약 구독 자동 승격 (startDate == today 인 것만)
        promoteReservedIfPossible(userId);

        // 6) 환불 처리(추후 Refund 테이블로 확장 가능)
        log.info("Refund processed amount={} for user={}", refundAmount, userId);

        return subscriptionMapper.toDto(current);
    }

    /**
     * 예약 구독 자동 승격
     * - 조건: startDate == today 인 RESERVED 히스토리
     * - 우선순위: startDate ASC 첫 번째
     * - Subscription 은 기존 row를 UPDATE 해서 1:1 관계 유지
     */
    private void promoteReservedIfPossible(Long userId) {

        LocalDate today = LocalDate.now();

        // 오늘 시작하는 예약 구독만 승격 대상
        List<SubscriptionHistory> reservedList =
                historyRepository.findPromotableReserved(
                        userId,
                        SubscriptionConstants.SUBSCRIPTION_HIST_STATUS.RESERVED.code(),
                        today
                );

        if (reservedList.isEmpty()) {
            return; // 승격할 예약 구독 없음
        }

        // 정확히 오늘 시작하는 가장 빠른 예약 구독 선택
        SubscriptionHistory nextHist = reservedList.get(0);

        // 기존 Subscription 조회 (EXPIRED/CANCELED 포함)
        Subscription current = subscriptionRepository.findByUserId(userId).orElse(null);

        Subscription newActive;
        if (current != null) {
            // 기존 row 재사용 → 1:1 유지
            newActive = current.toBuilder()
                    .price(nextHist.getPrice())
                    .startDate(nextHist.getStartDate())
                    .endDate(nextHist.getEndDate())
                    .status(SubscriptionConstants.SUBSCRIPTION_STATUS.ACTIVE.code())
                    .build();
        } else {
            // 이론상 거의 없겠지만 방어 코드
            newActive = Subscription.builder()
                    .userId(userId)
                    .price(nextHist.getPrice())
                    .startDate(nextHist.getStartDate())
                    .endDate(nextHist.getEndDate())
                    .status(SubscriptionConstants.SUBSCRIPTION_STATUS.ACTIVE.code())
                    .build();
        }

        subscriptionRepository.save(newActive);

        // 예약 이력 → ACTIVE 이력으로 상태 업데이트
        nextHist = nextHist.toBuilder()
                .status(SubscriptionConstants.SUBSCRIPTION_HIST_STATUS.ACTIVE.code())
                .build();

        historyRepository.save(nextHist);

        log.info("예약 구독이 활성화되었습니다. userId={}", userId);
    }

    /**
     * 만료 처리 (스케줄러 또는 배치에서 userId 순회하며 호출)
     * 1) endDate <= today 이고 ACTIVE 인 구독만 만료
     * 2) Subscription: ACTIVE -> EXPIRED
     * 3) History: ACTIVE -> COMPLETED
     * 4) 예약 구독 자동 승격 (startDate == today)
     */
    public void expireSubscription(Long userId) {
        Subscription current = subscriptionRepository.findByUserId(userId)
                .orElse(null);

        if (current == null ||
                !SubscriptionConstants.SUBSCRIPTION_STATUS.ACTIVE.code().equals(current.getStatus())) {
            return; // ACTIVE가 아니면 만료 처리 불필요
        }

        LocalDate today = LocalDate.now();

        // endDate <= today 인 경우에만 만료 처리
        if (current.getEndDate() == null ||
                current.getEndDate().isAfter(today)) {
            return;
        }

        // 1) ACTIVE → EXPIRED
        current = current.toBuilder()
                .status(SubscriptionConstants.SUBSCRIPTION_STATUS.EXPIRED.code())
                .build();
        subscriptionRepository.save(current);

        // 2) 히스토리 COMPLETED로 업데이트
        SubscriptionHistory history =
                historyRepository.findActiveHistoryByUserId(userId)
                        .orElse(null);

        if (history != null) {
            history = history.toBuilder()
                    .status(SubscriptionConstants.SUBSCRIPTION_HIST_STATUS.COMPLETED.code())
                    .endDate(current.getEndDate())
                    .build();
            historyRepository.save(history);
        }

        // 3) 만료 후 자동 승격 (startDate == today)
        promoteReservedIfPossible(userId);
    }
    public <T> T test() {
        ApiResponse<T> res = userClient.connection(
                HttpMethod.GET,
                "/auth/userinfo-uid/{id}",
                null,
                Map.of("id", "1"),
                Map.of()
        );

        return res.data();
    }
}
