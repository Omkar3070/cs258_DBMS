import java.io.*;
import java.sql.*;
import java.util.ArrayList;
//import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.time.LocalDate;
//import java.util.Calender;

class Assignment {

	//private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");

	private static String readEntry(String prompt) {
		try {
			StringBuffer buffer = new StringBuffer();
			System.out.print(prompt);
			System.out.flush();
			int c = System.in.read();
			while(c != '\n' && c != -1) {
				buffer.append((char)c);
				c = System.in.read();
			}
			return buffer.toString().trim();
		} catch (IOException e) {
			return "";
		}
 	}

	public static boolean checkDate(String Date){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
				try{
					dateFormat.parse(Date);
				}catch(ParseException q){
							return false;
				}
				return true;
	}


	/**
	* @param conn An open database connection
	* @param productIDs An array of productIDs associated with an order
  * @param quantities An array of quantities of a product. The index of a quantity correspeonds with an index in productIDs
	* @param orderDate A string in the form of 'DD-Mon-YY' that represents the date the order was made
	* @param staffID The id of the staff member who sold the order
	*/
	public static void option1(Connection conn, int[] productIDs, int[] quantities, String orderDate, int staffID) {
		// Incomplete - Code for option 1 goes here
			Statement stmt;
			String insertSql;

			//get the order IDs and insert in ORDERS
			int orderID = getCurrOrderID(conn);
			insertSql = "INSERT INTO ORDERS VALUES(" + orderID + ", 'InStore',1,'" + orderDate + "')";

			try{
				stmt = conn.createStatement();
				stmt.executeUpdate(insertSql);
				stmt.close();
			}catch(SQLException q){
				System.err.println("Error updating ORDERS");
			}
			generalOptions123(conn, productIDs, quantities, orderID, staffID);
	}

	/**
	* @param conn An open database connection
	* @param productIDs An array of productIDs associated with an order
  * @param quantities An array of quantities of a product. The index of a quantity correspeonds with an index in productIDs
	* @param orderDate A string in the form of 'DD-Mon-YY' that represents the date the order was made
	* @param collectionDate A string in the form of 'DD-Mon-YY' that represents the date the order will be collected
	* @param fName The first name of the customer who will collect the order
	* @param LName The last name of the customer who will collect the order
	* @param staffID The id of the staff member who sold the order
	*/
	public static void option2(Connection conn, int[] productIDs, int[] quantities, String orderDate, String collectionDate, String fName, String LName, int staffID) {
		// Incomplete - Code for option 2 goes here
		Statement stmt;
		String insertSql;

		int orderID = getCurrOrderID(conn);
		insertSql = "INSERT INTO ORDERS VALUES(" + orderID + ", 'Collection',0,'" + orderDate + "')";

		try{
			stmt = conn.createStatement();
			stmt.executeUpdate(insertSql);
			stmt.close();
		}catch(SQLException q){
			System.err.println("Error updating ORDERS");
		}

		 //Insert into collections
		 insertSql = "INSERT INTO COLLECTIONS VALUES(" + orderID + ",'" + fName + "', '" + LName + "', '" + collectionDate + "')";

		 try{
 			stmt = conn.createStatement();
 			stmt.executeUpdate(insertSql);
 			stmt.close();
 		}catch(SQLException q){
 			System.err.println("Error updating COLLECTIONS");
 		}
		generalOptions123(conn, productIDs, quantities, orderID, staffID);
	}

