package pt.ulisboa.tecnico.securechildlocator.ws.monitor.io;

import java.util.Scanner;

public class Input {
	
	/** Commands available. */
	private Command commands[];
	
	public Input(Command commands[]) {
		this.commands = commands;
	}

	public void open() {
		Scanner scanner = new Scanner(System.in);
		
		while (true) {
			String command = scanner.next();
			
			if (command.equals("listLocations")) {
				commands[0].execute();
			}
			
			if (command.equals("exit")) {
				break;
			}
		}
		
		scanner.close();
	}
}
