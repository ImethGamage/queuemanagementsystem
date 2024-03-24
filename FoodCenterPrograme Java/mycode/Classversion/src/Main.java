import java.util.Scanner;


public class Main {


    public static void main(String[] args) {
        Foodqueue queueManager = new Foodqueue();//create  object using FoodieFaveQueueManager class
        Scanner scanner = new Scanner(System.in);


        queueManager.displayMenu();

        while (true) {

            System.out.print("Enter your choice: ");
            String choice = scanner.next();
            scanner.nextLine();  // Consume the newline character

            switch (choice) {
                case "100":
                case "AFQ":
                    queueManager.viewAllQueues();//call viewAllQueues method
                    break;

                case "101":
                case "VEQ":
                    queueManager.viewAllEmptyQueues();//call viewAllEmptyQueues method
                    break;

                case "102":
                case "ACQ":
                    System.out.print("Enter customer's first name: ");
                    String firstName = scanner.nextLine();//Get customer first name
                    System.out.print("Enter customer's last name: ");
                    String lastName = scanner.nextLine();//Get customer last name
                    String  FullName = firstName + " " +  lastName;//Add first name and last name and get Full name
                    System.out.print("Enter the number of burgers required: ");
                    int burgersRequired = scanner.nextInt();//Get burger order
                    scanner.nextLine();  // Consume the newline character1
                    queueManager.addCustomerToQueue(firstName, lastName,FullName,burgersRequired);
                    break;

                case "103":
                case "RCQ":
                    System.out.print("Enter the queue number (1, 2, or 3):");
                    int qNumber = scanner.nextInt();//Get the queue number
                    System.out.print("Enter the position of the customer to remove: ");
                    int position = scanner.nextInt();//Get the position
                    scanner.nextLine();  // Consume the newline character
                    queueManager.removeCustomerFromQueue(qNumber, position);
                    break;

                case "104":
                case "PCQ":
                    System.out.print("Enter the queue number (1, 2, or 3):");
                    int Qnumber = scanner.nextInt();//Get the queue number
                    queueManager.removeServedCustomerFromQueue(Qnumber);
                    break;

                case "105":
                case "VCS":
                    queueManager.viewCustomersSorted();//call the view customer sorted method
                    break;

                case "106":
                case "SPD":
                    queueManager.storeProgramData();//call the store data to file method
                    break;

                case "107":
                case "LPD":
                    queueManager.loadProgramData();//call the load programme  method

                    break;

                case "108":
                case "STK":
                    queueManager.viewRemainingBurgersStock();//call the view remaining burger stock
                    break;

                case "109":
                case "AFS":
                    System.out.print("Enter the quantity of burgers to add: ");
                    int quantity = scanner.nextInt();//Get the burger count that want to add
                    scanner.nextLine();  // Consume the newline character
                    queueManager.addBurgersToStock(quantity);
                    break;

                case "110":
                case "IFQ":
                    queueManager.printIncomeOfEachQueue();//Call print income method
                    break;

                case "999":
                case "EXT":
                    System.out.println("Exiting the program.");//Exit the programme
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a valid menu option.");//If user enter invalid menu option
            }

            System.out.println();
        }
    }
}