package ch.uzh.ifi.hase.soprafs21.websockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class DocumentController {

    @Autowired
    private SimpMessagingTemplate webSocketMessagingTemplate;

    @MessageMapping("/documents")
    public SharedDocument getDocument(SharedDocument document) {
        webSocketMessagingTemplate.convertAndSend("/topic/" + document.getGroupId().toString() + ".public.document", document);

        return document;
    }

    //testing TODO: remove later
    @MessageMapping("/user-all")
    @SendTo("/topic/user")
    public MessageBean sendToAll(@Payload MessageBean message) {
        return message;
    }


}
