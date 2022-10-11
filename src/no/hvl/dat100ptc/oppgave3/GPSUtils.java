package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max;

		max = da[0];

		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}

		return max;
	}

	public static double findMin(double[] da) {

		double min = da[0];

		for (double i : da) {

			if (i < min) {
				min = i;
			}

		}

		return min;

	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double[] latTab = new double[gpspoints.length];

		for (int i = 0; i < gpspoints.length; i++) {

			latTab[i] = (gpspoints[i].getLatitude());

		}

		return latTab;

	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double[] longTab = new double[gpspoints.length];

		for (int i = 0; i < gpspoints.length; i++) {

			longTab[i] = (gpspoints[i].getLongitude());
		}
		return longTab;
	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d = 0;
		double a = 0;
		double c = 0;
		double latitude1, longitude1, latitude2, longitude2;
		longitude1 = Math.toRadians(gpspoint1.getLongitude());
		latitude1 = Math.toRadians(gpspoint1.getLatitude());
		longitude2 = Math.toRadians(gpspoint2.getLongitude());
		latitude2 = Math.toRadians(gpspoint2.getLatitude());

		a = Math.pow((Math.sin(latitude2 - latitude1) / 2), 2)
				+ Math.cos(latitude1) * Math.cos(latitude2) * Math.pow((Math.sin(longitude2 - longitude1) / 2), 2);

		c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		d = R * c;
		return d;

	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;
		double distance = distance(gpspoint1, gpspoint2);
		secs = (gpspoint2.getTime() - gpspoint1.getTime());
		speed = distance / secs * 3.6;

		return speed;
	}

	public static String formatTime(int secs) {

		String timestr;
		String TIMESEP = ":";

		int timer = (secs / 3600);
		int minutter = (secs % 3600) / 60;
		int sekunder = (secs % 3600) % 60;

		if (timer < 10) {
			timestr = "  0" + timer;
		} else {
			timestr = "  " + timer;
		}

		timestr += TIMESEP;

		if (minutter < 10) {
			timestr += "0" + minutter;
		} else {
			timestr += "" + minutter;
		}

		timestr += ":";

		if (sekunder < 10) {
			timestr += "0" + sekunder;
		} else {
			timestr += "" + sekunder;
		}
		return timestr;

	}

	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		double avrundetTall = Math.round(d * 100d) / 100d;
		String str = "";

		int antallPositiveTall = 0;
		int tellar = (int) avrundetTall;

		while (tellar % 10 != 0) {
			tellar = tellar / 10;
			antallPositiveTall++;
		}

		int toDesimaler = 2;
		int punktum = 1;
		int ubruktPlass = TEXTWIDTH - (antallPositiveTall + punktum + toDesimaler);

		for (int i = 0; i < ubruktPlass; i++) {
			str += " ";
		}

		str += avrundetTall;
		return str;
	}
}
