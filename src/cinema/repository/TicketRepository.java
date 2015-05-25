package cinema.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cinema.model.Hall;
import cinema.model.Hour;
import cinema.model.Ticket;

public class TicketRepository extends AbstractRepository<Ticket>{
	
	private final PreparedStatement getAllStatement;
	private final PreparedStatement saveStatement;
	private final HallRepository hallRepository;

	public TicketRepository(Connection connection, HallRepository hallRepository) throws SQLException {
		super(connection);
		getAllStatement = connection.prepareStatement("SELECT * FROM tickets");
		saveStatement = connection.prepareStatement("INSERT INTO tickets(hour, \"hallId\", row, col) VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
		this.hallRepository = hallRepository;
	}
	
	public Collection<Ticket> getAll(){
		try {
			ResultSet rs = getAllStatement.executeQuery();
			Collection<Hall> halls = hallRepository.getAll();
			Map<Integer, Hall> hallMap = new HashMap<Integer, Hall>();
			for (Hall hall : halls) {
				hallMap.put(hall.getId(), hall);
			}
			List<Ticket> tickets = new ArrayList<Ticket>();

			while(rs.next()){
				Ticket ticket = new Ticket(rs.getInt("id"), hallMap.get(rs.getInt("hallId")), Hour.values()[rs.getInt("hour")], rs.getInt("row"), rs.getInt("col"));
				tickets.add(ticket);
			}
			return tickets;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Ticket save(Ticket ticket){
		try {
			saveStatement.setInt(1, getIndexOfHour(ticket.getHour()));
			saveStatement.setInt(2, ticket.getHall().getId());
			saveStatement.setInt(3, ticket.getRow());
			saveStatement.setInt(4, ticket.getCol());
			saveStatement.executeUpdate();

			ResultSet generatedKeys = saveStatement.getGeneratedKeys();
			Ticket result = null;
			while (generatedKeys.next()) {
				result = new Ticket(generatedKeys.getInt(1), ticket.getHall(), ticket.getHour(), ticket.getRow(), ticket.getCol());
			}
			return result;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private int getIndexOfHour(Hour hour){
		Hour[] values = Hour.values();
		for (int i = 0; i < values.length; i++) {
			if(values[i]==hour){
				return i;
			}
		}
		return -1;
	}

}
