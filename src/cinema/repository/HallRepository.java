package cinema.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cinema.model.Hall;

public class HallRepository extends AbstractRepository<Hall> {

	private final PreparedStatement getAllStatement;
	private final PreparedStatement saveStatement;

	public HallRepository(Connection connection) throws SQLException {
		super(connection);
		getAllStatement = connection.prepareStatement("SELECT * FROM halls");
		saveStatement = connection.prepareStatement(
				"INSERT INTO halls(rows, cols, name) VALUES (?, ?, ?);",
				Statement.RETURN_GENERATED_KEYS);
	}

	public Collection<Hall> getAll() {
		try {
			ResultSet rs = getAllStatement.executeQuery();
			List<Hall> halls = new ArrayList<>();
			while(rs.next()){
				Hall hall = new Hall(rs.getInt("id"), rs.getInt("rows"), rs.getInt("cols"), rs.getString("name"));
				halls.add(hall);
			}
			return halls;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Hall saveHall(Hall hall) {
		try {
			saveStatement.setInt(1, hall.getRows());
			saveStatement.setInt(2, hall.getCols());
			saveStatement.setString(3, hall.getName());
			saveStatement.executeUpdate();

			ResultSet generatedKeys = saveStatement.getGeneratedKeys();
			Hall result = null;
			while (generatedKeys.next()) {
				result = new Hall(generatedKeys.getInt(1), hall.getRows(),
						hall.getCols(), hall.getName());
			}
			return result;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
