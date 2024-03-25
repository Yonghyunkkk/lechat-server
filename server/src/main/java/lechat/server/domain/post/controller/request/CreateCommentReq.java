package lechat.server.domain.post.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateCommentReq {

    @NotNull
    @Schema(example = "1")
    private Long postId;

    @NotBlank
    @Schema(example = "Why is the answer like that?")
    private String content;
}
