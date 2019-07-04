package todos.tests;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)

public class HomePage_Browser
{
	@LocalServerPort
	  private int port;
	  private static HtmlUnitDriver browser;  
	  
	  @BeforeClass
	  public static void setup() {
	    browser = new HtmlUnitDriver();
	    
	    browser.manage().timeouts()
	          .implicitlyWait(10, TimeUnit.SECONDS);
	  }
	  
	  @AfterClass
	  public static void teardown() {
	    browser.quit();
	  }
	  
	  @Test
	  public void testHomePageBrowser() throws Exception {
	    String homePage = "http://localhost:" + port;
	    browser.get(homePage);
	    
	    String titleText = browser.getTitle();
	    Assert.assertEquals("Todo App", titleText);
	    
	    String h1Text = browser.findElementByTagName("h1").getText();
	    Assert.assertEquals("Todo Application", h1Text);
	    String h5Text1 = browser.findElementById("create").getText();
	    Assert.assertEquals("1.Create Todo Item", h5Text1);
	    String h5Text2 = browser.findElementById("view").getText();
	    Assert.assertEquals("2.View all Todo Items", h5Text2);
	   
	     }
	  
}
