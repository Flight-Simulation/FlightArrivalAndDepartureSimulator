/**
*
* @author Githu Elsa George
*/
package com.rt.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ChildPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3645811679144935083L;
	private JLabel lblFlightId;
	private JLabel lblFlightStatus;
	private JLabel lblRunway;
	private JProgressBar progressBar;
	private JLabel lblScheduledTime;
	private JLabel lblRescheduledTime;


	public ChildPanel() {
	initialize();
	}

	
	private void initialize() {
		
		this.setLayout(null);
		this.setBounds(6, 28, 610, 54);
		this.setBackground(Color.BLACK);
		
		lblFlightId = new JLabel();
		lblFlightId.setBounds(10, 22, 34, 16);
		lblFlightId.setForeground(Color.LIGHT_GRAY);
		lblFlightId.setText("101");
		this.add(lblFlightId);
		
		lblScheduledTime = new JLabel();
		lblScheduledTime.setBounds(102, 22, 62, 16);
		lblScheduledTime.setForeground(Color.LIGHT_GRAY);
		lblScheduledTime.setText("");
		this.add(lblScheduledTime);
		
		lblRescheduledTime = new JLabel();
		lblRescheduledTime.setBounds(209, 22, 56, 16);
		lblRescheduledTime.setForeground(Color.LIGHT_GRAY);
		lblRescheduledTime.setText("");
		this.add(lblRescheduledTime);
		
		lblFlightStatus = new JLabel();
		lblFlightStatus.setBounds(301, 22, 93, 16);
		lblFlightStatus.setForeground(Color.LIGHT_GRAY);
		lblFlightStatus.setText("Descending");
		this.add(lblFlightStatus);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(412, 18, 141, 20);
		progressBar.setPreferredSize(new Dimension(185, 50));
		progressBar.setForeground(Color.decode("#4A76E8"));
		this.add(progressBar);
		
		lblRunway = new JLabel();
		lblRunway.setBounds(560, 22, 28, 16);
		lblRunway.setForeground(Color.LIGHT_GRAY);
		this.add(lblRunway);
	}


	public JLabel getLblFlightId() {
		return lblFlightId;
	}


	public void setLblFlightId(JLabel lblFlightId) {
		this.lblFlightId = lblFlightId;
	}


	public JLabel getLblFlightStatus() {
		return lblFlightStatus;
	}


	public void setLblFlightStatus(JLabel lblFlightStatus) {
		this.lblFlightStatus = lblFlightStatus;
	}


	public JLabel getLblRunway() {
		return lblRunway;
	}


	public void setLblRunway(JLabel lblRunway) {
		this.lblRunway = lblRunway;
	}


	public JProgressBar getProgressBar() {
		return progressBar;
	}


	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}


	public JLabel getLblScheduledTime() {
		return lblScheduledTime;
	}


	public void setLblScheduledTime(JLabel lblScheduledTime) {
		this.lblScheduledTime = lblScheduledTime;
	}


	public JLabel getLblRescheduledTime() {
		return lblRescheduledTime;
	}


	public void setLblRescheduledTime(JLabel lblRescheduledTime) {
		this.lblRescheduledTime = lblRescheduledTime;
	}
}
