package gui;

import interfaces.Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import main.CFG;

/**
 * Game framework
 * Visualization.java
 * Plots a graph of the resource state in the common pool resource game
 * Optional, can be switched off by deactivating corresponding CFG entry
 * 
 * @author Friedrich Burkhard von der Osten
 * @version 2.0
 */

public class Visualization extends JPanel{
	
	private ArrayList<Game> games;
	private double wd;
	private double ht;
	
	private double maxy;
	private double maxy2;
	private double miny2;
	
	private int lx;
	private int hx;
	private int ly;
	private int hy;
	
	public Visualization(int w, int h){
		setPreferredSize(new Dimension(w, h));
		wd = w;
		ht = h;
		lx = (int)(0.05 * wd);
		hx = (int)(wd - 0.05 * wd);
		ly = (int)(ht - 0.05 * ht);
		hy = (int)(0.05 * ht);
		maxy = CFG.maxN;
		maxy2 = CFG.initA;
		miny2 = 0;
	}
	
	public void setGames(ArrayList<Game> g){
		games = g;
	}

	public void paintComponent(Graphics g) {		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		drawCoordinateSystem(g2);
		drawResource(g2);
	}
	
	public void drawCoordinateSystem(Graphics2D g2){		
		g2.drawLine(lx, ly, hx, ly);
		g2.drawLine(lx, ly, lx, hy);
		g2.drawString("Round "+CFG.cround, hx/2, ly-5);
		g2.drawLine((hx-lx)/2+lx, ly-5, (hx-lx)/2+lx, ly+5);
		g2.drawLine((hx-lx)/4+lx, ly-5, (hx-lx)/4+lx, ly+5);
		g2.drawLine((hx-lx)*3/4+lx, ly-5, (hx-lx)*3/4+lx, ly+5);
		g2.drawLine(hx, ly-5, hx, ly+5);
		g2.drawLine(lx-5, (hy-ly)/2+ly, lx+5, (hy-ly)/2+ly);
		g2.drawLine(lx-5, hy, lx+5, hy);
	}
	
	public void drawResource(Graphics2D g2){
		ArrayList<Double> gsh = games.get(0).getGameStateHistory();
		g2.setColor(Color.BLUE);//resource
		for(int i = 1; i < gsh.size(); i++){
				g2.drawLine((int)(lx+(i/(double)CFG.rounds)*(double)(hx-lx)), (int)(ly-(gsh.get(i)/maxy)*(ly-hy)),
						(int)(lx+((i-1)/(double)CFG.rounds)*(double)(hx-lx)), (int)(ly-(gsh.get(i-1)/maxy)*(ly-hy)));
		}
		g2.drawString(""+(int)maxy, 0, hy);
		g2.drawString("0", 0, ly);
		g2.drawString(""+gsh.get(gsh.size()-1).intValue(), hx, (ly-hy)/2);
		
		ArrayList<Double> ltv = games.get(0).getAverageLongTermValue();
		if(ltv.size() > 0){
			if(ltv.get(ltv.size()-1) > maxy2){
				maxy2 = ltv.get(ltv.size()-1);
			}
			if(ltv.get(ltv.size()-1) < miny2){
				miny2 = ltv.get(ltv.size()-1);
			}
		}
		g2.setColor(Color.RED);//average asset
		for(int i = 1; i < ltv.size(); i++){
			g2.drawLine((int)(lx+(i/(double)CFG.rounds)*(double)(hx-lx)), (int)(ly-((ltv.get(i)-miny2)/(maxy2-miny2))*(ly-hy)),
					(int)(lx+((i-1)/(double)CFG.rounds)*(double)(hx-lx)), (int)(ly-((ltv.get(i-1)-miny2)/(maxy2-miny2))*(ly-hy)));
		}
		g2.drawString(""+(int)maxy2, hx, hy);
		g2.drawString(""+(int)miny2, hx, ly);
		if(ltv.size() > 0){
			g2.drawString(""+ltv.get(ltv.size()-1).intValue(), 0, (ly-hy)/2);
		}
		
		ArrayList<Double> ah = games.get(0).getActionHistory();
		g2.setColor(Color.GREEN);//action
		for(int i = 1; i < ah.size(); i++){
			g2.drawLine((int)(lx+(i/(double)CFG.rounds)*(double)(hx-lx)), (int)(ly-(ah.get(i)/(CFG.action_max*CFG.agents))*(ly-hy)),
					(int)(lx+((i-1)/(double)CFG.rounds)*(double)(hx-lx)), (int)(ly-(ah.get(i-1)/(CFG.action_max*CFG.agents))*(ly-hy)));
		}
		if(ah.size() > 0){
			g2.drawString(""+ah.get(ah.size()-1).intValue(), hx/2, hy);
		}
	}
}
