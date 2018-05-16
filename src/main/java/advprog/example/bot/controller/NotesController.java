package advprog.example.bot.controller;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.MessageContentResponse;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.io.InputStream;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

@LineMessageHandler
public class NotesController {

    private static final Logger LOGGER = Logger.getLogger(NotesController.class.getName());

    @Autowired
    private LineMessagingClient lineMessagingClient;

    @EventMapping
    public TextMessage handleImageMessageEvent(MessageEvent<ImageMessageContent> event) {
        // You need to install ImageMagick
        final MessageContentResponse response;
        try {
            response = lineMessagingClient.getMessageContent(event.getMessage().getId())
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        String result = compVisionApi(response.getStream());
        if (result.length() < 1) {
            result = "No text.";
        }

        return new TextMessage(result);
    }

    @EventMapping
    public TextMessage handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
        String reply;
        int random = new Random().nextInt();
        if (random % 2 == 0) {
            reply = "Notes only!";
        } else {
            reply = "Please send an image of your handwritten note!";
        }
        return new TextMessage(reply);
    }

    public String compVisionApi(InputStream inputStream) {
        String jsonString = CompVisionApi.extractHandwriting(inputStream);
        final JSONObject obj = new JSONObject(jsonString);
        StringBuilder result = new StringBuilder();

        final JSONObject recognitionResult = obj.getJSONObject("recognitionResult");
        final JSONArray lines = recognitionResult.getJSONArray("lines");

        for (int i = 0; i < lines.length(); i++) {
            JSONObject line = (JSONObject) lines.get(i);
            String text = line.getString("text");
            if (i > 0) {
                result.append("\n");
            }
            result.append(text);
        }
        return result.toString();
    }
}