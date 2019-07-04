package todos.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ViewController implements WebMvcConfigurer {
	
	//no home controller getMapping("/") required now
	//If we remove this tests will fail
	  @Override
	  public void addViewControllers(ViewControllerRegistry registry) 
	  {
		  registry.addViewController("/").setViewName("Home"); 
	  }
	 
}