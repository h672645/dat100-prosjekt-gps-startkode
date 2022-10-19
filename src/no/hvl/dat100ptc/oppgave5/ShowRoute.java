package no.hvl.dat100ptc.oppgave5;

import static javax.swing.JOptionPane.showInputDialog;

import javax.swing.JOptionPane;
import static java.lang.String.*;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 1000;
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

		showStatistics();
		
		showRouteMap(MARGIN + MAPYSIZE);

	}

	// antall x-pixels per lengdegrad # of pixels per longitude
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon));

		return xstep;
	}

	// antall y-pixels per breddegrad # of pixels per latitude
	public double ystep() {

		double ystep;

		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		ystep = MAPYSIZE / (Math.abs(maxlat - minlat));
		return ystep;
	}

	public void showRouteMap(int ybase) {

		double[] speeds = gpscomputer.speeds();
<<<<<<< HEAD
		
=======
		System.out.println(speeds.length);
		System.out.println(gpspoints.length);

>>>>>>> b3a1f15040c5525d96ed54bb4d5f29882e74f410
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		double[] x = new double[gpspoints.length];
		double[] y = new double[gpspoints.length];
		double xStep = xstep();
		double yStep = ystep();
		int xTellar = 0;

		for (int i = 0; i < x.length; i++) {

			x[i] = xStep * (gpspoints[i].getLongitude() - minlon);
			y[i] = yStep * (gpspoints[i].getLatitude() - minlat);

		}
		
		setColor(0, 0, 255);
		int a = circle((int) (x[0] + 0.5), ybase - (int) (y[0] + 0.5),3);
		
		for (int i = 0; i < gpspoints.length; i++) {
			int tempY = (int) (y[i] + 0.5);
			int tempX = (int) (x[i] + 0.5);
			if (x[i] == x[gpspoints.length - 1]) {
				setColor(0, 0, 255);
				fillCircle(MARGIN + tempX, ybase - tempY, 5);
			} else {
				setColor(0, 255, 0);
				fillCircle(MARGIN + tempX, ybase - tempY, 2);
			}

			moveCircle(a, MARGIN + tempX , ybase - tempY);

			if (i > 0) {
				int tempYForige = (int) (y[i - 1] + 0.5);
				int tempXForige = (int) (x[i - 1] + 0.5);

				if (gpspoints[i].getElevation() > gpspoints[i - 1].getElevation()) {
					setColor(255, 0, 0);
					drawLine(MARGIN + tempX, ybase - tempY, MARGIN + tempXForige, ybase - tempYForige);
				} else {
					setColor(0, 255, 0);
					drawLine(MARGIN + tempX, ybase - tempY, MARGIN + tempXForige, ybase - tempYForige);
				}

				int speedGraph = (int) (speeds[i - 1]);
				drawLine(650 + xTellar, 75, 650 + xTellar, 75 - speedGraph);
				xTellar += 1;
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

	public int circle(int centerX, int centerY, int radius) {

		int circle = fillCircle(MARGIN + centerX, centerY, radius);

		return circle;
	}
	
}
