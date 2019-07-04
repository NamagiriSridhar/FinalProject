package todos.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CreateTodoUpdateView_Browser {
	private static HtmlUnitDriver browser;
	static String  todo [] ;
	@LocalServerPort
	private int port;

	//@Autowired
	//private TestRestTemplate rest;
	  
	@BeforeClass	
	public static  void setup() {
		browser = new HtmlUnitDriver();
		browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		todo=new String[] {
				"Do mud run",
				"Find a cool partner and have fun",
				"Namagiri S",
				"NOT_STARTED"
			};
		
	}

	@AfterClass
	public static void closeBrowser() {
		browser.quit();
	}

	@Test
	public void testCreateandViewandUpdateTodoItem() {
		browser.get(homePageUrl());
		clickCreateTodoLink();
		testGoHomeLink();
		clickCreateTodoLink();
		assertCreateTodoPageElements();
		createATodo(todo);
	    assertEquals(homePageUrl(), browser.getCurrentUrl());
	    clickViewAllTodosLink();
	    assertEquals(viewAllTodosPageUrl(), browser.getCurrentUrl());
	    browser.findElementById("update").click();
	    assertEquals(updateTodoPageUrl(), browser.getCurrentUrl());
	    browser.findElementByCssSelector("input#creator").sendKeys("Dhyan");
		browser.findElementByCssSelector("form").submit();
	    assertEquals(homePageUrl(), browser.getCurrentUrl());
	    
	}
	@Test
	public void testCreateandViewandUpdateInvalidTodoItem() {
		browser.get(homePageUrl());
		clickCreateTodoLink();
		assertCreateTodoPageElements();
		createATodo(todo);
	    assertEquals(homePageUrl(), browser.getCurrentUrl());
	    clickViewAllTodosLink();
	    assertEquals(viewAllTodosPageUrl(), browser.getCurrentUrl());
	    browser.findElementById("update").click();
	    assertEquals(updateTodoPageUrl(), browser.getCurrentUrl());
	    browser.findElementByCssSelector("input#creator").clear();
		browser.findElementByCssSelector("form").submit();
		assertEquals(updateTodoPageUrl(), browser.getCurrentUrl());
	    List<String> validationErrors = getValidationErrorTexts();
	    assertEquals(2, validationErrors.size());
	    assertTrue(validationErrors.contains("Please correct the problems below and resubmit."));
	    assertTrue(validationErrors.contains("Creator is required"));    
	}
	@Test
	public void testCreateEmptyTodoItem() 
	{
		browser.get(homePageUrl());
		clickCreateTodoLink();
		browser.findElementByCssSelector("input").clear();//to stop autofilling - getting error
		browser.findElementByCssSelector("form").submit();
	    assertEquals(createTodoPageUrl(), browser.getCurrentUrl());
	    List<String> validationErrors = getValidationErrorTexts();
	    assertEquals(4, validationErrors.size());
	    assertTrue(validationErrors.contains("Please correct the problems below and resubmit."));
	    assertTrue(validationErrors.contains("Title should be atleast 5 characters long"));
	    assertTrue(validationErrors.contains("Creator is required"));
	    assertTrue(validationErrors.contains("Status is required"));	   
	}
	@Test
	public void testInvalidTodoItem()
	{
		browser.get(homePageUrl());
		clickCreateTodoLink();
		assertCreateTodoPageElements();
		String[] todo = {
				"Attempt indoor climbing with ropes",
				"Find a cool partner and have fun",
				"",
				"NOT_STARTED"
			};
		createATodo(todo);
		assertEquals(createTodoPageUrl(), browser.getCurrentUrl());
	    List<String> validationErrors = getValidationErrorTexts();
	    assertEquals(2, validationErrors.size());
	    assertTrue(validationErrors.contains("Please correct the problems below and resubmit."));
	    assertTrue(validationErrors.contains("Creator is required"));
	}
	
	private void clickViewAllTodosLink() {
		assertEquals(homePageUrl(), browser.getCurrentUrl());
		browser.findElementByCssSelector("a[id='view']").click();
	}
	
	
	private List<String> getValidationErrorTexts() {
	    List<WebElement> validationErrorElements = browser.findElementsByClassName("validationError");
	    List<String> validationErrors = validationErrorElements.stream()
	        .map(el -> el.getText())
	        .collect(Collectors.toList());
	    return validationErrors;
	  }

	private void testGoHomeLink() {
		browser.findElementByTagName("a").click();
		assertEquals(homePageUrl(), browser.getCurrentUrl());
	}
	
	private void createATodo(String[] userInputs)
	{   
		browser.findElementByCssSelector("input").clear();//to stop autofilling - getting error
		List<WebElement> formGroups = browser.findElementsByTagName("input");
		int i=0;
		for (WebElement el : formGroups) 
		{
			el.sendKeys(userInputs[i++]);
			if(i==3) break;
		}
	      browser.findElementByCssSelector("input[value='" + userInputs[i] + "']").click();      
	      browser.findElementByCssSelector("form").submit();
	}
	
	private void assertCreateTodoPageElements() {
		assertEquals(createTodoPageUrl(), browser.getCurrentUrl());
		String h3Text = browser.findElementByTagName("h3").getText();
		Assert.assertEquals("Create a Todo item", h3Text);
		List<WebElement> formGroups = browser.findElementsByTagName("label");
		assertEquals(4, formGroups.size());
		String textFields[] = { "title", "description", "creator", "status" };
		int i = 0;
		for (WebElement el : formGroups) {
			assertTrue(el.getAttribute("for").toString().equals(textFields[i]));
			//System.out.println("***************" + el.getAttribute("for").toString());
			i++;
		}
		String btnText = browser.findElementByTagName("button").getText();
		Assert.assertEquals("Submit your todo", btnText);
	}

	private String createTodoPageUrl() {
		return homePageUrl() + "create";
	}
	
	private String viewAllTodosPageUrl() {
		return homePageUrl() + "view";
	}
	
	private String updateTodoPageUrl() {
		return homePageUrl() + "update/1";
	}

	
	private void clickCreateTodoLink() {
		assertEquals(homePageUrl(), browser.getCurrentUrl());
		browser.findElementByCssSelector("a[id='create']").click();
	}

	private String homePageUrl() {
		return "http://localhost:" + port + "/";
	}

}