	/**
	* @param conn An open database connection
	* @param productIDs An array of productIDs associated with an order
        * @param quantities An array of quantities of a product. The index of a quantity correspeonds with an index in productIDs
	* @param orderDate A string in the form of 'DD-Mon-YY' that represents the date the order was made
	* @param deliveryDate A string in the form of 'DD-Mon-YY' that represents the date the order will be delivered
	* @param fName The first name of the customer who will receive the order
	* @param LName The last name of the customer who will receive the order
	* @param house The house name or number of the delivery address
	* @param street The street name of the delivery address
	* @param city The city name of the delivery address
	* @param staffID The id of the staff member who sold the order
	*/
	public static void option3(Connection conn, int[] productIDs, int[] quantities, String orderDate, String deliveryDate, String fName, String LName,
				   String house, String street, String city, int staffID) {
		// Incomplete - Code for option 3 goes here
		Statement stmt;
		String insertSql;
		int orderID = getCurrOrderID(conn);
		insertSql = "INSERT INTO ORDERS VALUES(" + orderID + ", 'Collection',0,'" + orderDate + "')";

		try{
			stmt = conn.createStatement();
			stmt.executeUpdate(insertSql);
			stmt.close();
		}catch(SQLException q){
			System.err.println("Error updating ORDERS");
		}

		//insert into Deliveries
		insertSql = "INSERT INTO DELIVERIES VALUES(" + orderID + ", '" + fName + "', '" + LName + "', '" + house + "', '" + street + "', '" + city + "', '" + deliveryDate + "')";

		try{
		 stmt = conn.createStatement();
		 stmt.executeUpdate(insertSql);
		 stmt.close();
	 }catch(SQLException q){
		 System.err.println("Error updating DELIVERIES");
	 }
	 generalOptions123(conn, productIDs, quantities, orderID, staffID);
	}

	/**
	* @param conn An open database connection
	*/
	public static void option4(Connection conn) {
		// Incomplete - Code for option 4 goes here
		Statement stmt;
		String insertSql;
		ResultSet result;

		//The nested(inner) query returns rows containing ProductID, ProductDesc, ProductPrice,
		//and the total quantity of a particular product which is sold. Then after the outer query
		//returns a table of ProductID, ProductDesc and the TotalValueSold which is the cost of the
		//total amount of products sold in Descending order.
		insertSql = "SELECT ProductID, ProductDesc, ProductPrice * NumOfSold " +
												"AS TotalValueSold FROM " +
												"(" +
																"SELECT INVENTORY.ProductID AS ProductID, INVENTORY.ProductDesc AS ProductDesc, INVENTORY.ProductPrice AS ProductPrice, SUM(ORDER_PRODUCTS.ProductQuantity) AS NumOfSold " +
																"FROM INVENTORY " +
																			"INNER JOIN ORDER_PRODUCTS " +
																						"ON INVENTORY.ProductID = ORDER_PRODUCTS.ProductID " +
																"GROUP BY INVENTORY.ProductID, INVENTORY.ProductDesc, INVENTORY.ProductPrice" +
												")" +
												"ORDER BY TotalValueSold DESC";

			try{
				stmt = conn.createStatement();
				result = stmt.executeQuery(insertSql);
				System.out.println("ProductID, ProductDesc,  TotalValueSold");
				while(result.next()){
						System.out.println(result.getString(1) + ",       " + result.getString(2) + ",       £" + result.getString(3));
				}
				stmt.close();
			}catch(SQLException q){
				System.err.println("Error displaying the table");
			}
	}

