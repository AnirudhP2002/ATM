package helloWorld;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import com.mysql.cj.jdbc.Driver;
class ATM {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "");
            Statement stmt = con.createStatement();	
            Scanner s = new Scanner(System.in);
            System.out.println("Welcome to ANI ATM");
            System.out.println("Enter the pin number");
            
            int pin = s.nextInt();				//Get the pin from the user 
            
            ResultSet rs = stmt.executeQuery("select * from list where ac_no=" + pin);

            String name = null;
            int count = 0;
            int balance = 0;
       
            while (rs.next()) {
            	
                name = rs.getString(3);
                balance = rs.getInt(4);
                
                count++;
            }

            int amount = 0;
            int choice;
            int takeAmount = 0;
            if (count > 0) {
                System.out.println("Hello " + name);
                while (true) {
                    System.out.println("Press 1 to check balance");			//Showing the possible options to be selected 
                    System.out.println("Press 2 to add amount");
                    System.out.println("Press 3 to take amount");
                    System.out.println("Press 4 to print the recipt");
                    System.out.println("Press 5 to exit/quit");

                    System.out.println();
                    System.out.println("Enter your choice");
                   choice = s.nextInt();											//Selecting the required choice 
                    switch (choice) {
                        case 1:			//Printing balance of the account
                            System.out.println(balance);
                            break;
                        case 2:			//Crediting the amount to the account
                            System.out.println("Enter the amount to be added ");
                            amount = s.nextInt();																				
                            balance += amount;				
                            int bal = stmt
                                    .executeUpdate("update list set balance = " + balance + " where ac_no = " + pin);
                            System.out.println("successfully added now your current balance: " + balance);
                            break;
                        case 3:			//Debiting the amount from the account 
                            System.out.println("How much money you want to take");
                            takeAmount = s.nextInt();
                            if (takeAmount > balance)
                                System.out.println("your balnce is insufficient");
                            else {
                                balance -= takeAmount;
                                int sub = stmt
                                        .executeUpdate("update list set balance = " + balance + " where ac_no = " + pin);
                                System.out.println("successfully debited your balance: " + balance);
                            }
                            break;
                        case 4:			//Printing the receipt 
                            System.out.println("Thanks for coming");
                            System.out.println("Your current balance is " + balance);
                            System.out.println("Amount added : " + amount);
                            System.out.println("Amount taken : " + takeAmount);
                            break;
                    }
                    if (choice == 5)
                        break;
                }
            } else {
                System.out.println("Invalid pin");		//Printing the entered pin is invalid
            }
            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}