package cinema.user.ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import cinema.model.Hall;
import cinema.model.Hour;
import cinema.model.Ticket;
import cinema.repository.HallRepository;
import cinema.repository.TicketRepository;

public class UserApp {

	private static final String DB_URL = "jdbc:postgresql://localhost/cinema";
	private static final String DB_USER = "postgres";
	private static final String DB_PASS = "1234";

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
		while (true) {
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
				continue;
			}

			System.out.println("Enter number of tickets:");
			Integer numTickets = Integer.valueOf(scanner.nextLine());

			if (availableSpace < numTickets) {
				System.out.println("There is not enough space for "
						+ numTickets + " people");
				continue;
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
				continue;
			}
			System.out.println("No contiguous seats are available for "
					+ numTickets + " people. Do you want to continue? (Y/N): ");
			String cont = scanner.nextLine();
			if (cont.equalsIgnoreCase("n")) {
				continue;
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
		return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	}

}
