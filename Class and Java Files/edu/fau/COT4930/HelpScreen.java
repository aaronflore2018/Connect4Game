package edu.fau.COT4930;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class HelpScreen {

	private JFrame frame;

	/**
	 * createFrame()
	 * Launch the application.
	 */
	public static void createFrame() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HelpScreen window = new HelpScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * HelpScreen()
	 * Create the application.
	 */
	public HelpScreen() {
		initialize();
	}

	/**
	 * initialize()
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Decoration
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.activeCaption);
		frame.getContentPane().setLayout(null);
		
		JLabel lblHelp = new JLabel("Help");
		lblHelp.setFont(new Font("Tahoma", Font.PLAIN, 32));
		lblHelp.setBounds(170, 11, 69, 34);
		frame.getContentPane().add(lblHelp);
		
		//Making the Help Text
		JTextArea txtrWelcomeToConnect = new JTextArea();
		txtrWelcomeToConnect.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtrWelcomeToConnect.setBackground(SystemColor.activeCaption);
		txtrWelcomeToConnect.setText("Welcome to Connect 4!\r\n\r\nIn this game, two players are met with a 7x6 grid with empty circles. \r\nEach player has tokens with a color assigned to them (Red or Black) \r\nand they can place thier token in which ever column they choose, \r\nkeep in mind though that your tokens will fall to the bottom that is \r\nnot filled with a token.\r\n\r\nThe goal of the game is to be the first person to get four in a row \r\nvertically, horizontally, or diagonally. Simple as that.");
		txtrWelcomeToConnect.setBounds(26, 56, 387, 154);
		frame.getContentPane().add(txtrWelcomeToConnect);
		
		//Button to go back to the start screen
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(167, 227, 89, 23);
		frame.getContentPane().add(btnBack);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		btnBack.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	//Goes back to the StartMenu
                StartMenu.createFrame();
                frame.setVisible(false);
            }
		});
		
	}
}
