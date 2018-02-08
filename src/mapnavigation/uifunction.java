package mapnavigation;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Button;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
//import java.io.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilterOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;

import javax.swing.JSpinner;
import javax.swing.JFormattedTextField;
import javax.swing.JProgressBar;
import javax.swing.JCheckBox;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;



public class uifunction extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	private JPanel contentPane;
	private JTextField Inputname;
	private JTextField Outputname;
	private JFormattedTextField startX;
	private JFormattedTextField startY;
	private JFormattedTextField targetY;
	private JFormattedTextField targetX;
	
	int xx,xy;
	
	int checkedInput;
	
	private int activeprogressbarvalue=75;
	private int progressbarvalue=50;

	/**
	 * Launch the application.
	 */
	public void unstaticmain(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					uifunction frame = new uifunction();
					frame.setUndecorated(true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					uifunction frame = new uifunction();
					frame.setUndecorated(true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	// going to borrow code from a gist to move frame.

	/**
	 * Create the frame.
	 */
	public uifunction() {
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 730*3/2, 480*3/2);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(0, 0, 346, 480);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("MapNavigation V1 ready to go");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setForeground(new Color(240, 248, 255));
		lblNewLabel.setBounds(17, 305, 312, 27);
		panel.add(lblNewLabel);
		
		JLabel label = new JLabel("");
		
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				 xx = e.getX();
			     xy = e.getY();
			}
		});
		label.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				
				int x = arg0.getXOnScreen();
	            int y = arg0.getYOnScreen();
	            uifunction.this.setLocation(x - xx, y - xy);  
			}
		});
		label.setBounds(-38, 0, 420, 275);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setIcon(new ImageIcon(Example.class.getResource("/designe/imgsource/picture1.jpg")));
		panel.add(label);
		
		JLabel Status = new JLabel("Status:");
		Status.setHorizontalAlignment(SwingConstants.CENTER);
		Status.setForeground(new Color(240, 248, 255));
		Status.setFont(new Font("Tahoma", Font.PLAIN, 13));
		Status.setBounds(80, 343, 54, 27);
		panel.add(Status);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setValue(progressbarvalue);
		progressBar.setForeground(Color.BLUE);
//		progressBar.setBackground(Color.GRAY);
		progressBar.setBounds(17, 399, 312, 14);
		panel.add(progressBar);
		
		JProgressBar progressBar_aktiv = new JProgressBar();
		progressBar_aktiv.setStringPainted(true);
		progressBar_aktiv.setValue(activeprogressbarvalue);
		progressBar_aktiv.setForeground(Color.GREEN);
//		progressBar_aktiv.setBackground(Color.GRAY);
		progressBar_aktiv.setBounds(17, 383, 312, 10);
		panel.add(progressBar_aktiv);
		
		JLabel StatusMessage = new JLabel("...We got you...");
		StatusMessage.setHorizontalAlignment(SwingConstants.LEFT);
		StatusMessage.setForeground(new Color(240, 248, 255));
		StatusMessage.setFont(new Font("Tahoma", Font.PLAIN, 13));
		StatusMessage.setBounds(140, 343, 158, 27);
		panel.add(StatusMessage);
		
		Button Run = new Button("Run Waycalc");
		Run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(calcingactive == 0) {
					calcingactive = 1;
				}
				writetoFile();
			}
		});
		Run.setForeground(Color.WHITE);
		Run.setBackground(new Color(241, 57, 83));
		Run.setBounds(395, 392, 283, 36);
		contentPane.add(Run);
		
		JLabel lbl_close = new JLabel("X");
		lbl_close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				finallydone = 1;
				writetoFile();
				System.exit(0);
			}
		});
		lbl_close.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_close.setForeground(new Color(241, 57, 83));
		lbl_close.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_close.setBounds(691, 0, 37, 27);
		contentPane.add(lbl_close);

		JLabel lbl_minimize = new JLabel("_");
		lbl_minimize.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
