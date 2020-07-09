package view;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import model.LoginInfo;
@SuppressWarnings("serial")
public class RecMailDlg extends JDialog implements ActionListener{
	
	JLabel jl1=null;
	JLabel jl2=null;
	JLabel jl3=null;
	JLabel jl4=null;
	JTextField jtf1=null;
	JTextField jtf2=null;
	JTextField jtf3=null;
	JTextField jtf4=null;
	JButton jb1=null;
	JButton jb2=null;
	JPanel jp=null;
	public RecMailDlg()
	{
		this.setTitle("Receive Mail Config Dialog");
			
		jp=new JPanel();
		jp.setLayout(null);
		jl1=new JLabel("用户名:");
		jl2=new JLabel("密码:");
		jl3=new JLabel("主机名:");
		jl4=new JLabel("端口:");
		jtf1=new JTextField(10);
		jtf2=new JTextField(10);
		jtf3=new JTextField(10);
		jtf4=new JTextField(10);
		jb1=new JButton("确定");
		jb1.addActionListener(this);
		jb2=new JButton("取消");
		jb2.addActionListener(this);
		jl1.setBounds(50, 80, 50, 30);
		jl1.setBackground(Color.lightGray);
		jtf1.setBounds(150, 80, 200, 30);
		jl2.setBounds(50, 140, 50, 30);
		jtf2.setBounds(150, 140, 200, 30);
		jl3.setBounds(50, 20, 50, 30);
		jl3.setBackground(Color.lightGray);
		jtf3.setBounds(150, 20, 200, 30);
		jl4.setBounds(50, 200, 50, 30);
		jl4.setBackground(Color.lightGray);
		jtf4.setBounds(150, 200, 200, 30);
		jb1.setBounds(100,250,80,40);
		jb2.setBounds(250, 250, 80, 40);
		jp.add(jl3);
		jp.add(jtf3);
		jp.add(jl1);
		jp.add(jtf1);
		jp.add(jl2);
		jp.add(jtf2);
		jp.add(jl4);
		jp.add(jtf4);
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
			LoginInfo.recUserName=jtf1.getText();
			LoginInfo.recPassword=jtf2.getText();
			LoginInfo.host=jtf3.getText();
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
