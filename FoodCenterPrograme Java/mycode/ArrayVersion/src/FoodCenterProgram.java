import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class FoodCenterProgram {
    private static String[][] cashierQueues = new String[3][5];// Array to store cashier queues
    private static int[] maxQueueSizes = {2, 3, 5};// Array to store maximum queue sizes for each cashier
    private static int stock = 50;// stock value

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

//Display Menu
        displayMenu();


        while (true) {

            System.out.print("\n\nEnter your choice: ");
            String choice = scanner.next();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {

                //View all Queues
                case "100":
                case "AFC":
                    ViewallQueues();
                    break;

                //View all empty Queues
                case "101":
                case "VEQ":
                    ViewallEmptyQueues();
                    break;

                //Add customer to queue
                case "102":
                case "ACQ":
                    addCustomer(scanner);
                    break;

                //Remove specific location customer from queue
                case "103":
                case "RCQ":
                    removeCustomer(scanner);
                    break;

                //Remove Served Customer from queue
                case "104":
                case "PCQ":
                    removeServedCustomer(scanner);
                    break;

                //View customer  names in alphabetical order
                case "105":
                case "VCS":
                    viewCustomersSorted(cashierQueues);
                    break;

               //Store programme data to file
                case "106":
                case "SPD":
                    storeProgramData();
                    break;

                //Load programme data from file
                case "107":
                case "LPD":
                    loadProgramData();
                    break;

                //View burger stock
                case "108":
                case "STK":
                    viewStock();
                    break;

                //Add  burgers to queue
                case "109":
                case "AFS":
                    addToStock(scanner);
                    break;

                //Exit program
                case "999":
                case "EXT":
                    exitProgram();
                    break;
                default:
                    System.out.println("Invalid Menu option! Please try again.");//If user enter invalid choice
            }
        }
    }

    //Display Main menu
    private static void displayMenu() {
        System.out.println("********** Food Center Program Menu Options**********");
        System.out.println("100 or VFQ: View all Queues.");
        System.out.println("101 or VEQ: View all Empty Queues.");
        System.out.println("102 or ACQ: Add customer to a Queue");
        System.out.println("103 or RCQ: Remove a customer from a Queue. (From a specific location)");
        System.out.println("104 or PCQ: Remove a served customer.");
        System.out.println("105 or VCS: View Customers Sorted in alphabetical order (Do not use library sort routine)");
        System.out.println("106 or SPD: Store Program Data into file.");
        System.out.println("107 or LPD: Load Program Data from file");
        System.out.println("108 or STK: View Remaining burgers  Stock.");
        System.out.println("109 or AFS: Add burgers to Stock.");
        System.out.println("999 or EXT: Exit the Program.");

    }


    private static void ViewallQueues() {
        System.out.println("******************************");
        System.out.println("*           Cashiers         *");
        System.out.println("******************************");
        int maxQueueSize = getMaxQueueSize();

        // Iterate over each row  in the queues
        for (int i = 0; i < maxQueueSize; i++) {

            // Iterate over each cashier
            for (int j = 0; j < cashierQueues.length; j++) {
                String[] queue = cashierQueues[j];

                if (i == 2) {
                    if (j == 0) {
                        System.out.print("\t \t");
                    } else {

                        if (i < queue.length && queue[i] != null) {
                            System.out.print("\tO\t");// Print O to  occupied slot
                        } else {
                            System.out.print("\tX\t");// print X to Empty slot
                        }
                    }
                } else if (i == 3 || i == 4) {
                    if (j == 0 || j == 1) {
                        System.out.print("\t \t");
                    } else {

                        if (i < queue.length && queue[i] != null) {
                            System.out.print("\t0\t");// Print O to  occupied slot
                        } else {
                            System.out.print("\tX\t");// print X to Empty slot
                        }
                    }
                } else {
                    if (i < queue.length && queue[i] != null) {
                        System.out.print("\tO\t");// Print O to  occupied slot
                    } else {
                        System.out.print("\tX\t");// print X to Empty slot
                    }
                }


            }
            System.out.println();
        }
    }

    // Method to get the maximum queue size among all cashiers
    private static int getMaxQueueSize() {
        int maxQueueSize = 0;

        // Iterate over each cashier's queue
        for (String[] queue : cashierQueues) {

            // Check if the current queue length is greater than the current maximum queue size
            if (queue.length > maxQueueSize) {
                maxQueueSize = queue.length;// Update the maximum queue size
            }
        }

        return maxQueueSize;// Return Maxqueue size
    }

    private static void ViewallEmptyQueues() {
        System.out.println("******************************");
        System.out.println("*           Cashiers         *");
        System.out.println("******************************");

        for (int i = 0; i < 3; ++i) { //Iterate cashier queues
            int status = 0;
            System.out.println();
            System.out.print("Queue:" + (i + 1) + "\tSlots- ");//print queue number and slots
            for (int j = 0; j < maxQueueSizes[i]; ++j) {//Iterate over th slots
                if (cashierQueues[i][j] == null) {//check empty slots
                    System.out.print((j + 1) + " ");//print slot number
                    status = 1;
                }
            }
            if (status == 0) { //Check if all slots are full
                System.out.print("Full");//print full message if all slots are full


            }
        }

    }

    private static void addCustomer(Scanner scanner) {
        System.out.print("Enter the queue number (1, 2, or 3): ");
        int queueNumber = scanner.nextInt();//Get queue number from user
        scanner.nextLine();

        if (queueNumber < 1 || queueNumber > 3) {
            System.out.println("Invalid queue number!");//If user input queue number is invalid print this message
            return;
        }

        System.out.print("Enter the customer name: ");
        String customerName = scanner.nextLine();//Get the name from user

        String[] queue = cashierQueues[queueNumber - 1];//Get the selected queue using queueNumber
        int maxQueueSize = maxQueueSizes[queueNumber - 1];//Get the max queue size on selected queue

        if (isFullQueue(queue, maxQueueSize)) {//check if cashier queue is full
            System.out.println("Queue " + queueNumber + " is full!");
            return;
        }

        //Iterate all the slots
        for (int i = 0; i < queue.length; i++) {
            if (queue[i] == null) {// Find the first available slot in the queue
                queue[i] = customerName;//Assign the customer name to available slot
                stock = stock - 5;//remove 5 burgers from stock

            if (stock <= 10){ //check if burger stock less than 10
                System.out.println("\n!!!Burger Stock warning Burger stock is less than 10 add some burgers!!!\n");
            }
                break;
            }
        }

         //Print customer adding message
        System.out.println("Added customer \"" + customerName + "\" to queue " + queueNumber);

    }

    private static boolean isFullQueue(String[] queue, int maxQueueSize) {
        int count = 0;//Variable for store not null slots

        //Iterate over each elements in th queue
        for (String customer : queue) {
            if (customer != null) {//check if current slot not null
                count++;// Increments the counter if the element is not null
            }
        }

        return count >= maxQueueSize;//return count is greater than or equal to max queue sizes
    }

    private static void removeCustomer(Scanner scanner) {
        System.out.print("Enter the queue number (1, 2, or 3): ");
        int queueNumber = scanner.nextInt();//Get the queue number from user
        scanner.nextLine();

        if (queueNumber < 1 || queueNumber > 3) {//Check if user queue number is invalid
            System.out.println("Invalid queue number!");
            return;
        }
        int maxQueueSize = maxQueueSizes[queueNumber - 1];

        System.out.print("Enter the slot Number: ");
        int slotNumber = scanner.nextInt();//Get the slot number from user
        scanner.nextLine();

        if (slotNumber < 1 || slotNumber > maxQueueSize) {//Check if user enterd slot number is invalid
            System.out.println("Invalid slot number!");
            return;
        }

        String[] queue = cashierQueues[queueNumber - 1];//Get the selected queue

        if (isEmptyQueue(queue)) {//Check if selected slot is empty
            System.out.println(queueNumber + " cashier " + slotNumber + " slot is empty!");
            return;
        }

        int indexToRemove = slotNumber - 1;// Calculate the index to remove the customer from

        //Check if the index is out of range or if there is no customer at the specified slot
        if (indexToRemove >= queue.length || queue[indexToRemove] == null) {
            System.out.println("No customer found at the specified location!");
            return;
        }

        String removedCustomer = queue[indexToRemove];// Retrieve the customer at the specified slot
        queue[indexToRemove] = null;// Remove the customer from the slot


        // Shift customers to fill the vacant position
        for (int i = indexToRemove + 1; i < queue.length; i++) {
            if (queue[i] != null) {// Check if there is a customer at the next slot
                queue[i - 1] = queue[i];// Shift the customer to the previous slot
                queue[i] = null;// Set the current slot to null
            }
        }

        stock += 5;//Add 5 burgers to the stock

        System.out.println("Removed customer: " + removedCustomer);//Print remove customer message

    }


    private static void removeServedCustomer(Scanner scanner) {
        System.out.print("Enter the queue number (1, 2, or 3): ");
        int queueNumber = scanner.nextInt();//Get the queue number from user
        scanner.nextLine();

        if (queueNumber < 1 || queueNumber > 3) {//Check if user queue number is invalid
            System.out.println("Invalid queue number!");
            return;
        }

        String[] queue = cashierQueues[queueNumber - 1];//Get the selected queue

        if (isEmptyQueue(queue)) {//Check if selected queue is empty
            System.out.println("Queue " + queueNumber + " is empty!");
            return;
        }

        int servedPosition = -1;// Initialize the served position variable

        for (int i = 0; i < maxQueueSizes[queueNumber - 1]; i++) {// Find the position of the served customer in the queue
            if (queue[i] != null) {
                servedPosition = i;
                break;
            }
        }

        if (servedPosition == -1) {// Check if no served customer is found in the queue
            System.out.println("No served customer found in queue " + queueNumber);
            return;
        }

        String removedCustomer = queue[servedPosition];// Retrieve the served customer from the queue

        //Shift the customers to fill the vacant position
        for (int i = servedPosition; i < queue.length - 1; i++) {
            queue[i] = queue[i + 1];
        }

        queue[queue.length - 1] = null;// Set the last position in the queue as null

        System.out.println("Removed served customer: " + removedCustomer);//print remove served customer message
    }


    private static void viewCustomersSorted(String[][] cashierQueues) {
        int totalCustomers = 0;

        // Count the total number of customers in all queues
        for (String[] queue : cashierQueues) {
            for (String customer : queue) {
                if (customer != null) {
                    totalCustomers++;
                }
            }
        }

        String[] allCustomers = new String[totalCustomers];// Create an array to store all the customers
        int index = 0;// Initialize the index variable to keep track of the position in the array

        // Collect all customers into a single array
        for (String[] queue : cashierQueues) {
            for (String customer : queue) {
                if (customer != null) {
                    allCustomers[index++] = customer;
                }
            }
        }

        // Bubble sort
        for (int i = 0; i < allCustomers.length - 1; i++) {
            for (int j = 0; j < allCustomers.length - i - 1; j++) {
                if (compareStrings(allCustomers[j], allCustomers[j + 1]) > 0) {
                    String temp = allCustomers[j];
                    allCustomers[j] = allCustomers[j + 1];
                    allCustomers[j + 1] = temp;
                }
            }
        }

        System.out.println("Customers Sorted in alphabetical order:");

        // Print the sorted customers
        for (String customer : allCustomers) {
            System.out.println(customer);
        }
    }

    private static int compareStrings(String str1, String str2) {
        int minLength = Math.min(str1.length(), str2.length());

        // Compare the characters of the two strings
        for (int i = 0; i < minLength; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                return str1.charAt(i) - str2.charAt(i);
            }
        }
        // Compare the lengths of the two strings
        return str1.length() - str2.length();
    }


    private static void storeProgramData() {

        String filepath = "FoodCenter.txt";// Specify the file path where the data will be written
        try {
            FileWriter writer = new FileWriter(filepath);//create a file writer object

            writer.write("\nRemaining burgers stock: " + stock + "\n\n");//write the remaining burger stock

            //write the view all cashier queues
            writer.write("******************************\n");
            writer.write("*           Cashiers         *\n");
            writer.write("******************************\n");

            int maxQueueSize = getMaxQueueSize();

            for (int i = 0; i < maxQueueSize; i++) {
                for (int j = 0; j < cashierQueues.length; j++) {
                    String[] queue = cashierQueues[j];

                    if (i == 2) {
                        if (j == 0) {
                            writer.write("\t  \t");
                        } else {
                            if (i < queue.length && queue[i] != null) {
                                writer.write("\tO\t");
                            } else {
                                writer.write("\tX\t");
                            }
                        }
                    } else if (i == 3 || i == 4) {
                        if (j == 0 || j == 1) {
                            writer.write("\t \t");
                        } else {
                            if (i < queue.length && queue[i] != null) {
                                writer.write("\tO\t");
                            } else {
                                writer.write("\tX\t");
                            }
                        }
                    } else {
                        if (i < queue.length && queue[i] != null) {
                            writer.write("\tO\t");
                        } else {
                            writer.write("\tX\t");
                        }
                    }
                }
                writer.write("\n"); // Add a line break after each row


            } //write customer name in alphabetical order
            int totalCustomers = 0;

            for (String[] queue : cashierQueues) {
                for (String customer : queue) {
                    if (customer != null) {
                        totalCustomers++;
                    }
                }
            }


            String[] allCustomers = new String[totalCustomers];
            int index = 0;

            for (String[] queue : cashierQueues) {
                for (String customer : queue) {
                    if (customer != null) {
                        allCustomers[index++] = customer;
                    }
                }
            }

            // Bubble sort
            for (int i = 0; i < allCustomers.length - 1; i++) {
                for (int j = 0; j < allCustomers.length - i - 1; j++) {
                    if (compareStrings(allCustomers[j], allCustomers[j + 1]) > 0) {
                        String temp = allCustomers[j];
                        allCustomers[j] = allCustomers[j + 1];
                        allCustomers[j + 1] = temp;
                    }
                }
            }




            writer.write("Customers name in alphabetical order ");
            writer.write("\n");


            for (String customer : allCustomers) {
                writer.write(customer);
                writer.write("\n");
            }

            writer.close(); // Close the File Writer


            System.out.println("Program data has been stored in the file.");
        } catch (IOException e) { // Handle any potential IOException that might occur during file writing
            System.out.println("An error occurred while writing the file: " + e.getMessage());
        }
    }




    private static void loadProgramData() {
        File file = new File("FoodCenter.txt");// Specify the file path where the data will be load

        try {
            Scanner reader = new Scanner(file);// Create a Scanner object to read from the file
            while (reader.hasNextLine()) {
                System.out.println(reader.nextLine()); // Print the current line
            }
            reader.close();//Close the scanner
        } catch (Exception e) {
            System.out.println(e);// Print any exception that occurs during file reading
        }

        System.out.println("Program data loaded from a file.");
    }


    private static void viewStock() {
        System.out.println("Remaining burgers stock: " + stock);//print remaining burger stock
    }

    private static void addToStock(Scanner scanner) {
        System.out.print("Enter the number of burgers to add: ");
        int burgersToAdd = scanner.nextInt();//Get the number of burger that user want to add
        scanner.nextLine();

        stock += burgersToAdd;//Add burgers to stock
        System.out.println(burgersToAdd + " burgers added to the stock. Total stock: " + stock);//print burger add msg
    }

    private static void exitProgram() {
        System.out.println("Exiting the program...");
        System.exit(0);//Exit the programme
    }

    private static boolean isEmptyQueue(String[] queue) {
        for (String customer : queue) {// Iterate over each customer in the queue
            if (customer != null) {// If a customer is found in the queue, the queue is not empty
                return false;// Return false to indicate the queue is not empty
            }
        }
        return true;// Return true to indicate the queue is empty
    }
}

