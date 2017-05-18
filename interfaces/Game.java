package interfaces;

import java.util.ArrayList;

import datastructures.GameSetup;

/**
 * Game framework
 * Game.java
 * Interface for a game
 * 
 * @author Friedrich Burkhard von der Osten
 * @version 2.0
 */

public interface Game {
	
	public int getID();
	
	public void playRound(ArrayList<Game> games);

	public ArrayList<Agent> getAgents();
	public double getGameState();
	public double getPreviousGameState();
	public double getCumulativeAction();
	public ArrayList<Double> getGameStateHistory();
	public ArrayList<Double> getAverageLongTermValue();
	public ArrayList<Double> getActionHistory();
	public GameSetup getSetup();
}
