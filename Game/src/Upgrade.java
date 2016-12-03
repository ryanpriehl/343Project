import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.GrayFilter;

public class Upgrade implements Drawable{
	
	private BufferedImage image;
	private Image imageGS;
	private boolean bought;
	private Point loc;
	private int cost;

	public Upgrade(BufferedImage im, Point p, int c){
		image = im;
		GrayFilter gf = new GrayFilter(true, 100);
		imageGS = gf.createDisabledImage(image);
		bought = false;
		loc = p;
		cost = c;
	}

	@Override
	public void draw(Graphics g, Panel p) {
		if(bought){
			g.drawImage(image, loc.x, loc.y, p);
		}
		else{
			g.drawImage(imageGS, loc.x, loc.y, p);
		}
		
	}
}
