package com.siller.applifting.monitor.user.service;

public interface UserService {

    User getUserByToken(String token) throws UserNotFound;

    User createUser(UserRegistration userRegistration);
}
