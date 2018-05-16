package advprog.example.bot.controller;

import advprog.example.bot.EventTestUtil;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(properties = "line.bot.handler.enabled=false")
@ExtendWith(SpringExtension.class)
public class NotesControllerTest {

    static {
        System.setProperty("line.bot.channelSecret", "SECRET");
        System.setProperty("line.bot.channelToken", "TOKEN");
    }

    @Autowired
    private NotesController notesController;

    @Test
    void testContextLoads() {
        assertNotNull(notesController);
    }

    @Test
    void testHandleTextMessageEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/echo Lorem Ipsum");

        TextMessage reply = notesController.handleTextMessageEvent(event);

        assertEquals("Lorem Ipsum", reply.getText());
    }

        @Test
        void testCompVisionApi() {
            String result = notesController.compVisionApi(EventTestUtil.createDummyImageInputStream());

            assertEquals("Our greatest glory is not\nin never failing ,\nbut "
                    + "in rising every time we fall", result);
        }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        notesController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }
}