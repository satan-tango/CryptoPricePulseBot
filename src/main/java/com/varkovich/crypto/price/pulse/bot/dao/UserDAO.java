package com.varkovich.crypto.price.pulse.bot.dao;

import com.varkovich.crypto.price.pulse.bot.entity.User;
import com.varkovich.crypto.price.pulse.bot.repositroy.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDAO {
    private final UserRepository userRepository;

    public boolean existsByTelegramUserId(long telegramUserId) {
        return userRepository.existsByTelegramUserId(telegramUserId);
    }

    public void saveNewUser(User user) {
        userRepository.save(user);
    }
}
