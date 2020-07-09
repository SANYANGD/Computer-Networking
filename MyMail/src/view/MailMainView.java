package view;

//sohu-->sina
import model.*;


import java.util.*;

import java.awt.*;

import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.net.PasswordAuthentication;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.swing.*;
import javax.swing.text.StyledDocument;

@SuppressWarnings({ "unused", "serial" })
public class MailMainView extends JFrame implements ActionListener,
		MouseListener, KeyListener {

	/**
	 * @param args
	 */
	JToolBar jtb = null;
	JButton confjb1 = null;
	JButton confjb2 = null;
	JButton confjb3 = null;
	JButton confjb4 = null;
	// JButton jp1_jb1=null;
	// JButton jp1_jb2=null;
	JLabel jp1_jl1 = null;
	JLabel jp1_jl2 = null;
	JSplitPane jsp = null;
	JTabbedPane jtp = null;
	JPanel jp1 = null;
	JPanel jp2, jp3, jp4;
	JButton jp3_jb1, jp3_jb2;
	JPanel jp3_jp1;
	JLabel jp4_jl1, jp4_jl2, jp4_jl3, jp4_jl4;
	JTextField jp4_jtf1, jp4_jtf2, jp4_jtf3;
	JButton jp4_jb1 = null;
	JButton jp4_jb2 = null;
	JButton jp4_jb3 = null;
	JButton jp4_jb4 = null;
	JTextPane jta = null;
	private StyledDocument doc = null;
	JTextArea jta1 = null;
	JScrollPane jscrollpane = null;
	ArrayList<String> picFileNames = null;
	ArrayList<String> attachFileNames = null;
	String body = "";
	MailParse mailparse = null;
	int index1 = 0, index2;

	public MailMainView() {
		Font fnt1 = new Font("微软雅黑", Font.BOLD, 16);
		Font fnt2 = new Font("微软雅黑", Font.BOLD, 8);
		jtb = new JToolBar();
		jtb.setBackground(new Color(255,255,255));
		//confjb1 = new JButton(new ImageIcon("images/userconfg.jpg"));
		confjb1 = new JButton("用户设置"); confjb1.setFont(fnt1);
		confjb1.addActionListener(this);
		confjb1.setToolTipText("设置发件人用户名密码");
		//confjb2 = new JButton(new ImageIcon("images/conconfg.jpg"));
		confjb2 = new JButton("发信设置"); confjb2.setFont(fnt1);
		confjb2.addActionListener(this);
		confjb2.setToolTipText("设置发件服务器");
		//confjb3 = new JButton(new ImageIcon("images/savingBox.jpg"));
		confjb3 = new JButton("收信设置"); confjb3.setFont(fnt1);
		confjb3.addActionListener(this);
		confjb3.setToolTipText("设置收件服务器");
		//confjb4 = new JButton(new ImageIcon("images/savingConf.jpg"));
		confjb4 = new JButton("保存设置"); confjb4.setFont(fnt1);
		confjb4.addActionListener(this);
		confjb4.setToolTipText("设置邮件保存路径");
		jtb.setFloatable(false);
		jtb.add(confjb1);
		jtb.add(confjb2);
		jtb.add(confjb3);
		jtb.add(confjb4);
		this.add(jtb, BorderLayout.NORTH);

		jp1 = new JPanel(); 
		// jp1.setBackground(new Color(214,236,200));
		/*
		 * jp1_jb1=new JButton(new ImageIcon("images/receive.jpg")); jp1_jb2=new
		 * JButton(new ImageIcon("images/send.jpg")); jp1.add(jp1_jb1);
		 * jp1.add(jp1_jb2);
		 */
		
		//jp1_jl1 = new JLabel(new ImageIcon("images/receive.jpg"));
		jp1_jl1 = new JLabel("--收信--");jp1_jl1.setFont(fnt1);
		jp1_jl1.addMouseListener(this);
		jp1_jl1.setBackground(new Color(255, 255, 255));
		//jp1_jl2 = new JLabel(new ImageIcon("images/write.jpg"));
		jp1_jl2 = new JLabel("--写信--");	jp1_jl2.setFont(fnt1);
		jp1_jl2.addMouseListener(this);
		jp1_jl2.setBackground(new Color(255, 255, 255));
		jp1.add(jp1_jl1);
		jp1.add(jp1_jl2);

		jtp = new JTabbedPane(); 
		jtp.setBackground(new Color(255, 255, 255));

		jp2 = new JPanel();
		jp2.setBackground(new Color(255, 255, 255));

		jp3 = new JPanel(); 
		jp3.setLayout(null);
		jp3.setBackground(new Color(255, 255, 255));
		jp3_jb1 = new JButton("查看收件箱");
		jp3_jb1.addActionListener(this);
		jp3_jb1.setBounds(150, 30, 100, 30);
		jp3_jb1.setBackground(new Color(255, 255, 255));
		jp3_jb2 = new JButton("删除");
		jp3_jb2.addActionListener(this);
		jp3_jb2.setBounds(350, 30, 100, 30);
		jp3_jb2.setBackground(new Color(255, 255, 255));
		/*
		 * jp3_jp1=new JPanel(); jp3_jp1.setBounds(30, 80, 610, 350);
		 */
		jp3.add(jp3_jb1);
		jp3.add(jp3_jb2);
		/* jp3.add(jp3_jp1); */

		jp4 = new JPanel(); 
		jp4.setBackground(new Color(255,255,255));
		jp4.setLayout(null);
		//jp4_jl1 = new JLabel(new ImageIcon("images/sender.jpg"));
		jp4_jl1 = new JLabel("发件人");
		jp4_jtf1 = new JTextField(30);
		jp4_jl1.setBounds(5, 5, 60, 20);
		jp4_jtf1.setBounds(65, 5, 240, 20);
		//jp4_jl2 = new JLabel(new ImageIcon("images/receiver.jpg"));
		jp4_jl2 = new JLabel("收件人");
		jp4_jtf2 = new JTextField(30);
		jp4_jl2.setBounds(5, 30, 60, 20);
		jp4_jtf2.setBounds(65, 30, 500, 20);
		//jp4_jl3 = new JLabel(new ImageIcon("images/subject.jpg"));
		jp4_jl3 = new JLabel("主题");
		jp4_jtf3 = new JTextField(30);
		jp4_jl3.setBounds(5, 55, 60, 20);
		jp4_jtf3.setBounds(65, 55, 600, 20);
		//jp4_jl4 = new JLabel(new ImageIcon("images/attachment.jpg"));
		jp4_jl4 = new JLabel("附件");
		//jp4_jb1 = new JButton(new ImageIcon("images/addattachment.jpg"));
		jp4_jb1 = new JButton("添加附件");jp4_jb1.setFont(fnt2);
		jta1 = new JTextArea(1, 1);
		jp4_jb1.addActionListener(this);
		jp4_jl4.setBounds(5, 80, 80, 30);
		jp4_jb1.setBounds(65, 80, 80, 20);
		jta1.setBounds(125, 80, 550, 20);
		jta1.setBackground(new Color(255, 255, 255));
		//jp4_jb2 = new JButton(new ImageIcon("images/send.jpg"));
		jp4_jb2 = new JButton("发送");
		jp4_jb2.setBackground(new Color(255,255,255));
		jp4_jb2.addActionListener(this);
		jp4_jb2.setBounds(200, 105, 70, 30);
		//jp4_jb3 = new JButton(new ImageIcon("images/save.jpg"));
		jp4_jb3 = new JButton("存草稿");
		jp4_jb3.addActionListener(this);
		jp4_jb3.setBounds(275, 105, 70, 30);
		//jp4_jb4 = new JButton(new ImageIcon("images/addPics.jpg"));
		jp4_jb4 = new JButton("插入图片");
		jp4_jb4.addActionListener(this);
		jp4_jb4.setBounds(65, 105, 80, 20);
		jp4_jb4.setBackground(new Color(255, 255, 255));
		jp4_jb4.setToolTipText("插入图片");jp4_jb4.setFont(fnt2);

		jta = new JTextPane();
		doc = jta.getStyledDocument();
		// jta.setBounds(5, 140, 660, 300);
		jscrollpane = new JScrollPane(jta);
		jscrollpane.setBounds(5, 140, 660, 300);
		// jta.addKeyListener(this);
		jp4.add(jp4_jl1);
		jp4.add(jp4_jtf1);
		jp4.add(jp4_jl2);
		jp4.add(jp4_jtf2);
		jp4.add(jp4_jl3);
		jp4.add(jp4_jtf3);
		jp4.add(jp4_jl4);
		jp4.add(jp4_jb1);
		jp4.add(jta1);
		jp4.add(jp4_jb2);
		jp4.add(jp4_jb3);
		jp4.add(jp4_jb4);
		// jp4.add(jta);
		jp4.add(jscrollpane);

		jtp.addTab("今日", jp2);
		jtp.addTab("收信", jp3);
		jtp.addTab("写信", jp4);
		/*JButton jb_jtp1 = new JButton("今日");
		JButton jb_jtp2 = new JButton("收信");
		JButton jb_jtp3 = new JButton("写信");
		jtp.add(jb_jtp1);
		jtp.add(jb_jtp2);
		jtp.add(jb_jtp3);*/

		jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, jp1, jtp);
		jsp.setDividerLocation(110);
		jsp.setDividerSize(0);
		jsp.setOneTouchExpandable(false);
		this.add(jsp, BorderLayout.CENTER);

		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setTitle("Mail client");
		this.setIconImage(new ImageIcon("images/mail.jpg").getImage());
		this.setSize(800, 600);
		this.setResizable(false);
		this.setLocation(width / 2 - 350, height / 2 - 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MailMainView mailmainview = new MailMainView();
	}

	public Message getMessage() {
		Properties properties = new PropertiesSend().getProperties();
		// Authenticator authenticator=null;
		Session session = null;
		if (DialogProperties.validate.equals("true")) {
			session = Session.getInstance(properties, new Authenticator() {
				protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
					return new javax.mail.PasswordAuthentication(
							LoginInfo.sendUserName, LoginInfo.sendPassword);
				}
			});
		} else {
			session = Session.getInstance(properties);
		}
		session.setDebug(true);
		String fileName1 = null;
		String fileName2 = null;
		// String body=jta.getText();
		// body+=jta.getText();
		if(body.equals("")){
			body=this.jta.getText();
			System.out.println(" "+jta.getText());
		}			
		System.out.println(" " + body);
		MailBean mailbean = new MailBean();
		mailbean.setFromAddress(jp4_jtf1.getText());
		mailbean.setToAddress(jp4_jtf2.getText());
		mailbean.setSubject(jp4_jtf3.getText());
		Message msg = new MessageMaker().createMessage(session, mailbean, body,
				attachFileNames, picFileNames);
		return msg;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == confjb1) {
			new LoginConfigDlg();
		} else if (e.getSource() == confjb2) {
			new ConConfigDlg();
		} else if (e.getSource() == confjb3) {
			new RecMailDlg();
		} else if (e.getSource() == confjb4) {
			new SaveDlg();
		} else if (e.getSource() == jp3_jb1) {
			
			mailparse = new MailParse();
			Message[] messages = mailparse.getAllMessages();
			System.out.println(" " + messages.length);
			int i = messages.length;
//			jp3_jp1 = new JPanel(new GridLayout(i, 1, 3, 3));
			jp3_jp1 = new JPanel();
			jp3_jp1.setLayout(null);
			jp3_jp1.setBounds(30, 80, 610, 350);
			JLabel maillabel[] = new JLabel[i];
			for (int j = 0; j < i; j++) {
				String date = mailparse.getDate(messages[j]);
				System.out.println(" " + date);
				String subject = mailparse.getSubject(messages[j]);
				System.out.println(" " + subject);
				String sender = mailparse.getFrom(messages[j]);
				System.out.println(" " + sender);
				maillabel[j] = new JLabel(j + 1 + " " + date + " " + subject
						+ " " + sender);
				maillabel[j].addMouseListener(this);
				maillabel[j].setBounds(10, j*30, 400, 20);
				maillabel[j].setBackground(new Color(246,241,232));
				jp3_jp1.add(maillabel[j]);
			}
			jp3.add(jp3_jp1);
		} else if (e.getSource() == jp3_jb2) {
		} else if (e.getSource() == jp4_jb1) {
			attachFileNames = new ArrayList<String>();
			JFileChooser f = new JFileChooser(); 
			f.showOpenDialog(null);
			String fileName = f.getSelectedFile().getAbsolutePath();
			System.out.println(" " + fileName);
			jta1.append(f.getSelectedFile().getName() + "  ");
			attachFileNames.add(fileName);
		} else if (e.getSource() == jp4_jb2) {
			Message msg = this.getMessage();
			try {
				Transport.send(msg);
				System.out.println("邮件已发送");
				JOptionPane.showMessageDialog(this, "邮件发送成功");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "邮件发送失败");
			}
		} else if (e.getSource() == jp4_jb3) {
			FileOutputStream fos = null;
			File file = new File(LoginInfo.savaPath);
			try {
				 
				if (file.exists()) {
					System.out.println(" ");
				}
				if (LoginInfo.savaPath.endsWith(File.separator)) {
					System.out.println(" ");
				}
				if (!file.getParentFile().exists()) {
					System.out.println(" ");
					if (!file.getParentFile().mkdirs()) {
						System.out.println(" ");
					}
				}
				if (file.createNewFile()) {
					System.out.println(" ");
				}
				Message msg = this.getMessage();
				fos = new FileOutputStream(file);
				msg.writeTo(fos);
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			} finally {
				try {
					fos.close();
				} catch (Exception e3) {
					// TODO: handle exception
					e3.printStackTrace();
				}
			}
		} else if (e.getSource() == jp4_jb4) {
			
			index2 = doc.getLength();
			body += jta.getText().substring(index1, index2);
			picFileNames = new ArrayList<String>();
			JFileChooser f = new JFileChooser();
			f.showOpenDialog(null);
			String fileName = f.getSelectedFile().getAbsolutePath();
			System.out.println(" " + fileName);
			// jta1.append(f.getSelectedFile().getName()+"  ");
			body += "<img src='cid:" + f.getSelectedFile().getName() + "'>";
			picFileNames.add(fileName);
			jta.setCaretPosition(doc.getLength());
			jta.insertIcon(new ImageIcon(fileName));
			index1 = doc.getLength();
			System.out.println(" " + jta.getText());
			System.out.println(" " + body);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getClickCount() == 1) {
			if (e.getSource() == jp1_jl1) {
				jtp.getComponentAt(1).setVisible(true);
				jtp.getComponentAt(2).setVisible(false);
			} else if (e.getSource() == jp1_jl2) {
				jtp.getComponentAt(2).setVisible(true);
				jtp.getComponentAt(1).setVisible(false);
			}
		} else if (e.getClickCount() == 2) {
			String text = ((JLabel) e.getSource()).getText();
			int index = text.indexOf(" ");
			int num = Integer.parseInt(text.substring(0, index));
			mailparse = new MailParse();
			// 转到新界面
			Message msg = mailparse.getSelectedMsg(num);
			String from = mailparse.getFrom(msg);
			String to = mailparse.getTo(msg);
			String subject = mailparse.getSubject(msg);
			mailparse.getMailContent(msg);
			String mailText = mailparse.getBodyText();
			System.out.println("邮件内容为：" + mailText);
			new CheckMailDlg(from, to, subject, mailText);
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == jp1_jl1) {
			jp1_jl1.setEnabled(true);
		} else if (e.getSource() == jp1_jl2) {
			jp1_jl2.setEnabled(true);
		}
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == jp1_jl1) {
			jp1_jl1.setEnabled(false);
			jp1_jl1.setBackground(new Color(255,255,255));
		} else if (e.getSource() == jp1_jl2) {
			jp1_jl2.setEnabled(false);
			jp1_jl2.setBackground(new Color(255,255,255));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		/*
		 * body+=e.getKeyText(e.getKeyCode()).toLowerCase();
		 * System.out.println(body);
		 */
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
