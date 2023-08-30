package self.sh.comm.member.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.model.OAuth2AccessToken;

import lombok.extern.slf4j.Slf4j;
import self.sh.comm.member.model.service.MemberService;
import self.sh.comm.member.model.service.OAuthService;
import self.sh.comm.member.model.vo.GoogleLoginResponse;
import self.sh.comm.member.model.vo.GoogleMember;
import self.sh.comm.member.model.vo.GoogleOAuthRequest;
import self.sh.comm.member.model.vo.KakaoLoginResponse;
import self.sh.comm.member.model.vo.KakaoMember;
import self.sh.comm.member.model.vo.KakaoOAuthRequest;
import self.sh.comm.member.model.vo.Member;
import self.sh.comm.todolist.controller.TodoListController;
import self.sh.comm.todolist.model.service.TodolistService;
import self.sh.comm.todolist.model.vo.Todolist;

@Controller
@RequestMapping("/member") // localhost:8080/comm/member �씠�븯�쓽 �슂泥��쓣 泥섎━�븯�뒗 而⑦듃濡ㅻ윭

@SessionAttributes({ "loginMember" }) // Model�뿉 異붽��맂 媛믪쓽 key�� �뼱�끂�뀒�씠�뀡�뿉 �옉�꽦�맂 媛믪씠 媛숈쑝硫�
										// �빐�떦 媛믪쓣 session scope �씠�룞�떆�궎�뒗 �뿭�븷
@Slf4j
public class MemberController {
	@Autowired
	private MemberService service;
	@Autowired
	private TodolistService todolistService;
	@Autowired
	private OAuthService oauthService;
	private Logger logger = LoggerFactory.getLogger(TodoListController.class);
	
	@Value("${google.auth.url}")
	private String googleAuthUrl;

	@Value("${google.login.url}")
	private String googleLoginUrl;

	@Value("${google.client.id}")
	private String googleClientId;

	@Value("${google.redirect.url}")
	private String googleRedirectUrl;

	@Value("${google.secret}")
	private String googleClientSecret;
	
	
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
	private String apiResult = null;
	@PostMapping("/login")
	public String login(@ModelAttribute Member inputMember, Model model, RedirectAttributes ra,
			HttpServletResponse resp, HttpServletRequest req,
			@RequestParam(name = "saveId", required = false) String saveId,
			Map<String,Object> todolist
						) {
		Member loginMember = service.login(inputMember);
		
		/*
		 * Model : 데이터를 맵 형식(K:V) 형태로 담아 전달하는 용도의 객체 -> request, session을 대체하는 객체
		 * 
		 * - 기본 scope : request - session scope로 변환하고 싶은 경우 클래스 레벨로 @SessionAttributes를
		 * 작성되면 된다.
		 */

		// @SessionAttributes 미작성 -> request scope

		if (loginMember != null) { // 로그인 성공 시
			model.addAttribute("loginMember", loginMember); // == req.setAttribute("loginMember", loginMember);

			System.out.println("로그인됨");

			// 로그인 성공 시 무조건 쿠키 생성
			// 단, 아이디 저장 체크 여부에 따라서 쿠기의 유지 시간을 조정
			Cookie cookie = new Cookie("saveId", loginMember.getMemberEmail());

			if (saveId != null) { // 아이디 저장이 체크 되었을 때
				 
				cookie.setMaxAge(60 * 60 * 24 * 365); // 초 단위로 지정 (1년)
			    cookie.setValue(loginMember.getMemberEmail());

			} else { // 체크 되지 않았을 때
				 cookie.setMaxAge(0); // 0초 -> 생성되자마자 사라짐 == 쿠키 삭제
				    cookie.setValue("");
//				    inputMember.setMemberEmail(""); // 아이디 초기화
//				    model.addAttribute("saveId", null); // 체크박스에 체크가 없도록 설정
				    
			}

			// 쿠키가 적용될 범위(경로) 지정
			cookie.setPath(req.getContextPath() + "/member/login");

			// 쿠키를 응답 시 클라이언트에게 전달
			resp.addCookie(cookie);
			List<Todolist> temp = todolistService.selectTodayList(loginMember.getMemberNo());
			todolist.put("today",temp);
			temp = todolistService.selectWeekList(loginMember.getMemberNo());
			todolist.put("week", temp);
			model.addAttribute("todolist",todolist);
			
			
			return "todolist/todolist";

		} else {
			// model.addAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다.");

			ra.addFlashAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
			System.out.println("로그인안됨");
			// -> redirect 시 잠깐 Session scope로 이동후
			// redirect가 완료되면 다시 Request scope로 이동

			// redirect 시에도 request scope로 세팅된 데이터가 유지될 수 있도록 하는 방법을
			// Spring에서 제공해줌
			// -> RedirectAttributes 객체 (컨트롤러 매개변수에 작성하면 사용 가능)

			return "redirect:/member/login";
		}
	}
	@GetMapping("/signUp")
	public String goSignUp() {
		return "member/signUp";
	}
	@ResponseBody
	@GetMapping("/idDupCheck")
	public int idDupCheck(Member member,
						  @RequestParam(value = "memberId")String memberId) {
		int result = 0;
		member.setMemberId(memberId);
		result = service.idDupCheck(member);
		return result;
		
	}
	@ResponseBody
	@GetMapping("/nickDupCheck")
	public int nickDupCheck(Member member) {
		int result = service.nickDupCheck(member);
		return result;
	}
	@ResponseBody
	@GetMapping("/emailDupCheck")
	public int emailDupCheck(Member member) {
		int result = service.emailDupCheck(member);
		return result;
	}
	@PostMapping("/signUp")
	public String signUp(Member signUpMember,String[] memberAddr,
			@RequestParam(value = "emailOptIn", defaultValue = "N") String emailOptIn, HttpServletRequest request,
			RedirectAttributes ra) {
		signUpMember.setMemberAddr(String.join(" ", memberAddr));
		signUpMember.setEmailOptIn(emailOptIn);
		int result = service.signUp(signUpMember);
		String direction = "";
		if(result == 0) {
			direction = "member/signUp";
		}else {
			direction = "common/main";
		}
		return direction;
	}
	
