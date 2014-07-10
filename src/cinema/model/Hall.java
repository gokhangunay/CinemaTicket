package cinema.model;

import java.util.Arrays;
import java.util.List;

public class Hall {

	private int id;
	private int rows;
	private int cols;
	private String name;

	public Hall(int id, int rows, int cols, String name) {
		super();
		this.id = id;
		this.rows = rows;
		this.cols = cols;
		this.name = name;
	}

	public Hall(int rows, int cols, String name) {
		super();
		this.rows = rows;
		this.cols = cols;
		this.name = name;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Hall [id=" + id + ", rows=" + rows + ", cols=" + cols
				+ ", name=" + name + "]";
	}

	public boolean[][] getEmptySeats(List<Ticket> tickets, Hour hour) {

		boolean[][] emptySeats = new boolean[rows][cols];
		for (int i = 0; i < emptySeats.length; i++) {
			boolean[] currentRow = new boolean[cols];
			Arrays.fill(currentRow, true);
			emptySeats[i] = currentRow;
		}

		for (Ticket ticket : tickets) {
			if (ticket.getHour().equals(hour) && ticket.getHall().equals(this)) {
				emptySeats[ticket.getRow()][ticket.getCol()] = false;
			}
		}

		return emptySeats;

	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Hall)) {
			return false;
		}
		Hall other = (Hall) obj;
		return this.getName().equals(other.getName());
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}

}
