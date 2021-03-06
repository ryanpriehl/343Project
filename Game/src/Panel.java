import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Panel extends JPanel implements KeyListener, MouseListener, Runnable {

	private String word = "";
	private String input = "";
	private boolean solved = true;
	private int bitcoins = -1;
	private int state = 0;
	private int level = 1;
	private int counter = 0;
	private Grid grid;
	private WordGenerator gen;
	private StatsTracker stats;
	private ArrayList<Achievement> achievements;
	private ArrayList<Upgrade> upgrades;
	private AI ai;

	public Panel() {
		grid = new Grid();
		gen = new WordGenerator();
		stats = new StatsTracker();
		achievements = new ArrayList<Achievement>();
		upgrades = new ArrayList<Upgrade>();
		ai = new AI();

		try {
			achievements.add(new Achievement(ImageIO.read(new File("Achievement 1.png")), new Point(440, 85)));
			achievements.add(new Achievement(ImageIO.read(new File("Achievement 2.jpg")), new Point(440, 220)));
			achievements.add(new Achievement(ImageIO.read(new File("Achievement 3.png")), new Point(440, 355)));
			achievements.add(new Achievement(ImageIO.read(new File("Achievement 4.jpg")), new Point(440, 490)));
			achievements.add(new Achievement(ImageIO.read(new File("Achievement 5.jpg")), new Point(440, 625)));
			achievements.add(new Achievement(ImageIO.read(new File("Achievement 6.jpeg")), new Point(720, 85)));
			achievements.add(new Achievement(ImageIO.read(new File("Achievement 7.jpg")), new Point(720, 220)));
			achievements.add(new Achievement(ImageIO.read(new File("Achievement 8.jpg")), new Point(720, 355)));
			achievements.add(new Achievement(ImageIO.read(new File("Achievement 9.jpg")), new Point(720, 490)));
			achievements.add(new Achievement(ImageIO.read(new File("Achievement 10.jpg")), new Point(720, 625)));

			upgrades.add(new Upgrade(ImageIO.read(new File("Upgrade 1.jpg")), new Point(1035, 85), 10));
			upgrades.add(new Upgrade(ImageIO.read(new File("Upgrade 2.jpg")), new Point(1035, 195), 20));
			upgrades.add(new Upgrade(ImageIO.read(new File("Upgrade 3.jpg")), new Point(1035, 305), 40));
			upgrades.add(new Upgrade(ImageIO.read(new File("Upgrade 4.jpg")), new Point(1035, 415), 30));
			upgrades.add(new Upgrade(ImageIO.read(new File("Upgrade 5.jpg")), new Point(1035, 525), 30));
			upgrades.add(new Upgrade(ImageIO.read(new File("Upgrade 6.jpg")), new Point(1035, 635), 80));
		} catch (IOException e) {
			e.printStackTrace();
		}

		setBounds(100, 100, 1400, 800);
		setBackground(Color.BLACK);

		addKeyListener(this);
		addMouseListener(this);
		setFocusable(true);
	}

	@Override
	public void run() {
		while (true) {
			repaint();
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		counter++;
		if (counter >= 90) {
			if (ai.hasTarget()) {
				ai.attackNode();
			} else {
				ai.chooseNode(grid);
			}
			if(upgrades.get(4).isBought()){
				bitcoins++;
			}
			counter = 0;
		}

		if (solved) {
			
			if (grid.hasTarget()) {
				grid.getTarget().reduceHealth();
				if (grid.getTarget().getHealth() <= 0) {
					stats.increaseNodes();
					grid.getTarget().setState(1);
					grid.clear();
				}
			}

			if (upgrades.get(3).isBought()) {
				bitcoins++;
			}

			stats.increaseSolved();
			if (level == 1) {
				stats.increaseSolvedEasy();
				bitcoins++;
				if(upgrades.get(5).isBought())
					bitcoins++;
			} else if (level == 2) {
				stats.increaseSolvedMedium();
				bitcoins += 2;
				if(upgrades.get(5).isBought())
					bitcoins += 2;
			} else if (level == 3) {
				stats.increaseSolvedHard();
				bitcoins += 3;
				if(upgrades.get(5).isBought())
					bitcoins += 3;
			}

			if (bitcoins > stats.getBitcoinsMax()) {
				stats.setBitcoinsMax(bitcoins);
			}

			if (stats.getSolved() == 10) {
				achievements.get(0).setAchieved();
			} else if (stats.getSolved() == 50) {
				achievements.get(1).setAchieved();
			} else if (stats.getSolved() == 100) {
				achievements.get(2).setAchieved();
			}

			if (stats.getBitcoinsMax() >= 100) {
				achievements.get(5).setAchieved();
			} else if (stats.getBitcoinsMax() >= 50) {
				achievements.get(4).setAchieved();
			} else if (stats.getBitcoinsMax() >= 10) {
				achievements.get(3).setAchieved();
			}

			if (stats.getNodes() == 1) {
				achievements.get(6).setAchieved();
			} else if (stats.getNodes() == 10) {
				achievements.get(7).setAchieved();
			} else if (stats.getNodes() == 25) {
				achievements.get(8).setAchieved();
			}

			try {
				word = gen.generateWord(level);
			} catch (FileNotFoundException e) {
			}

			solved = false;
		}

		if (input.equals(word)) {
			solved = true;
			input = "";
		}

		g.setColor(Color.CYAN);

		// main borders
		g.fillRect(0, 0, 1380, 10);
		g.fillRect(0, 0, 10, 760);
		g.fillRect(0, 752, 1385, 10);
		g.fillRect(1375, 0, 10, 760);
		g.fillRect(0, 300, 400, 10);

		// frame dividers
		g.fillRect(400, 0, 10, 760);
		g.fillRect(1000, 0, 10, 760);

		// tab dividers
		g.fillRect(400, 50, 985, 10);
		g.fillRect(600, 0, 10, 50);
		g.fillRect(800, 0, 10, 50);

		// various strings
		g.setFont(new Font("Arial", Font.BOLD, 25));
		g.drawString("Map", 480, 40);
		g.drawString("Stats", 675, 40);
		g.drawString("Achievements", 820, 40);
		g.drawString("Upgrades", 1130, 40);
		g.drawString("Current word: ", 50, 65);
		g.drawString("Current input: ", 50, 185);
		
		// difficulty settings
		g.drawString("Difficulty: ", 50, 365);
		if(level == 1){
			g.drawString("Easy", 170, 365);
		} else if(level == 2){
			g.drawString("Medium", 170, 365);
		}else if(level == 3){
			g.drawString("Hard", 170, 365);
		}
		
		g.drawRect(50, 385, 310, 100);
		g.drawString("Easy", 170, 445);
		if(upgrades.get(0).isBought()){
			g.drawRect(50, 505, 310, 100);
			g.drawString("Medium", 155, 565);
		}
		if(upgrades.get(1).isBought()){
			g.drawRect(50, 625, 310, 100);
			g.drawString("Hard", 170, 685);
		}

		g.setFont(new Font("Arial", Font.BOLD, 35));
		g.drawString(word, 50, 130);
		g.drawString(input, 50, 250);

		int upgCounter = 0;
		for (Upgrade u : upgrades) {
			if(u.isBought())
				upgCounter++;
			if(upgCounter >= 6)
				achievements.get(9).setAchieved();
			u.draw(g, this);
		}

		// what's displayed
		if (state == 0) {
			if(grid.gameOver() == 1){
				g.setFont(new Font("Arial", Font.BOLD, 60));
				g.drawString("YOU WIN!", 550, 180);
				try {
					g.drawImage(ImageIO.read(new File("FeelsGoodMan.png")), 425, 250, this);
				} catch (IOException e) {}
			} 
			else if(grid.gameOver() == 2){
				g.setFont(new Font("Arial", Font.BOLD, 60));
				g.drawString("YOU LOSE...", 530, 180);
				try {
					g.drawImage(ImageIO.read(new File("FeelsBadMan.png")), 430, 225, this);
				} catch (IOException e) {}
			}
			else{
				g.drawString("Bitcoins: " + bitcoins, 610, 150);
				grid.draw(g, this);
			}
			
		} else if (state == 1) {
			stats.draw(g, this);
		} else if (state == 2) {
			for (Achievement a : achievements) {
				a.draw(g, this);
			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point loc = e.getPoint();
		Rectangle tabRect = new Rectangle(410, 10, 590, 40);
		Rectangle upgRect = new Rectangle(1035, 85, 320, 630);
		Rectangle difRect = new Rectangle(50, 385, 310, 340);

		if (tabRect.contains(loc)) {
			Rectangle tab = new Rectangle(410, 10, 190, 40);
			if (tab.contains(loc)) {
				state = 0;
			}
			tab.setLocation(tab.x + 200, tab.y);
			if (tab.contains(loc)) {
				state = 1;
			}
			tab.setLocation(tab.x + 200, tab.y);
			if (tab.contains(loc)) {
				state = 2;
			}
		}

		else if (upgRect.contains(loc)) {
			Rectangle upg = new Rectangle(1035, 85, 320, 80);
			if (upg.contains(loc) && !upgrades.get(0).isBought() && upgrades.get(0).getCost() <= bitcoins) {
				upgrades.get(0).setBought();
				bitcoins -= upgrades.get(0).getCost();
			}
			upg.setLocation(upg.x, upg.y + 110);
			if (upg.contains(loc) && !upgrades.get(1).isBought() && upgrades.get(1).getCost() <= bitcoins) {
				upgrades.get(1).setBought();
				bitcoins -= upgrades.get(1).getCost();
			}
			upg.setLocation(upg.x, upg.y + 110);
			if (upg.contains(loc) && !upgrades.get(2).isBought() && upgrades.get(2).getCost() <= bitcoins) {
				upgrades.get(2).setBought();
				bitcoins -= upgrades.get(2).getCost();
				for (Node[] nodeArray : grid.getGrid()) {
					for (Node n : nodeArray) {
						if (n.getState() == 1) {
							n.addHealth();
						}
					}
				}
			}
			upg.setLocation(upg.x, upg.y + 110);
			if (upg.contains(loc) && !upgrades.get(3).isBought() && upgrades.get(3).getCost() < bitcoins) {
				upgrades.get(3).setBought();
				bitcoins -= upgrades.get(3).getCost();
			}
			upg.setLocation(upg.x, upg.y + 110);
			if (upg.contains(loc) && !upgrades.get(4).isBought() && upgrades.get(4).getCost() < bitcoins) {
				upgrades.get(4).setBought();
				bitcoins -= upgrades.get(4).getCost();
			}
			upg.setLocation(upg.x, upg.y + 110);
			if (upg.contains(loc) && !upgrades.get(5).isBought() && upgrades.get(5).getCost() < bitcoins) {
				upgrades.get(5).setBought();
				bitcoins -= upgrades.get(5).getCost();
			}
		}
		
		else if(difRect.contains(loc)){
			Rectangle dif = new Rectangle(50, 385, 310, 100);
			if(dif.contains(loc))
				level = 1;
			dif.setLocation(dif.x, dif.y + 120);
			if(dif.contains(loc) && upgrades.get(0).isBought())
				level = 2;
			dif.setLocation(dif.x, dif.y + 120);
			if(dif.contains(loc) && upgrades.get(1).isBought())
				level = 3;
		}

		else {
			for (Node[] nodeArray : grid.getGrid()) {
				for (Node n : nodeArray) {
					if (n.containsPoint(loc) && (n.getState() == 0 || n.getState() == 2) && grid.canAttack(n, 1)) {
						grid.clear();
						n.setAttacking();
					}
				}
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			if (input.length() >= 1)
				input = input.substring(0, input.length() - 1);
		} else if (Character.valueOf(e.getKeyChar()) instanceof Character && grid.hasTarget() && grid.gameOver() == 0) {
			input = input + Character.toUpperCase(e.getKeyChar());
		}

	}

	// unused listener methods

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}
