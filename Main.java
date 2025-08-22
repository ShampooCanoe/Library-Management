import java.util.Scanner;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

public class Main {

    public static final float STUDENT_DISCOUNT = (float) 0.7; // (30% Discount)

    public static void main(String[] args) {

        // Loading Customer List and Book Inventory

        Scanner scanner = new Scanner(System.in);

        ArrayList<Book> bookInventory = new ArrayList<Book>();
        ArrayList<Person> customers = new ArrayList<Person>();

        /*
         * Main Menu:
         * 0: Exit
         * 1: Add book to Inventory
         * 2: Remove Book from Inventory
         * 3: Add Person to customer list
         * 4: Remove Person from customer list
         * 5: List Books
         * 6: List Customers
         * 7: Rent Book Out
         * 8: Return Book
         */

        while (true) {

            clearScreen();

            try {
                bookInventory = Book.load("inventory.dat");
                System.out.println("Book Inventory Loaded.");
            } catch (Exception e) {
                System.out.println("No Book Inventory List Loaded.");
            }

            try {
                customers = Person.personLoad("customers.dat");
                System.out.println("Customer List Loaded.");
            } catch (Exception e) {
                System.out.println("No Customer List Loaded.");
            }
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a");
            System.out.print("Current Date: " + LocalDate.now().toString());
            System.out.println(" | Current Time: " + LocalTime.now().format(timeFormat));

            updateDues(bookInventory, customers);

            System.out.print("Main Menu (-1 for Options): ");
            int userOption = scanner.nextInt();
            scanner.nextLine();

            if (userOption == 0)
                break;

            switch (userOption) {

                // Help
                case -1: {
                    System.out.printf("         Main Menu:\r\n" +
                            "        0: Exit\r\n" +
                            "        1: Add book to Inventory\r\n" +
                            "        2: Remove Book from Inventory\r\n" +
                            "        3: Add Person to customer list\r\n" +
                            "        4: Remove Person from customer list\r\n" +
                            "        5: List Books\r\n" +
                            "        6: List Customers\r\n" +
                            "        7: Rent Book Out\r\n" +
                            "        8: Return Book\r\n");

                    System.out.println("Press Enter to Continue: ");
                    scanner.nextLine();
                    break;
                }

                // Add book to inventory
                case 1: {
                    Book newBook = new Book(ThreadLocalRandom.current().nextInt(0, 99999), 0, "", "", 0);

                    System.out.println("Enter the rental price: ");
                    newBook.setRentalPrice(scanner.nextFloat());
                    scanner.nextLine();

                    System.out.println("Enter the Title: ");
                    newBook.setTitle(scanner.nextLine());

                    System.out.println("Enter the Author: ");
                    newBook.setAuthor(scanner.nextLine());
                    bookInventory.add(newBook);
                    Book.saveBook(bookInventory, "inventory.dat");

                    System.out.println("Press Enter to Continue: ");
                    scanner.nextLine();

                    break;

                    // Remove book from inventory
                }
                case 2: {
                    System.out.println("Enter the ID to remove: ");
                    int removeId = scanner.nextInt();
                    scanner.nextLine();
                    boolean found = false;

                    for (int i = 0; i < bookInventory.size(); i++) {
                        if (bookInventory.get(i).getId() == removeId) {
                            found = true;
                            if (bookInventory.get(i).getRenterID() == 0) {
                                bookInventory.remove(i);
                                System.out.println("Book removed successfully!");
                                Book.saveBook(bookInventory, "inventory.dat");
                                break;
                            } else {
                                System.out.println("Error! Book is currently rented.");
                            }
                        }
                    }
                    if (!found) {
                        System.out.println("Error! No such ID found.");
                    }

                    System.out.println("Press Enter to Continue: ");
                    scanner.nextLine();
                    break;

                }
                case 3: { // Add Person

                    Person newPerson = new Person(ThreadLocalRandom.current().nextInt(0, 99999), "", 4, false, 0, 0);

                    System.out.println("Enter Name: ");

                    newPerson.setName(scanner.nextLine());
                    System.out.println("Enter Age: ");
                    newPerson.setAge(Integer.parseInt(scanner.nextLine().trim()));
                    System.out.println("Are you a student? y/n: ");

                    String yn = scanner.nextLine().trim();
                    if (!yn.isEmpty() && yn.charAt(0) == 'y') {
                        newPerson.setStudent(true);
                    } else {
                        newPerson.setStudent(false);
                    }
                    customers.add(newPerson);
                    Person.savePerson(customers, "customers.dat");

                    System.out.println("Press Enter to Continue: ");
                    scanner.nextLine();

                    break;

                }
                case 4: { // Remove person

                    System.out.println("Enter the ID to remove: ");
                    int removeId = scanner.nextInt();
                    scanner.nextLine();
                    boolean found = false;

                    for (int i = 0; i < customers.size(); i++) {
                        if (customers.get(i).getId() == removeId) {
                            found = true;
                            if (customers.get(i).getBooksRented() == 0) {

                                if (customers.get(i).getAmountDue() == 0) {
                                    customers.remove(i);
                                    System.out.println("Person removed successfully!");
                                    Person.savePerson(customers, "customers.dat");
                                } else {
                                    System.out.println(
                                            "This person has amount due of: $" + customers.get(i).getAmountDue());
                                    System.out.println("Are you sure you would like to delete them? y/n: ");

                                    String yn = scanner.nextLine().trim();
                                    if (!yn.isEmpty() && yn.charAt(0) == 'y') {
                                        customers.remove(i);
                                        System.out.println("Person removed successfully!");
                                        Person.savePerson(customers, "customers.dat");
                                    } else {
                                        System.out.println("No changes have been made.");
                                    }

                                }
                                System.out.println("Press Enter to Continue: ");
                                scanner.nextLine();
                                break;
                            } else {
                                System.out.println("Error! Person is currently renting books.");
                            }
                        }

                    }
                    if (!found) {
                        System.out.println("Error! No such ID found.");
                    }
                    System.out.println("Press Enter to Continue: ");
                    scanner.nextLine();
                    break;

                }
                case 5: { // Print Book Inventory

                    System.out.println("Would you like in stock books only? y/n: ");
                    String yn = scanner.nextLine().trim();
                    if (!yn.isEmpty() && yn.charAt(0) == 'y') {
                        for (Book e : bookInventory) {
                            if (e.getRenterID() == 0) {
                                System.out.println(e + " (" + e.getId() + ")");
                            }
                        }
                    } else {
                        for (Book e : bookInventory) {
                            System.out.println(e + " (" + e.getId() + ")");
                        }
                    }

                    System.out.println("Press Enter to Continue: ");
                    scanner.nextLine();

                    break;
                }
                case 6: {

                    for (Person e : customers) {
                        System.out.println(e);
                    }

                    System.out.println("Press Enter to Continue: ");
                    scanner.nextLine();
                    break;

                }
                case 7: { // Rents out a book to someone

                    System.out.println("Enter Renter Id: ");
                    int renterId = Integer.parseInt(scanner.nextLine().trim());

                    System.out.println("Enter Book Id: ");
                    int bookId = Integer.parseInt(scanner.nextLine().trim());

                    boolean customerFound = false;
                    boolean bookFound = false;
                    boolean bookRented = false;

                    for (int i = 0; i < customers.size(); i++) {

                        if (customers.get(i).getId() == renterId) {
                            customerFound = true;
                            for (int j = 0; j < bookInventory.size(); j++) {

                                if (bookInventory.get(j).getId() == bookId) {
                                    bookFound = true;

                                    if (bookInventory.get(j).getRenterID() == 0) {

                                        bookInventory.get(j).setRenterID(renterId);
                                        customers.get(i).setBooksRented(customers.get(i).getBooksRented() + 1);
                                        customers.get(i).setDailyDue(
                                                customers.get(i).getDailyDue() + bookInventory.get(j).getRentalPrice());
                                        customers.get(i).setAmountDue(customers.get(i).getAmountDue()
                                                + bookInventory.get(j).getRentalPrice());
                                        System.out.println("Book rented successfully!");
                                        bookRented = true;
                                        Book.saveBook(bookInventory, "inventory.dat");
                                        Person.savePerson(customers, "customers.dat");
                                    } else {
                                        System.out.println("Error! Book is already rented.");
                                    }

                                    System.out.println("Press Enter to Continue: ");
                                    scanner.nextLine();

                                    break;

                                }
                            }

                            System.out.println("Press Enter to Continue: ");
                            scanner.nextLine();

                            break;
                        }
                    }

                    if (!customerFound) {
                        System.out.println("Error! Renter ID not found.");
                    } else if (!bookFound) {
                        System.out.println("Error! Book ID not found.");
                    } else if (!bookRented && bookFound) {
                        System.out.println("Unable to rent the book.");
                    }

                    System.out.println("Press Enter to Continue: ");
                    scanner.nextLine();

                    break;

                }
                case 8: {

                    System.out.println("Enter Renter Id: ");
                    int renterId = Integer.parseInt(scanner.nextLine().trim());

                    System.out.println("Enter Book Id: ");
                    int bookId = Integer.parseInt(scanner.nextLine().trim());

                    boolean customerFound = false;
                    boolean bookFound = false;

                    for (int i = 0; i < customers.size(); i++) {

                        if (customers.get(i).getId() == renterId) {
                            customerFound = true;
                            for (int j = 0; j < bookInventory.size(); j++) {

                                if (bookInventory.get(j).getId() == bookId) {
                                    bookFound = true;

                                    if (bookInventory.get(j).getRenterID() == customers.get(i).getId()) {

                                        bookInventory.get(j).setRenterID(0);
                                        customers.get(i).setBooksRented(customers.get(i).getBooksRented() - 1);
                                        customers.get(i).setDailyDue(
                                                customers.get(i).getDailyDue() - bookInventory.get(j).getRentalPrice());
                                        System.out.println("Book returned successfully!");
                                        Book.saveBook(bookInventory, "inventory.dat");
                                        Person.savePerson(customers, "customers.dat");
                                    } else {
                                        System.out.println(
                                                "Error! Book is not rented to Id: " + customers.get(i).getId());
                                    }

                                    System.out.println("Press Enter to Continue: ");
                                    scanner.nextLine();

                                    break;
                                }
                            }

                            System.out.println("Press Enter to Continue: ");
                            scanner.nextLine();

                            break;
                        }
                    }
                    if (!customerFound) {
                        System.out.println("No Renter Id Found.");
                    }
                    if (!bookFound) {
                        System.out.println("No Book Id Found.");
                    }
                }
            }
        }
        scanner.close();
        System.out.println("Program Exited.");
    }

    // END OF MAIN
    // --------------------------------------------------------------------------------------------------------------------------------------

    public static void updateDues(ArrayList<Book> bookInventory, ArrayList<Person> customers) {

        LocalDate lastUpdated = DateUpdater.loadDate();
        LocalDate today = LocalDate.now();
        long daysPassed = ChronoUnit.DAYS.between(lastUpdated, today);

        if (daysPassed > 0) {

            for (Book e : bookInventory) {
                if (e.getRenterID() != 0) {
                    float tally = e.getRentalPrice() * daysPassed;

                    for (Person o : customers) {
                        if (o.getId() == e.getRenterID()) {
                            if (o.isStudent() == true) {
                                o.setAmountDue(o.getAmountDue() + (tally * STUDENT_DISCOUNT));
                                break;
                            } else {
                                o.setAmountDue(o.getAmountDue() + tally);
                                break;
                            }
                        }
                    }
                }

            }

            Book.saveBook(bookInventory, "inventory.dat");
            Person.savePerson(customers, "customers.dat");
            DateUpdater.saveDate(today);

        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
