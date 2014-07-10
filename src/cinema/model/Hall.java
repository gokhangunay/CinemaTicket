package cinema.model;

import java.util.ArrayList;
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

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Hall [id=" + id + ", rows=" + rows + ", cols=" + cols
				+ ", name=" + name + "]";
	}
	
	public List<Ticket> getSeats(List<Ticket> tickets, Hour hour, int numPeople){
		boolean[][] emptySeats = getEmptySeats(tickets, hour);
		List<Ticket> result = new ArrayList<>();
		int counter = 0;
		for (int i = 0; i < emptySeats.length; i++) {
			for (int j = 0; j < emptySeats[i].length; j++) {
				if(emptySeats[i][j]){
					Ticket t = new Ticket(this, hour, i+1, j+1);
					result.add(t);
					counter++;
				}
				if(counter==numPeople){
					return result;
				}
			}
		}
		return result;
	}
	
	public List<Ticket> getContiguousSeats(List<Ticket> tickets, Hour hour, int numPeople){
		boolean[][] emptySeats = getEmptySeats(tickets, hour);
		int counter = 0;
		List<Ticket> result = new ArrayList<>();
		for (int i = 0; i < emptySeats.length; i++) {
			counter = 0;
			for (int j = 0; j < emptySeats[i].length; j++) {
				if(emptySeats[i][j]){
					counter++;
				}else{
					counter = 0;
				}
				if(counter==numPeople){
					for (int k = 0; k < numPeople; k++) {
						Ticket t= new Ticket(this, hour, i+1, j+1-k);
						result.add(t);
					}
					return result;
				}
			}
		}
		return result;
	}
	
	public int getAvailableSpace(List<Ticket> tickets, Hour hour){
		boolean[][] emptySeats = getEmptySeats(tickets, hour);
		int available = 0;
		for (int i = 0; i < emptySeats.length; i++) {
			for (int j = 0; j < emptySeats[i].length; j++) {
				if(emptySeats[i][j]){
					available++;
				}
			}
		}
		return available;
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
				emptySeats[ticket.getRow()-1][ticket.getCol()-1] = false;
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