	/**
	* @param conn An open database connection
	* @param date The target date to test collection deliveries against
	*/
	public static void option5(Connection conn, String date) {
		// Incomplete - Code for option 5 goes here
		PreparedStatement stmt = null;
		String insertSql1;
		String insertSql2;
		String insertSql3;
		ResultSet result;
		int OrdID;
		int OrderID;
		ArrayList<Integer> orderIDList;

		//getting the orderID of the orders to be cancelled.
		insertSql1 = "Select ORDERS.OrderID " +
							 	 "FROM ORDERS " +
								 		"INNER JOIN COLLECTIONS " +
								 				"ON ORDERS.OrderID = COLLECTIONS.OrderID " +
								 "WHERE ORDERS.OrderType = 'Collection' AND ORDERS.OrderCompleted = 0 AND (TO_DATE(?, 'DD-Mon-YY') - COLLECTIONS.collectionDate) >= 8 ";

		orderIDList = new ArrayList<Integer>();
		try{
						stmt = conn.prepareStatement(insertSql1);
						stmt.setString(1, date);
						result = stmt.executeQuery();
						while(result.next()){
							OrdID = Integer.parseInt(result.getString(1));
							orderIDList.add(OrdID);
						}
					//	result.close();
						stmt.close();
		}catch(SQLException q){
			System.err.format("SQL State: %s\n%s", q.getSQLState(), q.getMessage());
			q.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}

		for(int i = 0; i < orderIDList.size(); i++){
			OrderID = orderIDList.get(i);

			//Update the product stock amount by adding the number of the product quantity
			//of the orders that are cancelled
			insertSql2 = "UPDATE"
									+"("
									+"SELECT INVENTORY.ProductStockAmount AS StockAmount, ORDER_PRODUCTS.ProductQuantity AS ProductQuantity "
									+"FROM INVENTORY "
									+"INNER JOIN ORDER_PRODUCTS "
									+"ON INVENTORY.ProductID = ORDER_PRODUCTS.ProductID "
									+"WHERE ORDER_PRODUCTS.OrderID = ?" + ") temp "
									+"SET temp.StockAmount = temp.StockAmount + temp.ProductQuantity ";

		try{
						stmt = conn.prepareStatement(insertSql2);
						stmt.setInt(1, OrderID);
						stmt.executeQuery();
						stmt.close();
		}catch(SQLException q){
			System.err.format("SQL State: %s\n%s", q.getSQLState(), q.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}

			//Remove the data of the orders that are cancelled from the ORDERS table.
			//Delete only from orders as other tables are linked to it. even still it
			//throws violated - child record found. I couldnt figure out why this is the case
			insertSql3 = "DELETE FROM ORDERS WHERE OrderID = ? ";

		try{
						stmt = conn.prepareStatement(insertSql3);
						stmt.setInt(1, OrderID);
						stmt.executeQuery();
						stmt.close();
		}catch(SQLException q){
			System.err.format("SQL State: %s\n%s", q.getSQLState(), q.getMessage());
			}catch(Exception e){
				e.printStackTrace();
			}
			System.out.println("Order " + OrderID + " has been cancelled");
		}
	}

	/**
	* @param conn An open database connection
	*/
	public static void option6(Connection conn) {
		// Incomplete - Code for option 6 goes here
		Statement stmt;
		String insertSql;
		ResultSet result;

		//get the staff first name and last name who have sold more products
		//worth more than £50000.
		insertSql = "SELECT STAFF.FName, STAFF.LName, SUM(INVENTORY.ProductPrice * ORDER_PRODUCTS.ProductQuantity) AS TotalValueSold " +
												 "FROM STAFF " +
												 				"INNER JOIN STAFF_ORDERS " +
																		"ON STAFF.StaffID = STAFF_ORDERS.StaffID " +
																"INNER JOIN ORDER_PRODUCTS " +
																		"ON STAFF_ORDERS.OrderID = ORDER_PRODUCTS.OrderID " +
																"INNER JOIN INVENTORY " +
																		"ON ORDER_PRODUCTS.ProductID = INVENTORY.ProductID " +
													"GROUP BY STAFF.StaffID, staff.FName, staff.LName " +
													"HAVING SUM(INVENTORY.ProductPrice * ORDER_PRODUCTS.ProductQuantity) >= 50000 " +
													"ORDER BY TotalValueSold DESC";

		try{
					stmt = conn.createStatement();
					result = stmt.executeQuery(insertSql);
					System.out.println("EmployeeName,   TotalValueSold");

					while(result.next()) {
						System.out.println(result.getString(1) + " " + result.getString(2) + ",     £" + result.getString(3));
					}
					stmt.close();
		}catch(SQLException q){
						q.printStackTrace();
						System.err.println("Error while selecting");
		}
	}

	/**
	* @param conn An open database connection
	*/

	//For this option Daisy started crashing so couldnt test it the insertion
	//was working
	public static void option7(Connection conn) {
		// Incomplete - Code for option 7 goes here
		Statement stmt;
		ResultSet result;
		String insertSql;
	//	ArrayList<Integer> productIDList = new ArrayList<Integer>();

		//In this query the result is sorted in a descending order based on the
		//total monetary value of the high selling products and then by product quantity
		insertSql = "SELECT s.iProductID, s1.fName, s1.lName, s1.Staffsold " +
								"FROM ( " +
								"SELECT INVENTORY.ProductID AS iProductID, SUM(INVENTORY.ProductPrice * ORDER_PRODUCTS.ProductQuantity) AS Totalmade " +
								"FROM INVENTORY " +
										"INNER JOIN ORDER_PRODUCTS " +
												"ON INVENTORY.ProductID = ORDER_PRODUCTS.ProductID " +
								"WHERE SUM(INVENTORY.ProductPrice * ORDER_PRODUCTS.ProductQuantity) > 20000 " +
								"ORDER BY Totalmade DESC" +
								") s" +
								"INNER JOIN (" +
								"SELECT STAFF.FName AS fName, STAFF.LName AS lName, ORDER_PRODUCTS.ProductID as opProductID, SUM(ORDER_PRODUCTS.ProductQuantity) AS Staffsold " +
								"FROM STAFF " +
										"INNER JOIN STAFF_ORDERS " +
												 "ON STAFF.StaffID = STAFF_ORDERS.StaffID " +
										"INNER JOIN ORDER_PRODUCTS " +
												 "ON STAFF_ORDERS.OrderID = ORDER_PRODUCTS.OrderID " +
								"WHERE ORDER_PRODUCTS.ProductID IN ( " +
								"SELECT INVENTORY.ProductID " +
								"FROM INVENTORY " +
										"INNER JOIN ORDER_PRODUCTS " +
												 "ON INVENTORY.ProductID = ORDER_PRODUCTS.ProductID " +
								"GROUP BY INVENTORY.ProductID " +
								"HAVING SUM(INVENTORY.ProductPrice * ORDER_PRODUCTS.ProductQuantity) > 20000 )" +
								"GROUP BY STAFF.FName, STAFF.LName, StaffID, ORDER_PRODUCTS.ProductID " +
								"ORDER BY Totalmade DESC) s1 " +
								"ON s.ProductID = s1.opProductID";

				try{
							stmt = conn.createStatement();
							result = stmt.executeQuery(insertSql);
							while(result.next()){
								System.out.println(result.getString(1) + " | " + result.getString(2) + " " + result.getString(3) + " | " + result.getInt(4));
							}
							stmt.close();
						}catch(SQLException q){
							q.printStackTrace();
							System.err.println("Error while Selecting");
						}
}

	/**
	* @param conn An open database connection
	* @param year The target year we match employee and product sales against
	*/

	//For this option daisy started crashing so couldnt test if the insertion was working.
	public static void option8(Connection conn, int year) {
		// Incomplete - Code for option 8 goes here
		PreparedStatement preStmt = null;
		ResultSet result;
		String insertSql;

		//In this query, the first table gets the names of the employees who have sold atleast
		//one of the the products that made atleast £20000 in sales.
		//The second table gets the names of the employees who sold over £30000 worth of products.
		//The we take the intersection of these two tables using Natural join.
		insertSql = "SELECT * " +
								"FROM ( " +
								"SELECT STAFF.FName, STAFF.LName " +
								"FROM INVENTORY " +
										"INNER JOIN ORDER_PRODUCTS " +
												"ON INVENTORY.ProductID = ORDER_PRODUCTS.ProductID " +
										"INNER JOIN STAFF_ORDERS " +
												"ON ORDER_PRODUCTS.OrderID = STAFF_ORDERS.OrderID " +
										"INNER JOIN STAFF " +
												"ON STAFF_ORDERS.StaffID = STAFF.StaffID " +
										"INNER JOIN ORDERS " +
												"ON STAFF_ORDERS.OrderID = ORDERS.OrderID " +
										"WHERE OrderPlaced >= ? AND OrderPlaced < ? " +
										"GROUP BY INVENTORY.ProductID, STAFF.FName, STAFF.LName " +
										"HAVING SUM(INVENTORY.ProductPrice * ORDER_PRODUCTS.ProductQuantity) > 20000) " +
										"NATURAL JOIN (SELECT STAFF.FName, STAFF.LName " +
										"FROM INVENTORY " +
										"INNER JOIN ORDER_PRODUCTS " +
												"ON INVENTORY.ProductID = ORDER_PRODUCTS.ProductID " +
										"INNER JOIN STAFF_ORDERS " +
												"ON ORDER_PRODUCTS.OrderID = STAFF_ORDERS.OrderID " +
										"INNER JOIN STAFF " +
												"ON STAFF_ORDERS.StaffID = STAFF.StaffID " +
										"INNER JOIN ORDERS " +
												"ON STAFF_ORDERS.OrderID = ORDERS.OrderID " +
										"WHERE OrderPlaced >= ? AND OrderPlaced < ? " +
										"GROUP BY STAFF.FName, STAFF.LName " +
										"HAVING SUM(INVENTORY.ProductPrice * ORDER_PRODUCTS.ProductQuantity) >= 30000)";

						try{
								//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
										preStmt = conn.prepareStatement(insertSql);
										String date = Integer.toString(year) + "-01-01";
										String date1 = Integer.toString(year + 1) + "-01-01";
										LocalDate localDate = LocalDate.parse(date);
										LocalDate localDate1 = LocalDate.parse(date1);

										preStmt.setDate(1, Date.valueOf(localDate));
										preStmt.setDate(2, Date.valueOf(localDate1));
										preStmt.setDate(3, Date.valueOf(localDate));
										preStmt.setDate(4, Date.valueOf(localDate1));

										result = preStmt.executeQuery();

										while(result.next()){
											System.out.println(result.getString(1) + " " + result.getString(2));
										}
										preStmt.close();

						}catch(SQLException q){
							System.err.format("SQL State: %s\n%s", q.getSQLState(), q.getMessage());
						}catch(Exception e){
							e.printStackTrace();
						}

	}

	public static Connection getConnection() {

		// User and password should be left blank. Do not alter!
		String user = "";
        	String passwrd = "";
        	Connection conn;

	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	        } catch (ClassNotFoundException x) {
	            System.out.println("Driver could not be loaded");
	        }

	        try {
	            conn = DriverManager.getConnection("jdbc:oracle:thin:@arryn-ora-prod-db-1.warwick.ac.uk:1521:cs2db",user,passwrd);
	            return conn;
	        } catch(SQLException e) {
	            System.out.println("Error retrieving connection");
	            return null;
	        }

	}

