package p1;

import java.sql.*;
import java.util.*;

public class CarRental {
    int seat, c_id, sc_id;
    String c_name, c_number, sc_name, source, destination, date;
    double ppkm, bill;
    int km;
    Scanner sc = new Scanner(System.in);

    public void bookCar(int c_id) throws Exception {
        Admin.viewCar();
        this.c_id = c_id;

        System.out.println();

        try {
            System.out.print("Enter Car Id : ");
            this.sc_id = sc.nextInt();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Please Enter Only Digits...");
        }
        boolean available = false;
        int noOfCar = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "root", "");

        String sql = "select * from car";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            if (rs.getInt(1) == sc_id) {
                this.sc_id = rs.getInt(1);
                this.sc_name = rs.getString(2);
                this.seat = rs.getInt(3);
                this.ppkm = rs.getDouble(4);
                noOfCar = rs.getInt(5);
                available = true;
            }
        }
        if (available) {
            if (noOfCar <= 0) {
                System.out.println("Car Not Available...");
            } else {
                String sql1 = "update car set noOfCar=? where id=?";
                PreparedStatement pst = con.prepareStatement(sql1);
                pst.setInt(1, noOfCar - 1);
                pst.setInt(2, sc_id);
                int r = pst.executeUpdate();

                String sql2 = "select * from customer where id=" + c_id;
                Statement st1 = con.createStatement();
                ResultSet rs1 = st1.executeQuery(sql2);
                while (rs1.next()) {
                    this.c_name = rs1.getString(2);
                    this.c_number = rs1.getString(3);
                }
                displayCarRental();
                source();
                bill();
                payment();
            }
        } else {
            System.out.println("Car Not Available...");
        }
    }

    public void source() {
        boolean dateCheck = true;
        String date1;
        sc.nextLine();
        do {
            System.out.print("Enter Date(yyyy/mm/dd) : ");
            date1 = sc.nextLine();
            dateCheck = dateCheck(date1);
        } while (dateCheck);
        this.date = date1;
        System.out.print("Enter Source : ");
        this.source = sc.nextLine();

        System.out.print("Enter Destination : ");
        this.destination = sc.nextLine();
        System.out.print("Enter distence(km) between these locations : ");
        this.km = sc.nextInt();
        System.out.println("Date : " + date);
        System.out.println("Source : " + source);
        System.out.println("Destination : " + destination);
        System.out.println("Distance : " + km + " km");
    }

    public void displayCarRental() {
        System.out.println("  ");
        System.out.println("Customer Name : " + c_name);
        System.out.println("Customer Number : " + c_number);
        System.out.println("Selected Car Name : " + sc_name);
        System.out.println("Seater : " + seat);
        System.out.println("Price Per Km : " + ppkm);
    }

    public void bill() throws Exception {
        Double bill1 = ppkm * km;
        System.out.println("Bill (without GST): " + bill1);
        double bill2 = bill1 + bill1 * 0.18;
        this.bill = bill2;
        System.out.println("Bill(with GST) : " + bill);
        view();
    }

    public void payment() {
        System.out.println("Enter 1. Cash\n      2. Credit/Debit Card\n      3. UPI/PayTm/PhonePay/GPay");
        int choice = sc.nextInt();
        boolean paymentCheck = true;
        do {
            switch (choice) {
                case 1:
                    displayCarRental();
                    System.out.println("Date : " + date);
                    System.out.println("Source : " + source);
                    System.out.println("Destination : " + destination);
                    System.out.println("Distance : " + km + " km");
                    System.out.println("Bill(with GST) : " + bill);
                    System.out.println("Thank you...(*_*)");
                    paymentCheck = false;
                    break;
                case 2:
                    creditCard();
                    paymentCheck = false;
                    break;
                case 3:
                    upi();
                    paymentCheck = false;
                    break;
                default:
                    System.out.println("Enter Valid Number...");
            }
        } while (paymentCheck);
    }

    public void view() throws Exception {
        String sql = "insert into bookedcar(c_id,c_name,c_number,source,destination,km,date,sc_id,sc_name,sc_seat,sc_ppkm,bill,bill_paid) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CarRental", "root", "");
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, c_id);
        pst.setString(2, c_name);
        pst.setString(3, c_number);
        pst.setString(4, source);
        pst.setString(5, destination);
        pst.setInt(6, km);
        pst.setString(7, date);
        pst.setInt(8, sc_id);
        pst.setString(9, sc_name);
        pst.setInt(10, seat);
        pst.setDouble(11, ppkm);
        pst.setDouble(12, bill);
        pst.setString(13, "YES");
        int r = pst.executeUpdate();
    }

    public void creditCard() {
        System.out.print("Enter Credit Card Number : ");
        String n = sc.next();
        System.out.println("Date : " + date);
        System.out.println("Source : " + source);
        System.out.println("Destination : " + destination);
        System.out.println("Distance : " + km + " km");
        System.out.println("Bill(with GST) : " + bill + " (Paid)");
        System.out.println("Thank you...(*_*)");
    }

    public void upi() {
        System.out.print("Enter UPI Id  : ");
        String n = sc.next();
        System.out.println("Date : " + date);
        System.out.println("Source : " + source);
        System.out.println("Destination : " + destination);
        System.out.println("Distance : " + km + " km");
        System.out.println("Bill(with GST) : " + bill + " (Paid)");
        System.out.println("Thank you...(*_*)");
    }

    public boolean dateCheck(String date) {
        int year = 0;
        int month = 0;
        int day = 0;
        if (date.length() == 10) {
            String[] c = date.split("/");
            if (c.length == 3) {
                for (int i = 0; i < c[0].length(); i++) {
                    if (c[0].charAt(i) >= 48 && c[0].charAt(i) <= 57) {
                        year++;
                    }
                }
                for (int i = 0; i < c[1].length(); i++) {
                    if (c[1].charAt(i) >= 48 && c[1].charAt(i) <= 57) {
                        month++;
                    }
                }
                for (int i = 0; i < c[2].length(); i++) {
                    if (c[2].charAt(i) >= 48 && c[2].charAt(i) <= 57) {
                        day++;
                    }
                }
                if (day == 2 && month == 2 && year == 4) {
                    return false;
                } else {
                    System.out.println("Enter date in (yyyy/mm/dd) Format...");
                    return true;
                }
            } else {
                System.out.println("Enter date in (yyyy/mm/dd) Format...");
                return true;
            }

        } else {
            System.out.println("Enter date in (yyyy/mm/dd) Format...");
            return true;
        }
    }
}
