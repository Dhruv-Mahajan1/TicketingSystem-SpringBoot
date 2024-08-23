package com.example.ticketingsystem.util;


import com.example.ticketingsystem.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserContext {
    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    public static void setUser(User user) {
        currentUser.set(user);
    }

    public static User getUser() {
        return (User)currentUser.get();
    }

    public static void clear() {
        currentUser.remove();
    }
}
