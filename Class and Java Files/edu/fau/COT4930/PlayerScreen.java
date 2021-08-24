package edu.fau.COT4930;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerScreen {

	//Private Variables
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * createFrame()
	 * Launch the application.
	 */
	public static void createFrame() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerScreen window = new PlayerScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * PlayerScreen()
	 * Create the application.
	 */
	public PlayerScreen() {
		initialize();
	}

	/**
	 * initialize()
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Creating JFrame
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.activeCaption);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//JLabels add names
		JLabel lblNameSelection = new JLabel("Name Selection");
		lblNameSelection.setFont(new Font("Tahoma", Font.PLAIN, 32));
		lblNameSelection.setBounds(108, 11, 242, 40);
		frame.getContentPane().add(lblNameSelection);
		
		JLabel lblNewLabel = new JLabel("Player 1 Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(72, 106, 109, 20);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblPlayerName = new JLabel("Player 2 Name");
		lblPlayerName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPlayerName.setBounds(72, 147, 109, 20);
		frame.getContentPane().add(lblPlayerName);
		
		//JTextField add names
		textField = new JTextField();
		textField.setBounds(217, 108, 96, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(217, 147, 96, 20);
		frame.getContentPane().add(textField_1);
		
		//Button to confirm selections
		JButton btnSelectNames = new JButton("Select Names");
		btnSelectNames.setBounds(160, 195, 129, 30);
		frame.getContentPane().add(btnSelectNames);
		btnSelectNames.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	if(textField.getText().length() <= 10) {
            		Game.createFrame(new Player(textField.getText()), new Player(textField_1.getText()));
                    frame.setVisible(false);
            	}else {
            		JOptionPane.showMessageDialog(null, "Please enter within 10 characters for your names");
            	}
            }
		});
	}
}
