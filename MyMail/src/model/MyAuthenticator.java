/**
 * ������֤
 */
package model;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import javax.mail.*;

@SuppressWarnings("unused")
public class MyAuthenticator extends Authenticator {
	String username = null;
	String password = null;

	public MyAuthenticator(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password.toCharArray());
	}

}
