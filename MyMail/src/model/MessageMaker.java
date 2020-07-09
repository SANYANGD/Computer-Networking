package model;

import javax.activation.DataHandler;

import java.util.*;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@SuppressWarnings("unused")
public class MessageMaker {
	//生成附件
	public MimeBodyPart createAttachment(String fileName)
	{
		MimeBodyPart attachment=new MimeBodyPart();
		DataSource ds=new FileDataSource(fileName);
		DataHandler dh=new DataHandler(ds);
		try {
			attachment.setDataHandler(dh);
			int j=fileName.lastIndexOf("\\");	
			attachment.setFileName(fileName.substring(j, fileName.length()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("附件已生成");
		return attachment;	
	}
	
	public MimeBodyPart createContent(String body,ArrayList<String> picFileNames){
		MimeBodyPart contentBody=new MimeBodyPart();		
		try {	//正文文本
			MimeMultipart contentMulti=new MimeMultipart();
			MimeBodyPart textBody=new MimeBodyPart();
			textBody.setContent(body, "text/html;charset=gbk");
			contentMulti.addBodyPart(textBody);
			if(picFileNames!=null){
				contentMulti.setSubType("related");
				for(int i=0;i<picFileNames.size();i++){//正文图片
					MimeBodyPart picBody=new MimeBodyPart();
					DataSource ds=new FileDataSource(picFileNames.get(i));
					DataHandler dh=new DataHandler(ds);
					picBody.setDataHandler(dh);
					int j=picFileNames.get(i).lastIndexOf("\\");					
					picBody.setContentID(picFileNames.get(i).substring(j, picFileNames.get(i).length()));
					picBody.setFileName(picFileNames.get(i).substring(j, picFileNames.get(i).length()));
					System.out.println("图片名为"+picFileNames.get(i).substring(j, picFileNames.get(i).length()));
					contentMulti.addBodyPart(picBody);
				}
			}
			contentBody.setContent(contentMulti);			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return contentBody;	
	}
	
	//创建总的消息
	public MimeMessage createMessage(Session session,MailBean mailbean,String body,ArrayList<String> attachFileNames,ArrayList<String> picFileNames)
	{
		MimeMessage msg=new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(mailbean.getFromAddress()));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(mailbean.getToAddress()));
			msg.setSubject(mailbean.getSubject());
			msg.setSentDate(new Date());   //设置发送时间  
			//创建邮件的各个部分
			MimeMultipart mimemultipart=new MimeMultipart();
			if(attachFileNames!=null) {//有附件
				mimemultipart.setSubType("mixed");
				for(int i=0;i<attachFileNames.size();i++)
				{
					MimeBodyPart attachment=this.createAttachment(attachFileNames.get(i));
					mimemultipart.addBodyPart(attachment);
				}
			}
			MimeBodyPart content=this.createContent(body, picFileNames);
	
			mimemultipart.addBodyPart(content);
//			mimemultipart.addBodyPart(attachment);			
			msg.setContent(mimemultipart);
			msg.saveChanges();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
		return msg;
	}
}