	@GetMapping("/getGoogleAuthUrl")
	public ResponseEntity<?> getGoogleAuthUrl(HttpServletRequest request) throws Exception {

		String reqUrl = googleLoginUrl + "/o/oauth2/v2/auth?client_id=" + googleClientId + "&redirect_uri="
				+ googleRedirectUrl + "&response_type=code&scope=email%20profile%20openid&access_type=offline";

		logger.info("myLog-LoginUrl : {}", googleLoginUrl);
		logger.info("myLog-ClientId : {}", googleClientId);
		logger.info("myLog-RedirectUrl : {}", googleRedirectUrl);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create(reqUrl));

		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}

	@GetMapping("/oauth_google_check")
	public String oauth_google_check(HttpServletRequest request, @RequestParam(value = "code") String authCode,
			Model model, Member member) throws Exception {
		int isLogin = 0;
		String googleUid = null;

		GoogleOAuthRequest googleOAuthRequest = GoogleOAuthRequest.builder().clientId(googleClientId)
				.clientSecret(googleClientSecret).code(authCode).redirectUri(googleRedirectUrl)
				.grantType("authorization_code").build();

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<GoogleLoginResponse> apiResponse = restTemplate.postForEntity(googleAuthUrl + "/token",
				googleOAuthRequest, GoogleLoginResponse.class);
		GoogleLoginResponse googleLoginResponse = apiResponse.getBody();

		logger.info("responseBody {}", googleLoginResponse.toString());

		String googleToken = googleLoginResponse.getId_token();

		String requestUrl = UriComponentsBuilder.fromHttpUrl(googleAuthUrl + "/tokeninfo")
				.queryParam("id_token", googleToken).toUriString();

		String resultJson = restTemplate.getForObject(requestUrl, String.class);
		ObjectMapper objectMapper = new ObjectMapper();
		GoogleMember googleMember = objectMapper.readValue(resultJson, GoogleMember.class);
		member.setMemberId(googleMember.getEmail());
		member.setSocialType("google");
		String pattern = "\\((.*?)\\)";

		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(googleMember.getName());

		if (matcher.find()) {
			String memberNick = matcher.group(1);
			member.setMemberNick(memberNick);
			//member.setMemberName(googleMember.getName().substring(0, googleMember.getName().indexOf("(")));
		}

		isLogin = service.selectApiMemberCount(member);
		if (isLogin > 0) {
			// 로그인 유저가 있으면 로그인을 진행.
			Member loginMember = service.selectApiMember(member);
			model.addAttribute("loginMember", loginMember);

			return "todolist/todolist";
		} else {
			int signUpInt = service.snssignUp(member);
			Member loginMember = member;
			model.addAttribute("loginMember", member);
			// int signUp = service.insertApiMember(googleMember);
			return "todolist/todolist";
		}
	}
	
	@GetMapping("/getKaKaoAuthUrl")
	public ResponseEntity<?> getKakaoAuthUrl(HttpServletRequest request) throws Exception {
		//https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}
		String reqUrl = kakaoAuthUrl + "?response_type=code&client_id=" + kakaoClientId + "&redirect_uri="
				+ kakaoRedirectUrl;

		logger.info("myLog-LoginUrl : {}", kakaoAuthUrl);
		logger.info("myLog-ClientId : {}", kakaoClientId);
		logger.info("myLog-RedirectUrl : {}", kakaoRedirectUrl);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create(reqUrl));

		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}

	@GetMapping("/oauth_kakao_check")
	public String oauth_kakao_check(HttpSession session,HttpServletRequest request, @RequestParam(value = "code") String authCode,
			Model model, Member member) throws Exception {
		int isLogin = 0;
		String kakaoUid = null;
		logger.info(authCode);
//		KakaoOAuthRequest kakaoOAuthRequest = KakaoOAuthRequest.builder().client_id(kakaoClientId)
//				.code(authCode).redirectUri(kakaoRedirectUrl)
//				.grantType("authorization_code").build();
//				//.client_secret(kakaoClientSecret)
//
//		RestTemplate restTemplate = new RestTemplate();
//		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
//		ResponseEntity<KakaoLoginResponse> apiResponse = restTemplate.postForEntity(kakaoLoginUrl + "/token",
//				kakaoOAuthRequest, KakaoLoginResponse.class);
//		KakaoLoginResponse kakaoLoginResponse = apiResponse.getBody();//POST방식임.
//
//		
//		logger.info("responseBody {}", kakaoLoginResponse.toString());
//
//		String kakaoToken = kakaoLoginResponse.getId_token();
//
//		String requestUrl = UriComponentsBuilder.fromHttpUrl(kakaoAuthUrl + "/tokeninfo")
//				.queryParam("id_token", kakaoToken).toUriString();
		
		OAuth2AccessToken oauthToken;
		oauthToken = oauthService.getAccessToken(session, authCode);
		
		/* 로그인 사용자 정보를 읽어옵니다. */
		apiResult = oauthService.getUserProfile(oauthToken);

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObj;

		jsonObj = (JSONObject) jsonParser.parse(apiResult);
		JSONObject response_obj = (JSONObject) jsonObj.get("kakao_account");
		JSONObject response_obj2 = (JSONObject) response_obj.get("profile");

//		String resultJson = restTemplate.getForObject(requestUrl, String.class);
//		ObjectMapper objectMapper = new ObjectMapper();
//		KakaoMember kakaoMember = objectMapper.readValue(resultJson, KakaoMember.class);
		// 프로필 조회
				String email = (String) response_obj.get("email");
//				String nickname = (String) response_obj2.get("nickname");
				
				String socialType = "kakao";

				logger.info("Email: " + email);
				//logger.info("nickname: " + nickname);

				logger.info("socialType:" + socialType);
		//member.setMemberId(kakaoMember.getEmail());
		member.setSocialType("google");
		member.setMemberId(email);
//		String pattern = "\\((.*?)\\)";
//
//		Pattern regex = Pattern.compile(pattern);
//		Matcher matcher = regex.matcher(kakaoMember.getName());
//
//		if (matcher.find()) {
//			String memberNick = matcher.group(1);
//			member.setMemberNick(memberNick);
//			//member.setMemberName(googleMember.getName().substring(0, googleMember.getName().indexOf("(")));
//		}
	
		isLogin = service.selectApiMemberCount(member);
		if (isLogin > 0) {
			// 로그인 유저가 있으면 로그인을 진행.
			Member loginMember = service.selectApiMember(member);
			model.addAttribute("loginMember", loginMember);

			return "todolist/todolist";
		} else {
			int signUpInt = service.snssignUp(member);
			Member loginMember = member;
			model.addAttribute("loginMember", member);
			// int signUp = service.insertApiMember(googleMember);
			return "todolist/todolist";
		}
	}
	
	
}
