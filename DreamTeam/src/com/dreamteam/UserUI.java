package com.dreamteam;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.util.Timer;
import java.util.TimerTask;

public class UserUI {
	JPanel User = new JPanel();
	JLabel imageicon = new JLabel();
	JButton imagechange = new JButton("            修改头像            ");
	static JButton passchange = new JButton("            修改密码            ");
	static JTextField oldpass = new JTextField(30);
	static JTextField newpass = new JTextField(30);
	static int flag = 0;
	static boolean jug = true;
	static String Users = Menu.userName;
	static String nep = new String();
	static String olp = new String();

	public UserUI() {
		init();
		flag = 0;
		action();
	}

	public JPanel getUser() {
		return User;
	}

	public void setUser(JPanel User) {
		this.User = User;
	}

	public void init() {
		User.setLayout(new GridBagLayout());
		imageicon.setIcon(new ImageIcon("image/cute.png"));

		GridBagConstraints c1 = new GridBagConstraints();

		JPanel c = new JPanel();
		c.add(new JLabel("请输入你现在的密码"));
		c.add(oldpass);
		c1.gridx = 0;
		c1.gridy = 0;
		c1.insets = new Insets(150, 0, 0, 0);
		c1.fill = GridBagConstraints.NONE;
		c1.weightx = 0;
		c1.weighty = 0;
		User.add(c, c1);

		JPanel d = new JPanel();
		d.add(new JLabel("请输入你的新密码"));
		d.add(newpass);
		
		c1.gridx = 0;
		c1.gridy = 1;
		c1.insets = new Insets(150, 0, 0, 0);
		c1.fill = GridBagConstraints.NONE;
		c1.weightx = 0;
		c1.weighty = 0;
		User.add(d, c1);

		c1.gridx = 2;
		c1.gridy = 0;
		c1.insets = new Insets(150, 0, 0, 0);
		c1.fill = GridBagConstraints.NONE;
		c1.weightx = 1;
		c1.weighty = 0;
		User.add(imageicon, c1);

		c1.gridx = 2;
		c1.gridy = 1;
		c1.fill = GridBagConstraints.NONE;
		c1.insets = new Insets(5, 0, 0, 0);
		c1.weightx = 0;
		c1.weighty = 0;
		User.add(imagechange, c1);

		c1.gridx = 0;
		c1.gridy = 2;
		c1.fill = GridBagConstraints.NONE;
		c1.insets = new Insets(15, 0, 0, 0);
		c1.weightx = 0;
		c1.weighty = 0;
		User.add(passchange, c1);

		JPanel p = new JPanel();
		c1.gridx = 0;
		c1.gridy = 3;
		c1.fill = GridBagConstraints.NONE;
		c1.insets = new Insets(5, 0, 0, 0);
		c1.weightx = 1;
		c1.weighty = 1;
		User.add(p, c1);

	}

	public static void pass(String oldp, String newp, String Users) {
		// 设定数据库驱动，数据库连接地址、端口、名称，用户名，密码
		String driverName = "com.mysql.jdbc.Driver";
		ResultSet rs = null;
		PreparedStatement pstmt1 = null; // 用于查询语句
		PreparedStatement pstmt2 = null;// 用于插入语句

		// 数据库连接对象
		Connection conn = null;

		// 建立连接
		Properties properties = new Properties();
		FileInputStream fis = null;
		try {
			// 加载驱动
			Class.forName(driverName);
			try {
				fis = new FileInputStream("mysql.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				properties.load(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}

			String database = properties.getProperty("database");
			String user = properties.getProperty("user");
			String password = properties.getProperty("password");
			String sql = null;// 记录sql语句

			if (database != null && user != null && password != null) {
				String url = "jdbc:mysql://localhost:3306/" + database + "?useUnicode=true&characterEncoding=utf-8";
				conn = DriverManager.getConnection(url, user, password);
			}

			sql = "select count(*) cou from user where password=? and name = ?";
			// 创建该连接下的PreparedStatement对象
			pstmt1 = conn.prepareStatement(sql);

			// 传递第一个参数值 ，代替第一个问号
			pstmt1.setString(1, oldp);
			pstmt1.setString(2, Users);

			// 执行查询语句，将数据保存到ResultSet对象中
			rs = pstmt1.executeQuery();

			int count = 0;
			if (rs.next()) {
				count = rs.getInt("cou");
			}

			if (count == 1) {

				sql = "update user set password=? where name = ?";
				// 创建该连接下的PreparedStatement对象
				pstmt2 = conn.prepareStatement(sql);

				// 传递第一个参数值 ，代替第一个问号
				pstmt2.setString(1, newp);
				pstmt2.setString(2, Users);
				pstmt2.executeUpdate();
				flag = 1;
			} else
				flag = 2;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt1 != null) {
					pstmt1.close();
				}
				if (pstmt2 != null) {
					pstmt2.close();
				}
				if (conn != null) {
					conn.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	private static void showDialog(int type, String msg) {
		JOptionPane.showMessageDialog(null, msg, "操作提示", type);
	}
	
	public static void action() {
		// 为统计数字按钮添加事件
		passchange.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (oldpass.getText().trim().equals("")) {
					showDialog(JOptionPane.ERROR_MESSAGE, "没输入密码！");
					//JOptionPane.showMessageDialog(null, "没写密码！");
					return;
				}
				if (newpass.getText().trim().equals("")) {
					showDialog(JOptionPane.ERROR_MESSAGE, "没输入密码！");
					//JOptionPane.showMessageDialog(null, "没写密码！", null, JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (olp.equals("") == false && nep.equals("") == false) {
					pass(olp, nep, Users);
				}
				if (flag == 1) {
					JOptionPane.showMessageDialog(null, "修改成功", null, JOptionPane.INFORMATION_MESSAGE);
				
					return;
				} else if (flag == 2) {
					JOptionPane.showMessageDialog(null, "修改失败", null, JOptionPane.INFORMATION_MESSAGE);

					return;
				}
			}
		});// end

		// 为textFiled添加事件
		oldpass.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				olp = oldpass.getText().trim();
			}
		});// end

		// 为textFiled添加事件
		newpass.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				nep = newpass.getText().trim();
			}
		});// end

	}

}