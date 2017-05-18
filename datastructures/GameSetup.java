package datastructures;

import main.CFG;

public class GameSetup {

	public int agents;
	public double ah;
	public double bh;
	public double rg;
	
	public double xmin;
	public double xmax;
	public double nmax;
	public double inita;
	public double initn;
	public double cost;

	public GameSetup() {
		agents = CFG.agents;
		ah = CFG.ah;
		bh = CFG.bh;
		rg = CFG.rg;
		
		xmin = CFG.action_min;
		xmax = CFG.action_max;
		nmax = CFG.maxN;
		inita = CFG.initA;
		cost = CFG.cost;
	}

}
