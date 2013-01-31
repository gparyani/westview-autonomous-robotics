import lejos.nxt.LCD;
public class UStest {
	
	public static Sensor sensor;

	public static void main(String[] args) {
				
		for (int i=0;i<10;i++){
			LCD.clearDisplay();
			sensor = new Sensor(false);
			sensor.displayUS();
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}

			
		
		}
		

	}

}

