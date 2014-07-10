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
			
			System.out.println("Enter number of tickets:");
			Integer numTickets = Integer.valueOf(scanner.nextLine());
			
			int availableSpace = chosenHall.getAvailableSpace(tickets, chosenHour);
			if(availableSpace<numTickets){
				System.out.println("There is not enough space for "+numTickets+" people");
				continue;
			}
			List<Ticket> contiguousSeats = chosenHall.getContiguousSeats(tickets, chosenHour, numTickets);
			if(contiguousSeats.size()>0){
				System.out.println("Tickets are listed below:");
				System.out.println(contiguousSeats);
				tickets.addAll(contiguousSeats);
				continue;
			}
			
		}
		
		
	}
	
	private static void printSeats(boolean[][] seats){
		for (int i = 0; i < seats.length; i++) {
			for (int j = 0; j < seats[i].length; j++) {
				System.out.print(seats[i][j]+"\t");
			}
			System.out.println();
		}
	}

}
