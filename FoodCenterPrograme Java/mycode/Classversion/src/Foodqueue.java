import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.File;

class Foodqueue{
    private int stock = 50;//Stock value
    private ArrayList<ArrayList<Customer>> queues;//Arraylist to save customer details

    private static String[][] cashierQueues = new String[3][5];// Array to store cashier queues

    private static int[] maxQueueSizes = {2, 3, 5};// Array to store maximum queue sizes for each cashier
    private int[] servedCustomer = {0, 0, 0};//create  array to store served customers

    public static final CircularQueue<Customer> waitingList = new CircularQueue<>(10);//create object from circular queue


    public Foodqueue() {
        queues = new ArrayList<>();
        queues.add(new ArrayList<>());  // Cashier 1
        queues.add(new ArrayList<>());  // Cashier 2
        queues.add(new ArrayList<>());  // Cashier 3
    }

    //Display Menu
    public void displayMenu() {
        System.out.println();
        System.out.println("Menu:");
        System.out.println("100 or VFQ: View all Queues.");
        System.out.println("101 or VEQ: View all Empty Queues.");
        System.out.println("102 or ACQ: Add customer to a Queue.");
        System.out.println("103 or RCQ: Remove a customer from a Queue. (From a specific location)");
        System.out.println("104 or PCQ: Remove a served customer.");
        System.out.println("105 or VCS: View Customers Sorted in alphabetical order");
        System.out.println("106 or SPD: Store Program Data into file.");
        System.out.println("107 or LPD: Load Program Data from file.");
        System.out.println("108 or STK: View Remaining burgers Stock.");
        System.out.println("109 or AFS: Add burgers to Stock.");
        System.out.println("110 or IFQ: Print income of each queue.");
        System.out.println("999 or EXT: Exit the Program.\n\n");

    }


    public void viewAllQueues() {
        System.out.println("******************************");
        System.out.println("*           Cashiers         *");
        System.out.println("******************************");

        for (int i = 0; i < getMaxQueueSize(maxQueueSizes); i++) {
            for (int j = 0; j < queues.size(); j++) {// Iterate through each queue in the 'queues' ArrayList.
                ArrayList<Customer> queue = queues.get(j);
                int maxQueueSize = maxQueueSizes[j];

                if (i < maxQueueSize) {
                    if (i < queue.size() && queue.get(i) != null) {
                        System.out.print("\tO\t");//print O to occupied slots
                    } else {
                        System.out.print("\tX\t");//print X to not occupied slots
                    }
                } else {
                    System.out.print("\t \t"); // Print empty slots for queues that have fewer slots
                }
            }
            System.out.println();
        }
    }

    private static int getMaxQueueSize(int[] maxQueueSizes) { //Method to Get maximum queue
        int maxQueueSize = 0;

        for (int size : maxQueueSizes) {
            if (size > maxQueueSize) {
                maxQueueSize = size;// Update the maximum queue size to the current size value.
            }
        }

        return maxQueueSize;//return max queue size
    }

    public void viewAllEmptyQueues() {
        for (int i = 0; i < 3; ++i) {
            int status = 0;//variable to track queue id full or not
            System.out.println();// Print an empty line
            System.out.print("Queue:" + (i + 1) + "\tSlots- ");//print queue number and slots

            for (int j = 0; j < maxQueueSizes[i]; ++j) {// Iterate from 0 to the maximum queue size for the current queue.
                if (cashierQueues[i][j] == null) {//check whether current queue slot is null
                    System.out.print((j + 1) + " ");//print slot number
                    status = 1;//set the status variable to 1
                }
            }
            if (status == 0) { //Check if queue is full
                System.out.print("Full");


            }
        }
    }


