package cinema.user.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import cinema.model.Hall;
import cinema.model.Hour;
import cinema.model.Ticket;

public class UserApp {
	
	public static void main(String[] args) {
		
		Hall h1 = new Hall(4, 4, "salon 1");
		Hall h2 = new Hall(15, 15, "salon 2");
		
		List<Ticket> tickets = new ArrayList<>();
		List<Hall> halls = new ArrayList<>();
		halls.add(h1);
		halls.add(h2);
		
		tickets.add(new Ticket(h1, Hour.EIGHTEEN, 1, 1));
//		tickets.add(new Ticket(h1, Hour.EIGHTEEN, 1, 2));
		tickets.add(new Ticket(h1, Hour.EIGHTEEN, 1, 3));
		tickets.add(new Ticket(h1, Hour.EIGHTEEN, 1, 4));
		tickets.add(new Ticket(h1, Hour.EIGHTEEN, 2, 1));
		tickets.add(new Ticket(h1, Hour.EIGHTEEN, 2, 2));
//		tickets.add(new Ticket(h1, Hour.EIGHTEEN, 2, 3));
		tickets.add(new Ticket(h1, Hour.EIGHTEEN, 2, 4));
		tickets.add(new Ticket(h1, Hour.EIGHTEEN, 3, 1));
		tickets.add(new Ticket(h1, Hour.EIGHTEEN, 3, 2));
		tickets.add(new Ticket(h1, Hour.EIGHTEEN, 3, 3));
//		tickets.add(new Ticket(h1, Hour.EIGHTEEN, 3, 4));
		tickets.add(new Ticket(h1, Hour.EIGHTEEN, 4, 1));
		tickets.add(new Ticket(h1, Hour.EIGHTEEN, 4, 2));
		tickets.add(new Ticket(h1, Hour.EIGHTEEN, 4, 3));
		tickets.add(new Ticket(h1, Hour.EIGHTEEN, 4, 4));
		
//		System.out.println(h1.getSeats(tickets, Hour.EIGHTEEN, 2));
		 
		
		Scanner scanner = new Scanner(System.in);
		while(true){
			System.out.println("Select a hall:");
			for (int i = 0; i < halls.size(); i++) {
				System.out.println((i+1)+": "+halls.get(i));
			}
			Integer hallChoice = Integer.valueOf(scanner.nextLine());
			Hall chosenHall = halls.get(hallChoice-1);
			
			System.out.println("Select an hour:");
			Hour[] hours = Hour.values();
			for (int i = 0; i < hours.length; i++) {
				System.out.println((i+1)+": "+hours[i].getHour()+":00");
			}
			Integer hourChoice = Integer.valueOf(scanner.nextLine());
			Hour chosenHour = hours[hourChoice-1];
			int availableSpace = chosenHall.getAvailableSpace(tickets, chosenHour);
			if(availableSpace==0){
				System.out.println("No space available for the selected hour.");
				continue;
			}
			
			System.out.println("Enter number of tickets:");
			Integer numTickets = Integer.valueOf(scanner.nextLine());
			
			if(availableSpace<numTickets){
				System.out.println("There is not enough space for "+numTickets+" people");
				continue;
			}
			List<Ticket> contiguousSeats = chosenHall.getContiguousSeats(tickets, chosenHour, numTickets);
			if(contiguousSeats.size()>0){
				System.out.println("Tickets are listed below:");
				System.out.println(contiguousSeats);
				tickets.addAll(contiguousSeats);
				System.out.println("Ticket sale complete. The seat diagram for the hall "+chosenHall.getName()+" is shown below:");
				printSeats(chosenHall.getEmptySeats(tickets, chosenHour));
				System.out.println();
				continue;
			}
			System.out.println("No contiguous seats are available for "+numTickets+" people. Do you want to continue? (Y/N): ");
			String cont = scanner.nextLine();
			if(cont.equalsIgnoreCase("n")){
				continue;
			}
			List<Ticket> seats = h1.getSeats(tickets, chosenHour, numTickets);
			System.out.println("Tickets are listed below:");
			System.out.println(seats);
			tickets.addAll(seats);
			
			System.out.println("Ticket sale complete. The seat diagram for the hall "+chosenHall.getName()+" is shown below:");
			printSeats(chosenHall.getEmptySeats(tickets, chosenHour));
			System.out.println();
		}
		
		
	}
	
	private static void printSeats(boolean[][] seats){
		for (int i = 0; i < seats.length; i++) {
			for (int j = 0; j < seats[i].length; j++) {
				System.out.print((seats[i][j]?"#":"☺")+" ");
			}
			System.out.println();
		}
	}

}
