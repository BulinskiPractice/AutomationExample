package app.main;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import app.config.Config;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * This class represents the error handling, if a testcase throw an error and failed
 * 
 * @author Michael Bulinski
 */

public class Testcase_Error_Handling {
	
	private Config config = new Config();
	
	public Testcase_Error_Handling(WebDriver driver, Exception ex, Logger logger, TextArea center_text_area, TextArea right_text_area, Label wrong_count,
			String output_dir, String classname) {
		logger.error(ex.toString());
		center_text_area.appendText("\n" + ex.toString() + "\n\n");
		right_text_area.appendText(logger.getName() + " - Testcase failed\n");
		right_text_area.appendText(config.getRightSeperator());
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				wrong_count.setText(String.valueOf(Integer.parseInt(wrong_count.getText()) + 1));
			}

		});
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			String timeStamp = new SimpleDateFormat("dd.MM.yyyy_HH.mm.ss").format(Calendar.getInstance().getTime());
			String screenshot_name = "C:\\IT\\RailwayTest_InputOutput\\"+ output_dir +"\\"+ classname + " " + timeStamp + ".png";
			FileUtils.copyFile(scrFile, new File(screenshot_name));
			logger.info("Screenshot " + screenshot_name + " was successfully created");
			center_text_area.appendText(logger.getName() + " - Screenshot " + screenshot_name + " was successfully created\n");
			center_text_area.appendText(config.getCenter_seperator());
		} catch (IOException io) {
			logger.error(io.toString());
			center_text_area.appendText("\n" + io.toString() + "\n\n");
		}
	}

}
