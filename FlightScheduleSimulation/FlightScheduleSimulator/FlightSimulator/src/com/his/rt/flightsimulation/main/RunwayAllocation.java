package com.his.rt.flightsimulation.main;
/**
* @author Namitha Moothedathu Kalesh
* 
*/


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

import com.his.rt.flightsimulation.bean.FlightBean;
import com.his.rt.flightsimulation.bean.RunwayBean;
import com.his.rt.flightsimulation.constants.AirportConstants;
import com.his.rt.flightsimulation.constants.FlightConstants;
import com.rt.gui.ChildPanel;
import com.rt.gui.SimulationScreen;

@SuppressWarnings("deprecation")
public class RunwayAllocation {
	private ArrayList<String> flightList;
	private ArrayList<FlightBean> arrivalList;
	private ArrayList<FlightBean> departureList;
	private ArrayList<FlightBean> totalList;
	private ArrayList<FlightBean> finalList;
	private ArrayList<RunwayBean> runwayList;
	private int runwayNo;
	private static ArrayList<FlightBean> taxiQueue;
	private static ArrayList<FlightBean> holdingQueue;
	private SimulationScreen simulationScreen;
	private Simulation simulation;
	private SystemClock systemClockTime;

	public RunwayAllocation(ArrayList<String> flightList, int runWayNo) {
		this.runwayNo = runWayNo;
		this.flightList = flightList;
		initialize();
		populateObjectList();
		sort();
		simulationScreen = new SimulationScreen(totalList, runWayNo);
		setTimerStartTime();
	}
	/*
	 * Initializes all the Arraylists
	 */

	private void initialize() {

		totalList = new ArrayList<FlightBean>();
		finalList = new ArrayList<FlightBean>();
		arrivalList = new ArrayList<FlightBean>();
		departureList = new ArrayList<FlightBean>();
		runwayList = new ArrayList<RunwayBean>();
		taxiQueue = new ArrayList<FlightBean>();
		holdingQueue = new ArrayList<FlightBean>();

		int runwayID = 0;
		while (runwayID < runwayNo) {
			RunwayBean runwayObject = new RunwayBean();
			runwayObject.setRunwayId(runwayID);
			runwayObject.setRunwayStatus(AirportConstants.RUNWAY_STATUS_FREE);
			runwayList.add(runwayObject);
			runwayID++;
		}
	}

	/*
	 * Populates the flight Infos to an array List
	 */
	private void populateObjectList() {
		Random random = new Random();
		for (String flightInfo : flightList) {
			FlightBean flightbean = new FlightBean();
			flightbean.setFlightStatus(FlightConstants.FLIGHT_STATUS_EN_ROUTE);
			String[] splitString = flightInfo.split(" ");
			flightbean.setFlightId(splitString[0]);
			// Dummy date;
			flightbean.setFlightDate("01-01-2018");
			// flightbean.setFlightName(splitString[1]);
			flightbean.setFlightTime(splitString[2]);
			flightbean.formatTime();
			flightbean.setHoldingTime(random.nextInt(5)+1);//Holding time is a random value between 10000-20000 ms
			flightbean.setglidingAndRunningTime(random.nextInt(100)+50);//Gliding time is a random value between 3000-5000 ms
			if (splitString[1].equalsIgnoreCase("arrival")) {
				flightbean.setFlightType(FlightConstants.FLIGHT_TYPE_ARRIVAL);
//				arrivalList.add(flightbean);
			} else if (splitString[1].equalsIgnoreCase("departure")) {
				flightbean.setFlightType(FlightConstants.FLIGHT_TYPE_DEPARTURE);
				System.out.println(flightbean.getFlightType());
//				departureList.add(flightbean);
			}
			totalList.add(flightbean);

		}

	}

	/*
	 * Sorts the flight lists based on time
	 */
	private void sort() {
		Collections.sort(arrivalList);
		Collections.sort(departureList);
		Collections.sort(totalList);
		//Assign index to each flights
		int index=0;
		for(FlightBean flight:totalList) {
			flight.setScheduledIndex(index++);
			if (flight.getFlightType() == FlightConstants.FLIGHT_TYPE_ARRIVAL)
				arrivalList.add(flight);
			else
				departureList.add(flight);
		}
	}

	/*
	 * Sets the start of the timer as the arrival time of the first flight
	 */
	public void setTimerStartTime() {
		setTimerView(totalList.get(0).getDateTime());
	}

	/*
	 * Sets the view for the timer
	 */
	@SuppressWarnings("deprecation")
	private void setTimerView(Date timerStartTime) {
		simulationScreen.setVisible(true);
		systemClockTime = new SystemClock(simulationScreen.getTimeLabel(), totalList, timerStartTime);

		// We get the flight ready notifications here
		systemClockTime.addObserver(new Observer() {
			public void update(Observable obj, Object arg) {
				FlightBean f = (FlightBean) arg;
				System.out.println("Flight Notification "+f.getFlightId()+" flightTime " +f.getFlightTime());
				for (FlightBean flight : totalList) {
					if (flight.getFlightId().equals(f.getFlightId())) {
						flight.setFlightStatus(FlightConstants.FLIGHT_STATUS_READY);
						// change the status in status table also
						int index = flight.getScheduledIndex();
						ChildPanel cP = simulationScreen.getChildPanelList().get(index);
						cP.getLblFlightStatus()
								.setText(FlightConstants.getFlightStatus(FlightConstants.FLIGHT_STATUS_READY));
						
						
					}

				}

				allocateRunway((FlightBean) arg);
			}
		});

		new Thread(systemClockTime).start();
		simulationScreen.setSystemClockTime(systemClockTime);
	}

