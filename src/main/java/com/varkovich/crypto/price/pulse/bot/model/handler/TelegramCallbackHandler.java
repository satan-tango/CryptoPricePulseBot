package com.varkovich.crypto.price.pulse.bot.model.handler;

import com.varkovich.crypto.price.pulse.bot.utils.enums.BotState;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
@AllArgsConstructor
@Log4j
public class TelegramCallbackHandler {
    public BotApiMethod<?> handleCallbackQuery(CallbackQuery callbackQuery, BotState botState) {
        return null;
    }
}
