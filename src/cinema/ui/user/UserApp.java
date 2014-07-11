package cinema.ui.user;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import cinema.model.Hall;
import cinema.model.Hour;
import cinema.model.Ticket;
import cinema.repository.HallRepository;
import cinema.repository.TicketRepository;
import cinema.ui.PropertyReader;

public class UserApp {

	public static void main(String[] args) {


		Connection connection = null;
		HallRepository hallRepository = null;
		TicketRepository ticketRepository = null;
		try {
			connection = getConnection();
			hallRepository = new HallRepository(connection);
			ticketRepository = new TicketRepository(connection, hallRepository);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		List<Hall> halls = new ArrayList<>(hallRepository.getAll());
		List<Ticket> tickets = new ArrayList<>(ticketRepository.getAll());

		Scanner scanner = new Scanner(System.in);
		Integer choice = null;
		do{
			System.out.println("1. Show Hall status");
			System.out.println("2. Sell Ticket");
			System.out.println("3. Exit program");
			System.out.println("Choice ?");
			choice = Integer.valueOf(scanner.nextLine());
			switch (choice) {
			case 1:
				showHallStatus(scanner, halls, tickets);
				break;
			case 2:
				ticketSale(scanner, halls, tickets, ticketRepository);
				break;
			case 3:
				break;
			default:
				System.out.println("Invalid choice.");
				break;
			}
		}while(choice!=3);
		System.out.println("Goodbye.");

	}
	private static void showHallStatus(Scanner scanner, List<Hall> halls, List<Ticket> tickets){
		System.out.println("Select a hall:");
		for (int i = 0; i < halls.size(); i++) {
			System.out.println((i + 1) + ": " + halls.get(i));
		}
		Integer hallChoice = Integer.valueOf(scanner.nextLine());
		Hall chosenHall = halls.get(hallChoice - 1);

		System.out.println("Select an hour:");
		Hour[] hours = Hour.values();
		for (int i = 0; i < hours.length; i++) {
			System.out.println((i + 1) + ": " + hours[i].getHour() + ":00");
		}
		Integer hourChoice = Integer.valueOf(scanner.nextLine());
		Hour chosenHour = hours[hourChoice - 1];
		printSeats(chosenHall.getEmptySeats(tickets, chosenHour));
		
	}
	
	private static void ticketSale(Scanner scanner, List<Hall> halls, List<Ticket> tickets, TicketRepository ticketRepository){
		System.out.println("Select a hall:");
		for (int i = 0; i < halls.size(); i++) {
			System.out.println((i + 1) + ": " + halls.get(i));
		}
		Integer hallChoice = Integer.valueOf(scanner.nextLine());
		Hall chosenHall = halls.get(hallChoice - 1);

		System.out.println("Select an hour:");
		Hour[] hours = Hour.values();
		for (int i = 0; i < hours.length; i++) {
			System.out.println((i + 1) + ": " + hours[i].getHour() + ":00");
		}
		Integer hourChoice = Integer.valueOf(scanner.nextLine());
		Hour chosenHour = hours[hourChoice - 1];
		int availableSpace = chosenHall.getAvailableSpace(tickets,
				chosenHour);
		if (availableSpace == 0) {
			System.out.println("No space available for the selected hour.");
			return;
		}

		System.out.println("Enter number of tickets:");
		Integer numTickets = Integer.valueOf(scanner.nextLine());

		if (availableSpace < numTickets) {
			System.out.println("There is not enough space for "
					+ numTickets + " people");
			return;
		}
		List<Ticket> contiguousSeats = chosenHall.getContiguousSeats(
				tickets, chosenHour, numTickets);
		if (contiguousSeats.size() > 0) {
			System.out.println("Tickets are listed below:");
			System.out.println(contiguousSeats);
			for (Ticket ticket : contiguousSeats) {
				tickets.add(ticketRepository.save(ticket));
			}
			System.out
					.println("Ticket sale complete. The seat diagram for the hall "
							+ chosenHall.getName() + " is shown below:");
			printSeats(chosenHall.getEmptySeats(tickets, chosenHour));
			System.out.println();
			return;
		}
		System.out.println("No contiguous seats are available for "
				+ numTickets + " people. Do you want to continue? (Y/N): ");
		String cont = scanner.nextLine();
		if (cont.equalsIgnoreCase("n")) {
			return;
		}
		List<Ticket> seats = chosenHall.getSeats(tickets, chosenHour,
				numTickets);
		System.out.println("Tickets are listed below:");
		System.out.println(seats);
		for (Ticket ticket : seats) {
			tickets.add(ticketRepository.save(ticket));
		}

		System.out
				.println("Ticket sale complete. The seat diagram for the hall "
						+ chosenHall.getName() + " is shown below:");
		printSeats(chosenHall.getEmptySeats(tickets, chosenHour));
		System.out.println();
	}

	private static void printSeats(boolean[][] seats) {
		for (int i = 0; i < seats.length; i++) {
			for (int j = 0; j < seats[i].length; j++) {
				System.out.print((seats[i][j] ? "#" : "☺") + " ");
			}
			System.out.println();
		}
	}

	private static Connection getConnection() throws SQLException {
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
