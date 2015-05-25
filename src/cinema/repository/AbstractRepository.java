package cinema.repository;

import java.sql.Connection;

public class AbstractRepository<T> {
	
	private Connection connection;
	
	public AbstractRepository(Connection connection) {
		this.connection = connection;
	}

	protected Connection getConnection() {
		return connection;
	}
	
	
	
}
