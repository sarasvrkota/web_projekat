package com.Projekat_Web.Projekat_Web.service;

import java.util.Random;

public class Generator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 8;

    public static String generisiLozinku() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            char character = CHARACTERS.charAt(index);
            sb.append(character);
        }

        return sb.toString();
    }
}
