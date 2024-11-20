package tn.esprit.gnbapp.controllers;
import lombok.RequiredArgsConstructor;
import tn.esprit.gnbapp.entities.ChatMessage;
import tn.esprit.gnbapp.entities.MessageType;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;



@Controller
@RequiredArgsConstructor
public class ChatController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @MessageMapping("/chat/register")
    @SendTo("/topic/public")
    public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        System.out.println("chatMessage.content");
        return chatMessage;
    }

    @MessageMapping("/chat/send")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        System.out.println("chatMessage.content");
        return chatMessage;
    }



    @MessageMapping("/secured/chat")
    @SendTo("/secured/history")
    public  ChatMessage send( ChatMessage msg) throws Exception {
        return new ChatMessage(
                msg.getSender(),"",
                msg.getContent(),MessageType.CHAT);
    }

    @MessageMapping("/secured/room")
    public void sendSpecific(@Payload ChatMessage msg, Principal user, @Header("simpSessionId") String sessionId) throws Exception {
        ChatMessage out = new ChatMessage(msg.getSender(), msg.getContent(),"",MessageType.CHAT );
        simpMessagingTemplate.convertAndSendToUser(msg.getRecipient(), "/secured/user", out);
    }




}
