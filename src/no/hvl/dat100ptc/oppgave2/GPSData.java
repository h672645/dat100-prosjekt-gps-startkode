package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSData {

	private GPSPoint[] gpspoints;
	protected int antall = 0;

	public GPSData(int n) {

		gpspoints = new GPSPoint[n];
		antall = 0;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	protected boolean insertGPS(GPSPoint gpspoint) {

		boolean sjekk;
		if(antall < this.gpspoints.length) {
			gpspoints[antall] = gpspoint;
			sjekk = true;
		} else {
			sjekk = false;
		}
		antall++;
		return sjekk;
	}

	public boolean insert(String time, String latitude, String longitude, String elevation) {
		
		GPSPoint gpspoint = GPSDataConverter.convert(time, latitude, longitude, elevation);
		
		boolean sjekk = insertGPS(gpspoint);
		return sjekk;
	}

	public void print() {

		System.out.println("====== Konvertert GPS Data - START ======");

		for(int i = 0; i < gpspoints.length; i++) {
			System.out.println(gpspoints[i].toString());
		}
		
		System.out.println("====== Konvertert GPS Data - SLUTT ======");

	}
}