	public static void main(String args[]) throws SQLException, IOException {
		// You should only need to fetch the connection details once
		Connection conn = getConnection();
		displayMenu(conn);
		// Incomplete
		// Code to present a looping menu, read in input data and call the appropriate option menu goes here
		// You may use readEntry to retrieve input data

		conn.close();
	}

	//This method gets the order number for the latest order
	//which is going to be inserted in the database
	public static int getCurrOrderID(Connection conn){
		Statement stmt;
		String insertSql;
		ResultSet result;
		int orderID = 1;
		insertSql = "SELECT MAX(OrderID) FROM ORDERS";

		try{
					stmt = conn.createStatement();
					result = stmt.executeQuery(insertSql);
					while(result.next()){
						orderID = result.getInt("MAX(OrderID)") + 1;
					}
					stmt.close();
		}catch(SQLException q){
			System.err.println("Error getting OrderID");
		}
		return orderID;
	}

	//This method performs insertions and updates that are common for options 1, 2 and 3.
	//The ProductStockAmount is updated and displayed on the screen along with insertions
	//performed into STAFF_ORDERS and ORDER_PRODUCTS. Error handling is done in case the
	//insertions and updates do not take place as intended.
	public static void generalOptions123(Connection conn, int[] productIDs, int[] quantitiesP, int orderID, int staffID){
		Statement stmt;
		String insertSql;
		ResultSet result;

		insertSql = "INSERT INTO STAFF_ORDERS VALUES (" + staffID + ", " + orderID + ")";

		try{
					stmt = conn.createStatement();
					stmt.executeUpdate(insertSql);
					stmt.close();
		}catch(SQLException q){
					System.err.println("Error updating STAFF_ORDERS");
		}

		for(int i = 0; i < productIDs.length; i++){
			insertSql = "INSERT INTO ORDER_PRODUCTS VALUES (" + orderID + ", " + productIDs[i] + ", " + quantitiesP[i] + ")";

			try{
						stmt = conn.createStatement();
						stmt.executeUpdate(insertSql);
						stmt.close();
			}catch(SQLException q){
						System.err.println("Error updating ORDER_PRODUCTS");
			}

			insertSql = "UPDATE INVENTORY SET ProductStockAmount = ProductStockAmount - " + quantitiesP[i] + " WHERE ProductID = " + productIDs[i];

			try{
						stmt = conn.createStatement();
						stmt.executeUpdate(insertSql);
						stmt.close();
			}catch(SQLException q){
						System.err.println("Error updating INVENTORY");
			}
		}

		for(int i = 0; i < productIDs.length; i++){
			String printConn = "SELECT ProductID, ProductStockAmount FROM INVENTORY WHERE ProductID = " + productIDs[i];

				try{
					stmt = conn.createStatement();
					result = stmt.executeQuery(printConn);
					while(result.next()){
						System.out.println("Product ID " + result.getString(1) +" stock is now " + result.getString(2));
					}
					stmt.close();
				}catch(SQLException q) {
					System.err.println("Error with product IDs and product stock amount");
				}
		}
	}

