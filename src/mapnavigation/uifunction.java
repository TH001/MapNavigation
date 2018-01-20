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
import javax.swing.JSpinner;

public class uifunction extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	private JPanel contentPane;
	private JTextField Inputname;
	private JTextField Outputname;
	private JPasswordField StartX;
	
	int xx,xy;
	private JPasswordField StartY;
	private JPasswordField TargetY;
	private JPasswordField TargetX;

	/**
	 * Launch the application.
	 */
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
		setBounds(100, 100, 729, 476);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(0, 0, 346, 490);
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
		
		JLabel lblWeGotYou = new JLabel("Status: ...We got you...");
		lblWeGotYou.setHorizontalAlignment(SwingConstants.CENTER);
		lblWeGotYou.setForeground(new Color(240, 248, 255));
		lblWeGotYou.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblWeGotYou.setBounds(111, 343, 141, 27);
		panel.add(lblWeGotYou);
		
		Button button = new Button("Run Waycalc");
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(241, 57, 83));
		button.setBounds(395, 363, 283, 36);
		contentPane.add(button);
		
		Inputname = new JTextField();
		Inputname.setToolTipText("exect name of the input Map\r\nHas to be stored at the root-MapNavigation-folder");
		Inputname.setBounds(395, 83, 283, 36);
		contentPane.add(Inputname);
		Inputname.setColumns(10);
		
		JLabel lblUsername = new JLabel("Input Karte");
		lblUsername.setBounds(395, 58, 114, 18);
		contentPane.add(lblUsername);
		
		JLabel lblEmail = new JLabel("Output Karte");
		lblEmail.setBounds(395, 132, 114, 18);
		contentPane.add(lblEmail);
		
		Outputname = new JTextField();
		Outputname.setToolTipText("Any not jused name .png");
		Outputname.setColumns(10);
		Outputname.setBounds(395, 157, 283, 36);
		contentPane.add(Outputname);
		
		JLabel lblPassword = new JLabel("Startposition");
		lblPassword.setBounds(395, 204, 133, 18);
		contentPane.add(lblPassword);
		
		JLabel lbl_close = new JLabel("X");
		lbl_close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				System.exit(0);
			}
		});
		lbl_close.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_close.setForeground(new Color(241, 57, 83));
		lbl_close.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_close.setBounds(691, 0, 37, 27);
		contentPane.add(lbl_close);
		
		Button button_1 = new Button("Set Start");
		button_1.setForeground(Color.WHITE);
		button_1.setBackground(new Color(241, 57, 83));
		button_1.setBounds(395, 321, 133, 36);
		contentPane.add(button_1);
		
		Button button_2 = new Button("Set Target");
		button_2.setForeground(Color.WHITE);
		button_2.setBackground(new Color(241, 57, 83));
		button_2.setBounds(545, 321, 133, 36);
		contentPane.add(button_2);
		
		StartX = new JPasswordField();
		StartX.setToolTipText("X Coodinate");
		StartX.setBounds(395, 232, 133, 36);
		contentPane.add(StartX);
		
		StartY = new JPasswordField();
		StartY.setToolTipText("Y Coodinate");
		StartY.setBounds(395, 279, 133, 36);
		contentPane.add(StartY);
		
		TargetX = new JPasswordField();
		TargetX.setToolTipText("X Coodinate");
		TargetX.setBounds(545, 232, 133, 36);
		contentPane.add(TargetX);
		
		TargetY = new JPasswordField();
		TargetY.setToolTipText("Y Coodinate");
		TargetY.setBounds(545, 279, 133, 36);
		contentPane.add(TargetY);
		
		JLabel lblTargetposition = new JLabel("Targetposition");
		lblTargetposition.setBounds(545, 202, 133, 18);
		contentPane.add(lblTargetposition);
	}
}