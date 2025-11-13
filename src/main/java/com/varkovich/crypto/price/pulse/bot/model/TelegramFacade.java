package com.varkovich.crypto.price.pulse.bot.model;

import com.varkovich.crypto.price.pulse.bot.cash.BotStateCash;
import com.varkovich.crypto.price.pulse.bot.model.handler.TelegramMessageHandler;
import com.varkovich.crypto.price.pulse.bot.utils.MessageUtils;
import com.varkovich.crypto.price.pulse.bot.utils.enums.BotState;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
@Log4j
public class TelegramFacade {
    private final MessageUtils messageUtils;
    private final BotStateCash botStateCash;
    private final TelegramMessageHandler messageHandler;

    public BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            log.info("Callback has been received: " + update.getCallbackQuery().getData() + "; User Id: " + update.getCallbackQuery().getFrom().getId());
        } else {
            Message message = update.getMessage();
            long chatId = message.getChatId();
            long userId = message.getFrom().getId();

            if (message.hasText()) {
                log.info("Text message has been received: " + message.getText() + "; User Id: " + userId);
                return handleInputMessage(message, userId);
            } else {
                SendMessage sendMessage = messageUtils.generateSendMessage(chatId, "Unsupported message");
                log.debug("Unsupported message" + "; User Id: " + userId);
                return sendMessage;
            }
        }
        return null;
    }


    private BotApiMethod<?> handleInputMessage(Message message, long userId) {
        BotState botState;
        String inputMessage = message.getText();

        switch (inputMessage) {
            case "/start":
                botState = BotState.NEW_USER;
                break;
            case "GET PRICE":
                botState = BotState.GET_PRICE;
                break;
            case "\uD83D\uDEBCSupport":
                botState = BotState.SUPPORT;
                break;
            default: {
                if (botStateCash.getCurrentState(userId).isPresent()) {
                    botState = botStateCash.getCurrentState(userId).get();
                } else {
                    botState = BotState.START;
                }
            }
        }
        return messageHandler.handleInputMessage(message, botState);
    }
}
