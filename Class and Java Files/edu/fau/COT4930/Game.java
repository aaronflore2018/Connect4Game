package edu.fau.COT4930;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.LinkedList;

import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import javax.swing.text.PlainDocument;
import javax.swing.JToggleButton;
import javax.swing.JFormattedTextField;
import java.awt.SystemColor;

public class Game implements Serializable{

	private static final int ABORT = 0;
	private JFrame frame;

	/**
	 * Place to create the game frame (for new games)
	 * @param Player p1, p2
	 */
	public static void createFrame(Player p1, Player p2) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game window = new Game(p1,p2);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Place to create the game frame (for saved games)
	 * @param Game g
	 */
	public static void createFrame(Game g) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game game = new Game(g);
					game.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application (for new games).
	 * @param Player p1, p2
	 */
	public Game(Player p1, Player p2) {
		initialize(p1,p2);
	}
	
	/**
	 * Create the application (for saved games).
	 * @param Game g
	 * @wbp.parser.constructor
	 */
	public Game(Game g) {
		initialize(g.getP1(),g.getP2(),g.getGame());
	}

	/**
	 * Initialize the contents of the frame (for creating a new game).
	 * @param Player p1, p2
	 */
	private void initialize(Player p1, Player p2) {
		
		//frame Creation
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.activeCaption);
		frame.setBounds(100, 100, 543, 567);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Game Board Creation
		panel = new JPanel();
		panel.setBackground(Color.YELLOW);
		panel.setBounds(17, 86, 500, 400);
		
