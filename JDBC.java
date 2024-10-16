package p1;

import java.sql.*;
import java.util.*;

class JDBC {
    Scanner sc = new Scanner(System.in);
    Connection con;

    JDBC() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "root", "");
    }
}
