package self.sh.comm.member.controller;

import java.util.List;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;
import self.sh.comm.member.model.service.MemberService;
import self.sh.comm.member.model.vo.Member;
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
	
	
}
