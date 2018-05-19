package advprog.example.bot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GlobalValue {

    public static String subscriptionKey;

    @Value("${subscription-key}")
    public void setSubscriptionKey(String value) {
        subscriptionKey = value;
    }

}