package app.main;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import app.config.Config;
import app.testcases.pkp.PKP_1_0_0_1;
import app.testcases.rmv.RMV_1_0_0_1;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * This class ist the starter class for chrome test automation. Chrome
 * testcases are running in their own thread, for not blocking the java fx main
 * thread. This is necessary cause the logging text fields are updating during
 * the test
 * 
 * @author Michael Bulinski
 *
 */

public class TestStarter implements Runnable {

	private String url;
	private TextArea center_text_area;
	private TextArea right_text_area;
	private Label testcase_count;
	private Label correct_count;
	private Label wrong_count;
	private String testcase_file_path;
	private String company_name;

	private final Thread working_thread;
	private volatile boolean stop_thread = false;

	private WebDriver driver;
	private WebDriverWait wait;

	private Config config = new Config();
	private static Logger logger = Logger.getLogger("app.main.TestStarter");

	private String output_dir_string;

	public TestStarter(String default_url, TextArea center_text_area, TextArea right_text_area, Label testcase_count,
			Label correct_count, Label wrong_count, String testcase_file_path, String company_name) {
		this.url = default_url;
		this.center_text_area = center_text_area;
		this.right_text_area = right_text_area;
		this.testcase_count = testcase_count;
		this.correct_count = correct_count;
		this.wrong_count = wrong_count;
		this.testcase_file_path = testcase_file_path;
		this.company_name = company_name;
		working_thread = new Thread(this, "Working Thread");
	}

	@Override
	public void run() {
		while (!stop_thread) {
			PropertyConfigurator.configure("C:\\IT\\log4j.properties");
			center_text_area.clear();
			right_text_area.clear();
			ReadFile readFile = new ReadFile();
			ArrayList<String> testcase_list = readFile.read(testcase_file_path, center_text_area);

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					testcase_count.setText(String.valueOf(testcase_list.size()));
				}

			});

			if (testcase_list.size() != 0) {
				createEmptyLogfile();
				createOutputFolder();
				setupChrome();
				startChromeTest(center_text_area, right_text_area, testcase_list, output_dir_string, url);
				driver.quit();
				writeOutputFolder();

			} else {
				logger.info("No testcases selected. Test automation is not starting");
				this.center_text_area.appendText(logger.getName()
						+ "No testcases selected. Test automation is not starting");
			}
			stop_thread = true;
		}

	}

	private void createEmptyLogfile() {
		File search_in_folder = new File("C:\\IT\\RailwayTest_InputOutput");
		File[] matchingFiles = search_in_folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith("results");
			}
		});
		if (matchingFiles.length == 0) {
			File new_log_file = new File("C:\\IT\\RailwayTest_InputOutput\\results_logging.log");
			try {
				new_log_file.createNewFile();				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void createOutputFolder() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
		Date test_date = new Date();
		output_dir_string = dateFormat.format(test_date);
		File output_dir = new File("C:/IT/RailwayTest_InputOutput/" + output_dir_string);
		output_dir.mkdir();
		this.center_text_area.appendText("Created new output directory " + output_dir.getAbsolutePath() + "\n");

	}

	private void writeOutputFolder() {
		Path old_log_path = Paths.get("C:\\IT\\RailwayTest_InputOutput\\results_logging.log");
		Path new_log_path = Paths.get("C:\\IT\\RailwayTest_InputOutput\\" + output_dir_string + "\\results_logging.log");
		CopyOption[] options_log = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING,
				StandardCopyOption.COPY_ATTRIBUTES };
		try {
			Files.copy(old_log_path, new_log_path, options_log);
		} catch (IOException ex) {
			logger.error(ex.toString());
			this.center_text_area.appendText("\n" + ex.toString() + "\n\n");
		}
		LogManager.resetConfiguration();
		try {
			Files.delete(Paths.get("C:\\IT\\RailwayTest_InputOutput\\results_logging.log"));
		} catch (IOException ex) {
			logger.error(ex.toString());
			this.center_text_area.appendText("\n" + ex.toString() + "\n\n");
		}
		
		// search and copy pdf and png files
		File search_in_folder = new File("C:\\IT\\RailwayTest_InputOutput");
		File[] matchingFiles = search_in_folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".pdf");
			}
		});
		for(int i = 0; i<matchingFiles.length; i++){
			this.center_text_area.appendText(matchingFiles[i].toString());
			String[] splitStrings = matchingFiles[i].toString().split("\\\\");
			Path old_files_path = Paths.get(matchingFiles[i].toString());
			Path new_files_path = Paths.get("C:\\IT\\RailwayTest_InputOutput\\" + output_dir_string + "\\" + splitStrings[2]);
			CopyOption[] options_files = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING,
					StandardCopyOption.COPY_ATTRIBUTES };
			try {
				Files.copy(old_files_path, new_files_path, options_files);
			} catch (IOException ex) {
				logger.error(ex.toString());
				this.center_text_area.appendText("\n" + ex.toString() + "\n\n");
			}
			try {
				Files.delete(Paths.get(matchingFiles[i].toString()));
			} catch (IOException ex) {
				logger.error(ex.toString());
				this.center_text_area.appendText("\n" + ex.toString() + "\n\n");
			}
		}
		
	}

	public void start() {
		working_thread.start();
	}

	public void stop() {
		stop_thread = true;
	}

	private void setupChrome() {

		try {

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			logger.info("Initializing Chrome WebDriver...");
			this.center_text_area.appendText(logger.getName() + " - Initializing Chrome WebDriver...\n");

			//System.setProperty("webdriver.gecko.driver", config.getGeckodriver_path());
			System.setProperty("webdriver.chrome.driver", "C://IT//chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();			
			wait = new WebDriverWait(driver, config.getWait_time());

			logger.info("Chrome WebDriver was successful initialized - starting test automation");
			this.center_text_area.appendText(
					logger.getName() + " - Chrome WebDriver was successful initialized - starting test automation\n");

		} catch (WebDriverException ex) {
			logger.error(ex.toString());
			this.center_text_area.appendText("\n" + ex.toString() + "\n\n");
		}
	}

	private void startChromeTest(TextArea center_text_area, TextArea right_text_area, ArrayList<String> testcase_list,
			String output_dir_string, String url) {
		if(company_name.equals(config.getCompany_pkp())) {
			for (String testcase : testcase_list) {
				logger.info(testcase);
				if(testcase.equals("1_0_0_1")) {
					PKP_1_0_0_1 pkp_1_0_0_1 = new PKP_1_0_0_1(driver, wait, center_text_area, right_text_area, config.getLogin_name(), config.getLogin_pw(),
							correct_count, wrong_count, output_dir_string, url);
					if(pkp_1_0_0_1.isTest_end()) {
						continue;
					}	
				}
			}
		}
		else if(company_name.equals(config.getCompany_rmv())) {
			for (String testcase : testcase_list) {
				if(testcase.equals("1_0_0_1")) {
					RMV_1_0_0_1 rmv_1_0_0_1 = new RMV_1_0_0_1(driver, wait, center_text_area, right_text_area, config.getLogin_name(), config.getLogin_pw(),
							correct_count, wrong_count, output_dir_string, url);
					if(rmv_1_0_0_1.isTest_end()) {
						continue;
					}
				}
			}
		}		
	}
}