	//Used for showing the menu. Depending on the user choice,
	//the corresponding options are executed.
	public static void displayMenu(Connection conn){
		String date = " ";
		String userEntry = " ";
		//while(!userEntry.equals("0")){
		do{
		System.out.println("MENU");
		System.out.println("(1) In-Store Purchases");
		System.out.println("(2) Collection");
		System.out.println("(3) Delivery");
		System.out.println("(4) Biggest Seller");
		System.out.println("(5) Reserved Stock");
		System.out.println("(6) Staff Life-Time Success");
		System.out.println("(7) Staff Contribution");
		System.out.println("(8) Employees of the Year");
		System.out.println("(0) Quit");
		userEntry = readEntry("Enter your choice: ");
		if(userEntry.equals("1")){
			forOptions123(conn, 1);
		}else if(userEntry.equals("2")){
			forOptions123(conn, 2);
		}else if(userEntry.equals("3")){
			forOptions123(conn, 3);
		}else if(userEntry.equals("4")){
			option4(conn);
		}else if(userEntry.equals("5")){
			while(!checkDate(date)){
			date = readEntry("Enter the date: ");
		}
			option5(conn, date);
		}else if(userEntry.equals("6")){
			option6(conn);
		}else if(userEntry.equals("7")){
			option7(conn);
		}else if(userEntry.equals("8")){
			int year = 0;
			//String myYear = readEntry("Enter the year: ");
			String myYear = " ";
			while(true){
				myYear = readEntry("Enter the year: ");
			try{
				year = Integer.parseInt(myYear);
			}catch(NumberFormatException e){
				System.out.println("Year invalid");
			}if((year > 1000) && (year < 10000)){
				break;
			}else{
				System.out.println("Year invalid");
				readEntry("Enter the year: ");
			}
		}
		option8(conn,year);
	}else if(userEntry.equals("0")){
		System.out.println("Quit is selected");
	}else{
		System.out.println("Invalid input, Please enter the correct number");
	}
	}while(!userEntry.equals("0"));
	}

