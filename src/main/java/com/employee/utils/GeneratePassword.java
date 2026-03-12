package com.employee.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeneratePassword {

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGIT = "0123456789";
    private static final String SPECIAL = "@$!%*?&";

    private static final String ALL = UPPER + LOWER + DIGIT + SPECIAL;

    private static final SecureRandom random = new SecureRandom();

    public static String generatePassword(int length) {

        if (length < 8) {
            throw new IllegalArgumentException("Password length must be at least 8");
        }

        List<Character> password = new ArrayList<>();

        // Ensure at least one from each category
        password.add(UPPER.charAt(random.nextInt(UPPER.length())));
        password.add(LOWER.charAt(random.nextInt(LOWER.length())));
        password.add(DIGIT.charAt(random.nextInt(DIGIT.length())));
        password.add(SPECIAL.charAt(random.nextInt(SPECIAL.length())));

        // Fill remaining characters
        for (int i = 4; i < length; i++) {
            password.add(ALL.charAt(random.nextInt(ALL.length())));
        }

        // Shuffle characters
        Collections.shuffle(password);

        // Convert to string
        StringBuilder sb = new StringBuilder();
        for (char c : password) {
            sb.append(c);
        }

        return sb.toString();
    }
}