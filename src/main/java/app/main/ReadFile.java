package app.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import javafx.scene.control.TextArea;

/**
 * This class is reading the testcases from the testcase.txt file. After adding into a arraylist,
 * the string lines are splitting to get only the first string , which is the testcase identifier.
 * 
 * @author Michael Bulinski
 */

public class ReadFile {

	private static Logger logger = Logger.getLogger("test.vhgs.testhelper.ReadFile");

	public ArrayList<String> read(String testcase_path, TextArea center_text_area) {
		logger.info("testcase path - " + testcase_path);
		center_text_area.appendText(logger.getName() + " - testcase path - " + testcase_path + "\n");
		logger.info("Reading testcases");
		center_text_area.appendText(logger.getName() + " - Reading testcases\n");

		ArrayList<String> testcase_list = new ArrayList<String>();

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(testcase_path))) {
			int count = 0;
			String line = bufferedReader.readLine();
			while (line != null) {
				if (line.length() > 0) {
					if (Character.isDigit(line.charAt(0))) {
						String[] splitArray = line.split(" ");
						splitArray[0] = splitArray[0].replace('.', '_');
						testcase_list.add(splitArray[0]);
						count++;
					}
				}
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
			logger.info("Reading " + count + "  testcases successfully");
			center_text_area.appendText(logger.getName() + " - Reading " + count + " testcases successfully\n");

		} catch (IOException ex) {
			logger.error(ex.toString());
			center_text_area.appendText("\n" + ex.toString() + "\n\n");
		}
		return testcase_list;
	}

}
