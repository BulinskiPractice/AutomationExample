package app.config;

/**
 * This class represents the identifiers for the pkp website like ids, names, css selectors or xpaths 
 * 
 * @author Michael Bulinski
 */

public class PKP_Source_Identifier {
	
	private final String login_page_id = "zalogujLink";
	private final String username_page_id = "username";
	private final String password_page_id = "password";
	private final String buttonLogin_page_id = "buttonZaloguj";
	private final String logoutButton_page_id = "wyloguj";
	
	public String getLogin_page_id() {
		return login_page_id;
	}
	public String getUsername_page_id() {
		return username_page_id;
	}
	public String getPassword_page_id() {
		return password_page_id;
	}
	public String getButtonLogin_page_id() {
		return buttonLogin_page_id;
	}
	public String getLogoutButton_page_id() {
		return logoutButton_page_id;
	}
	
}
