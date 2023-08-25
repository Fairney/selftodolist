package self.sh.comm.todolist.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import self.sh.comm.member.model.vo.Member;
import self.sh.comm.todolist.model.service.TodolistService;
import self.sh.comm.todolist.model.service.TodolistServiceImpl;
import self.sh.comm.todolist.model.vo.Todolist;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Controller
@RequestMapping("/todolist")
public class TodoListController {
	
	private Logger logger = LoggerFactory.getLogger(TodoListController.class);
	@Autowired
	private TodolistService service;
	
		@ResponseBody
		@GetMapping("/choiceDate")
		public Map<String, Object> choiceDate(@RequestParam(value = "date")int date,
											  @RequestParam(value = "year")int year,
											  @RequestParam(value = "month")int month,
											  Todolist todolist,
											  HttpSession session
											  ){
			Map<String, Object> map = new HashMap<String, Object>();
			String sdate;
			String smonth;
			if(date<10) {
			sdate = "0"+ date;
			}else {
				sdate = ""+date;
			}
			if(month<10) {
				smonth = "0"+month;
			}else {
				smonth = ""+month;
			}
			String time = year + "-" + smonth + "-" + sdate;
			Member loginMember = (Member)session.getAttribute("loginMember");
			todolist.setMemberNo(loginMember.getMemberNo());
			todolist.setTodolistDate(time);
			List<Todolist> temp = service.choiceDateToday(todolist);
			map.put("todayList", temp);
			temp = service.choiceDateWeek(todolist);
			map.put("weekList", temp);
			logger.info("되냐");
			return map;
		}
		
		@PostMapping("/todayRegister")
		public String todayRegister(@RequestParam(value = "choicedate")int date,
									@RequestParam(value = "choiceyear")int year,
									@RequestParam(value = "choicemonth")int month,
									Todolist todolist,
									HttpSession session,
									Model model,RedirectAttributes ra) { 
		Map<String, Object> map = new HashMap<>();
			Member loginMember = (Member)session.getAttribute("loginMember");
		todolist.setMemberId(loginMember.getMemberId());
		todolist.setTodolist_Type(0);
		
//		 // 현재 날짜 구하기
//        LocalDate now = LocalDate.now();
// 
//        // 포맷 정의
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
// 
//        // 포맷 적용
//        String formatedNow = now.format(formatter);
		String sdate;
		String smonth;
		if(date<10) {
		sdate = "0"+ date;
		}else {
			sdate = ""+date;
		}
		if(month<10) {
			smonth = "0"+month;
		}else {
			smonth = ""+month;
		}
		String time = year + "-" + smonth + "-" + sdate;
        todolist.setTodolistDate(time);
        int result = service.todayRegister(todolist);
        if(result >0) {
        	List<Todolist> temp = service.selectTodayList(loginMember.getMemberNo());
        	map.put("today", temp);
        	temp = service.selectWeekList(loginMember.getMemberNo());
        	map.put("week", temp);
        	model.addAttribute("todolist", map);
        }else {
        	ra.addFlashAttribute("message", "실패하였습니다.");
        }
		return "todolist/todolist";
		}
		
		@PostMapping("/weekRegister")
		public String weekRegister(@RequestParam(value = "date")int date,
									@RequestParam(value = "year")int year,
									@RequestParam(value = "month")int month,
									Todolist todolist,
									HttpSession session,
									Model model,
									
									RedirectAttributes ra) { 
		Map<String, Object> map = new HashMap<>();
			Member loginMember = (Member)session.getAttribute("loginMember");
		todolist.setMemberId(loginMember.getMemberId());
		todolist.setTodolist_Type(0);
		
//		 // 현재 날짜 구하기
//        LocalDate now = LocalDate.now();
// 
//        // 포맷 정의
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
// 
//        // 포맷 적용
//        String formatedNow = now.format(formatter);
		String sdate;
		String smonth;
		if(date<10) {
		sdate = "0"+ date;
		}else {
			sdate = ""+date;
		}
		if(month<10) {
			smonth = "0"+month;
		}else {
			smonth = ""+month;
		}
		String time = year + "-" + smonth + "-" + sdate;
        todolist.setTodolistDate(time);
        int result = service.weekRegister(todolist);
        if(result >0) {
        	List<Todolist> temp = service.selectTodayList(loginMember.getMemberNo());
        	map.put("today", temp);
        	temp = service.selectWeekList(loginMember.getMemberNo());
        	map.put("week", temp);
        	model.addAttribute("todolist", map);
        }else {
        	ra.addFlashAttribute("message", "실패하였습니다.");
        }
		return "todolist/todolist";
		}
		
		@ResponseBody
		@PostMapping("/selectTodolist")
		public Map<String, Object> deleteWeekList(@RequestParam(value = "date")int date,
												  @RequestParam(value = "year")int year,
												  @RequestParam(value = "month")int month,
												  HttpSession session,
												  Todolist todolist,
												  @RequestParam(value = "deleteList")String deleteList
									) {
			Map<String,Object> result =new HashMap<>();
			Member loginMember = (Member)session.getAttribute("loginMember");
			todolist.setMemberNo(loginMember.getMemberNo());
			String[] slist = deleteList.split(",");
			logger.info(slist[0]);
			String sdate;
			String smonth;
			if(date<10) {
			sdate = "0"+ date;
			}else {
				sdate = ""+date;
			}
			if(month<10) {
				smonth = "0"+month;
			}else {
				smonth = ""+month;
			}
			String time = year + "-" + smonth + "-" + sdate;
			todolist.setTodolistDate(time);
			int resultInt = service.deleteList(todolist, slist);
			logger.info(resultInt+"");
			if(resultInt>0) {
				List<Todolist> temp = service.choiceDateWeek(todolist);
				result.put("weekList", temp);
				temp = service.choiceDateToday(todolist);
				result.put("todayList", temp);
			}
			return result;
		}
		
		@ResponseBody
		@PostMapping("/selectTodayTodolist")
		public Map<String, Object> deleteTodayList(@RequestParam(value = "date")int date,
												  @RequestParam(value = "year")int year,
												  @RequestParam(value = "month")int month,
												  HttpSession session,
												  Todolist todolist,
												  @RequestParam(value = "deleteList")String deleteList
									) {
			Map<String,Object> result =new HashMap<>();
			Member loginMember = (Member)session.getAttribute("loginMember");
			todolist.setMemberNo(loginMember.getMemberNo());
			String[] slist = deleteList.split(",");
			logger.info(slist[0]);
			String sdate;
			String smonth;
			if(date<10) {
			sdate = "0"+ date;
			}else {
				sdate = ""+date;
			}
			if(month<10) {
				smonth = "0"+month;
			}else {
				smonth = ""+month;
			}
			String time = year + "-" + smonth + "-" + sdate;
			todolist.setTodolistDate(time);
			int resultInt = service.deleteTodayList(todolist, slist);
			logger.info(resultInt+"");
			if(resultInt>0) {
				List<Todolist> temp = service.choiceDateWeek(todolist);
				result.put("weekList", temp);
				temp = service.choiceDateToday(todolist);
				result.put("todayList", temp);
			}
			return result;
		}
		
}
