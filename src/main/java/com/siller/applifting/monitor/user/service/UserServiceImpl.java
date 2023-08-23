package com.siller.applifting.monitor.user;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User getUserByToken(String token) throws UserNotFound {
        return switch (token) {
            case "93f39e2f-80de-4033-99ee-249d92736a25" -> new User("Applifting");
            case "dcb20f8a-5657-4f1b-9f7f-ce65739b359e" -> new User("Batman");
            default -> null;
        };
    }
}
