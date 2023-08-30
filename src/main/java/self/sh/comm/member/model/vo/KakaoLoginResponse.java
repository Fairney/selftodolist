package self.sh.comm.member.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KakaoLoginResponse {
    private String access_token; 
    private String expires_in;   
    private String refreshToken;    
    private String scope;
    private String token_type;   
    private String id_token;
}
