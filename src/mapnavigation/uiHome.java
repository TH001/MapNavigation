package mapnavigation;

//import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class uiHome extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtStartpositionX;
	private JTextField txtZielpositionX;
	private JTextField txtZielpositionY;
	private JTextField txtStartpositionY;
	
	int xx,xy;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					uiHome frame = new uiHome();
					frame.setUndecorated(true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public uiHome() {
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 500, 400);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
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
	            uiHome.this.setLocation(x - xx, y - xy);  
			}
		});
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(100, 800, 500, 100);
		contentPane.add(progressBar);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 45, 45);
		panel.setBackground(Color.CYAN);
		contentPane.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(201, 0, 30, 60);
		contentPane.add(panel_1);
		
		Button button = new Button("Submit Positions");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(241, 57, 83));
		button.setBounds(245, 276, 196, 36);
		contentPane.add(button);
		
		txtStartpositionX = new JTextField();
		txtStartpositionX.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtStartpositionX.setText("Startposition X");
		txtStartpositionX.setBounds(245, 206, 91, 29);
		contentPane.add(txtStartpositionX);
		txtStartpositionX.setColumns(10);
		
		txtZielpositionX = new JTextField();
		txtZielpositionX.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtZielpositionX.setText("Zielposition X");
		txtZielpositionX.setBounds(245, 241, 91, 29);
		contentPane.add(txtZielpositionX);
		txtZielpositionX.setColumns(10);
		
		txtZielpositionY = new JTextField();
		txtZielpositionY.setText("Zielposition Y");
		txtZielpositionY.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtZielpositionY.setColumns(10);
		txtZielpositionY.setBounds(350, 241, 91, 29);
		contentPane.add(txtZielpositionY);
		
		txtStartpositionY = new JTextField();
		txtStartpositionY.setText("Startposition Y");
		txtStartpositionY.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtStartpositionY.setColumns(10);
		txtStartpositionY.setBounds(350, 206, 91, 29);
		contentPane.add(txtStartpositionY);
		
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
	}
}
