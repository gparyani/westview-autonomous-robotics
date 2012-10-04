package org.wvrobotics.iaroc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

public class CustomLogger {
	
	private ArrayList<Float> log;
	DataOutputStream dat;
	NXTConnection con;
	
	public CustomLogger(){
		log = new ArrayList<Float>();
		System.out.println("Waiting for Bluetooth");
		con = Bluetooth.waitForConnection();
		LCD.clearDisplay();
		dat = con.openDataOutputStream();
	}
	
	
	
	public void write(float f0){
		log.add(f0);
	}
	
	public void send(){
		try
	      {

	        dat.writeInt(log.size());
	        dat.flush();
	        for (int i = 0; i < log.size(); i++)
	        {
	          Float v = log.get(i);
	          dat.writeFloat(v.floatValue());
	        }
	        dat.flush();
	        dat.close();
	      } catch (IOException e){}
	}

}

