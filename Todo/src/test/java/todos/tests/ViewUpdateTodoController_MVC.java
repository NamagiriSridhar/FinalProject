package todos.tests;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import todos.controllers.ViewUpdateTodoController;
import todos.domains.Todo;
import todos.domains.Todo.Status;
import todos.services.TodoService;


@RunWith(SpringRunner.class)
@WebMvcTest(ViewUpdateTodoController.class)
public class ViewUpdateTodoController_MVC 
{

	@Autowired
	private MockMvc mockMvc; 
	
	@MockBean // Didn't work if I changed it to @Autowired
	private  TodoService todoService; 
	
	private   List<Todo> todos;
	
	private Todo todo;
	
    private static final Long ID = 1L;

	
	@Before
	public void setup() 
	{
		Todo array[] = {
				new Todo(1L,"Join Bollywood Dance group","And dance to the beat","Namagiri",Status.NOT_STARTED),
				new Todo(2L,"Say thanks to everyone in bootcamp","For all the kindness ever received","Namagiri",Status.NOT_STARTED)
		};
		todos = Arrays.asList(array);
		System.out.println(todos);
		when(todoService.getAllTodos())
	       .thenReturn(todos);
		when(todoService.findTodoById(ID)).thenReturn(new Todo(1L,"Join Bollywood Dance group","And dance to the beat","Namagiri",Status.NOT_STARTED));		
		todo = todos.get(0);
	} 
	
	@Test
	public void testGetViewPage() throws Exception
	{
	  mockMvc.perform(get("/view"))
       .andExpect(status().isOk())
       .andExpect(view().name("View"))
       .andExpect(model().attributeExists("todos"));
	}
	
	@Test
	public void testShowUpdateForm() throws Exception
	{
		mockMvc.perform(get("/update/{id}", ID))
		 .andExpect(status().isOk())
		 .andExpect(view().name("Update"))
		 .andExpect(model().attributeExists("todo"))
		.andExpect(model().attribute("todo", todo));
	}
		
	@Test
	public void testPutUpdateForm() throws Exception
	{
		when(todoService.save(todo))
        .thenReturn(todo);
		  mockMvc.perform(put("/update/{id}", ID)
		  .content("title=Join+Bollywood+Dance+group&description=And+dance+to+the+beat&creator=Dhyan&status=NOT_STARTED")
		  .contentType(MediaType.APPLICATION_FORM_URLENCODED))
		  .andExpect(status().is3xxRedirection())
		  .andExpect(redirectedUrl("/")); 
	}
}
