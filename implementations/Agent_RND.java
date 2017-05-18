package implementations;

import java.util.ArrayList;
import java.util.Random;

import main.CFG;

import interfaces.Agent;
import interfaces.Game;

/**
 * Game framework
 * Agent_RND.java
 * Implementation of a random agent for the common pool resource game
 * 
 * @author Friedrich Burkhard von der Osten
 * @version 1.0
 */

public class Agent_RND implements Agent {
	
	private int id;
	private int gameid;
	private Random rnd;
	
	private double action;
	private ArrayList<Double> ahist;
	private ArrayList<Double> assetHistory;
	private double reward;
	private double assets;
	
	public Agent_RND(int gid){
		gameid = gid;
		id = CFG.aid;
		CFG.aid++;
		assets = CFG.initA;
		rnd = new Random();
		action = ((rnd.nextInt(CFG.actions)+1)/(double)CFG.actions) * (CFG.action_max - CFG.action_min) + CFG.action_min;
		ahist = new ArrayList<Double>();
		assetHistory = new ArrayList<Double>();
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public int getGameID() {
		return gameid;
	}

	@Override
	/**
	 * Chooses an action completely at random
	 * @param games List of all games being played in the current instance of the simulation
	 * @return action chosen
	 */
	public double playAction(ArrayList<Game> games) {
		action = ((rnd.nextInt(CFG.actions)+1)/(double)CFG.actions) * (CFG.action_max - CFG.action_min) + CFG.action_min;
		assets -= CFG.cost * action;
		ahist.add(action);
		return action;
	}

	@Override
	public void receiveReward(double r) {
		reward = r;
		assets += reward;
		assetHistory.add(assets);
	}

	@Override
	/**
	 * Adapts decision making mechanism based on game experience
	 * @param games List of all games currently played in this instance of the simulation
	 */
	public void learn(ArrayList<Game> games) {
		// nope, this agent is stupid!
	}

	@Override
	public double getActionPlayed() {
		return action;
	}

	@Override
	public double getRewardReceived() {
		return reward;
	}

	@Override
	public double getLongTermValue() {
		return assets;
	}

	@Override
	public ArrayList<Double> getActionHistory() {
		return ahist;
	}
	public ArrayList<Double> getAssetHistory() {
		return assetHistory;
	}
	@Override
	public Object getAbstractData() {
		// TODO return statistics for collection, can take any form, mostly Lists
		return null;
	}

	/**
	 * Converts an action into its index
	 * @param a the action
	 * @return the index of the action
	 */
	public int actionToIndex(double a){
		return (int)(((a - CFG.action_min)/(CFG.action_max - CFG.action_min)) * CFG.actions - 1);
	}
	
	/**
	 * Converts an index into its action
	 * @param i the index
	 * @return the action of the index
	 */
	public double indexToAction(int i){
		return ((i+1)/(double)CFG.actions) * (CFG.action_max - CFG.action_min) + CFG.action_min;
	}
	
	/**
	 * Converts a state into its index
	 * @param n the state
	 * @return the index of the state
	 */
	public int stateToIndex(double n){
		return (int)(n/CFG.maxN*(double)CFG.states);
	}
}
