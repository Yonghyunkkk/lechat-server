package lechat.server.domain.auth.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailValReq {

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email address has to be end with @connect.hku.hk")
    @Schema(example = "bob@connect.hku.hk")
    private String email;
}
