package com.varkovich.crypto.price.pulse.bot.repositroy;

import com.varkovich.crypto.price.pulse.bot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByTelegramUserId(long telegramUserId);
}
