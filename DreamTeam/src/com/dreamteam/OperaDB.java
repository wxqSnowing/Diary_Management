package com.dreamteam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.jdbc.Statement;

public class OperaDB {
	public static Vector<DiaryModel> diaryCollection = new Vector<DiaryModel>();
	public static UserModel usermodel = new UserModel();

	public static boolean registe(Connection conn, String name, String pwd) {
		ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		try {
			String sqll = "select * from user where name=?";
			pstmt = conn.prepareStatement(sqll);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();

			if (rs.next()) { // ResultSet的游标下移，类似于Iterator
				return false;
			} else {

				String sql = "insert into user values(?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, name);
				pstmt.setString(2, pwd);
				pstmt.executeUpdate();
				System.out.print("Register OK!!");
				return true;
			}

		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		} finally { // 必须放在finally模块中执行
			if (null != rs) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close(); // 记得完成一次数据库操作之后，一定要关闭Statement
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static boolean login(Connection conn, String name, String pwd) {
		ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		try {
			String sqll = "select * from user where name=? and password=?";
			pstmt = conn.prepareStatement(sqll);
			pstmt.setString(1, name);
			pstmt.setString(2, pwd);
            System.out.print(name+"---"+pwd);
			rs = pstmt.executeQuery();

			while (rs.next()) { // ResultSet的游标下移，类似于Iterator
				usermodel.setName(rs.getString(1));
				usermodel.setPassword(rs.getString(2));
				System.out.print("Login OK!!");
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // 必须放在finally模块中执行
			if (null != rs) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close(); // 记得完成一次数据库操作之后，一定要关闭Statement
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return false;

	}

	public static boolean save(Connection conn, String name, String title,
			String contenturl, String picturl, String time, String weather,
			String mood) {
		Statement stmt = null;
		ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		try {
			String sqll = "insert into diary(name, title, contenturl, picurl, time, weather, mood) values(?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sqll);
			pstmt.setString(1, name);
			pstmt.setString(2, title);
			pstmt.setString(3, contenturl);
			pstmt.setString(4, picturl);
			pstmt.setString(5, time);
			pstmt.setString(6, weather);
			pstmt.setString(7, mood);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // 必须放在finally模块中执行
			if (null != rs) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close(); // 记得完成一次数据库操作之后，一定要关闭Statement
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public static boolean searchedContent(String contenturl, String searchWords) {
		// 打开文件夹
		// 如果该文件存在，读取文件
		boolean rs = false;
		String path = null;
		System.out.println("文件相对路径：" + contenturl);
		String Opath = "E:/大三上/JAVA/workspace/";
		path = Opath + contenturl;
		System.out.println("文件绝对路径：" + path);
		File file = new File(path);
		Reader reader = null;
		char[] tempchars = new char[1024];
		try {
			if (file != null) {
				System.out.println("文件绝对路径：" + path);
				int charread = 0;
				reader = new InputStreamReader(new FileInputStream(path));
				try {
					while ((charread = reader.read(tempchars)) != -1) {
						if ((charread == tempchars.length)
								&& (tempchars[tempchars.length - 1] != '\r')) {
							System.out.print(tempchars);
						} else {
							for (int i = 0; i < charread; i++) {
								if (tempchars[i] == '\r') {
									continue;
								} else {
									System.out.print(tempchars[i]);
								}
							}
						}
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String content=new String(tempchars); 
				System.out.println("读取内容为："+content);
				Pattern pattern = Pattern.compile(searchWords);
				Matcher matcher = pattern.matcher(content);
//				Pattern pattern = Pattern.compile("hello");
//				Matcher matcher = pattern.matcher("helloSSWord");
				rs=matcher.find();
				if(rs==true){
					System.out.println("匹配成功！");
				}else{
					System.out.println("匹配失败！");
				}
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static Vector<DiaryModel> searchedAll(Connection conn, String name,
			String searchWords) {
		diaryCollection.removeAllElements();
		Vector<DiaryModel> diaryRightCollection = new Vector<DiaryModel>();
		ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		try {
			String sqll = "select * from diary where name=?";
			pstmt = conn.prepareStatement(sqll);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			while (rs.next()) { // ResultSet的游标下移，类似于Iterator
				DiaryModel dm = new DiaryModel();
				dm.setId(rs.getInt(1));
				dm.setName(rs.getString(2));
				dm.setTitle(rs.getString(3));
				dm.setContenturl(rs.getString(4));
				dm.setPicturl(rs.getString(5));
				dm.setTime(rs.getString(6));
				dm.setWeather(rs.getString(7));
				dm.setMood(rs.getString(8));
				diaryCollection.addElement(dm);
			}
			for (Object obj : diaryCollection) {
				DiaryModel d = (DiaryModel) obj;
				d.setSuccessRex(searchedContent(d.getContenturl(), searchWords));
				//d.setSuccessRex(searchedContent("DreamTeam/diary/u/u4/新建文本文档.txt", searchWords));
			}
			for (Object obj : diaryCollection) {
				DiaryModel d = (DiaryModel) obj;
				if(d.isSuccessRex()){
					diaryRightCollection.addElement(d);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // 必须放在finally模块中执行
			if (null != rs) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close(); // 记得完成一次数据库操作之后，一定要关闭Statement
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return diaryRightCollection;
	}

	public static Vector<DiaryModel> searched(Connection conn, String name, String weather,
			String date, String mood) {	
		diaryCollection.removeAllElements();
		ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		
		try {		
			
			//String sqll = "select * from diary where name= ?and weather=? and time=? and mood=?";
			StringBuffer sb = new StringBuffer();
			sb.append("select * from diary where name= '");
			sb.append(name);
			sb.append("' and "); 
			sb.append(weather);
			sb.append(" and ");
			sb.append(date);
			sb.append(" and ");
			sb.append(mood);
			
		//	String sqll = "select * from diary where name="+"'"+name+ "'"+ weather=? and time=? and mood=?";
			pstmt = conn.prepareStatement(sb.toString());
//			pstmt.setString(1, name);
//			pstmt.setString(2, weather);
//			pstmt.setString(3, date);
//			pstmt.setString(4, mood);
			rs = pstmt.executeQuery();

			while (rs.next()) { // ResultSet的游标下移，类似于Iterator
				DiaryModel dm = new DiaryModel();
				dm.setId(rs.getInt(1));
				dm.setName(rs.getString(2));
				dm.setTitle(rs.getString(3));
				dm.setContenturl(rs.getString(4));
				dm.setPicturl(rs.getString(5));
				dm.setTime(rs.getString(6));
				dm.setWeather(rs.getString(7));
				dm.setMood(rs.getString(8));
				diaryCollection.addElement(dm);;
			}
//			for (Object obj : diaryCollection) {
//				DiaryModel d = (DiaryModel) obj;
//				System.out.print(d.getId()+" "+d.getTitle() + ""+ d.getMood() + "\n");
//			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // 必须放在finally模块中执行
			if (null != rs) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close(); // 记得完成一次数据库操作之后，一定要关闭Statement
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return diaryCollection;
	}

	public static Vector<DiaryModel> searchedDiary(Connection conn, String name){

		diaryCollection.removeAllElements();
		ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		try {		
		
			String sqll = "select * from diary where name=?";
			pstmt = conn.prepareStatement(sqll);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			while (rs.next()) { // ResultSet的游标下移，类似于Iterator
				DiaryModel dm = new DiaryModel();
				dm.setId(rs.getInt(1));
				dm.setName(rs.getString(2));
				dm.setTitle(rs.getString(3));
				dm.setContenturl(rs.getString(4));
				dm.setPicturl(rs.getString(5));
				dm.setTime(rs.getString(6));
				dm.setWeather(rs.getString(7));
				dm.setMood(rs.getString(8));
				diaryCollection.add(dm);
			}
//			for (Object obj : diaryCollection) {
//				DiaryModel d = (DiaryModel) obj;
//				System.out.print(d.getId()+" "+d.getTitle() +" " + d.getMood() + "\n");
//			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // 必须放在finally模块中执行
			if (null != rs) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close(); // 记得完成一次数据库操作之后，一定要关闭Statement
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return diaryCollection;
		
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			// 加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 建立连接
			// 将用户名、密码和数据库名字存入配置文件是一个好的习惯
			Properties properties = new Properties();
			FileInputStream fis = null;
			try {
				fis = new FileInputStream("mysql.properties");
				properties.load(fis);

				String database = properties.getProperty("database");
				String user = properties.getProperty("user");
				String password = properties.getProperty("password");

				if (database != null && user != null && password != null) {
					String url = "jdbc:mysql://localhost:3306/" + database
							+ "?useUnicode=true&characterEncoding=utf-8";
					conn = DriverManager.getConnection(url, user, password);
				}
				System.out.print("OK!!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

	private static void closeDB(AutoCloseable con) {
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				System.err.print("failed to close");
			}
		}

	}

}
