package com.tinder.tinder.controller;


import com.tinder.tinder.dto.request.ChatMessage;
import com.tinder.tinder.service.MessageEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final MessageEntityService messageService;

    private final SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/chat.send/{matchId}")
    public void sendMessage(@DestinationVariable Long matchId, ChatMessage message) {
        ChatMessage saved = messageService.sendMessage(matchId, message.getSenderId(), message.getContent());
        log.info("Đã log đên đây rồi");
        messagingTemplate.convertAndSend("/topic/chat.match." + matchId, saved);
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public String sendMessage(String message) {
        System.out.println("Received: " + message);
        return "Server reply: " + message;
    }
}
