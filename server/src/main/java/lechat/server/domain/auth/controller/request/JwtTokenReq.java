package lechat.server.domain.auth.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JwtTokenReq {
    private String accessToken;
    private String refreshToken;
}
