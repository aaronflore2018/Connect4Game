package edu.fau.COT4930;

import java.io.Serializable;

import javax.swing.Icon;

/**
The Player class for the COT4930 project. This class must be extended for the project.
*/

public class Player implements Serializable
{
	private String name;
	private int playerScore;

	/**
		The constructor creates a default Player object.
	*/
	public Player()
	{
		name = "";
		playerScore = 0;
	}
	/**
		The constructor creates a Player object with the specified name.
		@param n represents the name of the Player.
	*/
	public Player(String n)
	{
		name = n;
	}

	/**
		Method to retrieve the name of the player.
		@return a String representing the name of the Player.
	*/
	public String getName()
	{ // return the name
		return name;
	}

	/**
		Method to set the Players name.
		@param n represents the name of the Player.
	*/
	public void setName(String n)
	{ // set the PLayers name
		name = n;
	}
	
	/**
	 * getScore()
	 * Method to return the player's score
	 * @return int playerScore
	*/
	public int getScore() {
		return playerScore;
	}
	
	/**
	 * addToScore()
	 * Method to add 1 to the player's score
	 * @param none
	 * @return none
	*/
	public void addToScore() {
		playerScore++;
	}
}
