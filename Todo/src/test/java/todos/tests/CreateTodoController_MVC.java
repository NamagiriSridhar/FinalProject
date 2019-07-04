package todos.tests;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import todos.controllers.CreateTodoController;
import todos.domains.Todo;
import todos.services.TodoService;
import todos.domains.Todo.Status;

@RunWith(SpringRunner.class)
@WebMvcTest(CreateTodoController.class)
public class CreateTodoController_MVC 
{
	@Autowired
	private MockMvc mockMvc; 
	
	@MockBean // Didn't work if I changed it to @Autowired
	private TodoService todoService; 
	
	private Todo todo;
		
	@Before
	public void setup() 
	{
	  	todo = new Todo();
	  	//todo.setId(1L); doesnt work - ofcourse duh!!
	  	todo.setTitle("Join Bollywood Dance group");
	  	todo.setDescription("And dance to the beat");
	  	todo.setCreator("Namagiri");
	  	todo.setStatus(Status.NOT_STARTED);
	}
	
	@Test
	public void testShowCreateForm() throws Exception
	{
	  mockMvc.perform(get("/create"))
       .andExpect(status().isOk())
       .andExpect(view().name("CreateTodo"))
       .andExpect(model().attributeExists("todo"));
	}
	
	@Test
	public void testPostCreateForm() throws Exception
	{
		when(todoService.save(todo))
        .thenReturn(todo);
		  mockMvc.perform(post("/create")
		  .content("title=Join+Bollywood+Dance+group&description=And+dance+to+the+beat&creator=Namagiri&status=NOT_STARTED")
		  .contentType(MediaType.APPLICATION_FORM_URLENCODED))
		  .andExpect(status().is3xxRedirection())
		  .andExpect(redirectedUrl("/"));      
	}

}
