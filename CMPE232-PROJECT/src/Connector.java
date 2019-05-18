import java.sql.*;

public class Connector {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";  
	static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";

	//  Database credentials
	static final String USER = "SYSTEM";
	static final String PASS = "Akdeniz98+";

	public static Connection createConnection() {

		Connection conn = null;

		try{
			//STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SYSTEM","Akdeniz98+");


		}catch (Exception e) {

			e.printStackTrace();
		}
		System.out.println("Goodbye!");
		return conn;
	}

	public static void displayCustomers(Connection conn) {

		try{

			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM CUSTOMER");
			rs.next();
			int i = 1;
			while(!rs.isAfterLast()) {

				System.out.println(i+"-"+rs.getString("CUSTOMER_USERNAME"));
				i++;
				rs.next();
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static boolean checkLogin(String userName,String password,Connection conn) {

		boolean isUser = false;

		try {

			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT CUSTOMER_USERNAME,CUSTOMER_PASSWORD FROM CUSTOMER WHERE CUSTOMER_USERNAME = '"+userName+"' AND "+"CUSTOMER_PASSWORD = '"+password+"'");
			rs.next();
			if(!rs.isAfterLast()) {
				System.out.println("Found");
				isUser = true;
			}
			else {
				System.out.println("User not found");
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		return isUser;
	}

	public static void displayEvents(Connection conn) {

		try{
			
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM EVENTS");
			
			rs.next();
			
			while(!rs.isAfterLast()) {
				
				
				System.out.println(rs.getString("EVENTS_ID")+" | "+rs.getString("EVENTS_TYPE")+" | "+rs.getString("EVENTS_AGE")+" | "+rs.getString("EVENTS_NAME")+" | "+rs.getString("EVENTS_PRICE"));

				rs.next();
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void displayTickets(Connection conn) {

		try{
			
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM TICKET");
			
			rs.next();
			
			while(!rs.isAfterLast()) {
				
				System.out.println(rs.getString("TICKET_ID")+rs.getString("TICKET_SEAT"));
				
				rs.next();
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void displayCC(Connection conn) {

		try{
			
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM CREDIT_CARD");
			
			rs.next();
			
			while(!rs.isAfterLast()) {
				
				System.out.println(rs.getString("CARD_ID")+rs.getString("BALANCE"));
				
				rs.next();
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void displayCoupon(Connection conn) {

		try{
			
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM COUPON");
			
			rs.next();
			
			while(!rs.isAfterLast()) {
				
				System.out.println(rs.getString("COUPON_ID")+rs.getString("COUPON_VALUE"));
				
				rs.next();
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}



