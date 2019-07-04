package todos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import todos.domains.Todo;
import todos.repositories.TodoRepository;

@Service
public class TodoService
{
	private TodoRepository todoRepository;
	
	@Autowired
	public TodoService(TodoRepository todoRepository)
	{
		this.todoRepository =  todoRepository;
	}
	
	public Todo save(Todo todo) 
	{
		return todoRepository.save(todo);
	}
	
	public Iterable<Todo> getAllTodos()
	{
		return todoRepository.findAll();
	}
	
	public Todo findTodoById(Long todoId)
	{
		return todoRepository.findById(todoId).orElse(null);
	}
	
}
