package self.sh.comm.todolist.model.service;

import java.util.List;

import self.sh.comm.todolist.model.vo.Todolist;

public interface TodolistService {

	List<Todolist> choiceDateToday(Todolist todolist);

	List<Todolist> choiceDateWeek(Todolist todolist);

	List<Todolist> selectTodayList(int memberNo);

	List<Todolist> selectWeekList(int memberNo);

	int todayRegister(Todolist todolist);

	int weekRegister(Todolist todolist);

	int deleteList(Todolist todolist, String[] slist);

	int deleteTodayList(Todolist todolist, String[] slist);

}
