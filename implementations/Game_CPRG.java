package implementations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import datastructures.GameSetup;

import main.CFG;
import main.Helper;

import interfaces.Agent;
import interfaces.Game;

/**
 * Game framework
 * Game_CPRG.java
 * Implementation of the common pool resource game
 * 
 * @author Friedrich Burkhard von der Osten
 * @version 2.0
 */

public class Game_CPRG implements Game {
	
	private int id;
	private GameSetup setup;
	private Random rnd;
	
	private ArrayList<Agent> agents;
	private ArrayList<Double> gameStates;
	private ArrayList<Double> ltv;
	private ArrayList<Double> actionhist;
	
	private double N;
	private double G;
	private double H;
	private double X;
	
	ArrayList<Double> gs;
	ArrayList<Double> hs;
	
	public Game_CPRG(String atype, GameSetup s){
		setup = s;
		id = CFG.gid;
		CFG.gid++;
		agents = new ArrayList<Agent>();
		for(int a = 0; a < setup.agents; a++){
			agents.add(Helper.initializeAgent(atype, id));
		}
		N = 0.5*s.nmax;
		gameStates = new ArrayList<Double>();
		gameStates.add(N);
		ltv = new ArrayList<Double>();
		actionhist = new ArrayList<Double>();
		rnd = new Random();
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	/**
	 * Coordinates one round of the common pool resource game
	 * -calculates harvest, growth and change of resource
	 * -lets agents learn
	 * @param games The list of all games being played in the current instance of the simulation
	 */
	public void playRound(ArrayList<Game> games) {
		X = 0;
		for(Agent a : agents){
			X += a.playAction(games);
		}
		actionhist.add(X); //total effort
		
		H = calcHarvest(X, N); //total harvest
		for(Agent a : agents){
			a.receiveReward((a.getActionPlayed()/X) * H);
		}
		
		G = calcGrowth(N);
		N = Math.abs(N - H + G);
		gameStates.add(N);
		
		for(Agent a : agents){
			a.learn(games);
		}
		
		double avgassets = 0;
		for(Agent a : agents){
			avgassets += a.getLongTermValue();
		}
		avgassets /= agents.size();
		ltv.add(avgassets);
	}

	@Override
	public ArrayList<Agent> getAgents() {
		return agents;
	}

	@Override
	public double getGameState() {
		return N;
	}
	
	public double calcHarvest(double effort, double resource){
		double h = setup.bh * Math.pow(effort, setup.ah) * Math.pow(resource, 1-setup.ah);
		return h;
	}
	
	public double calcGrowth(double resource){
		double g = setup.rg * resource * (1 - (resource/CFG.maxN));
		return g;
	}

	@Override
	public ArrayList<Double> getGameStateHistory() {
		return gameStates;
	}

	@Override
	public ArrayList<Double> getAverageLongTermValue() {
		return ltv;
	}

	@Override
	public double getCumulativeAction() {
		return X;
	}

	@Override
	public GameSetup getSetup() {
		return setup;
	}

	@Override
	public ArrayList<Double> getActionHistory() {
		return actionhist;
	}

	@Override
	public double getPreviousGameState() {
		return gameStates.get(gameStates.size()-2);
	}

}
