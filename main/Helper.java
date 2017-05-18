package main;

import implementations.Agent_Learn;
import implementations.Agent_RND;
import implementations.Game_CPRG;
import interfaces.Agent;
import interfaces.Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import datastructures.GameSetup;

/**
 * Game framework
 * Helper.java
 * Helper class with various useful global functions
 * 
 * @author Friedrich Burkhard von der Osten
 * @version 2.0
 */

public class Helper {
	
	/**
	 * Initializes a new Game of type gtype and fills it with Agents of type atype
	 * @param gtype Game type / name
	 * @param atype Agent type / name
	 * @return new Game of type gtype with Agents of type atype
	 */
	public static Game initializeGame(String gtype, String atype, GameSetup setup){
		Game game = null;
		if(gtype.equals("cprg")){
			game = new Game_CPRG(atype, setup);
		}
		else {
			System.out.println("Error, no game specified");
		}
		return game;
	}

	/**
	 * Initializes a new Agent of type atype
	 * @param atype Agent type / name
	 * @param gid ID of the game the agent is part of
	 * @return new Agent of type atype
	 */
	public static Agent initializeAgent(String atype, int gid){
		Agent agent = null;
		if(atype.equals("rnd")){
			agent = new Agent_RND(gid);
		}
		else if (atype.equals("learn")){
			agent = new Agent_Learn(gid);
		}
		else{
			System.out.println("Error, no agent specified");
		}
		return agent;
	}
	
	/**
	 * Writes a given list of values to disk
	 * @param filename Name of the file the data is written to
	 * @param vals List of double values to be written to disk
	 * @throws Exception for IO errors
	 */
	public static void writeListToFile(String filename, ArrayList<Double> vals) throws Exception {
		PrintWriter writer = new PrintWriter(filename, "UTF-8");
		for(int i = 0; i < vals.size(); i++){
			String roundNo = String.format("round %d",i );
			writer.println(roundNo + "," + vals.get(i) );
		}
		writer.close();
	}	
	
	public static void readSetupFromFile(String fn) throws Exception{
		FileReader fr = new FileReader(fn);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while((line = br.readLine()) != null){
			StringTokenizer st = new StringTokenizer(line, ",");
			String fname = st.nextToken();
			String fvalue = st.nextToken();
			if(fname.equals("run")){
				CFG.run = Integer.parseInt(fvalue);
			}
			if(fname.equals("gtype")){
				CFG.gtype = fvalue;
			}
			if(fname.equals("atype")){
				CFG.atype = fvalue;
			}
			if(fname.equals("rounds")){
				CFG.rounds = Integer.parseInt(fvalue);
			}
			if(fname.equals("games")){
				CFG.games = Integer.parseInt(fvalue);
			}
			if(fname.equals("agents")){
				CFG.agents = Integer.parseInt(fvalue);
			}
			if(fname.equals("gui")){
				CFG.gui = Boolean.parseBoolean(fvalue);
			}
			/*------------------------------------------*/
			if(fname.equals("rg")){
				CFG.rg = Double.parseDouble(fvalue);
			}
			if(fname.equals("cost")){
				CFG.cost = Double.parseDouble(fvalue);
			}
			if(fname.equals("actions")){
				CFG.actions = Integer.parseInt(fvalue);
			}
			if(fname.equals("states")){
				CFG.states = Integer.parseInt(fvalue);
			}
		}
		br.close();
	}
	
	/**
	 * Calculates the average of values in a list
	 * @param vals List of double values
	 * @return Average of all values in vals
	 */
	public static double avgDouble(ArrayList<Double> vals){
		int sz = vals.size();
		double avg = 0;
		for(Double d : vals){
			avg += d;
		}
		return (avg/sz);
	}
}
