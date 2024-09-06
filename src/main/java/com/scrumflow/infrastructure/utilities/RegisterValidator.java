package com.scrumflow.infrastructure.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class RegisterValidator {
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
    private static final Pattern PATTERN = Pattern.compile(PASSWORD_REGEX);

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean isPassValid(String password) {
        if (password == null) {
            return false;
        }

        Matcher matcher = PATTERN.matcher(password);
        return matcher.matches();
    }

    public static boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}
