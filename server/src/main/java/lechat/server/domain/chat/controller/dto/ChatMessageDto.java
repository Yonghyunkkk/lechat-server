package lechat.server.domain.chat.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ChatMessageDto {

    private String content;

    @NotBlank
    private String sender;
    private MessageType type;
}
