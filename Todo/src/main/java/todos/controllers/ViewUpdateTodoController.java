package todos.controllers;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import todos.domains.Todo;
import todos.services.TodoService;

@Controller
public class ViewUpdateTodoController
{
	private TodoService todoService;

	@ModelAttribute(name="todos")
	public Iterable<Todo> listOfTodos()
	{
		Iterable<Todo> todos = todoService.getAllTodos();
		return todos;
	}
	
	@Autowired //wouldn't work without this!!
	public ViewUpdateTodoController (TodoService todoService)
	{
		this.todoService = todoService;
	}
	
	@GetMapping("/view")
	public String getViewPage()
	{
		return "View";
	}	
	
	@GetMapping("/update/{id}")
	public String updateBlogStep1(@PathVariable Long id,Model model)
	{
		Todo todo=todoService.findTodoById(id);
		model.addAttribute(todo);
		return "Update";
	}
	
	@PutMapping("/update/{id}")
	public String updateBlogStep2(@Valid Todo todo, Errors errors)
	{
		 if(errors.hasErrors()) 
		 {
			 return "Update";
		 }
		todoService.save(todo);
		return "redirect:/";//redirect must for tests to work & also get mapping required for redirect
	}
}
