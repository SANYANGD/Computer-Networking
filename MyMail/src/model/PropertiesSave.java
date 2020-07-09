package model;

import java.util.Properties;

public class PropertiesSave {

	public Properties getProperties()
	{
		Properties p=new Properties();
		p.put("mail.pop3.host", LoginInfo.host);
		System.out.println(" "+LoginInfo.host);
		p.put("mail.pop3.prot", "995");
		p.put("mail.pop3.auth", 3>2);
		
		return p;
	}
}
