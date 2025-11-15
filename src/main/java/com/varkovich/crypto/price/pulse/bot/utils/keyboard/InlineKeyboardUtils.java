package com.varkovich.crypto.price.pulse.bot.utils.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class InlineKeyboardUtils {

    public InlineKeyboardMarkup getInlineKeyboardMarkupForPickCryptoToGetPrice(int numberOfState) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> listOfButtons = new ArrayList<>();
        List<InlineKeyboardButton> buttons;

        buttons = new ArrayList<>();
        InlineKeyboardButton BTCButton = new InlineKeyboardButton();
        BTCButton.setText("✅BTC");
        BTCButton.setCallbackData("BTC:" + numberOfState);
        buttons.add(BTCButton);
        listOfButtons.add(buttons);

        buttons = new ArrayList<>();
        InlineKeyboardButton ETHButton = new InlineKeyboardButton();
        ETHButton.setText("✅ETH");
        ETHButton.setCallbackData("ETH:" + numberOfState);
        buttons.add(ETHButton);
        listOfButtons.add(buttons);

        buttons = new ArrayList<>();
        InlineKeyboardButton SOLButton = new InlineKeyboardButton();
        SOLButton.setText("✅SOL");
        SOLButton.setCallbackData("SOL:" + numberOfState);
        buttons.add(SOLButton);
        listOfButtons.add(buttons);

        inlineKeyboardMarkup.setKeyboard(listOfButtons);

        return inlineKeyboardMarkup;
    }
}
