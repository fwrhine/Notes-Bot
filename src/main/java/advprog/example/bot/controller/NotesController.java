package advprog.example.bot.controller;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.MessageContentResponse;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;


@LineMessageHandler
public class NotesController {

    private static final Logger LOGGER = Logger.getLogger(NotesController.class.getName());

    @Autowired
    private LineMessagingClient lineMessagingClient;

    @EventMapping
    public MessageContentResponse handleImageMessageEvent(MessageEvent<ImageMessageContent> event) {
        // You need to install ImageMagick
        final MessageContentResponse response;
        try {
            response = lineMessagingClient.getMessageContent(event.getMessage().getId())
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

//        DownloadedContent jpg = saveContent("jpg", responseBody);

        return response;
    }

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        String replyText = contentText.replace("/echo", "");
        return new TextMessage(replyText.substring(1) + "yesseu");
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

//    private static DownloadedContent saveContent(String ext, MessageContentResponse responseBody) {
//        log.info("Got content-type: {}", responseBody);
//
//        DownloadedContent tempFile = createTempFile(ext);
//        try (OutputStream outputStream = Files.newOutputStream(tempFile.path)) {
//            ByteStreams.copy(responseBody.getStream(), outputStream);
//            log.info("Saved {}: {}", ext, tempFile);
//            return tempFile;
//        } catch (IOException e) {
//            throw new UncheckedIOException(e);
//        }
//    }
//
//    private static DownloadedContent createTempFile(String ext) {
//        String fileName = LocalDateTime.now().toString() + '-' + UUID.randomUUID().toString() + '.' + ext;
//        Path tempFile = KitchenSinkApplication.downloadedContentDir.resolve(fileName);
//        tempFile.toFile().deleteOnExit();
//        return new DownloadedContent(
//                tempFile,
//                createUri("/downloaded/" + tempFile.getFileName()));
//    }
//
//    @Value
//    public static class DownloadedContent {
//        Path path;
//        String uri;
//    }

    public String compVisionAPI(String uri) {
        String jsonString = "";
        return jsonString;
    }
}