//				this.frame.setState(Frame.ICONIFIED);//TODO change Wrong place
			}
		});
		lbl_minimize.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_minimize.setForeground(new Color(241, 57, 83));
		lbl_minimize.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbl_minimize.setBounds(665, 0, 37, 27);
		contentPane.add(lbl_minimize);
		
		JLabel Input = new JLabel("Input Karte");
		Input.setBounds(395, 58, 114, 18);
		contentPane.add(Input);
		
//		JLabel inputfound = new JLabel("#");
//		inputfound.setFont(new Font("Tahoma", Font.BOLD, 35));
//		inputfound.setBackground(Color.BLUE);
//		inputfound.setBounds(641, 83, 37, 36);
//		contentPane.add(inputfound);
		
		Inputname = new JTextField();
		Inputname.setFont(new Font("Tahoma", Font.ITALIC, 16));
		Inputname.setText("test4.png");
		Inputname.setToolTipText("exect name of the input Map\r\nHas to be stored at the root-MapNavigation-folder");
		Inputname.setBounds(395, 83, 188, 36);
		contentPane.add(Inputname);
		Inputname.setColumns(10);
		
		Button checkbutton = new Button("load?");
		checkbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(checkbutton.getLabel()=="load?") {
					File picture = new File(Inputname.getText());
					if(picture.exists()) {
						checkbutton.setLabel("checked");
						checkbutton.setBackground(Color.GREEN);
						checkbutton.setForeground(Color.DARK_GRAY);
						checkedInput = 2;
						System.out.println("changeto:" + checkedInput);
						writetoFile();
					}
					else {
						checkbutton.setLabel("not found");
						checkbutton.setBackground(Color.ORANGE);
						checkbutton.setForeground(Color.DARK_GRAY);
						checkedInput = 1;
						System.out.println("changeto:" + checkedInput);
						writetoFile();
					}
//					System.exit(1);
				}
				else if(checkbutton.getLabel()=="not found") {
					File picture = new File(Inputname.getText());
					if(picture.exists()) {
						checkbutton.setLabel("checked");
						checkbutton.setBackground(Color.GREEN);
						checkbutton.setForeground(Color.DARK_GRAY);
						checkedInput = 2;
						System.out.println("changeto:" + checkedInput);
						writetoFile();
					}
					else {
						checkbutton.setLabel("not found");
						checkbutton.setBackground(Color.ORANGE);
						checkbutton.setForeground(Color.DARK_GRAY);
						checkedInput = 1;
						System.out.println("changeto:" + checkedInput);
					}
				}
				else {
					File picture = new File(Inputname.getText());
					if(picture.exists()) {
//						checkbutton.setLabel("load?");		
//						checkbutton.setBackground(Color.DARK_GRAY);
//						checkbutton.setForeground(Color.WHITE);
//						checkedInput = 0;
						System.out.println("changeto:" + checkedInput);
						writetoFile();
					}
					else {
						checkbutton.setLabel("not found");
						checkbutton.setBackground(Color.ORANGE);
						checkbutton.setForeground(Color.DARK_GRAY);
						checkedInput = 1;
						System.out.println("changeto:" + checkedInput);
						writetoFile();
					}
				}
			}
		});
		checkbutton.setForeground(Color.WHITE);
		checkbutton.setBackground(Color.DARK_GRAY);
		checkbutton.setBounds(592, 82, 86, 36);
		contentPane.add(checkbutton);
		
		JLabel Output = new JLabel("Output Karte");
		Output.setBounds(395, 132, 114, 18);
		contentPane.add(Output);
		
		Outputname = new JTextField();
		Outputname.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				File outputfile = new File(Outputname.getText());
				if(outputfile.exists()) {
					Outputname.setBackground(Color.YELLOW);
					Outputname.setToolTipText("this name is already used try any other .png");
				}
				else {
					Outputname.setBackground(Color.WHITE);
					Outputname.setToolTipText("Any not jused name .png");
				}
			}
		});
		Outputname.setFont(new Font("Tahoma", Font.ITALIC, 16));
		Outputname.setText(newoutputname);
		Outputname.setToolTipText("Any not jused name .png");
		Outputname.setColumns(10);
		Outputname.setBounds(395, 157, 283, 36);
		contentPane.add(Outputname);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(245,245,245,190));
		panel_1.setBounds(382, 212, 306, 162);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel Start = new JLabel("Startposition");
		Start.setBounds(14, 4, 133, 18);
		panel_1.add(Start);
		
		JLabel Ziel = new JLabel("Targetposition");
		Ziel.setBounds(162, 4, 133, 18);
		panel_1.add(Ziel);
		
		startX = new JFormattedTextField();
		startX.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				startX.setText(startX.getText().replaceAll("\\D+",""));
				if(0>Integer.parseInt(startX.getText())||Integer.parseInt(startX.getText())>1/*pixelX*/) {
					startX.setBackground(Color.ORANGE);
				}
				else {
					startX.setBackground(Color.WHITE);
				}
			}
		});
		startX.setBounds(14, 29, 133, 36);
		panel_1.add(startX);
		startX.setText("2");
		startX.setFont(new Font("Tahoma", Font.ITALIC, 16));
		startX.setToolTipText("X Coodinate");
		
		startY = new JFormattedTextField();
		startY.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				startY.setText(startY.getText().replaceAll("\\D+",""));
				if(0>Integer.parseInt(startY.getText())||Integer.parseInt(startY.getText())>1/*pixelY*/) {
					startY.setBackground(Color.ORANGE);
				}
				else {
					startY.setBackground(Color.WHITE);
				}
			}
		});
		startY.setBounds(14, 73, 133, 36);
		panel_1.add(startY);
		startY.setFont(new Font("Tahoma", Font.ITALIC, 16));
		startY.setText("2");
		startY.setToolTipText("Y Coodinate");
		
		targetX = new JFormattedTextField();
		targetX.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				targetX.setText(targetX.getText().replaceAll("\\D+",""));
				if(0>Integer.parseInt(targetX.getText())||Integer.parseInt(targetX.getText())>1/*pixelX*/) {
					targetX.setBackground(Color.ORANGE);
				}
				else {
					targetX.setBackground(Color.WHITE);
				}
			}
		});
		targetX.setBounds(162, 29, 133, 36);
		panel_1.add(targetX);
		targetX.setFont(new Font("Tahoma", Font.ITALIC, 16));
		targetX.setText("14");
		targetX.setToolTipText("X Coodinate");
		
		targetY = new JFormattedTextField();
		targetY.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				targetY.setText(targetY.getText().replaceAll("\\D+",""));
				if(0>Integer.parseInt(targetY.getText())||Integer.parseInt(targetY.getText())>1/*pixelY*/) {
					targetY.setBackground(Color.ORANGE);
				}
				else {
					targetY.setBackground(Color.WHITE);
				}
			}
		});
		targetY.setBounds(162, 73, 133, 36);
		panel_1.add(targetY);
		targetY.setFont(new Font("Tahoma", Font.ITALIC, 16));
		targetY.setText("11");
		targetY.setToolTipText("Y Coodinate");
		
		Button SetStart = new Button("Set Start");
		SetStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(startset==0) {
					startset = 1;
					calcingactive = 0;
				}
				writetoFile();
			}
		});
		SetStart.setBounds(14, 116, 133, 36);
		panel_1.add(SetStart);
		SetStart.setForeground(Color.WHITE);
		SetStart.setBackground(Color.BLUE);
		
		Button SetTarget = new Button("Set Target");
		SetTarget.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(targetset == 0) {
					targetset = 1;
				}
				writetoFile();
			}
		});
		SetTarget.setBounds(162, 116, 133, 36);
		panel_1.add(SetTarget);
		SetTarget.setForeground(Color.WHITE);
		SetTarget.setBackground(Color.BLUE);
		
		
		JLabel background = new JLabel("");
		background.setBounds(242, -42, 600, 600);
		contentPane.add(background);
		background.setVerticalAlignment(SwingConstants.TOP);
		background.setIcon(new ImageIcon(uifunction.class.getResource("/designe/imgsource/LogoWaterprint.png")));
	}
	
	public void create() {
		
	}
