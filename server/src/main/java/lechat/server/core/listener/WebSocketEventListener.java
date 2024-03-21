package lechat.server.core.listener;

import lechat.server.domain.chat.controller.dto.ChatMessageDto;
import lechat.server.domain.chat.controller.dto.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener(
            SessionDisconnectEvent event
    ) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        String chatroomId = (String) headerAccessor.getSessionAttributes().get("chatroomId");

        if (username != null && chatroomId != null) {
            log.info("User disconnected: {}", username);
            var chatMessage = ChatMessageDto.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();

            messageTemplate.convertAndSend("/topic/chatrooms/" + chatroomId, chatMessage);
        }
    }
}
