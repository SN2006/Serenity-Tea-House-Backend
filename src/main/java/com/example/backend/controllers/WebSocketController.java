package com.example.backend.controllers;

import com.example.backend.dto.chatRoom.MessageDto;
import com.example.backend.entity.chatRoom.Message;
import com.example.backend.sercives.chatRoom.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins = {"http://localhost:3000/"})
public class WebSocketController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketController(MessageService messageService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/room")
    public void processMessage(@Payload Message message){
        MessageDto messageDto = messageService.saveMessage(message);
        messagingTemplate.convertAndSend(
                "/queue/messages/" + message.getChatRoom().getId(),
                messageDto
        );
    }

}
