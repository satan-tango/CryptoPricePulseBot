package com.varkovich.crypto.price.pulse.bot.model.handler;

import com.varkovich.crypto.price.pulse.bot.cash.BotStateCash;
import com.varkovich.crypto.price.pulse.bot.cash.UserCash;
import com.varkovich.crypto.price.pulse.bot.dao.UserDAO;
import com.varkovich.crypto.price.pulse.bot.model.handler.events.MessageEvents;
import com.varkovich.crypto.price.pulse.bot.service.BotSendMessageService;
import com.varkovich.crypto.price.pulse.bot.service.keyboard.MenuService;
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
    private final BotStateCash botStateCash;
    private final MenuService menuService;
    private final BotSendMessageService botSendMessageService;
    private final UserCash userCash;

    public BotApiMethod<?> handleInputMessage(Message message, BotState botState) {
        long userId = message.getFrom().getId();
        long chatId = message.getChatId();

        if (!userCash.isUserInSystem(userId)) {
            if (!userDAO.existsByTelegramUserId(userId)) {
                messageEvents.saveNewUser(message, userId);
                log.info("User with id " + userId + " has been saved");
            }
            userCash.addUserInSystem(userId);
        }


        botStateCash.saveBotState(userId, botState);

        switch (botState.name()) {
            case "NEW_USER" -> {
                botStateCash.cleaningBotState(userId);
                return menuService.generateMainMenu(userId, "Hello, this is a chat bot to get current crypto prices.");
            }
            case "START" -> {
                botStateCash.cleaningBotState(userId);
                return menuService.generateMainMenu(userId);
            }
            case "SUPPORT" -> {
                botSendMessageService.executeBotRequest("To ask a question or get any information, contact to [@ammor_faTtiI]: ", chatId);
                botStateCash.cleaningBotState(userId);
                return menuService.generateMainMenu(userId);
            }
            default -> {
                log.info("BotState " + botState.name() + " not recognized");
                botStateCash.cleaningBotState(userId);

                return null;
            }
        }

    }
}
