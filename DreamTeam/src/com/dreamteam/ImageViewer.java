package com.dreamteam;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;

public class ImageViewer {
	final int PREVIEW_SIZE = 100;
	JLabel label = new JLabel();
	JFileChooser chooser = new JFileChooser(".");
	JLabel accessory = new JLabel();
	ExtensionFileFilter filter = new ExtensionFileFilter();
	
	public ImageViewer() {
		init();
	}

	private void init() {

		filter.addExtension("jpg");
		filter.addExtension("jpeg");
		filter.addExtension("gif");
		filter.addExtension("png");
		filter.setDescription("Í¼Æ¬ÎÄ¼þ(*.jpg,*.jpeg,*.gif,*.png)");
		chooser.addChoosableFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileView(new FileIconView(filter));
		chooser.setAccessory(accessory);
		accessory.setPreferredSize(new Dimension(PREVIEW_SIZE, PREVIEW_SIZE));
		accessory.setBorder(BorderFactory.createEtchedBorder());
		chooser.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getPropertyName() == JFileChooser.SELECTED_FILE_CHANGED_PROPERTY) {
					File f = (File) event.getNewValue();
					if (f == null) {
						accessory.setIcon(null);
						return;
					}
					ImageIcon icon = new ImageIcon(f.getPath());
					if (icon.getIconWidth() > PREVIEW_SIZE) {
						icon = new ImageIcon(icon.getImage().getScaledInstance(PREVIEW_SIZE, -1, Image.SCALE_DEFAULT));
					}
					accessory.setIcon(icon);
				}
			}
		});
	}
}


class ExtensionFileFilter extends FileFilter {

	private String description = "";
	private ArrayList<String> extensions = new ArrayList<String>();
	public void addExtension(String extension) {
		if (!extension.startsWith(".")) {
			extension = "." + extension;
			extensions.add(extension.toLowerCase());
		}
	}

	public void setDescription(String aDescription) {
		description = aDescription;
	}


	public String getDescription() {
		return description;
	}

	public boolean accept(File f) {
		if (f.isDirectory())
			return true;
		String name = f.getName().toLowerCase();
		for (String extension : extensions) {
			if (name.endsWith(extension)) {
				return true;
			}
		}
		return false;
	}
}

class FileIconView extends FileView {
	private FileFilter filter;

	public FileIconView(FileFilter filter) {
		this.filter = filter;
	}

	public Icon getIcon(File f) {
		if (!f.isDirectory() && filter.accept(f)) {
			return new ImageIcon("ico/pict.png");
		} else if (f.isDirectory()) {
			File[] fList = File.listRoots();
			for (File tmp : fList) {
				if (tmp.equals(f)) {
					return new ImageIcon("ico/dsk.png");
				}
			}
			return new ImageIcon("ico/folder.png");
		}
		else {
			return null;
		}
	}
}