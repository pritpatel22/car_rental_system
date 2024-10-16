package p1;

import java.util.*;

public class Run {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        Admin admin = new Admin();
        Customer customer = new Customer();
        System.out.println();
        System.out.println("\t\t\t\t=> Welcome to Car Rental System\t\t\t\t");
        System.out.println();
        do {
            System.out.println("Enter 1. For Customer\n      2. For Admin Login\n      3. Exit");
            int user = 0;
            try {
                user = sc.nextInt();
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Please Enter Only Digits...");
                break;
            }
            switch (user) {
                case 1:
                    boolean check = true;
                    do {
                        System.out.println("If you are new user then register first...");
                        System.out.println("Enter 1. Login\n      2. Register\n      3. Exit ");
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
                                customer.login1();
                                break;
                            case 2:
                                customer.register();
                                break;

                            case 3:
                                System.out.print("Exit From System");
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

                    break;
                case 2:
                    admin.login();
                    boolean admin_check = true;
                    System.out.println("\t=> Your Function\t");
                    do {
                        System.out.println(
                                "Enter 1. Change Password\n      2. Add Car\n      3. Remove Car\n      4. View Cars\n      5. View Customer\n      6. Exit");
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
                                admin.changePass();
                                break;
                            case 2:
                                admin.addCar();
                                break;
                            case 3:
                                admin.removeCar();
                                break;
                            case 4:
                                admin.viewCar();
                                break;
                            case 5:
                                admin.displayCustomer();
                                break;
                            case 6:
                                System.out.print("Back to Main Menu");
                                Thread.sleep(500);
                                System.out.print(".");
                                Thread.sleep(500);
                                System.out.print(".");
                                Thread.sleep(500);
                                System.out.println(".");
                                Thread.sleep(500);
                                admin_check = false;
                                break;
                            default:
                                System.out.println("Enter Valid Number");
                                break;
                        }
                    } while (admin_check);

                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println();
                    System.out.println("Enter Valid Number");
                    System.out.println();
            }
        } while (true);
    }
}
