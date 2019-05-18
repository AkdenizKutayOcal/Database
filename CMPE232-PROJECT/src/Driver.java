import java.sql.*;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		
		Scanner keyboard = new Scanner(System.in);
		UI ui = new UI();
		Connector cn = new Connector();
		int operation;
		
		Connection conn = cn.createConnection();
		
		ui.loginMenuDisplay();
		operation = keyboard.nextInt();
		
		ui.loginMenuOperations(operation,keyboard,conn,cn);
		
		//ui.showEvents(conn, cn);
		//ui.showTickets(conn, cn);
		//ui.showCC(conn, cn);
		//ui.showCoupon(conn, cn);
	
	}

}
