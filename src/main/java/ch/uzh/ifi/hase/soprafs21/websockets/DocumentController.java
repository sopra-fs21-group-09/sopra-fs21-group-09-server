package ch.uzh.ifi.hase.soprafs21.websockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    private SimpMessagingTemplate webSocketMessagingTemplate;

    DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @MessageMapping("/{groupId}/user-all")
    public Message sendToAll(@DestinationVariable("groupId") Long groupId, @Payload Message message) {
        webSocketMessagingTemplate.convertAndSend("/topic/" + groupId.toString() + "/user", message);

        return message;
    }

    @GetMapping("/documents/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SharedDocument getDocument(@PathVariable("id") Long id) {
        return documentService.getDocument(id);
    }

    @PatchMapping("/documents")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void updateDocument(@RequestBody SharedDocument document) {
        documentService.updateDocument(document);
    }
}
