package self.sh.comm.main.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MainController {

	
	/*
	 * http://localhost:8080/comm/main
	 * */
	@RequestMapping("/main")
	public String login(Model model, HttpSession session,HttpServletRequest request) {
		

	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if (cookie.getName().equals("saveId")) {
	                if (cookie.getValue() != null && !cookie.getValue().isEmpty()) {
	                    model.addAttribute("saveId", "checked");
	                }
	                break;
	            }
	        }
	    }
		return "common/main"; //View Resolver에게 이동
		
	}
		
	
}
