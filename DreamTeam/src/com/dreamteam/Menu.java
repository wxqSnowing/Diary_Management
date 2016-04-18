package com.dreamteam;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;


public class Menu extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP,
			JTabbedPane.WRAP_TAB_LAYOUT);
	JPanel pAccont = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JLabel name;
	ImageIcon icon = new ImageIcon("ico/close.gif");
	String User;
	String Row;
	static String userName="";
	public Menu(String userName) {
		this.userName=userName;
		this.setTitle("我的日记");
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		init();
	}

	public void init() {
		/******************* panel模块 ***********************/
		name = new JLabel("用户： "+userName);
		name.setIcon(icon);
		pAccont.add(name);
		/******************* tab模块 ***********************/
		tabbedPane.addTab("                 我的日记                   ", icon,
				null, "查看我的日记");
		tabbedPane.addTab("                 写日记                   ", icon,
				null, "写日记");
		tabbedPane.addTab("                 信息修改                   ", icon,
				null, "修改个人信息");

		// 为JTabbedPane添加事件监听器
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				// 如果被选择的组件依然是空
				if (tabbedPane.getSelectedComponent() == null) {
					// 获取所选Tab页
					int n = tabbedPane.getSelectedIndex();
					// 为指定标前页加载内容
					loadTab(n);
				}
			}
		});
		// 系统默认选择第一页，加载第一页内容
		loadTab(0);
		tabbedPane.setPreferredSize(new Dimension(1000, 600));

		this.add(pAccont);
		this.add(tabbedPane, BorderLayout.CENTER);
		this.setResizable(false); // 设置是否可更改窗口大小
		this.setLocation(190, 20);// 设置窗口居中显示
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
	}

	private void loadTab(int n) {
		String title = tabbedPane.getTitleAt(n);

		String image =  "user/1/1/1.png,"+ "user/1/1/2.jpg,"+
				"user/1/1/3.jpg,"+"user/1/1/4.jpg" ;
		String text = "user/1/1/novel.txt";

		
	   MyDiary md = new MyDiary();
       WriteDiary m1=new WriteDiary(userName);
       UserUI uu = new UserUI();

		JPanel p = new JPanel();
		p = md.MyDiary;
		
		JPanel b = new JPanel();
		b = m1.getpShow();
		
		JPanel t = new JPanel();
		t = uu.getUser();
		if (n == 0)
			tabbedPane.setComponentAt(n, p);
		else if (n == 1)
			tabbedPane.setComponentAt(n, b);
		else if(n == 2)
			tabbedPane.setComponentAt(n, t);
		// 改变标签页的图标
		tabbedPane.setIconAt(n, new ImageIcon("ico/open.gif"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

//	public static void main(String[] args) {
//
////		 EventQueue.invokeLater(new Runnable() {
////		 @Override
////		 public void run() {
////		 JFrame.setDefaultLookAndFeelDecorated(true);
////		 JDialog.setDefaultLookAndFeelDecorated(true);
////		 try {
////		
////		 UIManager.setLookAndFeel(new
////		 org.jvnet.substance.skin.SubstanceAutumnLookAndFeel());
////		
////		 } catch (Exception e) {
////		
////		 e.printStackTrace();
////		 }
////		
////		 }
////		 });
//		 Menu browser = new Menu(userName);
//		 browser.setVisible(true);
////
////		OperaDB op = new OperaDB();
////		Connection conn = op.getConnection();
////		op.registe(conn, "yss", "yss");
////		op.login(conn, "yss", "yss");
////		
////		op.searchedAll(conn, "wxq", "hell");
//		
////		String name = "wxq", weather = "雨", time = "2015-12-12", mood = "开心",title="hi",contenturl="uytt",picturl="xzz";
////
////		Vector<DiaryModel> sr=op.searched(conn, name, weather, time, mood);
////		
////		op.save(conn,name, title, contenturl, picturl, time, weather, mood);
//
//	}

	
//	@Override
//	public void hyperlinkUpdate(HyperlinkEvent arg0) {
//
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent arg0) {
//
//	}
}
