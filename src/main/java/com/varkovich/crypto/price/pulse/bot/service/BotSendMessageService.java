package com.varkovich.crypto.price.pulse.bot.service;

import com.varkovich.crypto.price.pulse.bot.config.ApplicationContextProvider;
import com.varkovich.crypto.price.pulse.bot.model.TelegramBot;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@Log4j
public class BotSendMessageService {
    private TelegramBot bot;


    public void executeBotRequest(String text, long chatId) {
        bot = ApplicationContextProvider.getApplicationContext().getBean(TelegramBot.class);
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), text);

        try {
            bot.execute(sendMessage);
            log.info("Message: " + text + "has been sent to chatId:" + chatId);
        } catch (TelegramApiException exception) {
            log.error("Sending the message has failed.\n" + exception.getMessage());
        }
    }

    public void executeBotRequest(SendMessage sendMessage, long chatId) {
        bot = ApplicationContextProvider.getApplicationContext().getBean(TelegramBot.class);
        try {
            bot.execute(sendMessage);
            log.info("SendMessage has been sent to chatId:" + chatId);
        } catch (TelegramApiException exception) {
            log.error("Sending the message has failed.\n" + exception.getMessage());
        }
    }
}
