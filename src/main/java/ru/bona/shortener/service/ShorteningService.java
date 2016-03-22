package ru.bona.shortener.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ShorteningService
 *
 * @author Kontsur Alex (bona)
 * @since 23.11.14
 */
@Component
public class ShorteningService {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final long BASE = ALPHABET.length();

    @Value("${shortener.parent.url}")
    private String parentURL;
    
    @Value("${jetty.port}")
    private String port;


    private static String encode(long num) {
        StringBuilder sb = new StringBuilder();
        long n = num;
        while (n > 0) {
            sb.append(ALPHABET.charAt((int) (n % BASE)));
            n /= BASE;
        }
        return sb.reverse().toString();
    }

    private static long decode(String str) {
        long num = 0;
        for (int i = 0, len = str.length(); i < len; i++) {
            num = num * BASE + ALPHABET.indexOf(str.charAt(i));
        }
        return num;
    }

    public String shortURL(long num) {
        return parentURL + ":" + port + "/" + encode(num);
    }

}
