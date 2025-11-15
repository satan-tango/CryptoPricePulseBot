package com.varkovich.crypto.price.pulse.bot.service.keyboard;

import com.varkovich.crypto.price.pulse.bot.utils.keyboard.InlineKeyboardUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Service
@AllArgsConstructor
public class InlineKeyboardService {
    private final InlineKeyboardUtils inlineKeyboardUtils;

    public SendMessage generateInlineKeyboardForPickCryptoToGetPrice(long chatID, int numberOfState) {
        InlineKeyboardMarkup keyboardMarkup = inlineKeyboardUtils.getInlineKeyboardMarkupForPickCryptoToGetPrice(numberOfState);
        SendMessage message = new SendMessage();

        message.setChatId(chatID);
        message.setText("Pick crypto to get price");
        message.setReplyMarkup(keyboardMarkup);

        return message;
    }
}
