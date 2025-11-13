package com.varkovich.crypto.price.pulse.bot.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class MessageUtils {

    public SendMessage generateSendMessage(long chatId, String textMessage) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textMessage);

        return sendMessage;
    }
}
