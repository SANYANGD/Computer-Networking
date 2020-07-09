
package model;

import java.sql.Date;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.*;

@SuppressWarnings("unused")
public class MailParse {

	private MimeMessage message = null;
	private String attachSavPath = ""; 
	private StringBuffer bodyText = null;
	private Folder folder = null;
	private Store store = null;

	public MailParse() {
		attachSavPath = LoginInfo.savaPath;
		bodyText = new StringBuffer();
	}

	public Folder getFolder(){
		String host = LoginInfo.host;
		String userName = LoginInfo.recUserName;
		String passWord = LoginInfo.recPassword;
		Properties props=new PropertiesSave().getProperties();
		//Properties props=new Properties();
		Session session = Session.getInstance(props, new Authenticator() {
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(
						LoginInfo.recUserName, LoginInfo.recPassword);
			}
		});
		try {
			//store = session.getStore();
			store=session.getStore("pop3");
			System.out.println(host+userName+passWord);
			store.connect(host, userName, passWord); //
			folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		/*
		 * finally { try { if(folder!=null) { folder.close(false); }
		 * if(store!=null) { store.close(); } } catch (Exception e2) { // TODO:
		 * handle exception e2.printStackTrace(); }
		 */
		System.out.println("Folder");
		return folder;
	}

	public void closeFloder(Folder folder) {
		try {
			if (folder != null)
				folder.close(false);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public Message[] getAllMessages(){
		Message[] message = null;
		try {
			message = this.getFolder().getMessages();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//System.out.println("");
		return message;
	}

	public String getSubject(Message message) 
	{
		String subject = "";
		try {
			subject = MimeUtility.decodeText(message.getSubject());
			System.out.println(""+subject);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return subject;
	}

	@SuppressWarnings("deprecation")
	public String getDate(Message message)
	{
		String sendDate = null;
		try {
			sendDate = message.getSentDate().toGMTString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return sendDate;

	}

	public String getFrom(Message message){
		String from = null;
		try {
			InternetAddress[] address = (InternetAddress[]) message.getFrom();
			from = address[0].getAddress();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return from;
	}
	
	
	public String getTo(Message message){
		String to = null;
		try {
			InternetAddress[] address = (InternetAddress[]) message.getAllRecipients();
			to = address[0].getAddress();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return to;
	}

	public Message getSelectedMsg(int i) {
		Message message = null;
		try {
			message = this.getFolder().getMessage(i);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return message;
	}

	public void getMailContent(Part part) { 
		String contenttype = null;
		try {
			contenttype = part.getContentType();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		int nameindex = contenttype.indexOf("name "); 
		boolean conname = false; 
		if (nameindex != -1) {
			conname = true;
		}
		System.out.println("CONTENTTYPE: " + contenttype);
		try {
			if (part.isMimeType("text/plain ") && !conname) {
				bodyText.append((String) part.getContent());
			} else if (part.isMimeType("text/html ") && !conname) {
				bodyText.append((String) part.getContent());
			} else if (part.isMimeType("multipart/* ")) {
				Multipart multipart = (Multipart) part.getContent();
				int counts = multipart.getCount();
				for (int i = 0; i < counts; i++) {
					getMailContent(multipart.getBodyPart(i));
				}
			} else if (part.isMimeType("message/rfc822 ")) {
				getMailContent((Part) part.getContent());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public String getBodyText(){
		return this.bodyText.toString();
	}

	public boolean isContainAttach(Part part) { 
		boolean attachflag = false;
		try {
			String contentType = part.getContentType();
			if (part.isMimeType("multipart/* ")) {
				Multipart mp = (Multipart) part.getContent();
				for (int i = 0; i < mp.getCount(); i++) {
					BodyPart mpart = mp.getBodyPart(i);
					String disposition = mpart.getDisposition();
					if ((disposition != null)
							&& ((disposition.equals(Part.ATTACHMENT)) || (disposition
									.equals(Part.INLINE)))) {
						attachflag = true;
					} else if (mpart.isMimeType("multipart/* ")) {
						attachflag = isContainAttach((Part) mpart);
					} else {
						String contype = mpart.getContentType();
						if (contype.toLowerCase().indexOf("application ") != -1) {
							attachflag = true;
						}
						if (contype.toLowerCase().indexOf("name ") != -1) {
							attachflag = true;
						}
					}
				}
			} else if (part.isMimeType("message/rfc822 ")) {
				attachflag = isContainAttach((Part) part.getContent());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
		return attachflag;
	}

	public void saveAttachMent(Part part) throws Exception { 
		String fileName = " ";
		if (part.isMimeType("multipart/* ")) {
			Multipart mp = (Multipart) part.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart mpart = mp.getBodyPart(i);
				String disposition = mpart.getDisposition();
				if ((disposition != null)
						&& ((disposition.equals(Part.ATTACHMENT)) || (disposition
								.equals(Part.INLINE)))) {
					fileName = mpart.getFileName();
					if (fileName.toLowerCase().indexOf("gb2312 ") != -1) {
						fileName = MimeUtility.decodeText(fileName);
					}
					saveFile(fileName, mpart.getInputStream());
				} else if (mpart.isMimeType("multipart/* ")) {
					saveAttachMent(mpart);
				} else {
					fileName = mpart.getFileName();
					if ((fileName != null)
							&& (fileName.toLowerCase().indexOf("GB2312 ") != -1)) {
						fileName = MimeUtility.decodeText(fileName);
						saveFile(fileName, mpart.getInputStream());
					}
				}
			}
		} else if (part.isMimeType("message/rfc822 ")) {
			saveAttachMent((Part) part.getContent());
		}
	}

	private void saveFile(String fileName, InputStream in){ 
		String osName = System.getProperty("os.name ");
		String storedir = attachSavPath; 
		String separator = " ";
		if (osName == null) {
			osName = " ";
		}
		if (osName.toLowerCase().indexOf("win ") != -1) {
			separator = "\\ ";
			if ((storedir == null) || storedir.equals(" ")) {
				storedir = "c:\\tmp ";
			}
		} else {
			separator = "/ ";
			storedir = "/tmp ";
		}
		File storefile = new File(storedir + separator + fileName);
		System.out.println("storefile 's   path:   " + storefile.toString());
		// for(int i=0;storefile.exists();i++){
		// storefile = new File(storedir+separator+fileName+i);
		// }
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(storefile));
			bis = new BufferedInputStream(in);
			int c;
			while ((c = bis.read()) != -1) {
				bos.write(c);
				bos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bos.close();
				bis.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}			
		}
	}
}
