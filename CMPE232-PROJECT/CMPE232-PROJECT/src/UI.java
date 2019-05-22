import java.sql.Connection;
import java.util.Random;
import java.util.Scanner;

public class UI {


	public void loginMenuDisplay() {

		System.out.println("Welcome to the Ticketix");
		System.out.println("Login or SignUp!");
		System.out.println("1 - Login");
		System.out.println("2 - SignUp");
		System.out.println("3 - End Program");

	}
	public String loginMenuOperations(int operation,Scanner keyboard,Connection conn,Connector cn) {

		String userNameFinal = "";
		switch(operation) {

		case 1:

			do {
				System.out.println("Enter username and password:");

				String userName = keyboard.next();
				String password = keyboard.next();


				if(cn.checkLogin(userName,password,conn)) {
					System.out.println("Welcome");
					userNameFinal = userName;
					break;
				}
				else {
					System.out.println("Wrong Password or UserName");

				}
			}while(true);

			break;

		case 2:

			System.out.println("SignUp");
			System.out.println("Enter username:");
			String userName = keyboard.next();
			System.out.println("Enter FullName:");
			String fullName = keyboard.next();
			System.out.println("Enter phoneNumber:");
			String cusPhone = keyboard.next();
			System.out.println("Enter mail:");
			String mail = keyboard.next();
			System.out.println("Enter password:");
			String password = keyboard.next();
			System.out.println("Enter Address:");
			String cusAddress = keyboard.next();
			System.out.println("Enter your age:");
			String cusAge = keyboard.next();

			int id = generateID();
			String customerID = ""+id;

			cn.signUp(customerID, fullName, userName, cusPhone, mail, password, cusAddress, cusAge, conn);
			System.out.println("Signed Up as "+userName);
			System.out.println("Welcome!");
			userNameFinal = userName;

			cn.displayCustomers(conn);
			break;

		case 3:
			System.out.println("Thanks for using Ticketix...");
			System.exit(0);
			break;

		default:
			System.out.println("Unknown Command");
			break;
		}

		return userNameFinal;

	}

	public void ticketMenuDisplay() {

		System.out.println("\nSelect Operation");
		System.out.println("1 - Buy Ticket with CreditCard");
		System.out.println("2 - Buy Ticket with Coupon");
		System.out.println("3 - End Program");

	}

	public void ticketMenuOperation(int operation,Scanner keyboard,Connection conn,Connector cn,String customerID,int eventPrice) {

		switch(operation) {

		case 1: //Credit Card

			System.out.println("Enter ticketID:");
			String ticketID = keyboard.next();
			cn.displayCC(conn);
			//cn.buyWithCC(conn, customerID, ticketID,eventPrice);

			break;

		case 2:	//Coupon

			System.out.println("Enter ticketID:");
			String ticketID2 = keyboard.next();
			cn.buyWithCoupon(conn, customerID, ticketID2,eventPrice);
			
			break;

		case 3: //exit
			
			System.out.println("Thanks for using Ticketix...");
			System.exit(0);
			break;

		default:
			System.out.println("Unknown Command");
			break;

		}

	}

	public int afterPayment(Scanner keyboard,Connector cn,Connection conn) {

		System.out.println("Thank you for using Ticketix");
		System.out.println("Do you want to continue...");
		System.out.println("1 - Yes!");
		System.out.println("2 - No!");
		int operation = keyboard.nextInt();

		return operation;		

	}

	public void eventMenuDisplay() {

		System.out.println("\nSelect Event to Buy Ticket");
		System.out.println("Enter EventID:");
	}

	public int eventMenuOperation(String eventID,Scanner keyboard,Connection conn,Connector cn) {

		int eventPrice = Integer.parseInt(cn.getEventPrice(eventID, conn));
		return eventPrice;

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
	public int generateID() {

		Random random = new Random();
		int x = random.nextInt(900) + 100;
		return x;
	}
}
