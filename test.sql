INSERT INTO INVENTORY VALUES(1,'TV',100,8);
INSERT INTO INVENTORY VALUES(2,'Samsung',1000,25);
INSERT INTO INVENTORY VALUES(3,'Iphone',90000,70);
INSERT INTO INVENTORY VALUES(5,'Iphone+',100000,700);
INSERT INTO INVENTORY VALUES(4,'Nokia',1000,10000);
INSERT INTO INVENTORY VALUES(6,'RumChoco',2.4,500);
INSERT INTO INVENTORY VALUES(7,'Coffee',2.7,1000);
INSERT INTO INVENTORY VALUES(8,'CostaCoffee',3.2,1000);
INSERT INTO INVENTORY VALUES(9,'StarbucksCoffee',5,900);

INSERT INTO STAFF VALUES(1,'Ava','Spataru');
INSERT INTO STAFF VALUES(2,'Alex','Motoc');
INSERT INTO STAFF VALUES(3,'Irina','Neagu');
INSERT INTO STAFF VALUES(4,'Paul','Gramatovici');
INSERT INTO STAFF VALUES(5,'Alex','Cuturela');
INSERT INTO STAFF VALUES(6,'Raluca','Nae');
INSERT INTO STAFF VALUES(7,'Maryam','Rousse');
INSERT INTO STAFF VALUES(16,'ThisDude','A');
INSERT INTO STAFF VALUES(17,'ThisDude','B');
INSERT INTO STAFF VALUES(18,'ThisDude','C');
INSERT INTO STAFF VALUES(19,'ThisDude','D');
INSERT INTO STAFF VALUES(20,'ThisDude','E');
INSERT INTO STAFF VALUES(21,'ThisDude','F');
INSERT INTO STAFF VALUES(22,'ThatDude','A');
INSERT INTO STAFF VALUES(23,'ThatDude','B');
INSERT INTO STAFF VALUES(24,'ThatDude','C');
INSERT INTO STAFF VALUES(25,'ThatDude','D');
INSERT INTO STAFF VALUES(26,'ThatDude','E');
INSERT INTO STAFF VALUES(27,'ThatDude','F');
INSERT INTO STAFF VALUES(28,'ThatDude','G');



INSERT INTO ORDERS VALUES(1,'InStore',1,'10-MAY-17');
INSERT INTO STAFF_ORDERS VALUES(1,1);
INSERT INTO ORDER_PRODUCTS VALUES(1,1,1);
INSERT INTO ORDER_PRODUCTS VALUES(1,3,2);
INSERT INTO ORDER_PRODUCTS VALUES(1,5,200);

INSERT INTO ORDERS VALUES(2,'Collection',0,'11-MAY-17');
INSERT INTO STAFF_ORDERS VALUES(1,2);
INSERT INTO COLLECTIONS VALUES(2,'Curtis','Thompson','11-MAY-17');
INSERT INTO ORDER_PRODUCTS VALUES(2,1,2);
INSERT INTO ORDER_PRODUCTS VALUES(2,3,2);
INSERT INTO ORDER_PRODUCTS VALUES(2,4,10000);
INSERT INTO ORDER_PRODUCTS VALUES(2,2,2);
INSERT INTO ORDER_PRODUCTS VALUES(2,5,300);

INSERT INTO ORDERS VALUES(3,'InStore',1,'12-MAY-17');
INSERT INTO STAFF_ORDERS VALUES(2,3);
INSERT INTO ORDER_PRODUCTS VALUES(3,1,2);
INSERT INTO ORDER_PRODUCTS VALUES(3,3,2);
INSERT INTO ORDER_PRODUCTS VALUES(3,4,10000);
INSERT INTO ORDER_PRODUCTS VALUES(3,2,2);
INSERT INTO ORDER_PRODUCTS VALUES(3,5,300);

INSERT INTO ORDERS VALUES(4,'InStore',1,'10-MAY-17');
INSERT INTO STAFF_ORDERS VALUES(7,4);
INSERT INTO ORDER_PRODUCTS VALUES(4,1,5);

INSERT INTO ORDERS VALUES(5,'Delivery',0,'11-MAR-16');
INSERT INTO STAFF_ORDERS VALUES(28,5);
INSERT INTO DELIVERIES VALUES(5,'Ava','Spataru','24','Pershore','Cov','12-MAR-16');
INSERT INTO ORDER_PRODUCTS VALUES(5,8,234);

INSERT INTO ORDERS VALUES(6, 'InStore', 1, '17-Nov-17');
INSERT INTO STAFF_ORDERS VALUES(19, 6);
INSERT INTO ORDER_PRODUCTS VALUES(5, 6, 200);
INSERT INTO ORDER_PRODUCTS VALUES(5, 1, 2);