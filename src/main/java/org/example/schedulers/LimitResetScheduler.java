package org.example.schedulers;

import jakarta.transaction.Transactional;
import org.example.config.GlobalSettingsProperties;
import org.example.repository.UserLimitRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LimitResetScheduler {

    private final UserLimitRepository userLimitRepository;
    private final GlobalSettingsProperties globalSettingsProperties;

    public LimitResetScheduler(UserLimitRepository userLimitRepository, GlobalSettingsProperties globalSettingsProperties) {
        this.userLimitRepository = userLimitRepository;
        this.globalSettingsProperties = globalSettingsProperties;
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void resetDailyLimits() {
        userLimitRepository.resetAllLimits(globalSettingsProperties.getDefaultLimitValue());
    }
}