	//This function prepares the variables to be used for option 1,
	//option 2 and option 3
	public static void forOptions123(Connection conn, int option){
		ArrayList<Integer> productArray = new ArrayList<Integer>();
		ArrayList<Integer> quantitiesArray = new ArrayList<Integer>();
		int productID = 0;
		int quantity = 0;
		int staffID = 0;
		String fName = " ";
		String lName = " ";
		String orderDate = " ";
		String collectionDate = " ";
		String dateCollected = " ";
		String nameF = " ";
		String nameL = " ";
		String nameH = " ";
		String nameS = " ";
		String nameC = " ";


		if(option == 1 || option == 2 || option == 3){
		String anotherProduct = "Y";
		while(anotherProduct.equals("Y")){
			while(true){
						 try{
									productID = Integer.parseInt(readEntry("Enter a product ID: "));
									break;
						}catch(NumberFormatException q){
									System.out.println("Please enter a valid Product ID");
						}
			}
			//Add productIDs to the arraylist if it is valid.
			productArray.add(productID);

			while(true){
				     try{
							 		quantity = Integer.parseInt(readEntry("Enter the quantity sold: "));
									break;
						 }catch(NumberFormatException q){
							 		 System.out.println("Enter the correct amount of the quantity bought");
						 }
			}
			//Add quantity to the arraylist if it is valid.
			quantitiesArray.add(quantity);
			anotherProduct = readEntry("Is there another product in the order?: ");
		//If input is invalid, re-prompt the user.
		if(!anotherProduct.equals("Y") && !anotherProduct.equals("N")){
			while(!anotherProduct.equals("Y") && !anotherProduct.equals("N")){
				System.out.println("Invalid Input, Please enter Y for yes and N for no to proceed");
				anotherProduct = readEntry("Is there another product in the order?: ");
			  }
			}
		}
		//If the format for the date is wrong, reprompt the user.
		orderDate = readEntry("Enter the date sold: ");
		while(checkDate(orderDate) == false){
			System.out.println("Incorrect Format, Please enter the date in dd-MMM-yy format");
			orderDate = readEntry("Enter the date sold: ");
		}

		if(option == 2){
		collectionDate = readEntry("Enter the date of collection: ");
		while(checkDate(collectionDate) == false){
			System.out.println("Incorrect Format, Please enter the date in dd-MMM-yy format");
			collectionDate = readEntry("Enter the date of collection: ");
		}
		fName = readEntry("Enter the first name of the collector: ");
		lName = readEntry("Enter the last name of the collector: ");
	}

	if(option == 3){
		dateCollected = readEntry("Enter the date of collection: ");
		while(checkDate(dateCollected) == false){
			System.out.println("Incorrect Format, Please enter the date in dd-MMM-yy format");
			dateCollected = readEntry("Enter the date of collection: ");
		}
	 	nameF = readEntry("Enter the first name of the recipient: ");
	 	nameL = readEntry("Enter the last name of the recipient: ");
		nameH = readEntry("Enter the house name/no : ");
		nameS = readEntry("Enter the street: ");
		nameC = readEntry("Enter the City: ");
	}
		//Re-prompt the user until a valid staffID is entered.
		while(true){
			try{
				staffID = Integer.parseInt(readEntry("Enter your staff ID: "));
				break;
			}catch(NumberFormatException q){
				System.out.println("Please enter a valid staff ID");
			}
		}

		int[] productIDs = new int[productArray.size()];
		int[] quantitiesP = new int[quantitiesArray.size()];

		//Copy the contents of the arraylists into the static arrays
		//so that the arrays can be passed as argument to the option methods.
		for(int i = 0; i < productArray.size(); i++){
			productIDs[i] = productArray.get(i);
			quantitiesP[i] = quantitiesArray.get(i);
		}
		if(option == 1){
		option1(conn, productIDs, quantitiesP, orderDate, staffID);
		}else if(option == 2){
		option2(conn, productIDs, quantitiesP, orderDate, collectionDate, fName, lName, staffID);
		}else{
		option3(conn, productIDs, quantitiesP, orderDate, dateCollected, nameF, nameL, nameH, nameS, nameC, staffID);
	}
	}
}
}
