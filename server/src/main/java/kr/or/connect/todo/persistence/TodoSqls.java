package kr.or.connect.todo.persistence;

public class TodoSqls {
	static final String DELETE_BY_ID =
			"DELETE FROM todo WHERE id= :id";
	//click x button
	
	//clear completed
	static final String DELETE_BY_COMPLETED = 
			"DELETE FROM todo WHERE completed = :completed";
	
	//toggle checkbox
	static final String UPDATE_COMPLETED =
			"UPDATE todo SET completed = :completed WHERE id = :id";
		
	//Active, Completed.
	static final String SELECT_BY_COMPLETED = 
			"SELECT * FROM todo where completed = :completed ORDER BY DATE DESC";
	
	//ALL
	static final String SELECT_ALL =
			"SELECT * FROM todo ORDER BY DATE DESC";
	
	//showActive, completed()
	static final String COUNT_ACTIVE = 
			"SELECT COUNT(*) FROM todo WHERE completed = 0";
}
