package app.config;

/**
 * This class represents the identifiers for the rmv website like ids, names, css selectors or xpaths 
 * 
 * @author Michael Bulinski
 */

public class RMV_Source_Identifier {
	
	private final String timetable_start_page_id = "timetable-start";
	private final String timetable_end_page_id = "timetable-end";	
	private final String login_mail_page_id = "login-email";
	private final String login_password_page_id = "login-password";
	private final String login_button_css_select = ".login-button";
	private final String logout_button_css_select = ".logout-button";	
	private final String rmv_loginname_page_id = "meinrmv-loginname";
	
	public String getTimetable_start_page_id() {
		return timetable_start_page_id;
	}
	public String getTimetable_end_page_id() {
		return timetable_end_page_id;
	}
	public String getLogin_mail_page_id() {
		return login_mail_page_id;
	}
	public String getLogin_password_page_id() {
		return login_password_page_id;
	}
	public String getLogin_button_css_select() {
		return login_button_css_select;
	}
	public String getRmv_loginname_page_id() {
		return rmv_loginname_page_id;
	}
	public String getLogout_button_css_select() {
		return logout_button_css_select;
	}

}
