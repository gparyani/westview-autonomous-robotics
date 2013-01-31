import java.util.ArrayList;
import java.util.Arrays;

import lejos.nxt.SensorPort;
import lejos.nxt.addon.IRSeekerV2;
import lejos.nxt.addon.SensorMux;
import lejos.nxt.addon.tetrix.TetrixEncoderMotor;

public class Sensor {
    private SensorMux sm1;
    private SensorMux sm2;
    private IRSeekerV2 ir;

    public Sensor(boolean single) {
    	sm1 = new SensorMux(SensorPort.S2);
    	if(!single){
    		sm2 = new SensorMux(SensorPort.S3);
        	sm2.configurate();
    	}
    	ir = new IRSeekerV2(SensorPort.S4, IRSeekerV2.Mode.AC);
    	sm1.configurate();
    }
    
    public int get(int direction){
    	return sm1.getDistance(direction + 1);
	}

    public boolean canGo(int row, int col, int direction) {
    	ArrayList<Integer> sm1nums = new ArrayList<Integer>();
    	ArrayList<Integer> sm2nums = new ArrayList<Integer>();
    	for(int i = 0; i < 3; i++){
    		sm1nums.add(sm1.getDistance(direction + 1));
    		sm2nums.add(sm2.getDistance(direction + 1));
    		try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	int med = Math.min(median(sm1nums), median(sm2nums));
    	//IarocAlgorithm.log.write(0);
    	//IarocAlgorithm.log.write(med);
    	return med >= 50;
    }
    
    public int getDifference(int direction){
    	ArrayList<Integer> sm1nums = new ArrayList<Integer>();
		ArrayList<Integer> sm2nums = new ArrayList<Integer>();
    	for(int i = 0; i < 3; i++){
    		sm1nums.add(sm1.getDistance(direction + 1));
    		sm2nums.add(sm2.getDistance(direction + 1));
    		try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	int med1 = median(sm1nums);
    	int med2 = median(sm2nums);
    	return med1 - med2;
    }
    
    private int median(ArrayList<Integer> nums){
    	Object[] numsa = nums.toArray();
    	Arrays.sort(numsa);
    	return (Integer) numsa[nums.size() / 2];
    }

    public boolean isFinish(int row, int col) {
    	return ir.getSensorValue(3) >= 23 || ir.getSensorValue(2) >=23 || ir.getSensorValue(4) >= 23;
    }

	public void printData() {
		System.out.println();
		System.out.print("N1: " + sm1.getDistance(Headings.NORTH +  1) + "   ");
		System.out.println("N2: " + sm2.getDistance(Headings.NORTH +  1));
		System.out.print("W1: " + sm1.getDistance(Headings.WEST +  1) + "   ");
		System.out.println("W2: " + sm2.getDistance(Headings.WEST +  1));
		System.out.print("E1: " + sm1.getDistance(Headings.EAST +  1) + "   ");
		System.out.println("E2: " + sm2.getDistance(Headings.EAST +  1));
		System.out.print("S1: " + sm1.getDistance(Headings.SOUTH +  1) + "   ");
		System.out.println("S2: " + sm2.getDistance(Headings.SOUTH +  1));
	}
	
	
	public int getAverage(int direction) {
		ArrayList<Integer> sm1nums = new ArrayList<Integer>();
		ArrayList<Integer> sm2nums = new ArrayList<Integer>();
    	for(int i = 0; i < 3; i++){
    		sm1nums.add(sm1.getDistance(direction + 1));
    		sm2nums.add(sm2.getDistance(direction + 1));
    		try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	int med1 = median(sm1nums);
    	int med2 = median(sm2nums);
    	int avg = (med1 + med2) / 2;
    	//IarocAlgorithm.log.write(1);
    	//IarocAlgorithm.log.write(avg);
		return avg;
	}
	
	public int getMiddleIrVal(){
		return ir.getSensorValue(3);
	}
	
	public void displayUS(){
		System.out.println("      "+sm1.getDistance(Headings.NORTH+1)+"    "+sm2.getDistance(Headings.NORTH+1));
		System.out.println(sm1.getDistance(Headings.WEST+1)+"          "+sm2.getDistance(Headings.EAST+1));
		System.out.println(sm1.getDistance(Headings.WEST+1)+"          "+sm2.getDistance(Headings.EAST+1));
		System.out.println("      "+sm1.getDistance(Headings.SOUTH+1)+"    "+sm2.getDistance(Headings.SOUTH+1));
	}


};

