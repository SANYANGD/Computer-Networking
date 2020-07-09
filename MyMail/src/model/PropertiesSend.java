package model;

import java.util.Properties;

public class PropertiesSend {
	public Properties getProperties()	{
		Properties p=new Properties();
		//p.put("mail.smtp.host", DialogProperties.mailServet);
		//p.put("mail.smtp.prot", DialogProperties.mailServerPort);
		//p.put("mail.smtp.auth", DialogProperties.validate.equals("true"));
		p.setProperty("mail.smtp.host", DialogProperties.mailServet);
        p.setProperty("mail.smtp.port", DialogProperties.mailServerPort);
        p.setProperty("mail.smtp.auth", "true");
        p.setProperty("mail.smtp.ssl.enable", "false");//"true"
        p.setProperty("mail.smtp.connectiontimeout", "5000");
		return p;
	}
}
