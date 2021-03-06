package advprog.example.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import advprog.example.bot.EventTestUtil;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

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
    void testCompVisionApi() {
        String result = notesController.compVisionApi(EventTestUtil.createDummyImageInputStream());

        assertEquals("OUR greatest glory is not\nin never failing\nbut "
                + "in rising every time we fall", result);
    }

    @Test
    void testExtractHandwritingError() {
        String result = CompVisionApi
                .extractHandwriting(EventTestUtil.createDummyTextInputStream());

        assertEquals("Error.", result);
    }

    @Test
    void testHandleDefaultMessage() {
        Event event = mock(Event.class);

        notesController.handleDefaultMessage(event);

        verify(event, atLeastOnce()).getSource();
        verify(event, atLeastOnce()).getTimestamp();
    }

    @Test
    void testHandleEchoMessageEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("/echo Lorem Ipsum");

        TextMessage reply = notesController.handleTextMessageEvent(event);

        assertEquals("Lorem Ipsum", reply.getText());
    }

    @Test
    void testHandleTextMessageEvent() {
        MessageEvent<TextMessageContent> event =
                EventTestUtil.createDummyTextMessage("Lorem Ipsum");

        TextMessage reply = notesController.handleTextMessageEvent(event);

        assertTrue("Please send an image of your handwritten note!".equals(reply.getText())
                || "Notes only!".equals(reply.getText()));
    }
}