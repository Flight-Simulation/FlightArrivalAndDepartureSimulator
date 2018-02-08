package com.his.rt.flightsimulation.main;
/**
* @author Githu Elsa George
* 
*/


import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

import com.his.rt.flightsimulation.bean.FlightBean;
import com.his.rt.flightsimulation.bean.RunwayBean;
import com.his.rt.flightsimulation.constants.AirportConstants;
import com.his.rt.flightsimulation.constants.FlightConstants;
import com.rt.gui.SimulationScreen;

@SuppressWarnings("deprecation")
public class Simulation extends Observable implements Runnable {
	private RunwayBean runway;
	private int flightType;
	private JProgressBar pBar;
	private JLabel statusLabel;
	private FlightBean flight;
	private SimulationScreen screen;

	public Simulation(JProgressBar pBar, int flightType, RunwayBean runway, 
			JLabel statusLabel, FlightBean flight, SimulationScreen screen) {
		this.pBar = pBar;
		this.flightType = flightType;
		this.runway = runway;
		this.statusLabel = statusLabel;
		this.flight = flight;
		this.screen = screen;

	}

	/*
	 * start simulation at the end of simulation, notify main function so that it
	 * makes the runway status back to free
	 */

	@Override
	public void run() {
		updateStatus();
		
		

	}
	public void updateStatus() {
		if(flightType == FlightConstants.FLIGHT_TYPE_ARRIVAL) {
			flight.setFlightStatus(FlightConstants.FLIGHT_STATUS_DESCEND);
			statusLabel.setText("Landing");
			
		}else {
			flight.setFlightStatus(FlightConstants.FLIGHT_STATUS_CLIMB);
			statusLabel.setText("Departing");
		}
		UpdateProgress progress = new UpdateProgress(pBar, flight.getglidingAndRunningTime());
		progress.addObserver(new Observer() {

			@Override
			public void update(Observable arg0, Object arg1) {
				flight.setFlightStatus(FlightConstants.FLIGHT_STATUS_COMPLETED);
				statusLabel.setText("Completed");
				runway.setRunwayStatus(AirportConstants.RUNWAY_STATUS_FREE);
				screen.addFlightToCompletedList(flight);
				screen.removeRowInArrivalQueue(flight);
				JLabel lblEstimatedTime = screen.getChildPanelList().get(flight.getScheduledIndex()).getLblRescheduledTime();
				if(lblEstimatedTime.getText().isEmpty())
					lblEstimatedTime.setText(flight.getFlightTime());
				setChanged();
				notifyObservers(runway);
			}
		});
		new Thread(progress).start();
		
	}
	
	}
