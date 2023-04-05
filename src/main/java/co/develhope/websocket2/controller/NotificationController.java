package co.develhope.websocket2.controller;

import co.develhope.websocket2.entities.ClientMessageDto;
import co.develhope.websocket2.entities.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class NotificationController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/broadcast-message")
    public ResponseEntity sendNotificationToClient(@RequestBody MessageDto messageDto){
        simpMessagingTemplate.convertAndSend("/topic/broadcast",messageDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @MessageMapping("/client-message")
    @SendTo("/topic/broadcast")
    public MessageDto handleMessageFromWebSocket(ClientMessageDto clientMessageDTO){
        System.out.println("Arrived something on /app/hello -" + clientMessageDTO.getClientAlert() + " " + clientMessageDTO.getClientMessage());
        return new MessageDto("Message from client: " + clientMessageDTO.getClientMessage(), "- message: " + clientMessageDTO.getClientName(), "allert: " + clientMessageDTO.getClientAlert());

    }

}