    public void addCustomerToQueue(String firstName, String lastName, String fullName, int burgersRequired) {
        Customer customer = new Customer(firstName, lastName, fullName, burgersRequired);//create new object from customer class

        int queueNumber = calculateShortestQueue();//shortest queue assign to queuenumber variable

        ArrayList<Customer> queue = queues.get(queueNumber);// This line assigns the retrieved queue to the variable 'queue'
        int maxQueueSize = maxQueueSizes[queueNumber];// This line assigns the retrieved maximum queue size to the variable 'maxQueueSize'.

        // To check if any cashier is available
        while (queue.size() >= maxQueueSize) {// create a loop iterate until size of the current queue is greater than or equal to the maximum queue size.
            queueNumber++;// Increment the queueNumber to move to the next queue.

            if (queueNumber >= queues.size()) {//Check whether queue is Full
                System.out.println("All queues are full!");//Print All queues full message
                int selectedCashier = -1;// Initialize the 'selectedCashier' variable to -1.

                boolean addToWaitingList = (selectedCashier == -1);  // Create a boolean variable 'addToWaitingList' and assign it the result of the comparison (selectedCashier == -1).

                if (addToWaitingList) {
                    if (waitingList.isFull()) {//Check whether queue is full
                        System.out.println("Waiting list is full. Customer cannot be added.");
                        return;
                    }
                    waitingList.enqueue(customer);//add customer to waiting list

                    System.out.println(fullName + " added to the waiting list.");//print customer adding waiting list message
                }


                return;
            }
            queue = queues.get(queueNumber);
            maxQueueSize = maxQueueSizes[queueNumber];
        }



        if (burgersRequired > stock) {//Check whether burger order is less than the oreder
            System.out.println("Burger count is less than the order. Please decrease your order or add more burgers to stock.");
            return;
        }

        queue.add(customer);//add customer to queue
        stock -= burgersRequired;//remove order burgers from stock

        if (stock <= 10) {//check whether burger stock is less than 10
            System.out.println("\n!!!Burger Stock warning: Burger stock is less than 10. Add some burgers!!!\n");
        }

        String[] queueArray = cashierQueues[queueNumber];
        for (int i = 0; i < queueArray.length; i++) {  // Iterate through each element in the queueArray.
            if (queueArray[i] == null) {//Check whether queueArray is null
                queueArray[i] = customer.getFirstName() + " " + customer.getLastName();//Assign customer full name to current element in queue Array
                queueNumber = queueNumber + 1;   // Increment the queueNumber by 1
                System.out.println(fullName + " added to queue " + queueNumber);//print Customer adding message
                break;
            }
        }
    }

    private int calculateShortestQueue() {//method to get minimum length queue
        int shortestQueue = -1;
        int shortestSize = Integer.MAX_VALUE;

        for (int i = 0; i < queues.size(); i++) {// Iterate through each queue in the 'queues' ArrayList.
            ArrayList<Customer> queue = queues.get(i);
            int queueSize = queue.size();// Get the size of the current queue.

            if (queueSize < shortestSize) {// check the size of the current queue is smaller than the current shortest size

                shortestSize = queueSize;//assign that value to shoretestqueue
                shortestQueue = i;// Update the index of the shortest queue to the current index 'i'.
            }
        }

        return shortestQueue;// Return the index of the queue with the shortest size.
    }


    public void removeCustomerFromQueue(int queueNumber, int slotNumber) {
        if (queueNumber < 1 || queueNumber > queues.size()) { //Check customer entered queue number correct
            System.out.println("Invalid queue number!");//print Invalid queue Number message
            return;
        }

        ArrayList<Customer> queue = queues.get(queueNumber - 1);//  assigns the retrieved queue to the variable queue.

        if (isEmptyQueue(queue)) {//Check whether queue is empty
            System.out.println("Queue " + queueNumber + " is empty!");//print queue empty message
            return;
        }

        int indexToRemove = slotNumber - 1;

        if (indexToRemove >= queue.size() || queue.get(indexToRemove) == null) {//Check whether queue location is nu;;
            System.out.println("No customer found at the specified location!");//print no customer found message
            return;
        }

        Customer removedCustomer = queue.remove(indexToRemove);
        stock += removedCustomer.getBurgersRequired();//Add Customer order again to stock

        System.out.println("Removed  customer: " + removedCustomer.getFullName());//Display remove customer message with full name

        if(!waitingList.isEmpty()){//check if any slot empty
            Customer waitingcustomer1 = waitingList.dequeue();//remove first customer in waiting list
            queue.add(waitingcustomer1);//Add first customer in waiting lis to queue
            System.out.println( waitingcustomer1.getFullName() + " from waiting queue  added to queue " + queueNumber);//print adding message and queue number

        }
    }

    private static boolean isEmptyQueue(ArrayList<Customer> queue) {
        return queue.isEmpty();
    }


