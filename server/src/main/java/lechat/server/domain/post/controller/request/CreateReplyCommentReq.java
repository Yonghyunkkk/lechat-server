package lechat.server.domain.post.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class CreateReplyCommentReq {

    @NotNull
    @Schema(example = "1")
    private Long commentId;

    @NotBlank
    @Schema(example = "The answer is 1 + 2.")
    private String content;
}
