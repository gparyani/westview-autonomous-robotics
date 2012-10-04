package org.wvrobotics.iaroc;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.addon.SensorSelector;

public class GoldRush {
	private static int currentRow;
	private static GyroRobot robot;
	
	public static Runnable exitThread = new Runnable() {
		public void run() {
			while (true) {
				if (Button.ESCAPE.isDown())
					System.exit(0);
			}
		}
	};

	public static void main(String[] args) {
			robot = new GyroRobot(false);
			currentRow = 0;
			System.out.println("Press enter to start");
			new Thread(exitThread).start();
			while (!Button.ENTER.isDown()) {
			}
			try {
			while (true) {
				if (robot.sensor.get(Headings.NORTH) >= 40) {
					robot.motors.moveNorth();
					currentRow++;
				}
				else if (robot.sensor.get(Headings.EAST) >= 40) {
					robot.motors.moveEast();
				}
				else if (robot.sensor.get(Headings.WEST) >= 40) {
					robot.motors.moveWest();
				}
				else if (robot.sensor.get(Headings.SOUTH) >= 40) {
					robot.motors.moveSouth();
					currentRow--;
				}
				
				if (currentRow >= 8) {
					irScan();
					break;
				}
			}
		} catch (Exception e) {
			LCD.clearDisplay();
			e.printStackTrace();
		}
	}

	private static void irScan() {
		while (true) {
			System.out.println("Sweep");
			for (boolean setting = false ; ; setting = ! setting) {
				if (!setting && robot.sensor.get(Headings.WEST) >= 40)
					robot.motors.moveWest();
				else {
					robot.motors.moveNorth();
					break;
				}
				if (setting && robot.sensor.get(Headings.EAST) >= 40)
					robot.motors.moveEast();
				else {
					robot.motors.moveNorth();
					break;
				}
				if(robot.sensor.getMiddleIrVal() >= 10) {
					while(true)
						robot.getMotors().moveNorth();
				}
			}
		}
	}

}

