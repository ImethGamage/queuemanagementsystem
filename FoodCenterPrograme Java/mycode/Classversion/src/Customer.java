class Customer {
    private String firstName;//create variable For store customer first name
    private String lastName;//create variable for store customer last name

    private String FullName;//create variable for store customer full name

    private int burgersRequired;//create variable for store customer order


    public Customer(String firstName, String lastName, String FullName, int burgersRequired) {
        this.firstName = firstName;// Assign the provided first name to the 'firstName'
        this.lastName = lastName;// Assign the provided Last name to the 'lastName'
        this.FullName = FullName;// Assign the provided Full name to the 'FullName'
        this.burgersRequired = burgersRequired;// Assign the provided burger request to the 'burgersRequired'


    }

    public String getFirstName() {
        return firstName;// Return the value of the 'firstName'
    }

    public String getLastName() {
        return lastName;// Return the value of the 'lastName'
    }

    public String getFullName() {
        return FullName;// Return the value of the 'FullName'
    }

    public int getBurgersRequired() {
        return burgersRequired;// Return the value of the 'burgersRequired'
    }

}