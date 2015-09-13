package bildIt.hotel;

/**
 * @author Dijana Markovic
 *
 */
/* This is room class with basic data files */
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
	public int hashCode() {
		int hash = 0;
		hash += (number != null ? number.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// Warning - this method won't work in the case the number fields are
		// not set

		if (!(object instanceof Rooms)) {
			return false;
		}
		Rooms other = (Rooms) object;
		if ((this.number == null && other.number != null)
				|| (this.number != null && !this.number.equals(other.number))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "hotel.Room[ number=" + number + " ]";
	}

}