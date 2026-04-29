package org.example.schedulers;

import jakarta.transaction.Transactional;
import org.example.entities.GlobalSettings;
import org.example.repository.GlobalSettingsRepository;
import org.example.repository.UserLimitRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class LimitResetScheduler {

    private final UserLimitRepository userLimitRepository;
    private final GlobalSettingsRepository settingsRepository;

    public LimitResetScheduler(UserLimitRepository userLimitRepository, GlobalSettingsRepository settingsRepository) {
        this.userLimitRepository = userLimitRepository;
        this.settingsRepository = settingsRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void resetDailyLimits() {
        BigDecimal defaultLimit = settingsRepository.findById("DEFAULT_LIMIT_VALUE")
                .map(GlobalSettings::getSettingValue)
                .orElse(new BigDecimal("100000.00"));

        userLimitRepository.resetAllLimits(defaultLimit);
    }
}