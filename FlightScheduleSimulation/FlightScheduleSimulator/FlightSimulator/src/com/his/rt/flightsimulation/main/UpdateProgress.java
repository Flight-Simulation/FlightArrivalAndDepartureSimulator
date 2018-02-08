package com.his.rt.flightsimulation.main;

/**
*
* @author Githu Elsa George
*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JProgressBar;
import javax.swing.Timer;

@SuppressWarnings("deprecation")
public class UpdateProgress extends Observable implements Runnable{
	
	private JProgressBar progressBar;
	private int i=0;
	private int delay;
	
	public UpdateProgress(JProgressBar pBar, int delay) {
		this.progressBar = pBar;
		this.delay = delay;
		
	}

	

	@Override
	public void run() {
		Timer timer = new Timer(delay,null);
		
		timer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(i<=100)
					progressBar.setValue(i++);
				else {
					timer.stop();
					setChanged();
					notifyObservers(null);
				}
				
			}
		});
		timer.start();
		
	}

}
