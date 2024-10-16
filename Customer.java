package p1;

import java.sql.*;
import java.util.*;

public class Customer {
    String number, email, password, name;
    static Scanner sc = new Scanner(System.in);

    public void login1() throws Exception {
        System.out.println("-----Welcome------");
        System.out.println("  Plsese Login  ");
        System.out.println(" ");
        // sc.next();
        System.out.print("Enter User Name : ");
        this.name = sc.next();
        System.out.print("Enter Password : ");
        this.password = sc.next();

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "root", "");
        String sql = "select * from customer";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        // if (con != null) {
        // System.out.println("Connection");
        // }

        boolean userFound = false; // Add a flag to track whether a matching user is found

        while (rs.next()) {
            String dbUsername = rs.getString(2);
            String dbPassword = rs.getString(5);

            if (dbUsername.equalsIgnoreCase(name) && dbPassword.equalsIgnoreCase(password)) {
                System.out.println();
                System.out.println("---Login Successful---");
                System.out.println();
                function();
                userFound = true; // Set the flag to true to indicate a matching user was found
                break; // Exit the loop once a matching user is found
            }
        }

        // After the loop, check if a matching user was found
        if (!userFound) {
            System.out.println("User not Exist...");
            System.out.println("Please Register ");
            System.out.println();
        }

    }

    public void register() throws Exception {
        System.out.println("-----Welcome------");
        System.out.println("  Plsese Register  ");
        System.out.println();
        boolean isDuplicate = false;
        do {
            System.out.print("Enter User Name : ");
            this.name = sc.next();

            // Check if the username already exists in the database
            if (isUsernameTaken(name)) {
                System.out.println("Username is already in use. Please choose a different username.");
                isDuplicate = true; // Set the flag to true to continue the loop
            } else {
                isDuplicate = false; // Set the flag to false to exit the loop
            }
        } while (isDuplicate);

        boolean b;
        do {
            System.out.print("Enter Number : ");
            this.number = sc.next();
            b = numberCheck(number);
        } while (b);

        do {
            System.out.print("Enter Email :               @gamil.com\n\t\t");
            String email1 = sc.next();
            this.email = email1 + "@gmail.com";
            b = emailCheck(email1);
        } while (b);

        do {
            System.out.println("");
            System.out.println("PassWord must contain 1 Capital letter");
            System.out.println("                      1 Small letter");
            System.out.println("                      1 Symbol");
            System.out.println("                      1 Number");
            System.out.println("Password length should be minimum 8");
            System.out.print("Enter Password : ");
            this.password = sc.next();
            b = passCheck(password);
        } while (b);
        System.out.println("");
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "root", "");
        String sql = "insert into customer(name,number,email,password) values(?,?,?,?)";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, name);
        pst.setString(2, number);
        pst.setString(3, email);
        pst.setString(4, password);
        int r = pst.executeUpdate();
        if (r != 0) {
            System.out.println("Registration Succesfull");
        }
    }

    public void function() throws Exception {
        CarRental car = new CarRental();
        Boolean check = true;
        do {
            System.out.println("Enter 1. Book a Car\n      2. Exit");
            int choice = 0;
            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Please Enter Only Digits...");
                break;
            }
            switch (choice) {
                case 1:
                    car.bookCar(getCustomerId());
                    break;
                case 2:
                    System.out.print("Back to Login Page");
                    Thread.sleep(500);
                    System.out.print(".");
                    Thread.sleep(500);
                    System.out.print(".");
                    Thread.sleep(500);
                    System.out.println(".");
                    Thread.sleep(500);
                    check = false;
                    break;
                default:
                    System.out.println("Enter Valid Number");
                    break;
            }
        } while (check);
    }

    public boolean numberCheck(String num) {
        if (num.length() == 10) {
            int count = 0;
            for (int i = 0; i < 10; i++) {
                if (num.charAt(i) >= 48 && num.charAt(i) <= 57) { // number between 0 to 9
                    count++;
                }
            }
            if (count == 10) {
                return false;
            } else {
                System.out.println("Invalid number");
                return true;
            }

        } else {
            System.out.println("Invalid Number");
            return true;
        }
    }

    public boolean emailCheck(String e) {
        int count = 0;
        for (int i = 0; i < e.length(); i++) {
            if ((e.charAt(i) >= 48 && e.charAt(i) <= 57) || (e.charAt(i) >= 65 && e.charAt(i) <= 90)
                    || (e.charAt(i) >= 97 && e.charAt(i) <= 122)) {
                count++;
            }
        }
        if (count != 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean passCheck(String newPass) {
        int numCount = 0;
        int capCount = 0;
        int samCount = 0;
        int syCount = 0;
        if (newPass.length() >= 8) {
            for (int i = 0; i < newPass.length(); i++) {
                if ((newPass.charAt(i) >= 33 && newPass.charAt(i) <= 47)
                        || (newPass.charAt(i) >= 58 && newPass.charAt(i) <= 64)) {
                    syCount += 1;
                    // System.out.println("Sy count : " + syCount);
                } else if (newPass.charAt(i) >= 65 && newPass.charAt(i) <= 90) {
                    capCount += 1;
                    // System.out.println("cap count : " + capCount);
                } else if (newPass.charAt(i) >= 97 && newPass.charAt(i) <= 122) {
                    samCount += 1;
                    // System.out.println("Sam count : " + samCount);
                } else if (newPass.charAt(i) >= 48 && newPass.charAt(i) <= 57) {
                    numCount += 1;
                    // System.out.println("num count : " + numCount);
                }
            }
            if (numCount != 0 & samCount != 0 & capCount != 0 & syCount != 0) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }

    }

    String password() {
        String p = null;
        for (int i = 0; i < password.length(); i++) {
            p = p + "*";
        }
        return p;
    }

    int getCustomerId() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "root", "");
        String sql = "select * from customer where name='" + name + "' and password='" + password + "'";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        int id = 0;
        while (rs.next()) {
            id = rs.getInt(1);
        }
        return id;
    }

    private boolean isUsernameTaken(String username) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "root", "");
        String sql = "SELECT COUNT(*) FROM customer WHERE name=?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, username);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0; // If count is greater than 0, the username is taken
        }
        return false; // Username is not taken
    }
}
