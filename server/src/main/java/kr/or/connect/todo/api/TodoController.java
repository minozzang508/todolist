package kr.or.connect.todo.api;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.service.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
	private final TodoService todoService;
	private final Logger log = LoggerFactory.getLogger(TodoController.class);

	@Autowired
	public TodoController(TodoService service) {
		this.todoService = service;
	}
	
	@GetMapping("/all")
	Collection<Todo> readList() {
		return todoService.findAll();
	}	

	@GetMapping("/completed")
	Collection <Todo> readListCompleted() {
		return todoService.findByCompleted(1);
	}

	@GetMapping("/active")
	Collection<Todo> readListActive() {
		return todoService.findByCompleted(0);
	}
	
	@GetMapping()
	Integer countActive() {
		log.info("activeCount : {}", todoService.countActive());
		return todoService.countActive();
	}
	
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	Todo create(@RequestBody Todo todo) {
		Todo newTodo = todoService.create(todo);
		log.info("todo created : {}" , newTodo);
		return todo;
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void updateCompleted(@PathVariable Integer id, @RequestBody Todo todo) {
		todo.setId(id);
		todoService.updateCompleted(todo);
		log.info("todo updated : {}", todo);

	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void deleteById(@PathVariable Integer id) {
		todoService.deleteById(id);
		log.info("todo deleted");
	}
	
	@DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteBycompleted() {
        todoService.deleteByCompleted();
    }
}
