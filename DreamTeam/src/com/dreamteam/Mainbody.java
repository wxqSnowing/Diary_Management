package com.dreamteam;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class Mainbody extends JFrame implements ActionListener, HyperlinkListener {

		private static final long serialVersionUID = 1L;

		JLabel information = new JLabel();
		JPanel headImage = new JPanel();
		JPanel p = new JPanel();
		JLabel Head = new JLabel();
		JScrollPane sP = new JScrollPane();
		private static JTextArea textArea; // 文本区域
		private static String imagesUrl="";
		private static String textUrl="";
		private static String title="";


		public Mainbody(String title,String textUrl,String imagesUrl ) {
			this.imagesUrl = imagesUrl;
			this.textUrl = textUrl;
			this.title = title;
		
			init();
		}

		public void init() {
			p.setLayout(new GridBagLayout());
			headImage.setLayout(new BoxLayout(headImage, BoxLayout.Y_AXIS));
			JLabel[] a = new JLabel[100];
			int i = 0;
			String[]  imUrl=imagesUrl.split(",");
			
			for (String imUr : imUrl) {
				ImageIcon head = new ImageIcon(imUr);
				a[i] = new JLabel();
				a[i].setIcon(head);
				headImage.add(a[i]);
				i++;
			}

			Head = new JLabel("     "+title);
			
			Head.setFont(new Font("", 1, 30));

			GridBagConstraints c1 = new GridBagConstraints();

			c1.gridx = 0;
			c1.gridy = 0;
			c1.fill = GridBagConstraints.BOTH;
			c1.weightx = 0;
			c1.weighty = 0;
			p.add(headImage, c1);

			c1.gridx = 0;
			c1.gridy = 1;
			c1.fill = GridBagConstraints.BOTH;
			c1.insets = new Insets(10, 0, 0, 0);
			c1.weightx = 0;
			c1.weighty = 0;
			p.add(Head, c1);

			c1.gridx = 0;
			c1.gridy = 2;
			c1.fill = GridBagConstraints.BOTH;
			c1.insets = new Insets(10, 0, 0, 0);
			c1.weightx = 0;
			c1.weighty = 0;
			p.add(information, c1);

			textArea = new JTextArea(20, 25);
			loadFile();
			c1.gridx = 0;
			c1.gridy = 3;
			c1.fill = GridBagConstraints.BOTH;
			c1.weightx = 1;
			c1.weighty = 1;

			
			p.add(textArea, c1);
			sP.setViewportView(p);
			this.add(sP);
			this.setTitle(title);
			this.setPreferredSize(new Dimension(1000, 600));
			this.setResizable(false); // 设置是否可更改窗口大小
			this.setLocation(190, 20);// 设置窗口居中显示
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.pack();
		}
		public void loadFile() {
			FileReader fr = null;
			BufferedReader br = null;

			try {
				fr = new FileReader(this.textUrl);
				br = new BufferedReader(fr);

				String line = br.readLine();
				int i = 0;
				while (line != null) {
					i++;
					textArea.append(line + "\n");
					line = br.readLine();
					if (i >= 200) {// 大于100行就退出
						break;
					}
				} // while end

			} catch (FileNotFoundException e) {
				// 异常
				System.err.println(e.getMessage());
			} catch (IOException e) {
				System.err.println(e.getMessage());
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						System.err.println(e.getMessage());
					}
				} // if end

				if (fr != null) {
					try {
						fr.close();
					} catch (IOException e) {
						System.err.println(e.getMessage());
					}
				} // if end
			}

		}

//
//		public static void main(String[] args) {
//
//			 EventQueue.invokeLater(new Runnable() {
//			 @Override
//			 public void run() {
//			 JFrame.setDefaultLookAndFeelDecorated(true);
//			 JDialog.setDefaultLookAndFeelDecorated(true);
//			 try {
//			
//			 UIManager.setLookAndFeel(new
//			 org.jvnet.substance.skin.SubstanceAutumnLookAndFeel());
//			
//			 } catch (Exception e) {
//			
//			 e.printStackTrace();
//			 }
//			 Mainbody browser = new Mainbody(imagesUrl,textUrl);
//			 browser.setVisible(true);
//			 }
//			 });
//
//
//		}

		
		@Override
		public void hyperlinkUpdate(HyperlinkEvent arg0) {

		}

		@Override
		public void actionPerformed(ActionEvent arg0) {

		}
	}

