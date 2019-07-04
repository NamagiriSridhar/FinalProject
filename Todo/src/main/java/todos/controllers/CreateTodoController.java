package todos.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import todos.domains.Todo;
import todos.services.TodoService;

@Controller
public class CreateTodoController 
{
	private TodoService todoService;
	
	@Autowired //wouldn't work without this!!
	public CreateTodoController (TodoService todoService)
	{
		this.todoService = todoService;
	}
	
	@ModelAttribute(name="todo")
	public Todo todo()
	{
		return new Todo();
	}
	
	@GetMapping("/create")
	public String showCreateForm()
	{
		return "CreateTodo";
	}
	
	@PostMapping("/create")
	public String postCreateForm(@Valid Todo todo, Errors errors)
	{
		 if(errors.hasErrors()) 
		 {
			 return "CreateTodo";
		 }
		todoService.save(todo);
		return "redirect:/";//redirect must for tests to work & also get mapping required for redirect
	}
}
