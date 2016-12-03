import java.awt.Font;
import java.awt.Graphics;

public class StatsTracker implements Drawable{
	
	private int solved, solvedEasy, solvedMedium, solvedHard;
	private int bitcoinsMax;
	private int nodes;
	private double avg;
	
	public StatsTracker(){
		solved = -1;
		solvedEasy = -1;
		solvedMedium = 0;
		solvedHard = 0;
		bitcoinsMax = -1;
		nodes = 0;
	}
	
	public void increaseSolved(){
		solved++;
	}
	
	public void increaseSolvedEasy(){
		solvedEasy++;
	}
	
	public void increaseSolvedMedium(){
		solvedMedium++;
	}
	
	public void increaseSolvedHard(){
		solvedHard++;
	}
	
	public void increaseNodes(){
		nodes++;
	}
	
	public int getSolved(){
		return solved;
	}
	
	public void setBitcoinsMax(int i){
		bitcoinsMax = i;
	}
	
	public int getBitcoinsMax(){
		return bitcoinsMax;
	}

	@Override
	public void draw(Graphics g, Panel p) {
		g.setFont(new Font("Arial", Font.PLAIN, 30));
		g.drawString("Total problems solved: " + solved, 450, 120);
		g.drawString("Easy problems solved: " + solvedEasy, 475, 170);
		g.drawString("Medium problems solved: " + solvedMedium, 475, 220);
		g.drawString("Hard problems solved: " + solvedHard, 475, 270);
		g.drawString("Max bitcoins stockpiled: " + bitcoinsMax, 450, 370);
		
		if(bitcoinsMax != 0)
			avg = (double) solved/bitcoinsMax;
		else
			avg = 0;
		
		g.drawString("Average bitcoins/problem: " + Double.valueOf(avg), 475, 420);
		g.drawString("Nodes captured: " + nodes, 450, 520);
	}

}
