import java.sql.*;

public class Connector {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";  
	static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";

	//  Database credentials
	static final String USER = "SYSTEM";
	static final String PASS = "Akdeniz98+";

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		
		try{
			//STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SYSTEM","Akdeniz98+");
			
			displayCustomers(conn);
			/*Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM CUSTOMER");
			rs.next();
			int i = 1;
			while(!rs.isAfterLast()) {
				
				System.out.println(i+rs.getString("CUSTOMER_USERNAME")+" "+rs.getString("CUSTOMER_PASSWORD"));
				i++;
				rs.next();
			}*/
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("Goodbye!");
	}//end main
	
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
}



