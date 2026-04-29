package org.example.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.entities.GlobalSettings;
import org.example.entities.LimitReservation;
import org.example.entities.UserLimit;
import org.example.enums.ReservationStatus;
import org.example.errors.NotEnoughMoney;
import org.example.errors.OperationDoesntExist;
import org.example.errors.ReservationWrongStatus;
import org.example.repository.GlobalSettingsRepository;
import org.example.repository.LimitReservationRepository;
import org.example.repository.UserLimitRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class LimitService {

    private final UserLimitRepository userLimitRepository;
    private final LimitReservationRepository reservationRepository;
    private final GlobalSettingsRepository settingsRepository;

    public LimitService(UserLimitRepository userLimitRepository, LimitReservationRepository reservationRepository, GlobalSettingsRepository settingsRepository) {
        this.userLimitRepository = userLimitRepository;
        this.reservationRepository = reservationRepository;
        this.settingsRepository = settingsRepository;
    }

    @Transactional
    public void reserve(Long userId, BigDecimal amount, UUID operationId) {

        if (reservationRepository.existsById(operationId)) {
            throw new OperationDoesntExist("нет операции с UUID", operationId);
        }

        UserLimit user = userLimitRepository.findById(userId)
                .orElseGet(() -> {
                    BigDecimal defaultLimit = getDefaultLimit();
                    return userLimitRepository.save(new UserLimit(userId, defaultLimit, BigDecimal.ZERO));
                });

        int updatedRows = userLimitRepository.reserve(user.getId(), amount);

        if (updatedRows == 0) {
            throw new NotEnoughMoney("Недостаточно средств", amount);
        }

        LimitReservation reservation = new LimitReservation(operationId, user.getId(), amount, ReservationStatus.PENDING, LocalDateTime.now());
        reservationRepository.save(reservation);
    }

    @Transactional
    public void confirm(UUID operationId) {
        LimitReservation reservation = reservationRepository.findById(operationId)
                .orElseThrow(() -> new OperationDoesntExist("нет операции с UUID", operationId));

        if (reservation.getStatus() != ReservationStatus.PENDING) {
            throw new ReservationWrongStatus("Статус операции отличается от ожидаемого", ReservationStatus.PENDING, reservation.getStatus());
        }

        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservationRepository.save(reservation);
        userLimitRepository.confirm(reservation.getUserId(), reservation.getAmount());
    }

    @Transactional
    public void cancel(UUID operationId) {
        LimitReservation reservation = reservationRepository.findById(operationId)
                .orElseThrow(() -> new OperationDoesntExist("нет операции с UUID", operationId));

        if (reservation.getStatus() != ReservationStatus.PENDING) {
            throw new ReservationWrongStatus("Статус операции отличается от ожидаемого", ReservationStatus.PENDING, reservation.getStatus());
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
        userLimitRepository.cancel(reservation.getUserId(), reservation.getAmount());
    }

    private BigDecimal getDefaultLimit() {
        return settingsRepository.findById("DEFAULT_LIMIT_VALUE")
                .map(GlobalSettings::getSettingValue)
                .orElse(new BigDecimal("100000.00"));
    }

}