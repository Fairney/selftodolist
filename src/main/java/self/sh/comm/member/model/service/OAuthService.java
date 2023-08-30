package self.sh.comm.member.model.service;
import com.google.gson.JsonParser;

import self.sh.comm.member.KakaoOAuthApi;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.JsonElement;

import org.codehaus.plexus.component.annotations.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpSession;

@Service
public class OAuthService{


	@Value("${kakao.auth.url}")
	private String kakaoAuthUrl;

	@Value("${kakao.login.url}")
	private String kakaoLoginUrl;

	@Value("${kakao.clientId}")
	private String kakaoClientId;

	@Value("${kakao.redirectUri}")
	private String kakaoRedirectUrl;

	@Value("${kakao.secret}")
	private String kakaoClientSecret;
	public OAuth2AccessToken getAccessToken(HttpSession session,String code)throws Exception {
//		String sessionState=getSession(session);
//        System.out.println(sessionState);
       
//		if(StringUtils.pathEquals(sessionState, state)) {
//			OAuth20Service oauthService=new ServiceBuilder()
//					.apiKey(KAKAO_CLIENT_ID)
//					.apiSecret(KAKAO_CLIENT_SECRET)
//					.callback(KAKAO_REDIRECT_URI)
//					.state(state).build(KakaoOAuthApi.instance());
//			OAuth2AccessToken accessToken =oauthService.getAccessToken(code);
//			return accessToken;
//			
//		}
		OAuth20Service oauthService=new ServiceBuilder()
				.apiKey(kakaoClientId)
				.apiSecret(kakaoClientSecret)
				.callback(kakaoRedirectUrl)
				.build(KakaoOAuthApi.instance());
		OAuth2AccessToken accessToken =oauthService.getAccessToken(code);
		return accessToken;
	}
//	 private String getSession(HttpSession session) {
//		 return (String)session.getAttribute(KAKAO_SESSION_STATE);
//	 }
	public String getUserProfile(OAuth2AccessToken oauthToken)throws Exception {
		OAuth20Service oauthService=new ServiceBuilder()
				.apiKey(kakaoClientId)
				.apiSecret(kakaoClientSecret)
				.callback(kakaoRedirectUrl)
				.build(KakaoOAuthApi.instance());
		OAuthRequest request=new OAuthRequest(Verb.GET,"https://kapi.kakao.com/v2/user/me",oauthService);
		oauthService.signRequest(oauthToken, request);
		Response response=request.send();
		return response.getBody();
		
		
		
		
		}
}