package com.varkovich.crypto.price.pulse.bot.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum BotState {
    NEW_USER(0),
    START(1),
    GET_PRICE(2),
    SUPPORT(3);

    private final int numberOfState;
}
