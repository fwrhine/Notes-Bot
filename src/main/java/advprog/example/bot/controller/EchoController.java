package advprog.example.bot.controller;

import java.io.IOException;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.logging.Logger;

@LineMessageHandler
public class EchoController {

    private static final Logger LOGGER = Logger.getLogger(EchoController.class.getName());

//    @EventMapping
//    public void handleImageMessageEvent(MessageEvent<ImageMessageContent> event) throws IOException {
//        // You need to install ImageMagick
//        handleHeavyContent(
//                event.getReplyToken(),
//                event.getMessage().getId(),
//                responseBody -> {
//                    DownloadedContent jpg = saveContent("jpg", responseBody);
//                    DownloadedContent previewImg = createTempFile("jpg");
//                    system(
//                            "convert",
//                            "-resize", "240x",
//                            jpg.path.toString(),
//                            previewImg.path.toString());
//                    reply(((MessageEvent) event).getReplyToken(),
//                            new ImageMessage(jpg.getUri(), jpg.getUri()));
//                });
//    }

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        String replyText = contentText.replace("/echo", "");
        return new TextMessage(replyText.substring(1));
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }
}
