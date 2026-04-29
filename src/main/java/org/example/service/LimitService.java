package org.example.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.entities.GlobalSettings;
import org.example.entities.LimitReservation;
import org.example.entities.UserLimit;
import org.example.enums.ReservationStatus;
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
            return; // todo кинуть специфичное исключение
        }

        UserLimit user = userLimitRepository.findById(userId)
                .orElseGet(() -> {
                    BigDecimal defaultLimit = getDefaultLimit();
                    return userLimitRepository.save(new UserLimit(userId, defaultLimit, BigDecimal.ZERO));
                });

        int updatedRows = userLimitRepository.reserve(user.getId(), amount);

        if (updatedRows == 0) { // todo кинуть специфичное исключение
            throw new RuntimeException("Недостаточно средств или пользователь не найден");
        }

        LimitReservation reservation = new LimitReservation();
        reservation.setOperationId(operationId);
        reservation.setUserId(user.getId());
        reservation.setAmount(amount);
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setCreatedAt(LocalDateTime.now());

        reservationRepository.save(reservation);
    }

    @Transactional
    public void confirm(UUID operationId) {
        // todo кинуть специфичное исключение
        LimitReservation reservation = reservationRepository.findById(operationId)
                .orElseThrow(() -> new RuntimeException("Резерв не найден"));

        if (reservation.getStatus() != ReservationStatus.PENDING) {
            return;
        }

        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservationRepository.save(reservation);
        userLimitRepository.confirm(reservation.getUserId(), reservation.getAmount());
    }


    private BigDecimal getDefaultLimit() {
        return settingsRepository.findById("DEFAULT_LIMIT_VALUE")
                .map(GlobalSettings::getSettingValue)
                .orElse(new BigDecimal("100000.00"));
    }

    @Transactional
    public void cancel(UUID operationId) {
        // todo кинуть специфичное исключение
        LimitReservation reservation = reservationRepository.findById(operationId)
                .orElseThrow(() -> new RuntimeException("Резерв не найден"));

        if (reservation.getStatus() != ReservationStatus.PENDING) {
            return;
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
        userLimitRepository.cancel(reservation.getUserId(), reservation.getAmount());
    }
}