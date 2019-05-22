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
		return conn;
	}

	//////////////////////////////////////////
	// Displays all Customers in database
	//////////////////////////////////////////

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
			e.printStackTrace();
		}
	}

	//////////////////////////////////////////////
	// This method checks userName and password of
	// the customer and if it finds the customer 
	// in database returns true  
	/////////////////////////////////////////////

	public static boolean checkLogin(String userName,String password,Connection conn) {

		boolean isUser = false;

		try {

			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT CUSTOMER_USERNAME,CUSTOMER_PASSWORD FROM CUSTOMER WHERE CUSTOMER_USERNAME = '"+userName+"' AND "+"CUSTOMER_PASSWORD = '"+password+"'");
			rs.next();
			if(!rs.isAfterLast()) {
				System.out.println("Username and password is correct!");
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

	/////////////////////////////////////////////////////////
	// SignUp method that inserts new Customer to the Customer
	// table. Method takes it's values from User and inserts
	/////////////////////////////////////////////////////////

	public void signUp(String customerID,String fulName,String userName,String cusPhone,String mail,String password,String cusAddress,String cusAge,Connection conn) {

		try{

			Statement st = conn.createStatement();
			String temp = "Insert into CUSTOMER (CUSTOMER_ID,CUSTOMER_FULLNAME,CUSTOMER_USERNAME,CUSTOMER_PHONE,CUSTOMER_MAIL,CUSTOMER_PASSWORD,CUSTOMER_ADDRESS,CUSTOMER_AGE) values ("+customerID+",'"+fulName+"','"+userName+"','"+cusPhone+"','"+mail+"','"+password+"','"+cusAddress+"',"+cusAge+")";

			st.executeQuery(temp);

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/////////////////////////////////////////////////
	// Takes userName as parameter and returns the 
	// customerID of given Customer
	/////////////////////////////////////////////////

	public static String getCustomerID(String userName,Connection conn) {

		String customerID = "" ;

		try {

			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT CUSTOMER_ID FROM CUSTOMER WHERE CUSTOMER_USERNAME = '"+userName+"'");
			rs.next();
			if(!rs.isAfterLast()) {
				customerID = rs.getString("CUSTOMER_ID");

			}
			else {
				System.out.println("CustomerID could not found");
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		return customerID;
	}

	//////////////////////////////////////////////////////////////
	// This method is used to Buy Ticket with Credit Card. It takes 
	// customerID, ticketID and eventPrice and it deletes given ticket
	// from TicketTable and updates the balance of Customer's CC 
	// according to given eventPrice
	//////////////////////////////////////////////////////////////

	public static void buyWithCC(Connection conn,String customerID,String ticketID,int eventPrice) {

		deleteTicket(ticketID,conn);
		updateBalance(eventPrice,customerID,conn);
		System.out.println("Ticket with code"+ticketID+"is reserved.");
		System.out.println("Ticket cost was "+eventPrice);
		System.out.println("Your new Balance is"+getCardBalance(customerID,conn));
	}


	///////////////////////////////////////////////////////////////
	// This method is used to buy ticket with Coupon. If Customer's 
	// coupon's value is less than ticket's price, they can't use that 
	// coupon. Otherwise it deletes the coupon and ticket from their
	// table and saves the leftOver amount for later purchases
	////////////////////////////////////////////////////////////////
	
	public static void buyWithCoupon(Connection conn,String customerID,String ticketID,int eventPrice) {

		String couponID = getCustomerCoupon(customerID,conn);
		int couponValue = Integer.parseInt(getCouponValue(couponID,conn));
		
		if(eventPrice>couponValue) {
			
			System.out.println("Your Coupon's value is less that ticket's price. You can't use it.");
			
		}
		else {
			
			deleteTicket(ticketID,conn);	
			deleteCoupon(couponID,conn);

			System.out.println("Ticket with code "+ticketID+" is reserved.");
			System.out.println("Ticket cost was "+eventPrice);
			System.out.println("Coupon with ID"+ couponID+ " and with value "+couponValue+ " is used.");
			int leftOver = couponValue - eventPrice;
			System.out.println("The left over "+leftOver+" is saved for later purchases");
		}
		
	}

	////////////////////////////////////////////
	// After we buy ticket, this method deletes 
	// that ticket from Ticket table
	////////////////////////////////////////////

	public static void deleteTicket(String ticketID,Connection conn) {

		try{

			Statement st = conn.createStatement();
			String query = "DELETE FROM TICKET WHERE TICKET_ID = '"+ticketID+"'";
			st.executeQuery(query);

		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	///////////////////////////////////////////////
	// After we buy ticket with Coupon, this method  
	// deletes that coupon from Coupon table
	///////////////////////////////////////////////

	public static void deleteCoupon(String couponID,Connection conn) {

		try{

			Statement st = conn.createStatement();
			String query = "DELETE FROM COUPON WHERE COUPON_ID = '"+couponID+"'";
			st.executeQuery(query);

		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	/////////////////////////////////////////////////
	// This method takes customerID as parameter and 
	// returns customer's Coupons if it exists
	/////////////////////////////////////////////////

	public static String getCustomerCoupon(String customerID,Connection conn) {

		String couponID = "" ;

		try {

			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM COUPON WHERE CUSTOMER_COUPON_ID = '"+customerID+"'");

			rs.next();

			if(!rs.isAfterLast()) {
				couponID = rs.getString("COUPON_ID");

			}
			else {
				System.out.println("Coupon could not found");
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		return couponID;


	}

	//////////////////////////////////////
	// Method returns given Coupon's value
	//////////////////////////////////////
	
	public static String getCouponValue(String couponID,Connection conn) {

		String value = "";

		try{

			Statement st = conn.createStatement();

			String query = "SELECT * FROM COUPON WHERE COUPON_ID = "+couponID;
			ResultSet rs = st.executeQuery(query);

			while(rs.next()) {

				value = rs.getString("COUPON_VALUE");

				rs.next();
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	/////////////////////////////////////////
	// Takes eventID as parameter and returns 
	// event's ticket price
	/////////////////////////////////////////

	public static String getEventPrice(String eventID,Connection conn) {

		String eventPrice = "";

		try{

			Statement st = conn.createStatement();
			String query = "SELECT EVENT_PRICE FROM EVENT WHERE EVENT_ID = '"+eventID+"'";
			ResultSet rs = st.executeQuery(query);

			rs.next();

			while(!rs.isAfterLast()) {

				eventPrice = rs.getString("EVENT_PRICE");

				rs.next();
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

		return eventPrice;
	}

	///////////////////////////////////////////////////////
	// Method takes eventPrice and CustomerID as parameters
	// and updates the balance of this customers CreditCard
	///////////////////////////////////////////////////////

	public static void updateBalance(int eventPrice,String customerID,Connection conn) {

		int newBalance = getCardBalance(customerID,conn)-eventPrice;
		System.out.println(newBalance);

		try{

			Statement st = conn.createStatement();
			String query = "UPDATE CREDIT_CARD SET BALANCE = "+newBalance+" WHERE CUSTOMER_CARD_ID = "+customerID;
			st.executeQuery(query);

		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	/////////////////////////////////////////////////////
	// Method takes customerID as parameter and returns
	// the CreditCard balance of given customer
	/////////////////////////////////////////////////////

	public static int getCardBalance(String customerID,Connection conn) {

		int balance = 0;

		try{

			Statement st = conn.createStatement();

			String query = "SELECT * FROM CREDIT_CARD WHERE CUSTOMER_CARD_ID = "+customerID;
			ResultSet rs = st.executeQuery(query);

			while(rs.next()) {

				balance = rs.getInt("BALANCE");

				rs.next();
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return balance;
	}

	///////////////////////////////////////////////////
	// This method displays all Events in the database
	///////////////////////////////////////////////////

	public static void displayEvents(Connection conn) {

		try{

			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM EVENT");

			rs.next();

			while(!rs.isAfterLast()) {


				System.out.println(rs.getString("EVENT_ID")+" | "+rs.getString("EVENT_TYPE")+" | "+rs.getString("EVENT_AGE")+" | "+rs.getString("EVENT_NAME")+" | "+rs.getString("EVENT_PRICE"));

				rs.next();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////////////
	//This helper Method returns all Tickets in Ticket table
	////////////////////////////////////////////////////////

	public static void displayTickets(Connection conn) {

		try{

			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM TICKET");

			rs.next();

			while(!rs.isAfterLast()) {

				System.out.println(rs.getString("TICKET_ID")+" | "+rs.getString("TICKET_SEAT"));

				rs.next();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	////////////////////////////////////////////////////////////////
	//This helper Method returns all CreditCards in CreditCard table
	////////////////////////////////////////////////////////////////

	public static void displayCC(Connection conn) {

		try{

			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM CREDIT_CARD");

			rs.next();

			while(!rs.isAfterLast()) {

				System.out.println(rs.getString("CARD_ID")+" | "+rs.getString("BALANCE"));

				rs.next();
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////////////
	//This helper Method returns all Coupons in Coupon table
	////////////////////////////////////////////////////////

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
			e.printStackTrace();
		}
	}
}

