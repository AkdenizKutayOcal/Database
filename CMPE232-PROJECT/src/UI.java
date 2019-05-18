import java.sql.Connection;
import java.util.Scanner;

public class UI {


	public void loginMenuDisplay() {

		System.out.println("Welcome to the Ticketix");
		System.out.println("Login or SignUp!");
		System.out.println("1 - Login");
		System.out.println("2 - SignUp");
		System.out.println("3 - End Program");

	}
	public void loginMenuOperations(int operation,Scanner keyboard,Connection conn,Connector cn) {

		switch(operation) {

		case 1:

			do {
				System.out.println("Enter username and password:");

				/*String userName = keyboard.next();
			String password = keyboard.next();
				 */

				String userName = "yenilmezm";
				String password = "my8580";


				if(cn.checkLogin(userName,password,conn)) {
					System.out.println("Welcome");
					break;
				}
				else {
					System.out.println("Wrong Password or UserName");

				}
			}while(true);

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
	public void showEvents(Connection conn,Connector cn) {

		System.out.println("\n---Events Available---");
		cn.displayEvents(conn);
	}

	public void showTickets(Connection conn,Connector cn) {

		System.out.println("\n---Tickets Available---");
		cn.displayTickets(conn);
	}

	public void showCC(Connection conn,Connector cn) {

		System.out.println("\n---CC Available---");
		cn.displayCC(conn);
	}
	public void showCoupon(Connection conn,Connector cn) {

		System.out.println("\n---Coupon Available---");
		cn.displayCoupon(conn);
	}
}
