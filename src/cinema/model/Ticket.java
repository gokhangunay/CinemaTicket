package cinema.model;

public class Ticket {

	private int id;
	private Hall hall;
	private Hour hour;
	private int row;
	private int col;

	public Ticket(Hall hall, Hour hour, int row, int col) {
		super();
		this.hall = hall;
		this.hour = hour;
		this.row = row;
		this.col = col;
	}

	public Ticket(int id, Hall hall, Hour hour, int row, int col) {
		super();
		this.id = id;
		this.hall = hall;
		this.hour = hour;
		this.row = row;
		this.col = col;
	}

	public int getId() {
		return id;
	}

	public Hall getHall() {
		return hall;
	}

	public Hour getHour() {
		return hour;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", hall=" + hall + ", hour=" + hour
				+ ", row=" + row + ", col=" + col + "]";
	}

}
