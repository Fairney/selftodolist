package self.sh.comm.member.model.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KakaoOAuthRequest {
    private String redirectUri;
    private String client_id;
    private String client_secret;
    private String code;
    private String responseType;
    private String scope;
    private String accessType;
    private String grantType;
    private String state;
    private String includeGrantedScopes;
    private String loginHint;
    private String prompt;
}
