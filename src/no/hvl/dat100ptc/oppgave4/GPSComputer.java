package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {

	private GPSPoint[] gpspoints;

	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}

	// beregn total distances (i meter)
	public double totalDistance() {

		double distance = 0;
		GPSPoint[] gpspoints = this.gpspoints;

		for (int i = 1; i < gpspoints.length; i++) {

			distance += GPSUtils.distance(gpspoints[i - 1], gpspoints[i]);

		}

		return distance;
	}

	// beregn totale høydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;
		GPSPoint[] gpspoints = this.gpspoints;

		for (int i = 0; i < gpspoints.length; i++) {

			double høgde = gpspoints[i].getElevation();

			if (høgde > elevation) {
				elevation += (høgde - elevation);
			}
		}

		return elevation;
	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {

		GPSPoint[] gpspoints = this.gpspoints;
		int totalTid = 0;

		for (int i = 1; i < gpspoints.length; i++) {

			int tid = gpspoints[i].getTime();

			totalTid = totalTid + (tid - gpspoints[i - 1].getTime());

		}

		return totalTid;
	}

	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {

		GPSPoint[] gpspoints = this.gpspoints;
		double[] speed = new double[gpspoints.length - 1];

		for (int i = 1; i < gpspoints.length; i++) {

			double hastighet = GPSUtils.speed(gpspoints[i - 1], gpspoints[i]);

			speed[i - 1] = hastighet;
		}

		return speed;
	}

	public double maxSpeed() {

		GPSPoint[] gpspoints = this.gpspoints;
		double maxSpeed = 0;

		for (int i = 1; i < gpspoints.length; i++) {

			double hastighet = GPSUtils.speed(gpspoints[i - 1], gpspoints[i]);
			if (hastighet > maxSpeed) {
				maxSpeed = hastighet;
			}

		}

		return maxSpeed;

	}

	public double averageSpeed() {

		double average = 0;

		average = totalDistance() / totalTime() * 3.6;

		return average;

	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling, general
	 * 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0 bicycling,
	 * 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9 mph, racing or
	 * leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph, racing/not drafting
	 * or >19 mph drafting, very fast, racing general 12.0 bicycling, >20 mph,
	 * racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjøres med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal;

		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)
		double met = 0;
		double speedmph = speed * MS;
		double tid = secs;

		if (speedmph < 10) {
			met = 4.0;
		} else if (speedmph >= 10 && speedmph <= 12) {
			met = 6.0;
		} else if (speedmph >= 12 && speedmph <= 14) {
			met = 8.0;
		} else if (speedmph >= 14 && speedmph <= 16) {
			met = 10.0;
		} else if (speedmph >= 16 && speedmph <= 20) {
			met = 12.0;
		} else if (speedmph >= 20) {
			met = 16.0;
		}

		kcal = (double) met * (double) weight * (secs / 3600.0);

		return kcal;

	}

	public double totalKcal(double weight) {

		double totalkcal = 0;
		double[] speeds = speeds();
		int[] tider = new int[speeds.length];

		GPSPoint[] gpspoints = this.gpspoints;

		for (int i = 1; i < gpspoints.length; i++) {

			int tid = gpspoints[i].getTime();
			tider[i - 1] = (tid - gpspoints[i - 1].getTime());
		}

		for (int i = 0; i < tider.length; i++) {

			totalkcal += (double) kcal(weight, tider[i], speeds[i]);
		}

		return totalkcal;
	}

	private static double WEIGHT = 80.0;

	public void displayStatistics() {

		System.out.println("==============================================");
		System.out.println("Total time     :" + GPSUtils.formatTime(totalTime()));
		System.out.println("Total distance :" + GPSUtils.formatDouble(totalDistance()) + " km");
		System.out.println("Total elevation:" + GPSUtils.formatDouble(totalElevation()) + " m");
		System.out.println("Max Speed      :" + GPSUtils.formatDouble(maxSpeed()) + " km/t");
		System.out.println("Average Speed  :" + GPSUtils.formatDouble(averageSpeed()) + " km/t");
		System.out.println("Energy         :" + GPSUtils.formatDouble(totalKcal(WEIGHT)) + " kcal");
		System.out.println("==============================================");
	}

	public double[] climbs() {

		double[] hoyder = new double[gpspoints.length];
		double[] distance = new double[gpspoints.length - 1];
		double[] climbs = new double[gpspoints.length - 1];

		for (int i = 1; i < climbs.length; i++) {

			hoyder[i] = gpspoints[i].getElevation() - gpspoints[i - 1].getElevation();
			distance[i] = GPSUtils.distance(gpspoints[i - 1], gpspoints[i]);
			System.out.println("høydeforskjell" + hoyder[i - 1]);
			System.out.println("distance" + distance[i - 1]);

			double hoyderPow = Math.pow(hoyder[i], 2);
			double distancePow = Math.pow(distance[i], 2);
			double hyp = Math.sqrt(hoyderPow + distancePow);

			climbs[i - 1] = (Math.asin(hoyder[i] / hyp)) * 100;
			System.out.println(climbs[i - 1]);
		}

		return climbs;
		
	}

	public double maxClimb() {

		double max = 0;
		double[] maxClimb = climbs();
		
		for(double i: maxClimb) {
			if( i > max) {
				max = i;
			}
		}
	
		return max;
	}

}
