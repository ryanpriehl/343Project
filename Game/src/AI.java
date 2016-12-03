import java.util.ArrayList;
import java.util.Random;

public class AI {
	
	private Node target;
	
	public AI(){
		target = null;
	}
	
	public boolean hasTarget(){
		return target != null;
	}
	
	public void chooseNode(Grid g){
		ArrayList<Node> possible = new ArrayList<Node>();
		for(Node[] nodeArray : g.getGrid()){
			for(Node n : nodeArray){
				if(g.canAttack(n, 2)){
					possible.add(n);
				}
			}
		}
		Random rand = new Random();
		boolean found = false;
		while(!found){
			target = possible.get(rand.nextInt(possible.size() - 1));
			if(target.getState() != 2)
				found = true;
		}
		
	}
	
	public void attackNode(){
		target.reduceHealth();
		if(target.getHealth() <= 0){
			target.setState(2);
			target = null;
		}
			
	}

}
