package textedit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class texteditor extends JFrame implements ActionListener{

	JTextArea textArea;
	JScrollPane scrollpane;
	JSpinner fontsizespinner;
	JLabel fontlabel;
	JButton fontcolorbutton;
	JComboBox fontbox;
	JMenuBar Menubar;
	JMenu filemenu;
	JMenuItem openitem,saveitem,exititem;
	texteditor()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("nigga editor");
		this.setSize(500, 500);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);;
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Arial",Font.PLAIN,20));
        scrollpane=new JScrollPane(textArea);
		scrollpane.setPreferredSize(new Dimension(450, 450));
		scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		fontsizespinner=new JSpinner();
		fontlabel=new JLabel("Font");
		fontsizespinner.setPreferredSize(new Dimension(50,25));
		fontsizespinner.setValue(20);
		fontsizespinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int) fontsizespinner.getValue()));
			}
			
		});
		fontcolorbutton=new JButton("Color");
		fontcolorbutton.addActionListener(this);
		String [] fonts=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		fontbox=new JComboBox(fonts);
		fontbox.addActionListener(this);
		fontbox.setSelectedItem("Arial");
        //------------------------------------------------menubar-------------------------------------
		Menubar=new JMenuBar();
		filemenu=new JMenu("File");
        openitem=new JMenuItem("Open");
		saveitem=new JMenuItem("Save");
		exititem=new JMenuItem("Exit");
		openitem.addActionListener(this);
		saveitem.addActionListener(this);
		exititem.addActionListener(this);
		filemenu.add(openitem);
		filemenu.add(saveitem);
		filemenu.add(exititem);
		Menubar.add(filemenu);
		//------------------------------------------------menubar-------------------------------------
		this.setJMenuBar(Menubar);
		this.add(fontlabel);
		this.add(fontsizespinner);
		this.add(fontcolorbutton);
		this.add(fontbox);
        this.add(scrollpane);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==fontcolorbutton)
		{
			JColorChooser colorChooser=new JColorChooser();
			Color color=colorChooser.showDialog(null, "Choose a Color", Color.BLACK);
			textArea.setForeground(color);
		}
		if(e.getSource()==fontbox)
		{
			textArea.setFont(new Font((String)fontbox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
		}
		if(e.getSource()==openitem)
		{
			JFileChooser fileChooser=new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			FileNameExtensionFilter filter=new FileNameExtensionFilter("Text files", "txt");
			fileChooser.setFileFilter(filter);
			int response=fileChooser.showOpenDialog(null);
			if(response==JFileChooser.APPROVE_OPTION)
			{
				File file=new File(fileChooser.getSelectedFile().getAbsolutePath());
				Scanner filein=null;
				try {
					filein=new Scanner(file);
					if(file.isFile())
					{
						while(filein.hasNextLine())
						{
							String line=filein.nextLine()+"\n";
							textArea.append(line);
						}
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				finally
				{
					filein.close();
				}
			}
		}
		if(e.getSource()==saveitem)
		{
			JFileChooser fileChooser=new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			int response=fileChooser.showSaveDialog(null);
			if(response==JFileChooser.APPROVE_OPTION)
			{
				File file;
				PrintWriter fileout=null;
				file=new File(fileChooser.getSelectedFile().getAbsolutePath());
				try {
					fileout=new PrintWriter(file);
					fileout.println(textArea.getText());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				finally
				{
					fileout.close();
				}
			}
		}
		if(e.getSource()==exititem)
		{
			System.exit(0);
		}
	}

}
