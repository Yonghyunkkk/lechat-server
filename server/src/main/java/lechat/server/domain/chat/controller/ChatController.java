package lechat.server.domain.chat.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lechat.server.domain.chat.controller.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ChatController {

    @MessageMapping("/chatrooms/{chatroomId}/message")
    @SendTo("/topic/chatrooms/{chatroomId}")
    public ChatMessageDto sendMessage(
            @DestinationVariable String chatroomId,
            @Payload ChatMessageDto chatMessageDto) {
        return chatMessageDto;
    }

    @MessageMapping("/chatrooms/{chatroomId}/member")
    @SendTo("/topic/chatrooms/{chatroomId}")
    public ChatMessageDto addMember(
            @DestinationVariable String chatroomId,
            @Payload ChatMessageDto chatMessageDto,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessageDto.getSender());
        headerAccessor.getSessionAttributes().put("chatroomId", chatroomId);

        return chatMessageDto;
    }
}
