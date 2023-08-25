package self.sh.comm.todolist.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import self.sh.comm.todolist.model.vo.Todolist;

@Repository
public class TodolistDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	public List<Todolist> choiceDateToday(Todolist todolist) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("todolistMapper.choiceDateToday",todolist);
	}

	public List<Todolist> choiceDateWeek(Todolist todolist) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("todolistMapper.choiceDateWeek",todolist);
	}

	public List<Todolist> selectTodayList(int memberNo) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("todolistMapper.selectTodayList",memberNo);
	}

	public List<Todolist> selectWeekList(int memberNo) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("todolistMapper.selectWeekList",memberNo);
	}

	public int todayRegister(Todolist todolist) {
		// TODO Auto-generated method stub
		return sqlSession.insert("todolistMapper.todayRegister",todolist);
	}
	public int weekRegister(Todolist todolist) {
		return sqlSession.insert("todolistMapper.weekRegister",todolist);
	}

	public int deleteList(Todolist todolist) {
		// TODO Auto-generated method stub
		return sqlSession.update("todolistMapper.deleteList",todolist);
	}

	public int deleteTodayList(Todolist todolist) {
		// TODO Auto-generated method stub
		return sqlSession.update("todolistMapper.deleteTodayList",todolist);
	}

}
