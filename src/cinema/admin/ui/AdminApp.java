package cinema.admin.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cinema.model.Hall;

public class AdminApp {
	
	public static void main(String[] args) {
		List<Hall> halls = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);
		String current = "";
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
			halls.add(hall);
			System.out.println("Hall created.");			
		}
		scanner.close();
		
		System.out.println(halls);
		
	}

}
