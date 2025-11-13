package com.varkovich.crypto.price.pulse.bot.cash;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Log4j
public class UserCash {
    private final Map<Long, Boolean> inSystem = new HashMap<>();

    public boolean isUserInSystem(long userId) {
        if (!inSystem.containsKey(userId)) {
            log.info("User with ID " + userId + " is not in local cash.");
            return false;
        }

        if (inSystem.containsKey(userId) && !inSystem.get(userId)) {
            log.info("User with ID " + userId + " is not in local cash.");
            return false;
        }
        log.info("User with ID " + userId + " is in local cash.");
        return true;
    }

    public void addUserInSystem(long userId) {
        inSystem.put(userId, true);
        log.info("User with ID " + userId + " has been saved to local cash.");
    }

    public void removeUserFromSystem(long userId) {
        inSystem.remove(userId);
        log.info("User with ID " + userId + " has been removed from local cash.");
    }
}
