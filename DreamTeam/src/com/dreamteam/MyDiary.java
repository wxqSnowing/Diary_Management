package com.dreamteam;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MyDiary {

	public Mainbody mainbody;
	private OperaDB op = new OperaDB();
	private Connection conn = op.getConnection();
	private Vector<DiaryModel> searchResult;
	private UserModel usermodel = new UserModel();

	GridBagConstraints gbCon = new GridBagConstraints();

	JPanel MyDiary = new JPanel();
	JPanel btnpanel = new JPanel();
	JPanel showpanel = new JPanel();
	JScrollPane sP = new JScrollPane();

	JPanel showpi = new JPanel();

	JPanel showps = new JPanel();

	String[] weather = new String[] { "全部", "晴", "阴", "雨", "雪", "风" };
	JComboBox weatherCombo = new JComboBox(weather);
	
	JTextField date;
	
	String[] mood = new String[] { "全部", "开心", "悲伤", "害怕", "愤怒", "郁闷" };
	JComboBox moodCombo = new JComboBox(mood);
	JButton baseBut = new JButton("查找");

	JTextField fullField = new JTextField(20);
	JButton fullBut = new JButton("查找");

	JButton deleteBut = new JButton("删除");

	JList dairyList;
	DefaultListModel dairyModel = new DefaultListModel();

	JButton inforbtn = new JButton("基础信息查找");
	JButton searchbtn = new JButton("全文查找");
	JButton readbtn = new JButton("读取");
	

	public MyDiary() {

		init();
		addAction();
	}

	public JPanel getMyDiary() {
		return MyDiary;
	}



	private static void showDialog(int type, String msg) {
		JOptionPane.showMessageDialog(null, msg, "操作提示", type);
	}

	private void addAction() {

		inforbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showpi.setVisible(true);
				showps.setVisible(false);
				// MyDiary.repaint();
				showpanel.revalidate();
			}
		});// end

		searchbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showps.setVisible(true);
				showpi.setVisible(false);
				// MyDiary.repaint();
				showpanel.revalidate();
				
			}
			
		});// end
		
		readbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainbody.setVisible(true);
				
			}
			
		});

		baseBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String name = Menu.userName;
				String weather = "";
				String time = "";
				String mood = "";
				String weatherJuge = "";
				String timeJuge = "";
				String moodJuge = "";
				if (weatherCombo.getSelectedItem().toString().equals("全部")) {
					weatherJuge = "weather = weather";

				} else {
					weather = weatherCombo.getSelectedItem().toString();
					weatherJuge = "weather = '"
							+ weatherCombo.getSelectedItem().toString() + "'";
				}
				if (date.getText().equals("全部")) {
					timeJuge = "time = time";

				} else {
					time = date.getText();
					timeJuge = "time = '"
							+ date.getText() + "'";
				}
				if (moodCombo.getSelectedItem().toString().equals("全部")) {
					moodJuge = "	mood = mood";

				} else {
					mood = moodCombo.getSelectedItem().toString();
					moodJuge = "mood = '"
							+ moodCombo.getSelectedItem().toString() + "'";
				}

				dairyModel.removeAllElements();

				System.out.println(weather + time + mood + name);
				for (Object obj : op.searched(conn, name, weatherJuge,
						timeJuge, moodJuge)) {

					DiaryModel d = (DiaryModel) obj;
					dairyModel.addElement(d.getTitle()+"  "+d.getMood()+"  "+d.getContenturl()+"  "+d.getPicturl()+"  "+d.getTime());
				}
				dairyList = new JList(dairyModel);
				date.setText("全部");
				dairyList.revalidate();

			}
		});// end

		fullField.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				if (fullField.getText().trim().equals("")) {
					fullBut.setEnabled(false);
				} else {
					fullBut.setEnabled(true);
				}
			}
		});// end
		fullBut.setEnabled(false);
		
		dairyList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				// 获取用户所选择的所有图书
				Object[] books = dairyList.getSelectedValues();
//				favoriate.setText("");
				for (Object book : books) {
				
					String[] result=book.toString().split("  ");
					 
			        mainbody=new Mainbody(result[0],result[2], result[3]);
//			        mainbody.setVisible(true);
				}
				
			}
		});
		

		fullBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fullField.getText().trim().equals("")) {
					showDialog(JOptionPane.ERROR_MESSAGE, "输入查找必须输入关键字");
				} else {
					String name = "wxq";
					//op.searchedAll(conn, name, fullField.getText().trim());
					dairyModel.removeAllElements();
					//System.out.println("匹配文件个数："+op.searchedAll(conn, name, fullField.getText().trim()).size());
					
					for (Object obj : op.searchedAll(conn, name, fullField.getText().trim())) {
						DiaryModel d = (DiaryModel) obj;
						dairyModel.addElement(d.getTitle());
					}
					dairyList = new JList(dairyModel);
					dairyList.revalidate();
				}
			}
		});// end

	}

	public void init() {
        
		MyDiary = new JPanel(new BorderLayout());
		btnpanel.setLayout(new BoxLayout(btnpanel, BoxLayout.Y_AXIS));
		showpanel.setLayout(new GridBagLayout());

		btnpanel.add(inforbtn);
		btnpanel.add(searchbtn);
		btnpanel.add(readbtn);

		showpi.add(new JLabel("按天气查找："));
		showpi.add(weatherCombo);
		
		Chooser ser = Chooser.getInstance();
		date = new JTextField(10);
		date.setText("全部");
		ser.register(date);
		showpi.add(new JLabel("按日期查找:"));
		showpi.add(date);

		showpi.add(new JLabel("按心情查找:"));
		showpi.add(moodCombo);
		showpi.add(baseBut);

		showps.add(new JLabel("全文查找："));
		showps.add(fullField);
		showps.add(fullBut);

		gbCon.gridx = 0;
		gbCon.gridy = 0;
		gbCon.fill = GridBagConstraints.BOTH;
		gbCon.weightx = 0;
		gbCon.weighty = 0;

		showpi.setVisible(true);
		showps.setVisible(false);
		showpanel.add(showpi, gbCon);
		showpanel.add(showps, gbCon);

		gbCon.gridx = 0;
		gbCon.gridy = 1;
		gbCon.fill = GridBagConstraints.BOTH;
		gbCon.weightx = 1;
		gbCon.weighty = 1;

		// String name=usermodel.getName();
		String name =Menu.userName;

		for (Object obj : op.searchedDiary(conn, name)) {
			DiaryModel d = (DiaryModel) obj;
			dairyModel.addElement(d.getTitle()+"  "+d.getMood()+"  "+d.getContenturl()+"  "+d.getPicturl()+"  "+d.getTime());
		}
		dairyList = new JList(dairyModel);
		showpanel.add(dairyList, gbCon);

		JSplitPane left = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, btnpanel,
				showpanel);

		left.setOneTouchExpandable(true);

		left.resetToPreferredSizes();

		MyDiary.add(left);

	}

}
