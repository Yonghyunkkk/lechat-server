package lechat.server.domain.post.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateCommentReq {

    @NotBlank
    @Schema(example = "That is incorrect.")
    private String content;
}
