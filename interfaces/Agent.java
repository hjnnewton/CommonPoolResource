package interfaces;

import java.util.ArrayList;

/**
 * Game framework
 * Agent.java
 * Interface for an agent
 * 
 * @author Friedrich Burkhard von der Osten
 * @version 2.0
 */

public interface Agent {
	
	public int getID();
	public int getGameID();
	
	public double playAction(ArrayList<Game> games);
	public void receiveReward(double r);
	public void learn(ArrayList<Game> games);
	
	public double getActionPlayed();
	public double getRewardReceived();
	public double getLongTermValue();
	public ArrayList<Double> getActionHistory();
	
	public Object getAbstractData();
	public ArrayList<Double> getAssetHistory();
}
