package com.his.rt.flightsimulation.constants;
/**
* @author Namitha Moothedathu Kalesh
* 
*/


public class FlightConstants {
	
	/*Flight type*/
	public static final int FLIGHT_TYPE_ARRIVAL = 1;
	public static final int FLIGHT_TYPE_DEPARTURE = 2;
	
	/*Flight status*/
	public final static int FLIGHT_STATUS_READY = 1;
	public final static int FLIGHT_STATUS_EN_ROUTE = 2;
	public final static int FLIGHT_STATUS_WAITING = 3;
	public final static int FLIGHT_STATUS_HOLDING = 4;
	public final static int FLIGHT_STATUS_COMPLETED = 5;
	public final static int FLIGHT_STATUS_CLIMB = 6;
	public final static int FLIGHT_STATUS_TAXI = 7;
	public final static int FLIGHT_STATUS_DESCEND = 8;
	
	
	/*Date Format*/
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
	
	
	public static String getFlightStatus(int flightStatus) {
		String status="";
		switch(flightStatus) {
		case FLIGHT_STATUS_COMPLETED:
			status="Completed";
			break;
		case FLIGHT_STATUS_HOLDING:
			status="In Holding Circle";
			break;
		case FLIGHT_STATUS_EN_ROUTE:
			status="En Route";
			break;
		case FLIGHT_STATUS_READY:
			status="Ready";
			break;
		case FLIGHT_STATUS_CLIMB:
			status="Departing";
			break;
		case FLIGHT_STATUS_DESCEND:
			status="Landing";
			break;
		case FLIGHT_STATUS_TAXI:
			status="Taxi";
			break;
		case FLIGHT_STATUS_WAITING:
			status="Waiting";
			break;
			
		}
		return status;
		
	}
}
