
package com.his.rt.flightsimulation.bean;
/**
* @author Namitha Moothedathu Kalesh and Githu Elsa George
* 
*/

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.his.rt.flightsimulation.constants.FlightConstants;

public class FlightBean implements Comparable<FlightBean> {
	private String flightName;
	private String flightId;
	private int flightType;
	private String flightDate;
	private String flightTime;
	private int flightStatus;
	private Date dateTime;
	private int holdingTime;
	private int glidingAndRunningTime;
	private int scheduledIndex;

	public String getFlightName() {
		return flightName;
	}

	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}

	public String getFlightId() {
		return flightId;
	}

	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}

	public int getFlightType() {
		return flightType;
	}

	public void setFlightType(int flightType) {
		this.flightType = flightType;
	}

	public String getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	public String getFlightTime() {
		return flightTime;
	}

	public void setFlightTime(String flightTime) {
		this.flightTime = flightTime;
	}

	public int getFlightStatus() {
		return flightStatus;
	}

	public void setFlightStatus(int flightStatus) {
		this.flightStatus = flightStatus;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public void formatTime() {
		if (flightTime != null) {
			String[] timeString = flightTime.split("\\:");
			String hours = timeString[0];
			String minutes = timeString[1];
			//String seconds = timeString[2];
			
			String timeFormatted = getFlightDate() + " " + hours + ":" + minutes+":"+"00";
			Date formattedDate = formatTimeLocale(timeFormatted);
			
			if (formattedDate != null) {
				setDateTime(formattedDate);
			} else {

			}

		}
	}

	public Date formatTimeLocale(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat(FlightConstants.DATE_FORMAT, Locale.ENGLISH);
		//sdf.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"));
		try {
			Date utcDate = sdf.parse(time);
			return utcDate;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int compareTo(FlightBean flightBean) {
		if (getDateTime() == null || flightBean.getDateTime() == null)
			return 0;
		return getDateTime().compareTo(flightBean.getDateTime());

	}

	
	public int getHoldingTime() {
		return holdingTime;
	}

	public void setHoldingTime(int holdingTime) {
		this.holdingTime = holdingTime;
	}

	public int getglidingAndRunningTime() {
		return glidingAndRunningTime;
	}

	public void setglidingAndRunningTime(int glidingSpeed) {
		this.glidingAndRunningTime = glidingSpeed;
	}

	public int getScheduledIndex() {
		return scheduledIndex;
	}

	public void setScheduledIndex(int scheduledIndex) {
		this.scheduledIndex = scheduledIndex;
	}

}