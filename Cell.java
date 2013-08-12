package GameOfLife;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;


public class Cell extends JPanel implements MouseListener {

//------------------------------VARIABLES------------------------------------------//
	private boolean alive;
	
	private int x;
	
	private int y;
	
	private Color aliveColour = Color.WHITE;
	
	private Color deadColour = Color.BLACK;
	
//------------------------------CONSTRUCTOR----------------------------------------//
	Cell(int x, int y){
		this.addMouseListener(this);
		this.x = x;
		this.y = y;
	}
	
//--------------------------------METHODS------------------------------------------//
	public Color getAliveColour() {
		return aliveColour;
	}
	public void setAliveColour(Color aliveColour) {
		this.aliveColour = aliveColour;
	}
	public Color getDeadColour() {
		return deadColour;
	}
	public void setDeadColour(Color deadColour) {
		this.deadColour = deadColour;
	}
	
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
//-------------------------OVERRIDDEN JPANEL METHODS-------------------------------//
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(alive){
			g.setColor(aliveColour);
		}else{
			g.setColor(deadColour);
		}
		g.fillRect(0, 0, 20, 20);
	}
	

//-------------------------IMPLEMENTED MOUSELISTENER METHODS-----------------------//
	@Override
	public void mouseClicked(MouseEvent e) {
		Life.flipSquareState(this.x, this.y);
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}


}
