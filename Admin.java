package p1;

import java.sql.*;
import java.util.*;

public class Admin {

    int id = 1234;
    String pass = "Admin@1234";
    Scanner sc = new Scanner(System.in);

    public void login() {
        boolean adminCheck = true;
        System.out.println();
        System.out.println("Welcome Back Admin");
        do {
            System.out.println();
            System.out.print("Enter Your I`d : ");
            try {
                int id1 = sc.nextInt();
                System.out.print("Enter Your Password : ");
                String pass1 = sc.next();
                if (id == id1 && pass.equals(pass1)) {
                    adminCheck = false;
                } else {
                    System.out.println("Enter Right Details");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input Please Enter Correct Id(integer)!");
                sc.nextLine();
            }
        } while (adminCheck);
    }

    public void changePass() {
        System.out.println("PassWord must contain 1 Capital letter");
        System.out.println("                      1 Small letter");
        System.out.println("                      1 Symbol");
        System.out.println("                      1 Number");
        System.out.println("Minimum lengh of password is 8");
        boolean passCheck = true;
        do {
            System.out.print("Enter New Password : ");
            String newPass = sc.next();
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
                    this.pass = newPass;
                    passCheck = false;
                    System.out.println();
                    System.out.println("----Password Updated----");
                    System.out.println();
                } else {
                    // System.out.println("Enter inside else");
                    System.out.println("PassWord must contain 1 Capital letter");
                    System.out.println("                      1 Small letter");
                    System.out.println("                      1 Symbol");
                    System.out.println("                      1 Number");
                }
            } else {
                System.out.println("Password length should be minimum 8");
            }
        } while (passCheck);

    }

    public void addCar() throws Exception {

        System.out.println();
        System.out.println("----Add Car----\n");
        System.out.print("Enter Car Name : ");
        String name = sc.next();

        int seat = 0;
        try {
            System.out.print("Enter No. of Seat : ");
            seat = sc.nextInt();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Please Enter Only Digits...");
            System.out.print("Enter No. of Seat : ");
            seat = sc.nextInt();
        }
        Double ppkm = 0.0;
        try {
            System.out.print("Enter Price Per Km : ");
            ppkm = sc.nextDouble();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Please Enter Only Digits...");
            System.out.print("Enter Price Per Km : ");
            ppkm = sc.nextDouble();
        }

        System.out.println();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "root", "");

        String dname = null;
        int dseat, noOfCar = 0;
        double dppkm;
        String sql = "select * from car";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        int carExist = 0;
        while (rs.next()) {
            dname = rs.getString(2);
            dseat = rs.getInt(3);
            dppkm = rs.getDouble(4);
            noOfCar = rs.getInt(5);
            if (name.equalsIgnoreCase(dname) && dseat == seat && dppkm == ppkm) {
                carExist = 1;
                break;
            }
        }
        int r;
        if (carExist == 1) {
            String sql1 = "update car set noOfCar=? where name =?";
            PreparedStatement pst = con.prepareStatement(sql1);
            pst.setString(2, dname);
            pst.setInt(1, noOfCar + 1);
            r = pst.executeUpdate();
        } else {
            String sql1 = "insert into car(name,seat,ppkm,noOfCar) values(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql1);
            pst.setString(1, name);
            pst.setInt(2, seat);
            pst.setDouble(3, ppkm);
            pst.setInt(4, 1);
            r = pst.executeUpdate();
        }
        if (r != 0) {
            System.out.print("Car Updating");
            Thread.sleep(500);
            System.out.print(".");
            Thread.sleep(500);
            System.out.print(".");
            Thread.sleep(500);
            System.out.println(".");
            Thread.sleep(500);
            System.out.println("Car Update Successfully");
        } else {
            System.out.println("Car Updation Failed");
        }
        con.close();
    }

    public void removeCar() throws Exception {
        System.out.println();
        System.out.println("----Remove Car----\n");

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "root", "");
        String sql = "select * from car";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        System.out.println("---Available Cars---");
        while (rs.next()) {
            String dname = rs.getString(2);
            System.out.print(dname + " ");
        }
        System.out.println(" ");
        System.out.print("Enter Car Name(to delete car) : ");
        String name = sc.next();

        int carExist = 0;
        int noOfCar = 0;
        String sql2 = "select * from car";
        Statement st1 = con.createStatement();
        ResultSet rs1 = st1.executeQuery(sql2);
        while (rs1.next()) {
            String dname = rs1.getString(2);
            if (name.equalsIgnoreCase(dname)) {
                noOfCar = rs1.getInt(5);
                carExist = 1;
                break;
            }
        }

        int r;
        if (carExist == 1) {
            // if noofcar is 1 to delete row else car no -1 simple
            if (noOfCar <= 1) {
                String sql1 = "delete from car where name=?";
                PreparedStatement pst = con.prepareStatement(sql1);
                pst.setString(1, name);
                r = pst.executeUpdate();
            } else {
                String sql1 = "update car set noOfCar=? where name=?";
                PreparedStatement pst = con.prepareStatement(sql1);
                pst.setInt(1, noOfCar - 1);
                pst.setString(2, name);
                r = pst.executeUpdate();
            }

            if (r != 0) {
                System.out.print("Car Removing");
                Thread.sleep(500);
                System.out.print(".");
                Thread.sleep(500);
                System.out.print(".");
                Thread.sleep(500);
                System.out.println(".");
                Thread.sleep(500);
                System.out.println("Car Removed Successfully");
            } else {
                System.out.println("Car Remove Failed");
            }
            con.close();

        } else {
            System.out.println("Car dose`t Exist... ");
        }
    }

    public static void viewCar() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "root", "");

        String sql = "select * from car";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        System.out.printf("%-5s %-15s %-5s %-8s %-5s\n", "I`d", "Name", "Seat", "PPKM", "NoOfCar");
        System.out.println(
                "-------------------------------------------------------------------------------------------------------------");
        while (rs.next()) {
            int did = rs.getInt(1);
            String dname = rs.getString(2);
            int dseat = rs.getInt(3);
            double dppkm = rs.getDouble(4);
            int noOfCar = rs.getInt(5);
            // System.out.println("-----Detail of Car-" + did + "-----");
            System.out.printf("%-5s %-15s %-5s %-8s %-5s\n", did, dname, dseat, dppkm, noOfCar);
        }
    }

    public void displayCustomer() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "root", "");

        String sql = "select * from customer";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        System.out.println(" ");
        System.out.printf("%-5s %-15s %-15s %-15s \n", "I`d", "Name", "Number", "Email");
        while (rs.next()) {
            int did = rs.getInt(1);
            String dname = rs.getString(2);
            String dnum = rs.getString(3);
            String demail = rs.getString(4);
            System.out.printf("%-5s %-15s %-15s %-15s \n", did, dname, dnum, demail);
        }
        System.out.println(" ");
        displayCars();
    }

    public void displayCars() throws Exception {
        System.out.println(" ");
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "root", "");

        String sql = "select * from bookedcar";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        System.out.println(" ");
        System.out.printf("%-5s %-8s %-10s %-10s %-10s %-15s %-5s %-12s %-6s %-10s %-5s %-5s %-5s %-5s \n", "I`d",
                "Cust_id",
                "Cust_Name", "Cust_Number", "Source", "Destination", "KM", "Date", "Car_id",
                "Car_Name", "Seat", "PPKM", "Bill", "Paid");
        System.out.println(
                "-------------------------------------------------------------------------------------------------------------");
        while (rs.next()) {
            System.out.printf("%-5s %-8s %-10s %-11s %-10s %-15s %-5s %-12s %-6s %-10s %-5s %-5s %-5s %-5s \n",
                    rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5),
                    rs.getString(6), rs.getInt(7), rs.getString(8), rs.getInt(9), rs.getString(10), rs.getInt(11),
                    rs.getDouble(12), rs.getDouble(13), rs.getString(14));
        }
        System.out.println(" ");
    }
}
