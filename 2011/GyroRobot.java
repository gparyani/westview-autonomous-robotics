import java.util.ArrayList;

import lejos.nxt.SensorPort;
import lejos.nxt.addon.GyroSensor;

public class GyroRobot extends Robot{
	
	
	private GyroSensor gyro;
	private float degrees;
	private long previousTime;

	public GyroRobot(boolean isMaze) {
		super(isMaze, true);
		gyro = new GyroSensor(SensorPort.S3);
		previousTime = System.currentTimeMillis();
		degrees = 0;
	}
	
	public void moving(){
		degrees += gyro.getAngularVelocity() * (System.currentTimeMillis() - previousTime);
		previousTime = System.currentTimeMillis();
	}
	
	public int getSingleUS(int direction){
		return sensor.get(direction);
		
	}
	
		
	public float getDegrees(){
		return degrees;
	}
	
	/*public void rotate(){
		motors.get(Headings.NORTH).rotate((int) toRotate/4, true);
		motors.get(Headings.SOUTH).rotate((int) toRotate/4, true);
		motors.get(Headings.WEST).rotate((int) toRotate/4, true);
		motors.get(Headings.EAST).rotate((int) toRotate/4, false);
	}*/
}

