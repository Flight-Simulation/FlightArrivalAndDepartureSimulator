package com.rt.gui;

/**
*
* @author Githu Elsa George
*/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import com.his.rt.flightsimulation.bean.FlightBean;
import com.his.rt.flightsimulation.constants.FlightConstants;
import com.his.rt.flightsimulation.main.SystemClock;

public class SimulationScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6415233584470978230L;
	private JTable flightScheduleTable;
	private JTable flightRescheduleTable;
	private JTable arrivalQueueTable;
	private JTable departureQueueTable;
	private JTextField runway1;
	private JTextField runway2;
	private JTextField runway3;
	private DefaultTableModel flightListModelFT;
	private DefaultTableModel flightListModelST;
	private DefaultTableModel queueListModelFT;
	private DefaultTableModel queueListModelST;
	private ArrayList<FlightBean> flightList;
	private int runWayNo;
	private Color runwayFree = new Color(50, 205, 50);
	private Color runwayOccupied = new Color(220, 20, 60);

	private JLabel timeLabel;
	private JLabel lblFlightId;
	private ArrayList<ChildPanel> childPanelList = new ArrayList<ChildPanel>();
	private JPanel runwayPanel;
	private JPanel flightStatusPanel;
	private JButton btnNext;
	private SystemClock systemClockTime;


	/**
	 * Create the application.
	 */
	public SimulationScreen(ArrayList<FlightBean> flightList, int runWayNo) {
		this.flightList = flightList;
		this.runWayNo = runWayNo;
		initialize();
		populateFlightInfoTable();
		popuLateFlightStatusTable();

	}




	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JFrame frame = this;
		frame.setBounds(5, -5, 1080, 695);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Simulation Screen");
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.BLACK);

		JLabel lblScreenHeading = new JLabel("FLIGHT SCHEDULING SIMULATOR");
		lblScreenHeading.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblScreenHeading.setForeground(Color.GRAY);
		lblScreenHeading.setHorizontalAlignment(SwingConstants.CENTER);
		lblScreenHeading.setBounds(29, 6, 955, 25);
		frame.getContentPane().add(lblScreenHeading);

		JPanel flightInfoPanel = new JPanel();
		flightInfoPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		flightInfoPanel.setBorder(BorderFactory.createLineBorder(Color.green));
		flightInfoPanel.setBackground(Color.BLACK);
		flightInfoPanel.setBounds(10, 54, 414, 273);
		flightInfoPanel.setLayout(null);
		frame.getContentPane().add(flightInfoPanel);

		JLabel lblFlightInfoTableHeading = new JLabel("FLIGHT INFORMATION");
		lblFlightInfoTableHeading.setHorizontalAlignment(SwingConstants.CENTER);
		lblFlightInfoTableHeading.setForeground(new Color(188, 143, 143));
		lblFlightInfoTableHeading.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblFlightInfoTableHeading.setBounds(49, 0, 305, 27);
		flightInfoPanel.add(lblFlightInfoTableHeading);

		String[] columnNames = { "Flight Id","Type", "Time" };
		flightListModelFT = new DefaultTableModel(columnNames, 0);
		flightScheduleTable = new JTable(flightListModelFT);
		JScrollPane scrollPane = new JScrollPane(flightScheduleTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		flightScheduleTable.setFillsViewportHeight(true);
		flightScheduleTable.setRowSelectionAllowed(false);
		flightScheduleTable.setDefaultEditor(Object.class, null);
		scrollPane.setPreferredSize(new Dimension(200, 100));
		scrollPane.setBounds(10, 64, 190, 197);
		flightInfoPanel.add(scrollPane);

		flightListModelST = new DefaultTableModel(columnNames, 0);
		flightRescheduleTable = new JTable(flightListModelST);
		JScrollPane scrollPane_1 = new JScrollPane(flightRescheduleTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		flightRescheduleTable.setFillsViewportHeight(true);
		flightRescheduleTable.setRowSelectionAllowed(false);
		flightRescheduleTable.setDefaultEditor(Object.class, null);
		scrollPane_1.setPreferredSize(new Dimension(200, 100));
		scrollPane_1.setBounds(210, 64, 190, 197);
		flightInfoPanel.add(scrollPane_1);

		JLabel lblScheduleList = new JLabel("Schedule List");
		lblScheduleList.setHorizontalAlignment(SwingConstants.CENTER);
		lblScheduleList.setForeground(Color.LIGHT_GRAY);
		lblScheduleList.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblScheduleList.setBounds(40, 38, 120, 22);
		flightInfoPanel.add(lblScheduleList);

		JLabel lblRescheduleList = new JLabel("Reschedule List");
		lblRescheduleList.setHorizontalAlignment(SwingConstants.CENTER);
		lblRescheduleList.setForeground(Color.LIGHT_GRAY);
		lblRescheduleList.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblRescheduleList.setBounds(244, 38, 130, 22);
		flightInfoPanel.add(lblRescheduleList);


		JPanel flightQueuePanel = new JPanel();
		flightQueuePanel.setLayout(null);
		flightQueuePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		flightQueuePanel.setBorder(BorderFactory.createLineBorder(Color.green));
		flightQueuePanel.setBackground(Color.BLACK);
		flightQueuePanel.setBounds(10, 362, 414, 284);
		frame.getContentPane().add(flightQueuePanel);

		String[] queueColumnNames = { "Flight Id", "Time" };
		queueListModelFT = new DefaultTableModel(queueColumnNames, 0);
		arrivalQueueTable = new JTable(queueListModelFT);
		JScrollPane scrollPane_2 = new JScrollPane(arrivalQueueTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		arrivalQueueTable.setFillsViewportHeight(true);
		arrivalQueueTable.setRowSelectionAllowed(false);
		arrivalQueueTable.setDefaultEditor(Object.class, null);
		scrollPane_2.setPreferredSize(new Dimension(200, 100));
		scrollPane_2.setBounds(37, 82, 140, 180);
		flightQueuePanel.add(scrollPane_2);

		queueListModelST = new DefaultTableModel(queueColumnNames, 0);
		departureQueueTable = new JTable(queueListModelST);
		JScrollPane scrollPane_3 = new JScrollPane(departureQueueTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		departureQueueTable.setFillsViewportHeight(true);
		departureQueueTable.setRowSelectionAllowed(false);
		departureQueueTable.setDefaultEditor(Object.class, null);
		scrollPane_3.setPreferredSize(new Dimension(200, 100));
		scrollPane_3.setBounds(231, 82, 140, 180);
		flightQueuePanel.add(scrollPane_3);

		JLabel lblReadyQueue = new JLabel("READY QUEUE");
		lblReadyQueue.setHorizontalAlignment(SwingConstants.CENTER);
		lblReadyQueue.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblReadyQueue.setForeground(new Color(188, 143, 143));
		lblReadyQueue.setBounds(41, 11, 305, 27);
		flightQueuePanel.add(lblReadyQueue);

		JLabel lblArrivals = new JLabel("Arrivals:");
		lblArrivals.setHorizontalAlignment(SwingConstants.CENTER);
		lblArrivals.setForeground(Color.LIGHT_GRAY);
		lblArrivals.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblArrivals.setBounds(20, 49, 90, 22);
		flightQueuePanel.add(lblArrivals);

		JLabel lblDeparture = new JLabel("Departure:");
		lblDeparture.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeparture.setForeground(Color.LIGHT_GRAY);
		lblDeparture.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblDeparture.setBounds(221, 49, 90, 22);
		flightQueuePanel.add(lblDeparture);

		runwayPanel = new JPanel();
		runwayPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		runwayPanel.setBorder(BorderFactory.createLineBorder(Color.green));
		runwayPanel.setBackground(Color.black);
		runwayPanel.setBounds(468, 54, 280, 151);
		frame.getContentPane().add(runwayPanel);
		runwayPanel.setLayout(null);

		JLabel lblRunwayStatus = new JLabel("RUNWAY STATUS");
		lblRunwayStatus.setBounds(6, 7, 268, 19);
		lblRunwayStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblRunwayStatus.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblRunwayStatus.setForeground(new Color(188, 143, 143));
		runwayPanel.add(lblRunwayStatus);

		ArrayList<JLabel> labelList= new ArrayList<JLabel>();
		JLabel lblR1 = new JLabel("R1");
		lblR1.setBounds(19, 50, 30, 16);
		lblR1.setForeground(Color.LIGHT_GRAY);
		labelList.add(lblR1);

		JLabel lblR2 = new JLabel("R2");
		lblR2.setBounds(19, 78, 30, 16);
		lblR2.setForeground(Color.LIGHT_GRAY);
		labelList.add(lblR2);

		JLabel lblR3 = new JLabel("R3");
		lblR3.setBounds(19, 107, 30, 16);
		lblR3.setForeground(Color.LIGHT_GRAY);
		labelList.add(lblR3);

		runway1 = new JTextField();
		runway1.setBackground(runwayFree);
		runway1.setBounds(61, 45, 195, 26);
		runway1.setColumns(10);


		runway2 = new JTextField();
		runway2.setColumns(10);
		runway2.setBackground(runwayFree);
		runway2.setBounds(61, 73, 195, 26);


		runway3 = new JTextField();
		runway3.setColumns(10);
		runway3.setBackground(runwayFree);
		runway3.setBounds(61, 102, 195, 26);
		arrangeRunwayView(labelList);

		JPanel timePanel = new JPanel();
		timePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		timePanel.setBounds(783, 54, 248, 151);
		timePanel.setBorder(BorderFactory.createLineBorder(Color.green));
		timePanel.setBackground(Color.BLACK);
		frame.getContentPane().add(timePanel);
		timePanel.setLayout(null);

		JLabel lblDate = new JLabel();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM yyyy");
		LocalDate localDate = LocalDate.now();
		lblDate.setText(dtf.format(localDate));
		lblDate.setFont(new Font("SansSerif", Font.PLAIN, 25));
		lblDate.setForeground(Color.GRAY);
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate.setBounds(46, 11, 169, 29);
		timePanel.add(lblDate);

		timeLabel = new JLabel("");
		timeLabel.setBounds(46, 39, 169, 61);
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeLabel.setForeground(Color.GRAY);
		timePanel.setBackground(Color.BLACK);
		timeLabel.setFont(new Font("Digital-7", Font.PLAIN, 38));
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timePanel.add(timeLabel);

		btnNext = new JButton("FORWARD >>");
		btnNext.setForeground(new Color(0, 255, 0));
		btnNext.setFont(new Font("Yu Gothic", Font.BOLD, 11));
		btnNext.setBackground(new Color(0, 0, 0));
		btnNext.setBounds(81, 111, 119, 29);
		timePanel.add(btnNext);
		btnNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setNextAction();

			}
		});


		try {
			String filename="/Users/namitha/Documents/Namitha/RT/digital_7/digital-7\\ \\(mono\\).ttf";
			//this is for testing normally we would store the font file in our app 
			//(knows as an embedded resource), see this for help on that http://stackoverflow.com/questions/13796331/jar-embedded-resources-nullpointerexception/13797070#13797070

			Font font = Font.createFont(Font.TRUETYPE_FONT, new File(filename));
			font = font.deriveFont(Font.PLAIN,38);

			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(font);

			//JLabel l = new JLabel("Some Text");
			timeLabel.setFont(font);
		}
		catch(Exception e) {
			System.out.println("Clock font exception thrown:: IOException");
		}

		flightStatusPanel = new JPanel();
		flightStatusPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		flightStatusPanel.setBounds(440, 229, 600, 423);
		flightStatusPanel.setBackground(Color.BLACK);
		flightStatusPanel.setBorder(BorderFactory.createLineBorder(Color.green));
		frame.getContentPane().add(flightStatusPanel); 
		flightStatusPanel.setLayout(null);

		JLabel lblFlightId = new JLabel("FLIGHT ID");
		lblFlightId.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblFlightId.setBounds(10, 20, 73, 16);
		lblFlightId.setForeground(new Color(244, 164, 96));
		flightStatusPanel.add(lblFlightId);

		JLabel lblStatusBar = new JLabel("ACTUAL");
		lblStatusBar.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblStatusBar.setForeground(new Color(244, 164, 96));
		lblStatusBar.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatusBar.setBounds(184, 20, 98, 16);
		flightStatusPanel.add(lblStatusBar);

		JLabel lblStatus = new JLabel("STATUS");
		lblStatus.setBounds(312, 20, 61, 16);
		lblStatus.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblStatus.setForeground(new Color(244, 164, 96));

		flightStatusPanel.add(lblStatus);

		JLabel lblRunway = new JLabel("FLIGHT PROGRESS");
		lblRunway.setBounds(438, 20, 134, 16);
		lblRunway.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblRunway.setForeground(new Color(244, 164, 96));
		flightStatusPanel.add(lblRunway);

		JLabel lblScheduledTime = new JLabel("SCHEDULED");
		lblScheduledTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblScheduledTime.setForeground(new Color(244, 164, 96));
		lblScheduledTime.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblScheduledTime.setBounds(81, 20, 107, 16);
		flightStatusPanel.add(lblScheduledTime);

	}

	public void populateFlightInfoTable() {
		for (FlightBean flight : flightList) {
			flightListModelFT.addRow(new Object[] { flight.getFlightId(), getFlightType(flight.getFlightType()),
					flight.getFlightTime() });
		}
	}

	public void popuLateFlightStatusTable() {
		for (int i = 0; i < flightList.size(); i++) {
			ChildPanel child_panel = new ChildPanel();
			//			cP.setBounds(10, 66, 580, 38);
			child_panel.setBounds(10, 36 + (30 * i), 580, 38);
			child_panel.getLblFlightId().setText(flightList.get(i).getFlightId());
			child_panel.getLblFlightStatus()
			.setText(FlightConstants.getFlightStatus(flightList.get(i).getFlightStatus()));
			child_panel.getLblScheduledTime()
			.setText(flightList.get(i).getFlightTime());
			flightStatusPanel.add(child_panel);
			childPanelList.add(child_panel);
		}
	}


	public void updateRowInArrivalQueue(FlightBean flight) {
		String id = flight.getFlightId();
		boolean found = false;
		for (int i = 0; i < queueListModelFT.getRowCount(); i++) {
			if (queueListModelFT.getValueAt(i, 0).equals(id)) {
				queueListModelFT.setValueAt(flight.getFlightTime(), i, 1);
				found=true;
				break;
			}
		}
		if (!found) {
			queueListModelFT.addRow(new Object[] { flight.getFlightId(), flight.getFlightTime() });
		}
	}
	public void addTaxiQueueRow(FlightBean flight) {
		queueListModelST.addRow(new Object[] { flight.getFlightId(), flight.getFlightTime() });
	}

	public void removeFromDepartureQueueTable() {
		if (queueListModelST.getRowCount() > 0)
			queueListModelST.removeRow(0);
	}

	public void arrangeRunwayView(ArrayList<JLabel> labelList) {
		switch (runWayNo) {
		case 1:
			runwayPanel.add(labelList.get(0));
			runwayPanel.add(runway1);
			break;
		case 2:
			runwayPanel.add(labelList.get(0));
			runwayPanel.add(runway1);
			runwayPanel.add(labelList.get(1));
			runwayPanel.add(runway2);
			break;
		case 3:
			runwayPanel.add(labelList.get(0));
			runwayPanel.add(runway1);
			runwayPanel.add(labelList.get(1));
			runwayPanel.add(runway2);
			runwayPanel.add(labelList.get(2));
			runwayPanel.add(runway3);
			break;
		}

	}

	public void setRunwayStatus(int runWayId, boolean isFree) {
		switch (runWayId) {
		case 1:
			if (isFree) {
				runway1.setBackground(runwayFree);
			} else {
				runway1.setBackground(runwayOccupied);
			}
			break;
		case 2:
			if (isFree) {
				runway2.setBackground(runwayFree);
			} else {
				runway2.setBackground(runwayOccupied);
			}
			break;
		case 3:
			if (isFree) {
				runway3.setBackground(runwayFree);
			} else {
				runway3.setBackground(runwayOccupied);
			}
			break;
		}
	}

	public String getFlightType(int flightType) {
		String type = "";
		switch (flightType) {
		case FlightConstants.FLIGHT_TYPE_ARRIVAL:
			type = "Arrival";
			break;
		case FlightConstants.FLIGHT_TYPE_DEPARTURE:
			type = "Departure";
			break;

		}
		return type;

	}

	public void setNextAction() {
		Date dateTime = systemClockTime.getFlightList().get(0).getDateTime();
		systemClockTime.setHours(dateTime.getHours());
		systemClockTime.setMinutes(dateTime.getMinutes());
		systemClockTime.setSeconds(dateTime.getSeconds());
	}

	public void removeRowInArrivalQueue(FlightBean flight) {
		String id = flight.getFlightId();
		for (int i = 0; i < queueListModelFT.getRowCount(); i++) {
			if (queueListModelFT.getValueAt(i, 0).equals(id)) {
				queueListModelFT.removeRow(i);
				break;
			}
		}

	}

	public void addFlightToQueueTableST(FlightBean flight) {
		String type = getFlightType(flight.getFlightType());

		queueListModelST.addRow(new Object[] { flight.getFlightId(), type, flight.getFlightTime() });
	}
	public void addFlightToCompletedList(FlightBean flight) {
		String type = getFlightType(flight.getFlightType());

		flightListModelST.addRow(new Object[] { flight.getFlightId(), type, flight.getFlightTime() });
	}

	public JLabel getTimeLabel() {
		return timeLabel;
	}

	public void setTimeLabel(JLabel timeLabel) {
		this.timeLabel = timeLabel;
	}

	public ArrayList<ChildPanel> getChildPanelList() {
		return childPanelList;
	}

	public void setChildPanelList(ArrayList<ChildPanel> childPanelList) {
		this.childPanelList = childPanelList;
	}
	public SystemClock getSystemClockTime() {
		return systemClockTime;
	}


	public void setSystemClockTime(SystemClock systemClockTime) {
		this.systemClockTime = systemClockTime;
	}

}
