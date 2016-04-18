package com.dreamteam;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

@SuppressWarnings("serial")
public class Login extends JFrame implements ActionListener, HyperlinkListener {
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem chinese,english;
	JLabel label1,label2;
	JTextField user;
	JPasswordField password;
	JButton login,register;
	Box baseBox,boxV1,boxV2;
	
	public Login(){
		initBaseBox();
		setVisible(true);
		setBounds(500,200,400,200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void initBaseBox(){

		user=new JTextField(10);
		password=new JPasswordField(10);
		
		label1=new JLabel("用户名：");
		label2=new JLabel("密      码：");
		login=new JButton("登录");
		register=new JButton("注册");
		login.addActionListener(this);
		register.addActionListener(this);
		setLayout(new FlowLayout());
		boxV1=Box.createVerticalBox();
		boxV2=Box.createVerticalBox();
		boxV1.add(label1);
		boxV1.add(Box.createVerticalStrut(28));
		boxV1.add(label2);
		boxV1.add(Box.createVerticalStrut(18));
		boxV1.add(login);
		boxV2.add(user);
		boxV2.add(Box.createVerticalStrut(18));
		boxV2.add(password);
		boxV2.add(Box.createVerticalStrut(18));
		boxV2.add(register);
		baseBox=Box.createHorizontalBox();
		baseBox.add(boxV1);
		baseBox.add(boxV2);
		add(baseBox);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==login){
			String usernameString=user.getText();
			@SuppressWarnings({ "deprecation" })
			String passwordString=password.getText();			
			OperaDB op=new OperaDB();
			boolean isExist=op.login(op.getConnection(),usernameString,passwordString);
			System.out.print(usernameString+" ***"+passwordString);
			if(!isExist){
				JOptionPane.showMessageDialog(this, "不存在此用户或用户名密码错误","警告",JOptionPane.WARNING_MESSAGE);
				return;
			}else{
				Menu m=new Menu(usernameString);
				setVisible(false);
				m.setVisible(true);	
			}

		}else if(e.getSource()==register){
			Register registeForm=new Register();
				registeForm.setTitle("注册");
		}
	}
	void beautiful(){
   	 EventQueue.invokeLater(new Runnable() {
   		 @Override
   		 public void run() {
   		 JFrame.setDefaultLookAndFeelDecorated(true);
   		 JDialog.setDefaultLookAndFeelDecorated(true);
   		 try {
   		
   		 UIManager.setLookAndFeel(new
   		 org.jvnet.substance.skin.SubstanceAutumnLookAndFeel());
   		
   		 } catch (Exception e) {
   		
   		 e.printStackTrace();
   		 }
   		 }
   		 });
    }
		
	public static void main(String[] args) {
		 EventQueue.invokeLater(new Runnable() {
		 @Override
		 public void run() {
		 JFrame.setDefaultLookAndFeelDecorated(true);
		 JDialog.setDefaultLookAndFeelDecorated(true);
		 try {
		
		 UIManager.setLookAndFeel(new
		 org.jvnet.substance.skin.SubstanceAutumnLookAndFeel());
		
		 } catch (Exception e) {
		
		 e.printStackTrace();
		 }
		 Login l=new Login();
		 }
		 });
		
	}	
	@Override
	public void hyperlinkUpdate(HyperlinkEvent arg0) {

	}

}
