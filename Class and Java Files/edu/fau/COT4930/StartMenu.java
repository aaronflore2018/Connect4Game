package edu.fau.COT4930;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

import javax.swing.JToolBar;

public class StartMenu {

	private JFrame frame;
	
	public static void main(String[] args) {
		StartMenu.createFrame();
	}

	/**
	 * createFrame()
	 * Launch the application.
	 * @param none
	 */
	public static void createFrame() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
						//Game will start by checking for a saved game 'game.txt'
						String filename = "game.txt";
				        String workingDirectory = System.getProperty("user.dir");
				        File tempdir = new File(workingDirectory);
				        boolean check = tempdir.exists();
				        if (check == true) {
				        	File tempdb = new File(filename);
				            if(tempdb.exists()){
				            	//If found, the game will start from that point and skip this frame
				            	JOptionPane.showMessageDialog(null, "Hello! We found a game in progress! We will start from that point.");
				            	FileInputStream file = new FileInputStream(filename);
				                ObjectInputStream in = new ObjectInputStream(file);
				                Game game = (Game) in.readObject();
				            	Game.createFrame(game);
				            }else {
				            	//Game will start from the beginning
				            	StartMenu window = new StartMenu();
								window.frame.setVisible(true);
				            }
				        }
					}catch (IOException ex) {
						ex.printStackTrace();
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * StartMenu()
	 * Create the application.
	 */
	public StartMenu() {
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
		
		//Label for the title
		JLabel lblConnect = new JLabel("Connect 4");
		lblConnect.setFont(new Font("Tahoma", Font.PLAIN, 32));
		lblConnect.setBounds(143, 11, 149, 49);
		frame.getContentPane().add(lblConnect);
		
		//Button to start the game
		JButton btnStartGame = new JButton("Start Game");
		btnStartGame.setBounds(166, 100, 107, 40);
		frame.getContentPane().add(btnStartGame);
		btnStartGame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                PlayerScreen.createFrame();
                frame.setVisible(false);
            }
		});
		
		//Button to go to help screen
		JButton btnHelp = new JButton("Help");
		btnHelp.setBounds(166, 161, 107, 40);
		frame.getContentPane().add(btnHelp);
		btnHelp.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	//Creates the player screen
                HelpScreen.createFrame();
                frame.setVisible(false);
            }
		});
	}
}
