package com.siller.applifting.monitor.user.service;

import com.siller.applifting.monitor.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    public UserServiceImpl(@Autowired UserRepository repository, @Autowired UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public User getUserByToken(String token) throws UserNotFound {
        User user = repository.findByToken(token);
        if(user == null){
            throw new UserNotFound();
        }
        return user;
    }

    @Override
    public User createUser(UserRegistration userRegistration) {
        User user = mapper.toUser(userRegistration, String.valueOf(UUID.randomUUID()));
        repository.save(user);
        return user;
    }
}
