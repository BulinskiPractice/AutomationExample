package app.config;

/**
 * This class represents the configuration and the default values to run the testcases
 * 
 * @author Michael Bulinski
 */

public class Config {
	
	private final String pkp_url = "https://beta.bilkom.pl/";
	private final String rmv_url = "https://www.rmv.de/c/de/start/";
	
	private final String pkp_testcase_path = "C:\\IT\\\\RailwayTest_InputOutput\\pkp_testcases.txt";
	private final String rmv_testcase_path = "C:\\IT\\\\RailwayTest_InputOutput\\rmv_testcases.txt";
	
	private final String pkp_testcase_package = "app.testcases.pkp";
	private final String rmv_testcase_package = "app.testcases.rmv";
	
	private final String login_name = "bulinski@posteo.de";
	private final String login_pw = "12aaS$aa21";
	
	private final String company_pkp = "PKP";
	private final String company_rmv = "RMV";
	
	private final String pkp_from_station = "Warszawa Centralna";
	private final String pkp_to_station = "Katowice Ligota";
	
	private final int wait_time = 10;
	private final int long_wait_time = 120;
	
	private final String center_seperator = "-------------------------------------------------------------------------------------------------"
			+ "-------------------------------------------------------------------------------------------------\n";
	private final String right_seperator = "------------------------------------------\n";
	
	public String getPkp_url() {
		return pkp_url;
	}

	public String getRmv_url() {
		return rmv_url;
	}

	public String getPkp_testcase_path() {
		return pkp_testcase_path;
	}

	public String getRmv_testcase_path() {
		return rmv_testcase_path;
	}

	public int getWait_time() {
		return wait_time;
	}

	public int getLong_wait_time() {
		return long_wait_time;
	}

	public String getPkp_testcase_package() {
		return pkp_testcase_package;
	}

	public String getRmv_testcase_package() {
		return rmv_testcase_package;
	}

	public String getLogin_name() {
		return login_name;
	}

	public String getLogin_pw() {
		return login_pw;
	}

	public String getCompany_pkp() {
		return company_pkp;
	}

	public String getCompany_rmv() {
		return company_rmv;
	}

	public String getPkp_from_station() {
		return pkp_from_station;
	}

	public String getPkp_to_station() {
		return pkp_to_station;
	}

	public String getCenter_seperator() {
		return center_seperator;
	}

	public String getRightSeperator() {
		return right_seperator;
	}

}
