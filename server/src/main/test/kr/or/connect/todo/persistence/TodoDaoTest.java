package kr.or.connect.todo.persistence;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.todo.domain.Todo;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TodoDaoTest {
	@Autowired
	private TodoDao dao;
	
	@Test
	public void shouldInsertAndSelect() {
		// given 
		Todo todo=new Todo("study hard",1);
		Todo todo2=new Todo("play hard",1);
		Todo todo3=new Todo("work hard",0);
		Todo todo4=new Todo("super hard",0);
		Todo todo5=new Todo("not hard",1);
		//when
		Integer id = dao.insert(todo);
		Integer id2 = dao.insert(todo2);
		Integer id3 = dao.insert(todo3);
		Integer id4 = dao.insert(todo4);
		Integer id5 = dao.insert(todo5);
		// then
		List<Todo> selected = dao.selectAll();
		
		assertThat(selected.get(0).getTodo(), is("study hard"));
	}

	@Test
	public void shouldCount() {
		Todo todo=new Todo("study hard",1);
		Todo todo2=new Todo("play hard",1);
		Todo todo3=new Todo("work hard",0);
		Todo todo4=new Todo("super hard",0);
		Todo todo5=new Todo("not hard",1);
		Integer id = dao.insert(todo);
		Integer id2 = dao.insert(todo2);
		Integer id3 = dao.insert(todo3);
		Integer id4 = dao.insert(todo4);
		Integer id5 = dao.insert(todo5);
		
		Integer count = dao.countActive();
	}
	
	@Test
	public void shouldDelete() {
		// given
		Todo todo=new Todo("study hard",1);
		Todo todo2=new Todo("play hard",1);
		Todo todo3=new Todo("work hard",0);
		Todo todo4=new Todo("super hard",0);
		Todo todo5=new Todo("not hard",1);
		// when
		Integer id = dao.insert(todo);
		Integer id2 = dao.insert(todo2);
		Integer id3 = dao.insert(todo3);
		Integer id4 = dao.insert(todo4);
		Integer id5 = dao.insert(todo5);
		
		Integer numOfAffected = dao.selectAll().size() - dao.countActive();
		
		Integer affected = dao.deleteByCompleted(1);
		
		// Then
		assertThat(affected, is(numOfAffected));
	}
	
	@Test
	public void shouldUpdate() {		
		// given
		Todo todo = new Todo("shouldBeUpdated", 1);
		Integer id = dao.insert(todo);
		// When
		todo.setId(id);
		todo.setTodo("updateCompleted");
		todo.setCompleted(0);
		
		int affected = dao.update(todo);

		// Then
		assertThat(affected, is(1));
		Todo updated = dao.selectAll().get(0);
		assertThat(updated.getCompleted(), is(0));
	}
	
	@Test
	public void shouldSelectAll() {
		List<Todo> allTodos = dao.selectAll();
		assertThat(allTodos, is(notNullValue()));
	}
	
	@Test
	public void shouldSelectByCompleted() {
		List<Todo> activeTodos = dao.selectByCompleted(1);
		assertThat(activeTodos, is(notNullValue()));
	}	
}
