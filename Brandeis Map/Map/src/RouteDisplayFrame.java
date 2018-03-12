

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;


public class RouteDisplayFrame extends JFrame{
	
	private RouteDisplayPanel routeDisplay;
	public RouteDisplayFrame(File routeFile, File mapFile) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Map Viewer");
		
		routeDisplay = new RouteDisplayPanel(routeFile,mapFile);
		
		this.getContentPane().add(routeDisplay);
		
	}
	public void load() {
		try {
			routeDisplay.loadPath();
		} catch (FileNotFoundException e) {
		
			System.err.println("COULD NOT FIND ROUTE FILE!");
			e.printStackTrace();
			System.exit(1);
			
		} catch (Exception e) {
			
			System.err.println("ERROR READING ROUTE FILE!");
			e.printStackTrace();
			System.exit(1);
			
		}
		try {
			routeDisplay.loadMapImage();
		} catch (IOException e) {
			
			System.out.println("ERROR READING MAP FILE!");
			e.printStackTrace();
			System.exit(1);
			
		}
	}
	public void display() {
		routeDisplay.drawRoute();
		pack();
		setVisible(true);
	}

	
}
