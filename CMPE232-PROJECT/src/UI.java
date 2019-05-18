import java.util.Scanner;

public class UI {


	public void loginMenuDisplay() {

		System.out.println("Welcome to the Ticketix");
		System.out.println("Login or SignUp!");
		System.out.println("1 - Login");
		System.out.println("2 - SignUp");
		System.out.println("3 - End Program");

	}
	public void loginMenuOperations(int operation,Scanner keyboard) {

		switch(operation) {


		case 1:

			System.out.println("Enter username and password:");
			System.out.println("Username:");
			String userName = keyboard.next();
			System.out.println("Password:");
			String password = keyboard.nextLine();
			System.out.println("user"+userName+" Pass"+password);
			this.loginMenuActivate(userName, password);
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

	}
	public void loginMenuActivate(String userName,String password) {




	}
}
