import java.awt.Color;
import java.awt.Graphics;

public class Grid implements Drawable{
	
	private final int dimension = 5;
	private Node[][] grid;
	
	public Grid(){
		grid = new Node[dimension][dimension];
		for(int i = 0; i < dimension; i++){
			for(int j = 0; j < dimension; j++){
				grid[i][j] = new Node(500 + 100 * i, 250 + 100 * j);
			}
		}
		
		grid[0][0].setState(1);
		grid[4][4].setState(2);
	}
	
	public Node[][] getGrid(){
		return grid;
	}
	
	public int gameOver(){
		if(grid[0][0].getState() == 2)
			return 2;
		else if(grid[4][4].getState() == 1)
			return 1;
		else
			return 0;
	}
	
	public boolean canAttack(Node n, int state){
		int x = (n.getLoc().x - 500)/100;
		int y = (n.getLoc().y - 250)/100;
		
		if(x != 0 && grid[x - 1][y].getState() == state)
			return true;
		else if(x != 4 && grid[x + 1][y].getState() == state)
			return true;
		else if(y != 0 && grid[x][y - 1].getState() == state)
			return true;
		else if(y != 4 && grid[x][y + 1].getState() == state)
			return true;
		else
			return false;
	}
	
	public boolean hasTarget(){
		for(Node[] nodeArray : grid){
			for(Node n : nodeArray){
				if(n.getAttacking())
					return true;
			}
		}
		return false;
	}
	
	public Node getTarget(){
		for(Node[] nodeArray : grid){
			for(Node n : nodeArray){
				if(n.getAttacking())
					return n;
			}
		}
		return null;
	}
	
	public void clear(){
		for(Node[] nodeArray : grid){
			for(Node n : nodeArray){
				n.setNotAttacking();
			}
		}
	}

	@Override
	public void draw(Graphics g, Panel p) {
		for(int i = 0; i < dimension; i++){
			for(int j = 0; j < dimension; j++){
				g.setColor(Color.GRAY);
				if(i < dimension - 1){
					g.fillRect(500 + 100 * i, 250 + 100 * j, 75, 5);
				}
				if(j < dimension - 1){
					g.fillRect(500 + 100 * i, 250 + 100 * j, 5, 75);
				}
				grid[i][j].draw(g, p);
			}
		}
	}

}
