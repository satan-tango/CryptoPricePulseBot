package com.varkovich.crypto.price.pulse.bot.cash;

import com.varkovich.crypto.price.pulse.bot.utils.enums.BotState;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BotStateCash {
    private final Map<Long, List<BotState>> cashState = new HashMap<>();

    public void saveCashState(long userId, BotState state) {
        if (cashState.containsKey(userId)) {
            List<BotState> states = cashState.get(userId);
            if (states.get(states.size() - 1) != state) {
                //Чтобы количество состояние не превышало 15
                //Удаляю первое состояние
                if (states.size() > 15) {
                    states.remove(0);
                }
                states.add(state);
                cashState.put(userId, states);
            }


        } else {
            if (state == BotState.START) {
                cashState.put(userId, new ArrayList<>(Arrays.asList(BotState.START)));
            } else {
                cashState.put(userId, new ArrayList<>(Arrays.asList(BotState.START, state)));
            }
        }
    }

    public Optional<BotState> getCurrentState(long userId) {
        if (cashState.containsKey(userId)) {
            return Optional.of(cashState.get(userId).getLast());
        } else {
            return Optional.empty();
        }
    }

    public void cleaningCashState(long userId) {
        cashState.put(userId, new ArrayList<>(Arrays.asList(BotState.START)));
    }
}
