/**
 * 
 */
package year2013;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.IRSeekerV2;

/**
 * Tester class for IR Sensor
 * @author Gaurav Paryani
 *
 */
public class IRSensorTest {

	/**
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		IRSeekerV2 seeker = new IRSeekerV2(SensorPort.S4, IRSeekerV2.Mode.DC);
		while(Button.ESCAPE.isUp())
			LCD.drawString(String.valueOf(seeker.getAngle()), 0, 0);

	}

}
