package lechat.server.domain.post.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class CreatePostReq {

    @NotNull
    @Schema(example = "1")
    private Long courseId;

    @NotBlank
    @Schema(example = "What is the 3 times 4?")
    private String content;
}
