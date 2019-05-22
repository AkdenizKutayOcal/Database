import java.sql.*;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		
		Scanner keyboard = new Scanner(System.in);
		UI ui = new UI();
		Connector cn = new Connector();
		int operation;
		String customerID = "12345";
		String userName;
		Connection conn = cn.createConnection();
		
		ui.loginMenuDisplay();
		operation = keyboard.nextInt();
		userName = ui.loginMenuOperations(operation,keyboard,conn,cn);
		customerID = cn.getCustomerID(userName,conn);
		
		do {
		ui.showEvents(conn, cn);
		
		
		ui.eventMenuDisplay();
		String eventID = keyboard.next();
		int eventPrice = ui.eventMenuOperation(eventID, keyboard, conn, cn);
		
		
		ui.showTickets(conn,cn);
		ui.ticketMenuDisplay();
		operation = keyboard.nextInt();
		ui.ticketMenuOperation(operation,keyboard,conn,cn,customerID,eventPrice);
		
		operation = ui.afterPayment(keyboard, cn, conn);
		
		}while(operation == 1);
	}

}
