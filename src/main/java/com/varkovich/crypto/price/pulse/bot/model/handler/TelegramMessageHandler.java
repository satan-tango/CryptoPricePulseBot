package com.varkovich.crypto.price.pulse.bot.model.handler;

import com.varkovich.crypto.price.pulse.bot.dao.UserDAO;
import com.varkovich.crypto.price.pulse.bot.model.handler.events.MessageEvents;
import com.varkovich.crypto.price.pulse.bot.utils.enums.BotState;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Log4j
@AllArgsConstructor
public class TelegramMessageHandler {
    private final UserDAO userDAO;
    private final MessageEvents messageEvents;

    public BotApiMethod<?> handleInputMessage(Message message, BotState botState) {
        long userId = message.getFrom().getId();
        long chatId = message.getChatId();

        if (!userDAO.existsByTelegramUserId(userId)) {
            messageEvents.saveNewUser(message, userId);
            log.info("User with id " + userId + " has been saved");
        }
        return null;
    }
}
