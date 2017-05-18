package main;

import gui.Visualization;
import interfaces.Agent;
import interfaces.Game;

import java.util.ArrayList;

import javax.swing.JFrame;

import datastructures.GameSetup;

/**
 * Game framework Main.java Runs the main game loop and coordinates data
 * collection and visualisation
 * 
 * @author Friedrich Burkhard von der Osten
 * @version 2.0
 */
public class Main {

	/**
	 * @param args
	 *            Various parameters to be stored in CFG and used to run the
	 *            simulation, can be customized
	 * 
	 * @throws Exception
	 *             for IO errors
	 */
	public static void main(String[] args) throws Exception {
		// Helper.readSetupFromFile("cfg.txt");

		// Uncomment this block if you want to run externally with parameters
		/*
		 * CFG.run = Integer.parseInt(args[0]); CFG.gtype = args[1]; // "cprg"
		 * CFG.atype = args[2]; // "rnd" CFG.games = Integer.parseInt(args[3]);
		 * CFG.agents = Integer.parseInt(args[4]); CFG.rg =
		 * Double.parseDouble(args[5]); CFG.cost = Double.parseDouble(args[6]);
		 */

		CFG.action_max /= CFG.agents;
		CFG.action_min /= CFG.agents;

		ArrayList<Game> games = new ArrayList<Game>();
		GameSetup setup;
		for (int g = 0; g < CFG.games; g++) {
			setup = new GameSetup();
			games.add(Helper.initializeGame(CFG.gtype, CFG.atype, setup));
		}

		JFrame frame;
		Visualization panel = null;
		if (CFG.gui) {
			frame = new JFrame("Game framework: " + CFG.gtype + ", " + CFG.atype);
			frame.setSize(1020, 520);
			frame.setLocation(5, 5);
			frame.setResizable(false);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			panel = new Visualization(1000, 500);
			panel.setGames(games);
			frame.setContentPane(panel);
		}

		/*
		 * Main game loop
		 */
		long t_start = System.nanoTime();
		for (int i = 1; i < CFG.rounds; i++) {
			for (Game g : games) {
				g.playRound(games);
			}
			if (CFG.gui) {
				panel.repaint();
			}
			CFG.cround++;
		}
		long t_end = System.nanoTime();
		System.out.println("Time elapsed: " + (t_end - t_start) / 1000000000.0 + " second(s)");

		for (Game g : games) {
			// Optional output to file
			String fn = String.format("%s_%dg_%dag_%srg_%sc_run%d.csv", CFG.atype, g.getID(), CFG.agents, CFG.rg,
					CFG.cost, CFG.run);
			String fnn = "resource_" + fn;
			String fna = "average_" + fn;
			//Helper.writeListToFile(fnn, games.get(0).getGameStateHistory());
			Helper.writeListToFile(fnn, g.getGameStateHistory());
			Helper.writeListToFile(fna, g.getAverageLongTermValue());
			for (int i = 0; i < CFG.agents; i++) {
				String agentNo = String.format("agent_%d_asset.csv", g.getAgents().get(i).getID());
				Helper.writeListToFile(agentNo, g.getAgents().get(i).getAssetHistory());
			}
		}
	}

}
