package advprog.example.bot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GlobalValue {

    public static String subscriptionKey;
    public static String endpoint;

    @Value("${subscription_key}")
    public void setSubscriptionKey(String value) {
        subscriptionKey = value;
    }

    @Value("${endpoint}")
    public void setEndpoint(String value) {
        endpoint = value;
    }

}