package todos.tests;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import todos.controllers.CreateTodoController;
import todos.domains.Todo;
import todos.repositories.TodoRepository;
import todos.services.TodoService;

import org.springframework.boot.test.mock.mockito.MockBean;

@RunWith(SpringRunner.class)
@WebMvcTest  
public class HomePage_MVC  {
	
	@Autowired
	private MockMvc mockMvc; 
	
	//@MockBean // needed for the tests to pass
	//private TodoRepository todoRepository;
	
	@MockBean
	private TodoService todoService;
	
	//@MockBean
	//private CreateTodoController createtodoController; //OPTIONAL
	
//@MockBean
	//private ViewController viewController;// - Causes tests to fail
	
	//@MockBean
//	private Todo todo;//wow this works!!
	
	@Test
	public void testHomePageMVC() throws Exception
	{
		 mockMvc.perform(get("/"))    
		    
	      .andExpect(status().isOk())  
	      
	      .andExpect(view().name("Home")) 
	      
	      .andExpect(content().string(           
	          containsString("Choose one")));  
	}
}
