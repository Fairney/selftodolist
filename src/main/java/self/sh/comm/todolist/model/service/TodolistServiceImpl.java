package self.sh.comm.todolist.model.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import self.sh.comm.todolist.dao.TodolistDAO;
import self.sh.comm.todolist.model.vo.Todolist;

@Service
public class TodolistServiceImpl implements TodolistService{
	
	private Logger logger = LoggerFactory.getLogger(TodolistServiceImpl.class);
	@Autowired
	private TodolistDAO dao;
	
	@Override
	public List<Todolist> choiceDateToday(Todolist todolist) {
		// TODO Auto-generated method stub
		return dao.choiceDateToday(todolist);
	}

	@Override
	public List<Todolist> choiceDateWeek(Todolist todolist) {
		// TODO Auto-generated method stub
		return dao.choiceDateWeek(todolist);
	}

	@Override
	public List<Todolist> selectTodayList(int memberNo) {
		// TODO Auto-generated method stub
		return dao.selectTodayList(memberNo);
	}

	@Override
	public List<Todolist> selectWeekList(int memberNo) {
		// TODO Auto-generated method stub
		return dao.selectWeekList(memberNo);
	}

	@Override
	public int todayRegister(Todolist todolist) {
		// TODO Auto-generated method stub
		return dao.todayRegister(todolist);
	}

	@Override
	public int weekRegister(Todolist todolist) {
		// TODO Auto-generated method stub
		return dao.weekRegister(todolist);
	}

	@Override
	public int deleteList(Todolist todolist, String[] slist) {
		// TODO Auto-generated method stub
		int temp= 0;
		for(int i = 0; i<slist.length; i++){
			todolist.setTodolistContent(slist[i]);
			temp = dao.deleteList(todolist);
			if(temp == 0) {
				break;
			}else {
				if(slist.length == i) {
					temp = 1;
				}else {
					temp = 0;
				}
			}
		}
		return 1;
	}

	@Override
	public int deleteTodayList(Todolist todolist, String[] slist) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				int temp= 0;
				for(int i = 0; i<slist.length; i++){
					todolist.setTodolistContent(slist[i]);
					temp = dao.deleteTodayList(todolist);
					if(temp == 0) {
						break;
					}else {
						if(slist.length == i) {
							temp = 1;
						}else {
							temp = 0;
						}
					}
				}
				return 1;
	}

}