    public void removeServedCustomerFromQueue(int Qnumber) {
        if (Qnumber < 1 || Qnumber > queues.size()) {//Check customer entered queue number correct
            System.out.println("Invalid queue number!");
            return;
        }

        ArrayList<Customer> queue = queues.get(Qnumber - 1);

        if (queue.isEmpty()) {//Check any customer available on the queue
            System.out.println("No served customer found in queue " + Qnumber);
            return;
        }
        servedCustomer[Qnumber - 1] += queue.get(0).getBurgersRequired();
        Customer removedCustomer = queue.remove(0);


        System.out.println("Removed served customer: " + removedCustomer.getFullName());//Print remove served customer message with full name

        if(!waitingList.isEmpty()) {//check if any slot empty
            Customer waitingcustomer1 = waitingList.dequeue();//remove first customer in waiting list
            queue.add(waitingcustomer1);//Add first customer in waiting lis to queue

            System.out.println( waitingcustomer1.getFullName() + " from waiting queue  added to queue " + Qnumber);//print adding message and queue number
        }
    }


    public void viewCustomersSorted() {
        ArrayList<Customer> allCustomers = new ArrayList<>();// Create a new ArrayList to store all the customers from the queues.
        for (ArrayList<Customer> queue : queues) {// Iterate through each queue in the 'queues' ArrayList.
            allCustomers.addAll(queue);// Add all the customers from the current queue to the 'allCustomers' ArrayList.

        }
        Collections.sort(allCustomers, Comparator.comparing(Customer::getFirstName).thenComparing(Customer::getLastName));// Sort the 'allCustomers' ArrayList based on the first name and then the last name of the customers.
        for (Customer customer : allCustomers) {// Iterate through each customer in the 'allCustomers' ArrayList.
            System.out.println(customer.getFirstName() + " " + customer.getLastName());//Print Customers Full name
        }

    }


    public void storeProgramData() {
        String filepath = "classversion.txt";//Set the file path
        try {
            FileWriter writer = new FileWriter(filepath);

            writer.write("\nRemaining burgers stock: " + stock + "\n\n");//Write remaining burger count

            //Write view all que method
            writer.write("******************************\n");
            writer.write("*           Cashiers         *\n");
            writer.write("******************************\n");

            for (int i = 0; i < getMaxQueueSize(maxQueueSizes); i++) {
                for (int j = 0; j < cashierQueues.length; j++) {
                    String[] queue = cashierQueues[j];
                    int maxQueueSize = maxQueueSizes[j];

                    if (i < maxQueueSize) {
                        if (i < queue.length && queue[i] != null) {
                            writer.write("\tO\t");
                        } else {
                            writer.write("\tX\t");
                        }
                    } else {
                        writer.write("\t \t"); // Print empty slots for queues that have fewer slots
                    }
                }
                writer.write("\n"); // Add a line break after each row
            }

            writer.write("\nCustomers:\n");

            //write customer sorted method

            ArrayList<Customer> allCustomers = new ArrayList<>();
            for (ArrayList<Customer> queue : queues) {
                allCustomers.addAll(queue);
            }

            Collections.sort(allCustomers, Comparator.comparing(Customer::getFirstName).thenComparing(Customer::getLastName));

            for (Customer customer : allCustomers) {
                writer.write(customer.getFirstName() + " " + customer.getLastName() + "\n");
            }

            writer.close(); // Close the File Writer

            System.out.println("Program data has been stored in the file.");
        } catch (IOException e) {// the exception is caught and handled here.
            System.out.println("An error occurred while writing the file: " + e.getMessage());
        }
    }

    public void loadProgramData() {
        File file = new File("classversion.txt");//Set the File path

        try {
            Scanner reader = new Scanner(file); // Create a new Scanner object, 'reader', to read the content of the 'file'.
            while (reader.hasNextLine()) {
                System.out.println(reader.nextLine()); // Read the next line from the file
            }
            reader.close();
        } catch (Exception e) { // the exception is caught and handled here.
            System.out.println(e);//print exception details
        }

        System.out.println("Program data loaded from a file.");
    }


    public void printIncomeOfEachQueue() {
        System.out.println("Income of each queue:");
        for (int i = 0; i < servedCustomer.length; i++) { // Loop through the servedCustomer array from index 0 to the last index.
            System.out.println("Cashier " + i + ": " + servedCustomer[i] * 650);// Prints the income for each cashier queue.
        }
    }


    public void viewRemainingBurgersStock() {
        System.out.println("Remaining burgers stock: " + stock);//print remaining burger stock

    }

    public void addBurgersToStock(int quantity) {
        stock += quantity;//Add burgers
        System.out.println(quantity + " burgers added to stock. Total stock: " + stock);//print burger added message
    }

}