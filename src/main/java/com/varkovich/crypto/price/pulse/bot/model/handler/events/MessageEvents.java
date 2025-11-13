package com.varkovich.crypto.price.pulse.bot.model.handler.events;

import com.varkovich.crypto.price.pulse.bot.dao.UserDAO;
import com.varkovich.crypto.price.pulse.bot.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@AllArgsConstructor
public class MessageEvents {
    private final UserDAO userDAO;

    public void saveNewUser(Message message, long userId) {
        User user = User.builder()
                .telegramUserId(userId)
                .firstName(message.getFrom().getFirstName())
                .lastName(message.getFrom().getLastName())
                .username(message.getFrom().getUserName())
                .build();

        userDAO.saveNewUser(user);
    }
}
