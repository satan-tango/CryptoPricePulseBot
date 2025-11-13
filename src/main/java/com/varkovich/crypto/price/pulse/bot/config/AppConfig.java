package com.varkovich.crypto.price.pulse.bot.config;

import com.varkovich.crypto.price.pulse.bot.model.TelegramBot;
import com.varkovich.crypto.price.pulse.bot.model.TelegramFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Configuration
public class AppConfig {
    private final TelegramBotConfig botConfig;

    public AppConfig(TelegramBotConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(botConfig.getWebhookPath()).build();
    }

    @Bean
    public TelegramBot springWebHookBot(SetWebhook setWebhook, TelegramFacade telegramFacade) {
        TelegramBot telegramBot = new TelegramBot(setWebhook, telegramFacade);
        telegramBot.setBotPath(botConfig.getWebhookPath());
        telegramBot.setBotUsername(botConfig.getBotUsername());
        telegramBot.setBotToken(botConfig.getBotToken());

        return telegramBot;
    }
}