		//Making the panel 7x6 grid layout for the cells
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(7, 6, 0, 0));
		
		//Creating a 7x6 Grid of circles with states and adding them to the panel
		if(cells==null) {
			cells = new Cell[7][6];
			for(int i = 0; i< 7;i++) {
				for(int j = 0; j < 6;j++) {
					cells[i][j] = new Cell(State.emptyToken, size);
					panel.add(new JLabel(cells[i][j]));
				}
			}
		}
		
		//Adding dynamic turn Label
		lblPlayersTurn = new JLabel(p1.getName() + "'s Turn");
		lblPlayersTurn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPlayersTurn.setBounds(23, 30, 188, 45);
		frame.getContentPane().add(lblPlayersTurn);
		
		//Initializing the players
		this.p1 = p1;
		this.p2 = p2;
		
		//More JLabels
		JLabel lblEnterDesiredRow = new JLabel("Enter Desired Column");
		lblEnterDesiredRow.setBounds(221, 30, 131, 14);
		frame.getContentPane().add(lblEnterDesiredRow);
		
		JLabel lblfrom = new JLabel("(from 1-6)");
		lblfrom.setBounds(246, 49, 64, 14);
		frame.getContentPane().add(lblfrom);
		
		//A new FormattingTextField to make it only accept integers
		NumberFormat longFormat = NumberFormat.getIntegerInstance();
		
		//Creating the JTextField to read only integers
		NumberFormatter numberFormatter = new NumberFormatter(longFormat);
		numberFormatter.setAllowsInvalid(false);
		numberFormatter.setMinimum(0l);
		JFormattedTextField formattedTextField = new JFormattedTextField(numberFormatter);
		formattedTextField.setBounds(354, 27, 89, 20);
		frame.getContentPane().add(formattedTextField);
		
		//Creating Dynamic Score counter labels for both players
		lblScore2 = new JLabel(p2.getName() + " Score: " + p2.getScore());
		lblScore2.setBounds(415, 0, 102, 14);
		frame.getContentPane().add(lblScore2);
		
		lblScore1 = new JLabel(p1.getName() + " Score: " + p1.getScore());
		lblScore1.setBounds(287, 0, 108, 14);
		frame.getContentPane().add(lblScore1);
		
		//Creating button to end the game
		JButton btnEndGame = new JButton("End Game");
		btnEndGame.setBounds(56, 11, 108, 23);
		frame.getContentPane().add(btnEndGame);
		btnEndGame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	//once pressed the game will exit and the file will be deleted
                String filename = "game.txt";
                String workingDirectory = System.getProperty("user.dir");
                File tempdir = new File(workingDirectory);
                boolean check = tempdir.exists();
                if (check == true) {
                    File tempdb = new File(filename);
                    if(tempdb.exists()) {
                	    tempdb.delete();
                    }
                }
                System.exit(ABORT);
            }
		});
		
		//The enter button to read the column number entered from the player
		JButton btnEnter = new JButton("Enter");
		btnEnter.setBounds(354, 52, 89, 23);
		frame.getContentPane().add(btnEnter);
		btnEnter.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	String columnText = formattedTextField.getText();
            	int columnNumber = Integer.parseInt(columnText);
            	if(columnNumber < 1 || columnNumber > 6) { //If the string is not within the bounds of 1-6
            		JOptionPane.showMessageDialog(null, "Please enter within the bounds");
            	}else {
            		enterToken(columnNumber-1);
            	}
            }
		});
	}
	
	/**
	 * Initialize the contents of the frame (for loading a saved game).
	 * @param Player p1, p2, Cell[][] c
	 */
	
	private void initialize(Player p1, Player p2, Cell[][] c) {
		
		//frame Creation
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.activeCaption);
		frame.setBounds(100, 100, 543, 567);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Game Board Creation
		panel = new JPanel();
		panel.setBackground(Color.YELLOW);
		panel.setBounds(17, 86, 500, 400);
		
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(7, 6, 0, 0));
		
		//Adding dynamic turn Label
		lblPlayersTurn = new JLabel(p1.getName() + "'s Turn");
		lblPlayersTurn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPlayersTurn.setBounds(23, 30, 188, 45);
		frame.getContentPane().add(lblPlayersTurn);
		
		//Initializing the players
		this.p1 = p1;
		this.p2 = p2;
		cells = new Cell[7][6];
		for(int i = 0; i< 7;i++) {
			for(int j = 0; j < 6;j++) {
				cells[i][j] = c[i][j];
				panel.add(new JLabel(cells[i][j]));
			}
		}
		
		//More JLabels
		JLabel lblEnterDesiredRow = new JLabel("Enter Desired Column");
		lblEnterDesiredRow.setBounds(221, 30, 131, 14);
		frame.getContentPane().add(lblEnterDesiredRow);
		
		JLabel lblfrom = new JLabel("(from 1-6)");
		lblfrom.setBounds(246, 49, 64, 14);
		frame.getContentPane().add(lblfrom);
		
		//A new FormattingTextField to make it only accept integers
		NumberFormat longFormat = NumberFormat.getIntegerInstance();
		NumberFormatter numberFormatter = new NumberFormatter(longFormat);
		numberFormatter.setAllowsInvalid(false);
		numberFormatter.setMinimum(0l);
		JFormattedTextField formattedTextField = new JFormattedTextField(numberFormatter);
		formattedTextField.setBounds(354, 27, 89, 20);
		frame.getContentPane().add(formattedTextField);
		
		//Creating dynamic score counters for both players
		lblScore2 = new JLabel(p2.getName() + " Score: " + p2.getScore());
		lblScore2.setBounds(415, 0, 102, 14);
		frame.getContentPane().add(lblScore2);
		
		lblScore1 = new JLabel(p1.getName() + " Score: " + p1.getScore());
		lblScore1.setBounds(287, 0, 118, 14);
		frame.getContentPane().add(lblScore1);
		
		//Creating the end game button
		JButton btnEndGame = new JButton("End Game");
		btnEndGame.setBounds(54, 11, 111, 23);
		frame.getContentPane().add(btnEndGame);
		btnEndGame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	//Once pressed, it will exit the game and delete the saved game
                String filename = "game.txt";
                String workingDirectory = System.getProperty("user.dir");
                File tempdir = new File(workingDirectory);
                boolean check = tempdir.exists();
                if (check == true) {
                    File tempdb = new File(filename);
                    if(tempdb.exists()) {
                	    tempdb.delete();
                    }
                }
                System.exit(ABORT);
            }
		});
		
		//The enter button to read the column number enter from the player
		JButton btnEnter = new JButton("Enter");
		btnEnter.setBounds(354, 52, 89, 23);
		frame.getContentPane().add(btnEnter);
		btnEnter.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	String columnText = formattedTextField.getText();
            	int columnNumber = Integer.parseInt(columnText);
            	if(columnNumber < 1 || columnNumber > 6) { //If the string is not within the bounds of 1-6
            		JOptionPane.showMessageDialog(null, "Please enter within the bounds");
            	}else {
            		enterToken(columnNumber-1);
            	}
            }
		});
	}
	
	/**
	 * enterToken()
	 * method to add the token to the board and check for win condition
	 * @param int c
	 */
	public void enterToken(int c) {
		for(int r = 6;r>=0;r--) {
			if(cells[r][c].getToken() == State.emptyToken) {
				if(p1Turn) {
					cells[r][c].setToken(State.redToken);
					panel.repaint();
					if(checkWinCondition()) {
						JOptionPane.showMessageDialog(null, "Winner! " + p1.getName() + " wins!");
		    			for(int i = 0; i< 7;i++) {
		    				for(int j = 0; j < 6;j++) {
		    					cells[i][j].setToken(State.emptyToken);
		    				}
		    			}
		    			p1.addToScore();
		    			lblScore1.setText(p1.getName() + " Score: " + p1.getScore());
		    			
		    			panel.repaint();
					}
					if(isBoardFull()) {
						JOptionPane.showMessageDialog(null, "Tie! Noone wins!");
						for(int i = 0; i< 7;i++) {
		    				for(int j = 0; j < 6;j++) {
		    					cells[i][j].setToken(State.emptyToken);
		    				}
		    			}
						panel.repaint();
					}
					serializeGame();
					p1Turn = false;
					p2Turn = true;
					lblPlayersTurn.setText(p2.getName() + "'s Turn");
					break;
				}else {
					cells[r][c].setToken(State.blackToken);
					panel.repaint();
					if(checkWinCondition()) {
						JOptionPane.showMessageDialog(null, "Winner! " + p2.getName() + " wins!");
						for(int i = 0; i< 7;i++) {
		    				for(int j = 0; j < 6;j++) {
		    					cells[i][j].setToken(State.emptyToken);
		    				}
		    			}
						p2.addToScore();
		    			lblScore2.setText(p2.getName() + " Score: " + p2.getScore());
						panel.repaint();
					}
					if(isBoardFull()) {
						JOptionPane.showMessageDialog(null, "Tie! Noone wins!");
						for(int i = 0; i< 7;i++) {
		    				for(int j = 0; j < 6;j++) {
		    					cells[i][j].setToken(State.emptyToken);
		    				}
		    			}
						panel.repaint();
					}
					serializeGame();
					p2Turn = false;
					p1Turn = true;
					lblPlayersTurn.setText(p1.getName() + "'s Turn");
					break;
				}
			}
		}
	}
	
	/**
	 * checkWinCondition()
	 * place where it checks the board for a winner
	 * @param none
	 */
	private boolean checkWinCondition() {
		if(checkRows()) { //If there is a winner horizontally
			return true;
		}
		if(checkColumns()) { //If there is a winner vertically
			return true;
		}
		if(checkDiagnol()) { //If there is a winner Diagnolly
			return true;
		}
		return false;
		
	}
	
	/**
	 * checkDiagnol()
	 * place where it checks the board for a winner diagnolly
	 * @param none
	 */
	private boolean checkDiagnol() {
		for (int i = 0; i < cells.length - 3; i++)
	       {
	           for (int j = 0; j < cells[i].length - 3; j++)
	           {
	               if (cells[i][j].getToken() != State.emptyToken
	                       && cells[i][j].getToken() == cells[i + 1][j + 1].getToken()
	                       && cells[i][j].getToken() == cells[i + 2][j + 2].getToken()
	                       && cells[i][j].getToken() == cells[i + 3][j + 3].getToken())
	               {
	                   return true;
	               }
	           }
	       }
	       // Check for win diagonally, from top right
	       for (int i = 0; i < cells.length - 3; i++)
	       {
	           for (int j = 3; j < cells[i].length; j++)
	           {
	               if (cells[i][j].getToken() != State.emptyToken
	                       && cells[i][j].getToken() == cells[i + 1][j - 1].getToken()
	                       && cells[i][j].getToken() == cells[i][j - 2].getToken()
	                       && cells[i][j].getToken() == cells[i + 3][j - 3].getToken())
	               {
	                   return true;
	               }
	           }
	       }

	       // if not return blank
	       return false;
	   }
	
	/**
	 * checkColumns()
	 * place where it checks the board for a winner vertically
	 * @param none
	 */
	private boolean checkColumns() {
		int count = 0;
		for(int i = 0;i<6;i++) {
			count = 0;
			for(int j = 6; j>0;j--) {
				if(cells[j][i].getToken() == State.redToken && cells[j-1][i].getToken() == State.redToken) {
					count++;
				}
				if(count == 3) {
					return true;
				}
			}
		}
		
		for(int i = 0;i<6;i++) {
			count = 0;
			for(int j = 6; j>0;j--) {
				if(cells[j][i].getToken() == State.blackToken && cells[j-1][i].getToken() == State.blackToken) {
					count++;
				}
				if(count == 3) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * checkRows()
	 * place where it checks the board for a winner horizontally
	 * @param none
	 */
	private boolean checkRows() {
		int count = 0;
		for(int i = 0;i<7;i++) {
			count = 0;
			for(int j = 0; j<5;j++) {
				if(cells[i][j].getToken() == State.redToken && cells[i][j+1].getToken() == State.redToken) {
					count++;
				}
				if(count == 3) {
					return true;
				}
			}
		}
		for(int i = 0;i<7;i++) {
			count = 0;
			for(int j = 0; j<5;j++) {
				if(cells[i][j].getToken() == State.blackToken && cells[i][j+1].getToken() == State.blackToken) {
					count++;
				}
				if(count == 3) {
					return true;
				}
				
			}
		}
		return false;
	}
	
	/**
	 * isBoardFull()
	 * Place where it checks for a tie game
	 * @param none
	 */
	private boolean isBoardFull() {
		for(int i = 0;i<7;i++) {
			for(int j = 0; j<6;j++) {
				if(cells[i][j].getToken() == State.emptyToken) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * serializeGame()
	 * Place where it saves the game in a text file using serialization
	 * @param none
	 */
	private void serializeGame() {
		 String filename = "game.txt";
         String workingDirectory = System.getProperty("user.dir");
         File tempdir = new File(workingDirectory);
         boolean check = tempdir.exists();
         if (check == true) {
        	 try {
	             FileOutputStream fileOut = new FileOutputStream(filename);
	             ObjectOutputStream out = new ObjectOutputStream(fileOut);
	             Game game = new Game(p1,p2);
	             game.setGame(cells);
	             out.writeObject(game);
	             out.close();
        	 }catch (IOException e) {
                 e.printStackTrace();
             } 
         }
	}
	
	/**
	 * setP1()
	 * Place where it set who is player 1 in Game
	 * @param Player p
	 */
	public void setP1(Player p) {
		p1 = p;
	}
	
	/**
	 * setP2()
	 * Place where it set who is player 2 in Game
	 * @param Player p
	 */
	public void setP2(Player p) {
		p2 = p;
	}
	
	/**
	 * setGame()
	 * Place where it sets the current set of the game
	 * @param Cell[][] c
	 */
	public void setGame(Cell[][] c) {
		cells = c;
	}
	
	/**
	 * getP1()
	 * Place where it returns Player 1
	 * @param none
	 * @return Player p1
	 */
	public Player getP1() {
		return p1;
	}
	
	/**
	 * getP2()
	 * Place where it returns Player 2
	 * @param none
	 * @return Player p2
	 */
	public Player getP2() {
		return p2;
	}
	
	/**
	 * getGame()
	 * Place where it returns the state of the game
	 * @param none
	 * @return Cell [][] cells
	 */
	public Cell[][] getGame() {
		return cells;
	}
	
	//Private Variables
	private Cell cells[][];
	private boolean p1Turn = true;
	private boolean p2Turn = false;
	private int size = 50;
	JPanel panel;
	JLabel lblPlayersTurn;
	JLabel lblScore1;
	JLabel lblScore2;
	private Player p1;
	private Player p2;
}
