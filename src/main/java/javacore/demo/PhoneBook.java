package javacore.demo;

import java.util.Scanner;

/**
 * Phone book
 */
public final class PhoneBook {
    /**
     * Constant
     */
    private static final int PHONEBOOK_ACTION_EXIT = 0;
    private static final int PHONEBOOK_ACTION_ADD = 1;
    private static final int PHONEBOOK_ACTION_EDIT = 2;
    private static final int PHONEBOOK_ACTION_DELETE = 3;
    private static final int PHONEBOOK_ACTION_SHOW = 4;

    /**
     * Static variables
     */
    static Scanner userInput = new Scanner(System.in);
    static Database db = Database.getInstance();

    private PhoneBook() {
    }

    /**
     * Simple Phonebook.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        int option = 0;

        clearScreen();
        try {
            do {
                System.out.println("\n");
                System.out.println("++++++++++ PHONE BOOK ++++++++++");
                System.out.println("1. Add a new phone number.");
                System.out.println("2. Edit a phone number.");
                System.out.println("3. Delete a phone number.");
                System.out.println("4. Show all phone numbers.");
                System.out.println("0. Exit.");
                System.out.print("Input your choice: ");
                option = userInput.nextInt();
                userInput.nextLine();

                clearScreen();

                switch (option) {
                case PHONEBOOK_ACTION_ADD:
                    add();
                    break;
                case PHONEBOOK_ACTION_EDIT:
                    edit();
                    break;
                case PHONEBOOK_ACTION_DELETE:
                    delete();
                    break;
                case PHONEBOOK_ACTION_SHOW:
                    show();
                    break;
                case PHONEBOOK_ACTION_EXIT:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option " + option);
                    break;
                }
            } while (option != PHONEBOOK_ACTION_EXIT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (userInput != null) {
            userInput.close();
        }
    }

    private static void add() {
        Contact newContact = new Contact();
        newContact.input();
        if (!db.insert(newContact)) {
            System.err.println("Adding new contact failed!");
            return;
        }

        System.out.println("Created new contact");
        newContact.show();
    }

    private static void edit() {
        System.out.print("Input contact name: ");
        String name = userInput.nextLine();
        System.out.print("Input new phone number: ");
        String phone = userInput.nextLine();
        db.update(name, phone);
    }

    private static void delete() {
        System.out.print("Input contact name to delete: ");
        String name = userInput.nextLine();
        if (!db.delete(name)) {
            System.err.printf("Deleting contact with name %s failed\n", name);
            return;
        }
        System.out.println("Deleted contact with name " + name);
    }

    private static void show() {
        System.out.printf("%-20s %-10s\n", "Name", "Phone");
        System.out.println("--------------------------------\n");
        for (Contact contact : db.getDatabase()) {
            System.out.printf("%-20s %-10s\n", contact.getName(), contact.getPhone());
        }
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
