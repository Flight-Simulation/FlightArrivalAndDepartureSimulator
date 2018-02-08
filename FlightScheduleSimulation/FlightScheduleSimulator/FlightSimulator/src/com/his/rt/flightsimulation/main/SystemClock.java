package com.his.rt.flightsimulation.main;
/**
* @author Namitha Moothedathu Kalesh
* 
*/


import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.Timer;

import com.his.rt.flightsimulation.bean.FlightBean;

@SuppressWarnings("deprecation")
public class SystemClock extends Observable implements Runnable, ActionListener {

	/**
	* 
	*/
	private static final long serialVersionUID = -2202619348339912762L;
	private SimpleDateFormat sdf;
	private Date dateTime;
	private int hours;
	private int minutes;
	private int seconds;
	private ArrayList<FlightBean> flightList = new ArrayList<FlightBean>();
	private JLabel timeLabel;
	private Font font;
	

	@SuppressWarnings("deprecation")
	public SystemClock(JLabel timeLabel, ArrayList<FlightBean> flightList, Date dateTime) {
		this.dateTime=dateTime;
		hours=dateTime.getHours();
		minutes=dateTime.getMinutes();
		seconds=dateTime.getSeconds();
		this.flightList.addAll(flightList);
		this.timeLabel = timeLabel;
		
		sdf = new SimpleDateFormat("HH:mm:ss");
		updateTime();
		
		try {
			String filename="D:/Extracted Files/digital_7/digital-7 (mono).ttf";
			font = Font.createFont(Font.TRUETYPE_FONT, new File(filename));
			font = font.deriveFont(Font.PLAIN,40);

			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(font);
		}
		catch(Exception e) {
			System.out.println("Clock font exception thrown:: IOException");
		}

	}

	private void updateTime() {
		Timer timer = new Timer(1000, this);
		timer.start();
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent ae) {
		seconds++;
		if (seconds == 10) {
			minutes++;
			seconds = 0;
		}
		if (minutes == 60) {
			hours++;
			minutes = 0;
		}
		if (hours == 24) {
			hours = 0;
			minutes = 0;
			seconds = 0;
		}
		dateTime.setHours(hours);
		dateTime.setMinutes(minutes);
		dateTime.setSeconds(seconds);
		timeLabel.setText(sdf.format(dateTime));
		timeLabel.setFont(font);
				
	}

	@Override
	public void run() {
		
			String flightTime;

			while (!flightList.isEmpty()) {
				/*
				 * 1. take the time of the first object 
				 * 2. when the timer reaches the time of
				 * the first object, notify runway class with flightid 
				 * 3. runway class checks
				 * for free runways 
				 * 4. if free, sends the runway id and flight id to simulation
				 * class
				 */
				flightTime = flightList.get(0).getFlightTime();
//				String str = dateTime.toString();
//				String[] splitString = str.split(" ");
				long timedifference = timeDiff(flightTime, sdf.format(dateTime));
				if ( timedifference >= 0 ){
					FlightBean obj = flightList.get(0);
					System.out.println("Removing flight id "+flightList.get(0).getFlightId());
					flightList.remove(0);
					setChanged();
					notifyObservers(obj);
					
					
					

				}
			
		}
	}

	public ArrayList<FlightBean> getFlightList() {
		return flightList;
	}

	public void setFlightList(ArrayList<FlightBean> flightList) {
		this.flightList = flightList;
		for(FlightBean flight:flightList) {
			System.out.println("Flight Id "+flight.getFlightId()+" flightTime " +flight.getFlightTime());
		}
	}
	public long timeDiff(String time1, String time2) {
		time1+=":00";
		
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	    Date d1 = null;
		try {
			d1 = sdf.parse(time1);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    Date d2 = null;
		try {
			d2 = sdf.parse(time2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
		return d2.getTime() - d1.getTime();
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	
	
}
