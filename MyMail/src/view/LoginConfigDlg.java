package view;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import model.LoginInfo;
@SuppressWarnings("serial")
public class LoginConfigDlg extends JDialog implements ActionListener{
	
	JLabel jl1=null;
	JLabel jl2=null;
	JTextField jtf1=null;
	JTextField jtf2=null;
	JButton jb1=null;
	JButton jb2=null;
	JPanel jp=null;
	public LoginConfigDlg()
	{
		this.setTitle("User Config Dialog");
			
		jp=new JPanel();
		jp.setLayout(null);
		jl1=new JLabel("用户名:");
		jl2=new JLabel("密码 :");
		jtf1=new JTextField(10);
		jtf2=new JTextField(10);
		jb1=new JButton("确定");
		jb1.addActionListener(this);
		jb2=new JButton("取消");
		jb2.addActionListener(this);
		jl1.setBounds(50, 50, 50, 30);
		jl1.setBackground(Color.lightGray);
//		jl1.setForeground(Color.red);
		jtf1.setBounds(150, 50, 200, 30);
		jl2.setBounds(50, 150, 50, 30);
//		jl2.setForeground(Color.red);
		jtf2.setBounds(150, 150, 200, 30);
		jb1.setBounds(100,250,80,40);
		jb2.setBounds(250, 250, 80, 40);
		jp.add(jl1);
		jp.add(jtf1);
		jp.add(jl2);
		jp.add(jtf2);
		jp.add(jb1);
		jp.add(jb2);
		jp.setBackground(new Color(255,255,255));
		this.add(jp);
		
		int width=Toolkit.getDefaultToolkit().getScreenSize().width;
		int height=Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setSize(400,350);
		this.setLocation(width/4+150, height/4);
		this.setResizable(false);
//		this.setDefaultCloseOperation();
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb1)
		{
			LoginInfo.sendUserName=jtf1.getText();
			LoginInfo.sendPassword=jtf2.getText();
			this.dispose();
		}
		else if(e.getSource()==jb2)
		{
			this.dispose();
		}
	}

	/*public static void main(String []args)
	{
		LoginConfigDlg loginconfigdlg=new LoginConfigDlg();
	}*/
}
