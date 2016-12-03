import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Node implements Drawable{
	
	private Point loc;
	private int state, health;
	private boolean attacking;

	public Node(int x, int y){
		state = 0;
		loc = new Point(x, y);
		health = 1;
		attacking = false;
	}
	
	public void setState(int i){
		state = i;
		if(i == 0)
			health = 1;
		else if(i == 1 || i == 2)
			health = 3;
	}
	
	public void reduceHealth(){
		health--;
	}
	
	public int getHealth(){
		return health;
	}
	
	public Point getLoc(){
		return loc;
	}
	
	public void setAttacking(){
		attacking = true;
	}
	
	public void setNotAttacking(){
		attacking = false;
	}
	
	public boolean getAttacking(){
		return attacking;
	}
	
	public boolean containsPoint(Point p){
		Rectangle rect = new Rectangle(loc.x - 25, loc.y - 25, 50, 50);
		return rect.contains(p);
	}
	
	public int getState(){
		return state;
	}

	@Override
	public void draw(Graphics g, Panel p) {
		if(state == 0){
			g.setColor(Color.GRAY);
		}
		else if(state == 1){
			g.setColor(Color.GREEN);
		}
		else if(state == 2){
			g.setColor(Color.RED);
		}
		
		if(attacking){
			g.setColor(Color.YELLOW);
		}
		
		g.fillOval(loc.x - 25, loc.y - 25, 50, 50);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 30));
		g.drawString(String.valueOf(health), loc.x - 8, loc.y + 10);
		
	}

}
