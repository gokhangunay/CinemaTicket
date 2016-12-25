package cinema.ui.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cinema.model.Hall;
import cinema.repository.HallRepository;
import cinema.ui.PropertyReader;

public class AdminApp {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String current = "";
		Connection connection;
		HallRepository repository = null;
		try {
			connection = getConnection();
			repository = new HallRepository(connection);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		List<Hall> halls = new ArrayList<Hall>(repository.getAll());

		while(true){
			System.out.println("Enter a hall (rowsxcols):");
			current = scanner.nextLine();
			if(current.equals("")){
				break;
			}
			String[] splitted = current.split("x");
			int rows = Integer.valueOf(splitted[0]);
			int cols = Integer.valueOf(splitted[1]);
			System.out.println("Enter a name for this hall:");
			String name = scanner.nextLine();
			
			Hall hall = new Hall(rows, cols, name);
			Hall savedHall = repository.saveHall(hall);
			halls.add(savedHall);
			System.out.println("Hall created.");			
		}
		scanner.close();
		
		System.out.println(halls);
		
	}
	
	private static Connection getConnection() throws SQLException{
		try {
			Class.forName("org.postgresql.Driver"); 
			System.out.println("Driver loaded.");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
		PropertyReader propertyReader = null;
		try {
			propertyReader = new PropertyReader();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return DriverManager.getConnection(propertyReader.getDBURL(), 
				propertyReader.getDBUser(), 
				propertyReader.getDBPassword());
	}

}
