package edu.fau.COT4930;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

import javax.swing.Icon;

/**
 * enum State
 * Constructor to initialize the token states
 */
enum State
{
	emptyToken, redToken, blackToken
};

// Cell.java
public class Cell implements Icon, Serializable
{

	/**
	 * Cell()
	 * Constructor to initialize the token and size
	 * @param State tokenColor, int s
	 */
   public Cell(State tokenColor,int s)
   {
       this.token = tokenColor;
       //Checks for color
       if(token == State.redToken) {
    	   currentColor = Color.RED;
       }else if(token == State.blackToken) {
    	   currentColor = Color.BLACK;
       }else {
    	   currentColor = Color.WHITE;
       }
       size = s;
   }

   /**
	 * getToken()
	 * Method to return the state of the token
	 * @param none
	 * @return State token
	 */
   public State getToken()
   {
       return token;
   }

   /**
	 * setToken()
	 * Method to set the state of the space to desired color
	 * @param State token
	 */
   public void setToken(State token)
   {
	   //Setting the colors of the space
       this.token = token;
       if(token == State.redToken) {
    	   currentColor = Color.RED;
       }else if(token == State.blackToken) {
    	   currentColor = Color.BLACK;
       }else {
    	   currentColor = Color.WHITE;
       }
   }

	@Override
	/**
	 * paintIcon()
	 * Method to actually draw the space
	 * @param Component c, Graphics g, int x, int y
	 */
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(currentColor);
		g.fillOval(x, y, size, size);
	}

	@Override
	/**
	 * getIconWidth()
	 * Method to return the size of the circle 
	 * @param none
	 * @return int size
	 */
	public int getIconWidth() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	/**
	 * getIconHeight()
	 * Method to return the size of the circle 
	 * @param none
	 * @return int size
	 */
	public int getIconHeight() {
		// TODO Auto-generated method stub
		return size;
	}
	
	//private Variables
	State token;
	private int size;
	Color currentColor;
}