	/*
	 * Allocates the flight to a runway if a runway is free If no runway is
	 * available, the flight is added to a queue
	 */
	private void allocateRunway(FlightBean flightObject) {
		boolean runwayFound = false;
		int index = flightObject.getScheduledIndex();
		ChildPanel cPanel = simulationScreen.getChildPanelList().get(index);
		for (RunwayBean runwayBean : runwayList) {
			if (runwayBean.getRunwayStatus() == AirportConstants.RUNWAY_STATUS_FREE) {
				runwayBean.setRunwayStatus(AirportConstants.RUNWAY_STATUS_OCCUPIED);
				runwayFound = true;
				changeRunwayColor(runwayBean.getRunwayId(), false);

				cPanel.getLblRunway().setText(getRunwayId(runwayBean.getRunwayId()));
				startSimulation(cPanel, runwayBean, flightObject.getFlightType(), flightObject);
				break;

			}
		}

		if (runwayFound == false) {
			if (flightObject.getFlightType() == FlightConstants.FLIGHT_TYPE_ARRIVAL) {
				flightObject.setFlightStatus(FlightConstants.FLIGHT_STATUS_HOLDING);
				cPanel.getLblFlightStatus().setText("On Hold");
				
				ArrayList<FlightBean> notificationList = systemClockTime.getFlightList();
				notificationList.remove(flightObject);
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(flightObject.getDateTime());
				calendar.add(Calendar.MINUTE, flightObject.getHoldingTime());
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				flightObject.setFlightTime(sdf.format(calendar.getTime()));
				flightObject.formatTime();
				
				
				notificationList.add(flightObject);
				Collections.sort(notificationList);
				systemClockTime.setFlightList(notificationList);
				
				holdingQueue.add(flightObject);
				simulationScreen.updateRowInArrivalQueue(flightObject);
				simulationScreen.getChildPanelList().get(flightObject.getScheduledIndex()).getLblRescheduledTime().setText(flightObject.getFlightTime());

			} else {
				flightObject.setFlightStatus(FlightConstants.FLIGHT_STATUS_TAXI);
				cPanel.getLblFlightStatus().setText("Taxi");
				taxiQueue.add(flightObject);
				simulationScreen.addFlightToQueueTableST(flightObject);
			}
			
			
		}

	}

	/*
	 * Starts the simulation of take off or landing
	 */
	@SuppressWarnings("deprecation")
	private void startSimulation(ChildPanel cPanel, RunwayBean runway, int flightType, FlightBean flight) {
		JProgressBar pBar = cPanel.getProgressBar();
		JLabel statusLabel = cPanel.getLblFlightStatus();
		simulation = new Simulation(pBar, flightType, runway, statusLabel, flight,simulationScreen);
		simulation.addObserver(new Observer() {

			@Override
			public void update(Observable arg0, Object arg1) {
				
				RunwayBean runwayObj = (RunwayBean) arg1;
				
				changeRunwayColor(runwayObj.getRunwayId(), true);
				if (!taxiQueue.isEmpty()) {
					allocateRunwayfromQueue((RunwayBean) arg1);
				}
			}
		});
		new Thread(simulation).start();
	}

	/*
	 * Allocates runway to a flight which was waiting in queue
	 */
	private void allocateRunwayfromQueue(RunwayBean runwayObject) {

		for (RunwayBean runwayBean : runwayList) {
			if (runwayBean.getRunwayId() == runwayObject.getRunwayId()) {
				runwayBean.setRunwayStatus(AirportConstants.RUNWAY_STATUS_OCCUPIED);
				changeRunwayColor(runwayBean.getRunwayId(), false);
				if (!taxiQueue.isEmpty()) {

					FlightBean flight = taxiQueue.get(0);
					int index = flight.getScheduledIndex();
					ChildPanel cPanel = simulationScreen.getChildPanelList().get(index);
					if (index >= 0) {
						cPanel.getLblRunway().setText(getRunwayId(runwayBean.getRunwayId()));
						startSimulation(cPanel, runwayBean, flight.getFlightType(), flight);
					}
					if (!taxiQueue.isEmpty()) {
						taxiQueue.remove(0);
						simulationScreen.removeFromDepartureQueueTable();
					}
					break;
				}
			}
		}

	}

	public void changeRunwayColor(int runwayNo, boolean isFree) {
		simulationScreen.setRunwayStatus(runwayNo + 1, isFree);

	}

	public String getRunwayId(int id) {
		String runwayId = "";
		switch (id + 1) {
		case 1:
			runwayId = "R1";
			break;
		case 2:
			runwayId = "R2";
			break;
		case 3:
			runwayId = "R3";
			break;
		}
		return runwayId;
	}

	public static void arrangeQueue(String flightId) {
		int index = -1;
		FlightBean tempObject = null;
		for (FlightBean flight : taxiQueue) {
			if (flight.getFlightId().equals(flightId)) {
				index = taxiQueue.indexOf(flight);
				tempObject = flight;
			}
		}
		if (tempObject != null && index!=-1) {
			taxiQueue.remove(index);
			taxiQueue.add(0, tempObject);
		}

	}

}
