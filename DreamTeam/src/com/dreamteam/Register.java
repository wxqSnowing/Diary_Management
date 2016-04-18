package com.dreamteam;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Register extends JFrame implements ActionListener{
	JLabel label1,label2,label3;
	JTextField user;
	JPasswordField password,password2;
	Box baseBox,boxV1,boxV2;
	JButton save,cancle;
	public Register(){
		setVisible(true);
		init();
		setBounds(500,200,400,200);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	void init(){
		user=new JTextField(10);
		password=new JPasswordField(10);
		password2=new JPasswordField(10);
	
		label1=new JLabel("用  户  名：");
		label2=new JLabel("密          码：");
		label3=new JLabel("确认密码：");
		save=new JButton("确定");
		cancle=new JButton("取消");
	
		save.addActionListener(this);
		cancle.addActionListener(this);
		setLayout(new FlowLayout());
		boxV1=Box.createVerticalBox();
		boxV2=Box.createVerticalBox();
		boxV1.add(label1);
		boxV1.add(Box.createVerticalStrut(28));
		boxV1.add(label2);
		boxV1.add(Box.createVerticalStrut(20));
		boxV1.add(label3);
		boxV1.add(Box.createVerticalStrut(18));
		boxV1.add(save);
		boxV2.add(user);
		boxV2.add(Box.createVerticalStrut(18));
		boxV2.add(password);
		boxV2.add(Box.createVerticalStrut(18));
		boxV2.add(password2);
		boxV2.add(Box.createVerticalStrut(18));
		boxV2.add(cancle);
		baseBox=Box.createHorizontalBox();
		baseBox.add(boxV1);
		baseBox.add(boxV2);
		add(baseBox);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==save){
			String userString=user.getText();
			@SuppressWarnings("deprecation")
			String passString=password.getText();
			@SuppressWarnings("deprecation")
			String passString2=password2.getText();
			if(userString.equals("")||userString==null
					||passString.equals("")||passString==null
					||passString2.equals("")||passString2==null){
				JOptionPane.showMessageDialog(this, "请将信息输入完整！?","警告",JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(userString.length()!=user.getText().trim().length()){
				JOptionPane.showMessageDialog(this, "密码两次输入不同！","警告",JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(!passString.equals(passString2)){
					JOptionPane.showMessageDialog(this, "用户名不能有空格","警告",JOptionPane.WARNING_MESSAGE);
				return;
			}
			char c[]=(passString).toCharArray();
			for(int i=0;i<c.length;i++){
				if((!(c[i]>='a'&&c[i]<='z'))
						&&(!(c[i]>='0'&&c[i]<='9'))){
						JOptionPane.showMessageDialog(this, "密码只能包含数字和字母","警告",JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
			OperaDB ro=new OperaDB();
			@SuppressWarnings("static-access")
			boolean isregeste=ro.registe(ro.getConnection(),userString,passString);
			if(!isregeste){
				JOptionPane.showMessageDialog(this, "用户已存在","警告",JOptionPane.WARNING_MESSAGE);
				return;
			}else{
				JOptionPane.showMessageDialog(this, "注册成功","成功",JOptionPane.INFORMATION_MESSAGE);
						setVisible(false);
						@SuppressWarnings("unused")
						File file=new File("USER\\"+userString);
			}	
		}else{
			this.setVisible(false);
		}
		
	}
}
