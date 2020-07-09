package view;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import model.LoginInfo;
@SuppressWarnings("serial")
public class SaveDlg extends JDialog implements ActionListener{
	
	JLabel jl1=null;
	JTextField jtf1=null;
	JButton jb1=null;
	JButton jb2=null;
	JPanel jp=null;
	public SaveDlg()
	{
		this.setTitle("Receive Mail Config Dialog");
			
		jp=new JPanel();
		jp.setLayout(null);
		jl1=new JLabel("路径:");
		jtf1=new JTextField(10);
		jb1=new JButton("...");
		jb2=new JButton("确定");
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		jl1.setBounds(30, 50, 40, 30);
		jl1.setBackground(Color.lightGray);
		jtf1.setBounds(80, 50, 200, 30);
		jb1.setBounds(300,50,60,30);
		jb2.setBounds(170, 100, 60, 30);
		jp.add(jl1);
		jp.add(jtf1);
		jp.add(jb1);
		jp.add(jb2);
		jp.setBackground(new Color(255,255,255));
		this.add(jp);
		
		int width=Toolkit.getDefaultToolkit().getScreenSize().width;
		int height=Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setSize(400,200);
		this.setLocation(width/4+150, height/4);
		this.setResizable(false);
		this.setVisible(true);
	}
	public static void main(String []args)
	{
		@SuppressWarnings("unused")
		SaveDlg savedlg=new SaveDlg();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb1)
		{
			JFileChooser f = new JFileChooser(); 
			f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			f.showOpenDialog(null);
			String fileName=f.getSelectedFile().getAbsolutePath();
			if(fileName!=null)
			{
				jtf1.setText(fileName);
				LoginInfo.savaPath=fileName;
			}	
			System.out.println(" "+fileName);
		}	
		if(e.getSource()==jb2)
		{
			this.dispose();
		}
	}
}
