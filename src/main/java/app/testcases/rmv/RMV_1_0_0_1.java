package app.testcases.rmv;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import app.config.Config;
import app.config.RMV_Source_Identifier;
import app.main.Testcase_Error_Handling;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * This testcase try to login and logout a specific user account on thermv webside.
 * 
 * @author Michael Bulinski
 */

public class RMV_1_0_0_1 {
	
	private static String classname = "app.testcases.rmv.RMV_1_0_0_1";
	private static Logger logger = Logger.getLogger(classname);
	private Label correct_count;
	private Label wrong_count;
	private String output_dir;
	private String url;
	private boolean test_end = false;
	private Config config = new Config();
	private RMV_Source_Identifier rmv_ident = new RMV_Source_Identifier();
	
	public RMV_1_0_0_1(WebDriver driver, WebDriverWait wait, TextArea center_text_area, TextArea right_text_area, String login_name,
			String login_pw, Label correct_count, Label wrong_count, String output_dir, String url) {
		this.correct_count = correct_count;
		this.wrong_count = wrong_count;
		this.output_dir = output_dir;
		this.url = url;
		right_text_area.appendText(config.getRightSeperator());
		right_text_area.appendText(logger.getName() + " - Starting testcase\n");
		startTest(driver, wait, center_text_area, right_text_area);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		this.setTest_end(true);
		center_text_area.appendText(logger.getName() + " - Finished testcase\n");
		
	}
	
	public void startTest(WebDriver driver, WebDriverWait wait, TextArea center_text_area, TextArea right_text_area) {

		try {
			center_text_area.appendText(config.getCenter_seperator());
			
			// browse to landing page
			driver.get(this.url);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(rmv_ident.getTimetable_start_page_id())));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(rmv_ident.getTimetable_end_page_id())));
			logger.info("Landing Page successfully loaded");
			center_text_area.appendText(logger.getName() + " - Landing Page successfully loaded\n");
			
			// sign in
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(rmv_ident.getLogin_mail_page_id()))).clear();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(rmv_ident.getLogin_mail_page_id()))).sendKeys(config.getLogin_name());
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(rmv_ident.getLogin_password_page_id()))).clear();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(rmv_ident.getLogin_password_page_id()))).sendKeys(config.getLogin_pw());			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(rmv_ident.getLogin_button_css_select()))).click();
			
			WebElement login_name = driver.findElement(By.id(rmv_ident.getRmv_loginname_page_id()));
			login_name.getText().equals(config.getLogin_pw());
			logger.info("User successfully logged in");
			center_text_area.appendText(logger.getName() + " - User successfully logged in\n");
			
			// sign out
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(rmv_ident.getLogout_button_css_select()))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(rmv_ident.getLogin_button_css_select())));			
			logger.info("User successfully logged out");
			center_text_area.appendText(logger.getName() + " - User successfully logged out\n");		
			
			right_text_area.appendText(logger.getName() + " - Testcase successfully completed\n");
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					correct_count.setText(String.valueOf(Integer.parseInt(correct_count.getText()) + 1));
				}

			});
		} catch (TimeoutException | NullPointerException | NoSuchWindowException ex) {
			new Testcase_Error_Handling(driver, ex, logger, center_text_area, right_text_area, wrong_count, output_dir, classname);
			return;
		}
	}

	public boolean isTest_end() {
		return test_end;
	}

	public void setTest_end(boolean test_end) {
		this.test_end = test_end;
	}


}