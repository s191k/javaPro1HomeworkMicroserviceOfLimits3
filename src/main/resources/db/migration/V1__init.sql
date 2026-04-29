-- Таблица настроек
CREATE TABLE global_settings (
    setting_key VARCHAR(255) PRIMARY KEY,
    setting_value DECIMAL(19, 2) NOT NULL
);

-- Таблица лимитов пользователей
CREATE TABLE user_limits (
    id BIGINT PRIMARY KEY,
    available_balance DECIMAL(19, 2) NOT NULL,
    reserved_sum DECIMAL(19, 2) NOT NULL DEFAULT 0.00
);

-- Таблица резервов операций
CREATE TABLE limit_reservations (
    operation_id UUID PRIMARY KEY,
    user_id BIGINT NOT NULL,
    amount DECIMAL(19, 2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user_limit FOREIGN KEY (user_id) REFERENCES user_limits(id)
);

INSERT INTO global_settings (setting_key, setting_value)
VALUES ('DEFAULT_LIMIT_VALUE', 100000.00);