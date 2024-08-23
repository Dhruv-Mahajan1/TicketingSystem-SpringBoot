package com.example.ticketingsystem.annotations.impl;

import com.example.ticketingsystem.annotations.ValidateHeader;
import com.example.ticketingsystem.entity.User;
import com.example.ticketingsystem.exceptions.NotFoundException;
import com.example.ticketingsystem.repository.UserRepo;
import com.example.ticketingsystem.util.Token;
import com.example.ticketingsystem.util.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Aspect
@Component
public class ValidateHeaderImp {

    @Autowired
    UserRepo userRepo;




    @Before("@within(validateHeader)")
    public void validate(JoinPoint joinPoint, ValidateHeader validateHeader) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");

        User user = Token.validateToken(token, this.userRepo);
        if (user == null) {
            throw new NotFoundException("user not found");
        } else {
            UserContext.setUser(user);
        }
    }

    @After("@within(validateHeader)")
    public void clearMDC() {
        UserContext.clear();
    }
}
