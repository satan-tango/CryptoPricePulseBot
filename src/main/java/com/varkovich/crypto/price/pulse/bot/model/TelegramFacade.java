package com.varkovich.crypto.price.pulse.bot.model;

import com.varkovich.crypto.price.pulse.bot.cash.BotStateCash;
import com.varkovich.crypto.price.pulse.bot.model.handler.TelegramCallbackHandler;
import com.varkovich.crypto.price.pulse.bot.model.handler.TelegramMessageHandler;
import com.varkovich.crypto.price.pulse.bot.utils.MessageUtils;
import com.varkovich.crypto.price.pulse.bot.utils.enums.BotState;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
@AllArgsConstructor
@Log4j
public class TelegramFacade {
    private final MessageUtils messageUtils;
    private final BotStateCash botStateCash;
    private final TelegramMessageHandler messageHandler;
    private final TelegramCallbackHandler telegramCallbackHandler;

    public BotApiMethod<?> handleUpdate(Update update) {

        if (update.hasCallbackQuery()) {
            long userID = update.getCallbackQuery().getFrom().getId();
            log.info("Callback has been received: " + update.getCallbackQuery().getData() + "; User Id: " + update.getCallbackQuery().getFrom().getId());
            CallbackQuery callbackQuery = update.getCallbackQuery();
            Optional<BotState> state = botStateCash.getCurrentState(userID);

            if (state.isEmpty() || (state.get() != BotState.GET_PRICE)) {
                log.info("Callback has been received: " + callbackQuery.getData() + " which are connected to state; User Id: " + userID);
                return null;
            }

            return telegramCallbackHandler.handleCallbackQuery(callbackQuery, state.get());

        } else {
            Message message = update.getMessage();
            long chatID = message.getChatId();
            long userID = message.getFrom().getId();
            if (message.hasText()) {
                log.info("Text message has been received: " + message.getText() + "; User Id: " + userID);
                return handleInputMessage(message, userID);
            } else {
                SendMessage sendMessage = messageUtils.generateSendMessage(chatID, "Unsupported message");
                log.debug("Unsupported message" + "; User Id: " + userID);
                return sendMessage;
            }
        }
    }


    private BotApiMethod<?> handleInputMessage(Message message, long userId) {
        BotState botState;
        String inputMessage = message.getText();

        switch (inputMessage) {
            case "/start":
                botState = BotState.NEW_USER;
                break;
            case "Get Price":
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
