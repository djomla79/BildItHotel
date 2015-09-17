package bildIt.hotel;

/**
 * @author Dijana Markovic
 *
 */
public class Rooms {
	public static final int TWO_DOUBLE_BEDS_ROOM = 0;
	public static final int ONE_BED_ROOM = 0;
	private Integer number;
	private int type;
	private boolean isOccupied;

	public Rooms() {

	}

	public Rooms(int number, int type, boolean isOccupied) {
		this.number = number;
		this.type = type;
		this.isOccupied = isOccupied;

	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	@Override
	public String toString() {
		return "hotel.Room[ number=" + number + " ]";
	}

}