
package view;
import java.awt.*;

import javax.swing.*;
@SuppressWarnings("serial")
public class CheckMailDlg extends JDialog{
	private JPanel jp1=null;
	private JLabel jp1_jl1=null;
	private JTextField jp1_jtf1=null;
	private JLabel jp1_jl2=null;
	private JTextField jp1_jtf2=null;
	private JLabel jp1_jl3=null;
	private JTextField jp1_jtf3=null;
	private JLabel jp1_jl4=null;
	private JTextArea jta1;
	private JTextPane jta;
	private JScrollPane jsp=null;
	public CheckMailDlg(String from,String to,String subject,String content){
		jp1=new JPanel();        
		jp1.setBackground(new Color(255,255,255));
		jp1.setLayout(null);
		//jp1_jl1=new JLabel(new ImageIcon("images/sender.jpg"));
		jp1_jl1=new JLabel("发信人");
		jp1_jtf1=new JTextField(30);
		jp1_jtf1.setEditable(false);
		jp1_jl1.setBounds(5, 5, 60, 20);
		jp1_jtf1.setBounds(65, 5, 240, 20);
		jp1_jtf1.setText(from);
		//jp1_jl2=new JLabel(new ImageIcon("images/receiver.jpg"));
		jp1_jl2=new JLabel("收信人");
		jp1_jtf2=new JTextField(30);
		jp1_jtf2.setEditable(false);
		jp1_jl2.setBounds(5, 30, 60, 20);
		jp1_jtf2.setBounds(65, 30, 500, 20);
		jp1_jtf2.setText(to);
		//jp1_jl3=new JLabel(new ImageIcon("images/subject.jpg"));
		jp1_jl3=new JLabel("主题");
		jp1_jtf3=new JTextField(30);
		jp1_jtf3.setEditable(false);
		jp1_jl3.setBounds(5, 55, 60, 20);
		jp1_jtf3.setText(subject);
		jp1_jtf3.setBounds(65, 55, 600, 20);
		//jp1_jl4=new JLabel(new ImageIcon("images/attachment.jpg"));
		jp1_jl4=new JLabel("附件");
		jta1=new JTextArea(1,1);
		jta1.setEditable(false);
		jp1_jl4.setBounds(5, 80, 60, 20);
		jta1.setBounds(125, 80, 550, 20);
		jta1.setBackground(new Color(255,255,255));
		jta=new JTextPane();
		jta.setEditable(false);
		jta.setContentType("text/html");
//		jta.setBounds(5, 120, 660, 300);
		jsp=new JScrollPane(jta);
		jsp.setBounds(5, 120, 660, 300);
		jta.setText(content);
		jp1.add(jp1_jl1);
		jp1.add(jp1_jtf1);
		jp1.add(jp1_jl2);
		jp1.add(jp1_jtf2);
		jp1.add(jp1_jl3);
		jp1.add(jp1_jtf3);
		jp1.add(jp1_jl4);
		jp1.add(jta1);
		jp1.add(jsp);
		this.add(jp1);
		
		int width=Toolkit.getDefaultToolkit().getScreenSize().width;
		int height=Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setTitle("Check Mail Dialog");
		this.setIconImage(new ImageIcon("images/mail.jpg").getImage());
		this.setSize(680, 470);
		this.setResizable(false);
		this.setLocation(width/2-350, height/2-300);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
