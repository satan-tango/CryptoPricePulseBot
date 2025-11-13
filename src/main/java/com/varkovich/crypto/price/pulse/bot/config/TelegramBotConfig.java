package com.varkovich.crypto.price.pulse.bot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TelegramBotConfig {
    @Value("${telegrambot.webhookPath}")
    private String webhookPath;
    @Value("${telegrambot.userName}")
    private String botUsername;
    @Value("${telegrambot.botToken}")
    private String botToken;
}
