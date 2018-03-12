import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class RouteDisplayPanel extends JPanel {
	private ArrayList<Point> path;
	private File inputFile;
	private File mapFile;
	private BufferedImage rawImage;
	public RouteDisplayPanel(File input, File map) {
		
		inputFile = input;
		mapFile = map;
		
		path = new ArrayList<Point>();
	
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension threeQuarterSize = new Dimension((int)(0.75 * screenSize.getWidth()), (int)(0.75 * screenSize.getHeight()));
		setPreferredSize(threeQuarterSize);
		
	}
	public void loadPath() throws FileNotFoundException {
		Scanner scan = new Scanner(inputFile);
		while(scan.hasNextInt()) {
			int x = scan.nextInt();
			int y = scan.nextInt();
			
			Point nextPoint = new Point(x,y);
			path.add(nextPoint);
		}
		scan.close();
	}
	public void loadMapImage() throws IOException {
			rawImage = ImageIO.read(mapFile);
	}
	public void drawRoute() {
		Graphics2D graphics = (Graphics2D)rawImage.getGraphics();
		
		
		
		
		
		graphics.setStroke(new BasicStroke(8));
		graphics.setColor(Color.MAGENTA);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		for(int index = 0;index<path.size()-1;index++) {
			Point start = path.get(index);
			Point end = path.get(index+1);
			graphics.drawLine((int)start.getX(),(int)start.getY(),(int)end.getX(),(int)end.getY());
		}
		
		
		
		graphics.setStroke(new BasicStroke(4));
		graphics.setColor(Color.GREEN);
		Point base = path.get(0);
		graphics.drawLine((int)base.getX(), (int)base.getY(), (int)base.getX(), (int)base.getY()-50);
		graphics.fillPolygon(new int[]{(int)base.getX(), (int)base.getX(),(int)base.getX() + 40},
				new int[]{(int)base.getY() - 30,(int)base.getY() - 50,(int)base.getY() - 40},3); 
		
		graphics.setColor(Color.RED);
		base = path.get(path.size()-1);
		graphics.drawLine((int)base.getX(), (int)base.getY(), (int)base.getX(), (int)base.getY()-50);
		graphics.fillPolygon(new int[]{(int)base.getX(), (int)base.getX(),(int)base.getX() + 40},
				new int[]{(int)base.getY() - 30,(int)base.getY() - 50,(int)base.getY() - 40},3);
		
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);  
		
		g2.drawImage(rawImage,0,0,this.getWidth(),this.getHeight(),null);
		
	}
	
}