//	public int picturefound () {
//		if(theirisapicturefound==1) {
//			return 1;
//		}
//		else {
//			return 0;
//		}
//	}
	public void request() {
		//add output to put in a map
	}
	public int getInputfound() {
		System.out.println("checked:" + checkedInput);
		
		return checkedInput;
		/*
		if(checkedInput==true) {
//			File file = new File(Inputname.getText());
//				if(file.exists()) {
					return true;
//				}
//				else {
//					return false;
//				}
		}
		else {
			return false;
		}*/
	}
//	private int i = 0;
	String newoutputname = "out.png";
	public String generateNewOutputname() {
		boolean newfound = false;
		System.out.println("Test1");
		for (int i = 0; newfound == false; i++) {
			System.out.println("Test2: " + i);
			File outputfile = new File(newoutputname);
			newfound=outputfile.exists();
			newoutputname="output"+ Integer.toString(i) + ".png";
		}
		System.out.println("Test3");
		return newoutputname;
	}
	
	//not more needed transver via transferbuffer.temp
	public String getInputname() {
		return Inputname.getText();
	}
	public String getOutputname() {
		return Outputname.getText();
	}
	public int getStartX() {
		return Integer.parseInt(startX.getText());
	}
	public int getStartY() {
		return Integer.parseInt(startY.getText());
	}
	public int getTagetX() {
		return Integer.parseInt(targetX.getText());
	}
	public int getTagetY() {
		return Integer.parseInt(targetY.getText());
	}
	public void outputfinal() {
		//give distance and way
	}
	int valuetotest1 = 0;
	public int getdynamictest() {
		valuetotest1++;
		return valuetotest1;
	}
	
	public void setStatus(String statusmessage, int activepersentage, int persentage) {
		progressbarvalue=persentage;
		activeprogressbarvalue=activepersentage;
	}
	
	public void reset(String variable/*, int value = 0*/) {
		switch (variable) {
			case "writingattempt":
				writingattempt = 0;
				writetoFile();
			break;
			case "startset":
				startset = 0;
				writetoFile();
			break;
			case "targetset":
				targetset = 0;
				System.out.println("reset targetset");
				writetoFile();
			break;
			case "calcingactive":
				calcingactive = 0;
				writetoFile();
			break;
		}
	}
	
	private int writingattempt = 0;
	private int startset = 0;
	private int targetset = 0;
	private int calcingactive = 0;
	private int finallydone = 0;
	public void writetoFile() {
		
		try {
			writingattempt++;
			File transferbuffer = new File("transferbuffer.tmp");
			FileWriter fileWriter = new FileWriter(transferbuffer);	//Textdatei leeren
			fileWriter.write("");
			fileWriter.close();
			PrintStream fileStream = new PrintStream(transferbuffer);
			fileStream.println(writingattempt);
			fileStream.println(getInputfound());
			fileStream.println(startset);//TODO add get
			fileStream.println(targetset);
			fileStream.println(calcingactive);//calcingon war auch nicht schlecht
			fileStream.println(getInputname());
			fileStream.println(getOutputname());
			fileStream.println(getStartX());
			fileStream.println(getStartY());
			fileStream.println(getTagetX());
			fileStream.println(getTagetY());
			fileStream.print(finallydone);
			fileStream.flush();
			fileStream.close();
//			fileWriter.write(fileStream);
//			fileWriter.flush();
//			fileWriter.close();
//			File.createTempFile("transferbuffer", ".temp");
//			Files.write(transferbuffer.temp, 1, checkedInput);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		readfromFile();
	}
	private String [] fileOutput = new String[12];
	private void readfromFile() {
		String line;
	    try {
	    	File transferbuffer = new File("transferbuffer.tmp");
			// Creates a FileReader Object
			FileReader filereader = new FileReader(transferbuffer); 
	        BufferedReader bufferreader = new BufferedReader(filereader);
	        line = bufferreader.readLine();

	        for (int i = 0; line != null; i++) {
	        	fileOutput[i] = line;
	        	line = bufferreader.readLine();
			}
//	        while (line != null) {     
//	          //do whatever here 
//	            line = bufferreader.readLine();
//	        }
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	    
	    //TESTOUTPUT
//	    System.out.println("beginn");
//	    for (int i = 0; i < fileOutput.length; i++) {
//			System.out.println(fileOutput[i]);
//		}
//	    System.out.println("end");
	}
	private void readfromFiletooutput() {
		try {
			File transferbuffer = new File("transferbuffer.temp");
			// Creates a FileReader Object
			FileReader filereader = new FileReader(transferbuffer); 
		    char [] a = new char[50];
		    filereader.read(a);   // reads the content to the array
		    for(char c : a)
		    System.out.print(c);   // prints the characters one by one
		    filereader.close();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

//	public void inputwindow() {
//		JFrame inputnamewindow = new JFrame();
//		inputnamewindow.setUndecorated(true);
//		inputnamewindow.setVisible(true);
//		inputnamewindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		inputnamewindow.setBackground(Color.WHITE);
//		inputnamewindow.setBounds(100, 100, 480, 200);
//		
//		JLabel Input = new JLabel("Input Karte");
//		Input.setBounds(0, 0, 114, 18);
//		inputnamewindow.add(Input);
//		
//		Inputname = new JTextField();
//		Inputname.setFont(new Font("Tahoma", Font.ITALIC, 16));
//		Inputname.setText("test4.png");
//		Inputname.setToolTipText("exect name of the input Map\r\nHas to be stored at the root-MapNavigation-folder");
//		Inputname.setBounds(0, 10, 40, 10);
//		inputnamewindow.add(Inputname);
//		Inputname.setColumns(10);
//		
//		Button checkbutton = new Button("load?");
//		checkbutton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				if(checkbutton.getLabel()=="load?") {
//					File picture = new File(Inputname.getText());
//					if(picture.exists()) {
//						checkbutton.setLabel("checked");
//						checkbutton.setBackground(Color.GREEN);
//						checkbutton.setForeground(Color.DARK_GRAY);
//						checkedInput = 2;
//						System.out.println("changeto:" + checkedInput);
//						writetoFile();
//					}
//					else {
//						checkbutton.setLabel("not found");
//						checkbutton.setBackground(Color.ORANGE);
//						checkbutton.setForeground(Color.DARK_GRAY);
//						checkedInput = 1;
//						System.out.println("changeto:" + checkedInput);
//						writetoFile();
//					}
////					System.exit(1);
//				}
//				else if(checkbutton.getLabel()=="not found") {
//					File picture = new File(Inputname.getText());
//					if(picture.exists()) {
//						checkbutton.setLabel("checked");
//						checkbutton.setBackground(Color.GREEN);
//						checkbutton.setForeground(Color.DARK_GRAY);
//						checkedInput = 2;
//						System.out.println("changeto:" + checkedInput);
//						writetoFile();
//					}
//					else {
//						checkbutton.setLabel("not found");
//						checkbutton.setBackground(Color.ORANGE);
//						checkbutton.setForeground(Color.DARK_GRAY);
//						checkedInput = 1;
//						System.out.println("changeto:" + checkedInput);
//					}
//				}
//				else {
//					File picture = new File(Inputname.getText());
//					if(picture.exists()) {
////						checkbutton.setLabel("load?");		
////						checkbutton.setBackground(Color.DARK_GRAY);
////						checkbutton.setForeground(Color.WHITE);
////						checkedInput = 0;
//						System.out.println("changeto:" + checkedInput);
//						writetoFile();
//					}
//					else {
//						checkbutton.setLabel("not found");
//						checkbutton.setBackground(Color.ORANGE);
//						checkbutton.setForeground(Color.DARK_GRAY);
//						checkedInput = 1;
//						System.out.println("changeto:" + checkedInput);
//						writetoFile();
//					}
//				}
//			}
//		});
//		checkbutton.setForeground(Color.WHITE);
//		checkbutton.setBackground(Color.DARK_GRAY);
//		checkbutton.setBounds(50, 10, 86, 36);
//		inputnamewindow.add(checkbutton);
//		
//		JLabel background = new JLabel("");
//		background.setBounds(20, -42, 100, 100);
//		inputnamewindow.add(background);
//		background.setVerticalAlignment(SwingConstants.TOP);
//		background.setIcon(new ImageIcon(uifunction.class.getResource("/designe/imgsource/LogoWaterprint.png")));
//	}
}