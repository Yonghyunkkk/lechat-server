package lechat.server.domain.post.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdatePostReq {

    @NotBlank
    @Schema(example = "What is the answer for this question?")
    private String content;
}
