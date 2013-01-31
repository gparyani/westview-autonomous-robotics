import java.io.Serializable;

public class State implements Serializable{
    /**
	 * 
	 */
	private int previousHeading;
    private int nextHeading;
    private int positionRow;
    private int positionCol;
    private int length;

    public void setPreviousHeading(int inputPreviousHeading) {
	previousHeading = inputPreviousHeading;
    }

    public int getPreviousHeading() {
	return previousHeading;
    }

    public void setNextHeading(int inputNextHeading) {
	nextHeading = inputNextHeading;
    }

    public int getNextHeading() {
	return nextHeading;
    }

    public void setPositionRow(int inputPositionRow) {
	positionRow = inputPositionRow;
    }

    public int getPositionRow() {
	return positionRow;
    }

    public void setPositionCol(int inputPositionCol) {
	positionCol = inputPositionCol;
    }

    public int getPositionCol() {
	return positionCol;
    }

    public void setLength(int inputLength) {
	length = inputLength;
    }

    public int getLength() {
	return length;
    }
};

