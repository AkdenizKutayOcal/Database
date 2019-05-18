import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		
		Scanner keyboard = new Scanner(System.in);
		UI ui = new UI();
		int operation;
		
		
		ui.loginMenuDisplay();
		operation = keyboard.nextInt();
		
		ui.loginMenuOperations(operation,keyboard);
		/*switch(operation) {
		
		
		case 1:
			
			System.out.println("Enter username and password:");
			System.out.println("Username:");
			String userName = keyboard.next();
			System.out.println("Password:");
			String password = keyboard.nextLine();
			
			ui.loginMenuActivate(userName, password);
			break;
			
		case 2:
			
			System.out.println("SignUp");
			break;
			
		case 3:
			System.out.println("Thanks for using Ticketix...");
			System.exit(0);
			break;
			
		default:
			System.out.println("Unknown Command");
			break;
		}
*/
	}

}
