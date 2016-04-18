package com.dreamteam;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class WriteDiary extends JFrame implements ActionListener{
	JPanel pShow= new JPanel();
	JButton saveButton,addButton;
	JTextArea textArea;
	JTextField date;
	JTextField title;
	JPanel panel1,panel2,panel3;
	JPanel pic1,pic2;
	JLabel label,label2;
	@SuppressWarnings("rawtypes")
	JComboBox wether,mood;
	String userName="";	
	String namepath1="";
	String namepath2="";
	
	public WriteDiary(String userName){
		this.userName=userName;
		//pShow.setLayout(new GridBagLayout());
		initInterface();
		//setVisible(true);
		//setBounds(100,0,1024,700);
		//setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void initInterface(){
		panel1=new JPanel();
		panel2=new JPanel();
		panel3=new JPanel();
		pic1=new JPanel();
		pic2=new JPanel();
		pic1.setPreferredSize(new Dimension(300, 200));
		pic2.setPreferredSize(new Dimension(300, 200));
	    addButton=new JButton("上传图片");
	    saveButton=new JButton("保存当前日记");
		panel1.setLayout(new GridBagLayout());
		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 0;
		c1.gridy = 0;
		c1.fill = GridBagConstraints.NONE;
		c1.insets = new Insets(10,10,10,10);
		c1.weightx = 0;
		c1.weighty = 0;
		panel1.add(pic1,c1);
		c1.gridx = 0;
		c1.gridy = 1;
		c1.fill = GridBagConstraints.NONE;
		c1.insets = new Insets(10,10,10,10);
		c1.weightx = 0;
		c1.weighty = 0;
		panel1.add(pic2,c1);
		c1.gridx = 0;
		c1.gridy = 2;
		c1.fill = GridBagConstraints.NONE;
		c1.insets = new Insets(10,10,10,10);
		c1.weightx = 0;
		c1.weighty = 0;
		panel1.add(addButton, c1);
		c1.gridx = 0;
		c1.gridy = 3;
		c1.fill = GridBagConstraints.NONE;
		c1.insets = new Insets(10,10,10,10);
		c1.weightx = 0;
		c1.weighty = 0;
		panel1.add(saveButton, c1);	    
	    addButton.addActionListener(this);
	    saveButton.addActionListener(this);
	
	    Box panel2Box=Box.createHorizontalBox();
	    panel2Box.add(new JLabel("标题:"));
	    title=new JTextField(20);
	    panel2Box.add(Box.createVerticalStrut(10));
	    panel2Box.add(title);
	    Box panel21Box=Box.createVerticalBox();
	    panel21Box.add(panel2Box);
	    textArea=new JTextArea(30,50);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);	
		JScrollPane pane=new JScrollPane(textArea);
	    panel21Box.add(Box.createVerticalStrut(10));
	    panel21Box.add(pane);
	    panel2.add(panel21Box);
		
		panel3.setLayout(new FlowLayout());
		date=new JTextField(15);
		wether=new JComboBox();
		wether.addItem("晴");
		wether.addItem("阴");
		wether.addItem("风");
		wether.addItem("雨");
		wether.addItem("雪");
		mood=new JComboBox();
		mood.addItem("开心");
		mood.addItem("悲伤");
		mood.addItem("害怕");
		mood.addItem("愤怒");
		mood.addItem("郁闷");
		Box box=Box.createHorizontalBox();
		Chooser ser = Chooser.getInstance();
		date = new JTextField(10);
		date.setText("2015-12-30");
		ser.register(date);
		box.add(new JLabel("日期:"));
		box.add(date);
		box.add(Box.createHorizontalStrut(130));
		box.add(new JLabel("天气:"));
		box.add(wether);
		box.add(Box.createHorizontalStrut(130));
		box.add(new JLabel("心情:"));
		box.add(mood);
		panel3.add(box);
		
		JSplitPane pane2=new JSplitPane(JSplitPane.VERTICAL_SPLIT,panel3,panel2);
		JSplitPane pane1=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,panel1,pane2);
		pane1.setResizeWeight(0.7);
		pane2.setResizeWeight(0.3);		
		pShow.add(pane1);
		//add(pane1);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==saveButton){
			File file1=new File("diary\\"+userName);
			if(!file1.exists()){
				try {
					file1.mkdir();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			JFileChooser fileDialog=new JFileChooser();
			fileDialog.setCurrentDirectory(file1);
			int state=fileDialog.showSaveDialog(this);
			if(state==JFileChooser.APPROVE_OPTION){
				try {
					String contentPath=fileDialog.getSelectedFile().toString()+".txt";
					String picPath1 = null;
					String picPath2 = null;					
					File dirFile=new File(contentPath);
					if(dirFile.exists()){
						JOptionPane.showMessageDialog(this, "文件已存在，请重新命名并保存","警告",JOptionPane.WARNING_MESSAGE);
						return;
					}
					FileWriter fileWriter=new FileWriter(dirFile);
					BufferedWriter out=new BufferedWriter(fileWriter);
					out.write(date.getText()+"   "+wether.getSelectedItem()+"   "+mood.getSelectedItem());
					out.newLine();
					out.write(title.getText());
					out.newLine();
					out.write(textArea.getText());
					out.close();
					fileWriter.close();
				if(!namepath1.equals("")==true){
					 picPath1=fileDialog.getSelectedFile().toString()+"_1.jpg";
					try {
						FileInputStream in=new FileInputStream(namepath1);
						FileOutputStream out1=new FileOutputStream(picPath1);
						byte[] bytes=new byte[1024];
						int len=0;
						while((len=in.read(bytes))!=-1){
							out1.write(bytes,0,len);
						}
						out.close();
						in.close();
						
					} catch (IOException e1) {
						e1.printStackTrace();}
				}
				if(!namepath2.equals("")==true){
					 picPath2=fileDialog.getSelectedFile().toString()+"_2.jpg";
					try {
						FileInputStream in1=new FileInputStream(namepath2);
						FileOutputStream out2=new FileOutputStream(picPath2);
						byte[] bytes=new byte[1024];
						int len1=0;
						while((len1=in1.read(bytes))!=-1){
							out2.write(bytes,0,len1);
						}
						out2.close();
						in1.close();
						
					} catch (IOException e3) {
						e3.printStackTrace();}
				}
				String picPath=picPath1+","+picPath2;
				OperaDB op=new OperaDB();
				boolean isExist=op.save(op.getConnection(), userName, title.getText(), contentPath,picPath,date.getText(),wether.getSelectedItem().toString(),mood.getSelectedItem().toString());
				if(!isExist){
					JOptionPane.showMessageDialog(this, "保存失败","警告",JOptionPane.WARNING_MESSAGE);
					return;
				}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			}
		if(e.getSource()==addButton){
			ImageViewer image1=new ImageViewer();
			image1.chooser.setCurrentDirectory(new File("."));
			if(namepath1.equals("")==true&&namepath2.equals("")==true){
			    int result =image1.chooser.showDialog(pic1, "打开图片文件");			
			    if (result == JFileChooser.APPROVE_OPTION) {
				namepath1 = image1.chooser.getSelectedFile().getPath();
				label=new JLabel();
				label.setIcon(new ImageIcon(namepath1));
				}
				JScrollPane j=new JScrollPane();
				j.setViewportView(label);
				pic1.add(j);
				pic1.revalidate();
				return;
			}
			if(!namepath1.equals("")==true&&namepath2.equals("")==true){
				int result1 =image1.chooser.showDialog(pic1, "打开图片文件");			
			    if (result1 == JFileChooser.APPROVE_OPTION) {
				namepath2 = image1.chooser.getSelectedFile().getPath();
				label2=new JLabel();
				label2.setIcon(new ImageIcon(namepath2));
				}
				JScrollPane j1=new JScrollPane();
				j1.setViewportView(label2);
				pic2.add(j1);
				pic2.revalidate();
				addButton.setEnabled(false);
				return;
			}

		}
		
	}
	public JPanel getpShow() {
		return pShow;
	}

	public void setpShow(JPanel pShow) {
		this.pShow = pShow;
	}

//	public static void main(String[] args) {
//		WriteDiary w=new WriteDiary("1");
//	} 
}
