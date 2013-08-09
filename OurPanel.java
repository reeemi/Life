package GameOfLife;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class OurPanel extends JPanel {
	boolean alive;
	
	Color aliveColour = Color.green;
	
	Color deadColour = Color.black;
	
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

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		if(alive){
			g.setColor(aliveColour);
		}else{
			g.setColor(deadColour);
		}
		g.fillRect(0, 0, 20, 20);
	}


}
