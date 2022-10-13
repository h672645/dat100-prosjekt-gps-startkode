package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;

	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);

		showStatistics();
	}

	// antall x-pixels per lengdegrad    # of pixels per longitude
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon));

		
		return xstep;
	}

	// antall y-pixels per breddegrad    # of pixels per latitude
	public double ystep() {

		double ystep;

		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		ystep = MAPYSIZE / (Math.abs(maxlat - minlat));
		return ystep;
	}

	public void showRouteMap(int ybase) {
		
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		double[] x = new double[gpspoints.length];
		double[] y = new double[gpspoints.length];
		double xStep = xstep();
		double yStep = ystep();
		
		//just to track values
		System.out.println("xStep: " + xStep + "  " + "yStep: " + yStep); 
		
		for(int i = 0; i < x.length; i++) {
			
			x[i] = xStep * (gpspoints[i].getLongitude() - minlon);
			y[i] = yStep * (gpspoints[i].getLatitude() - minlat);
			
			//just to keep track of values while testing
			System.out.println(x[i] + "  " + y[i]);
		}
		
		for(int i = 0; i < gpspoints.length; i++) {
			
			int tempY = (int)(y[i]+0.5);
			int tempX = (int)(x[i]+0.5);
			setColor(0,255,0);
			
			if(i == gpspoints.length-1) {
				setColor(0,0,255);
				fillCircle(MARGIN + tempX, ybase - tempY, 5);
			} else {
			
			fillCircle(MARGIN + tempX, ybase - tempY, 3);
			}
		}
		
	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;
		int yPos = 0;

		setColor(0, 0, 0);
		setFont("Courier", 12);
		String[] info = new String[6];

		
		info[0] = "Total time        :" + GPSUtils.formatTime(gpscomputer.totalTime());
		info[1] = "Total distance :" + GPSUtils.formatDouble(gpscomputer.totalDistance()) + " km";
		info[2] = "Total elevation :" + GPSUtils.formatDouble(gpscomputer.totalElevation()) + " m";
		info[3] = "Max speed       :" + GPSUtils.formatDouble(gpscomputer.maxSpeed()) + " km/t";
		info[4] = "Average speed:" + GPSUtils.formatDouble(gpscomputer.averageSpeed()) + " km/t";
		info[5] = "Energy              :" + GPSUtils.formatDouble(gpscomputer.totalKcal(80.0)) + " kcal";

		for (int i = 0; i < info.length; i++) {
			yPos = yPos + TEXTDISTANCE;
			drawString(info[i], MARGIN, yPos);

		}
		

	}

}
