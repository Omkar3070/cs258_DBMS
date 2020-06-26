                              CS258 Coursework
        Name: Omkar Satish Gadhave           University ID: 1806161

                            Assignment.java File:
Options1, Option2 and Option3 involves storing and updating of the data related to all the orders. So in order to make this process a lot simpler, I have created two methods namely generalOptions123() and forOptions123().
generalOptions123() does all the insertions and updates that are common for Option1, Option2 and Option3 rather than writing it down every single time. The rest of the specific insertions are in each of their methods respectively.
•	Inserts the staffID and orderID into Staff_ORDERS  
•	Inserts the orderID and the productIDs for that order into ORDER_PRODUCTS.
•	Updates the Product Stock amount after an order is issued by subtracting the product quantity from the existing Stock amount.
•	If any error is encountered during these operations, the user is notified by the printed statements on the console.
forOptions123() does all the error checking required for the first three options. forOptions123() takes a Connection object and an option of type int as an argument. Depending on the option number, the required code is executed.
The possible errors while error checking that could be encountered are listed below
•	Catch any error while converting from String to int for ProductID.
•	Catch any error while converting from String to int for quantity sold.
•	Implemented a checkdate() function to check if the date entered in the case of orderDate and collectionDate is of the correct format
•	Catch any error while converting from String to int for StaffID.
I have used a function called getCurrOrderID() in Option1, Option2 and Option3 to get the order number for the latest order which is going to be inserted in the database.

                            Schema.sql File:
The schema.sql file contains the sql code used for setting up the tables for this project.
The first step involves table creation. Each attribute in the table has the basic constraints such as each table should have a Primary key. Every attribute that isn’t a Primary Key or a Foreign key cannot have a null value.
I have added the constraints which were required
•	OrderType can either be InStore, Collection or Delivery.
•	OrderCompleted can be either 0 or 1 where 0 stands for not completed and 1 for completed
Apart from these I have also added some extra constraints such as
•	The ProductID should be greater than 0 and the ProductPrice and ProductStockAmount should atleast be 0
•	The ProductQuantity should atleast be 0
Cascade Constraints is used as some of the tables are referenced by a foreign-key constraint.

                              Design Choices:
I would change some of the entries from having a INTEGER constraint to a FLOATING constraint because if the ProductID or the OrderID have a floating point value, these values would be rounded to the nearest integer. Also for ProductDesc I would change the constraint from varchar(30) to varchar(60) as product description can be long at times.
