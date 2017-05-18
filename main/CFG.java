package main;

/**
 * Game framework
 * CFG.java
 * Configuration file to store global parameters
 * 
 * @author Friedrich Burkhard von der Osten
 * @version 2.0
 */

public class CFG {
	public static int run = 1;
	public static String gtype = "cprg";
	public static String atype = "rnd";
	
	public static int rounds = 1000;
	public static int cround = 1;
	public static int games = 1;
	public static int gid = 0;
	public static int agents = 5;
	public static int aid = 0;
	public static boolean gui = true;
	
	/*
	 * CPRG
	 */
	public static double maxN = 1000.0; // maximum carrying capacity
	public static double ah = 0.4; // harvest factor
	public static double bh = 0.35; 
	public static double rg = 0.5; // resource growing rate
	
	public static double action_max = 500.0; //500
	public static double action_min = 75; //100
	
	public static double initA = 500.0; // initial assets
	public static double cost = 0.5;
		
	public static int actions = 20; // 20
	public static int states = 6; // 5
	
}
