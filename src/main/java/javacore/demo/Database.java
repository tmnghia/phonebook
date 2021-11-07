package javacore.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Database {
    private static Database instance;
    private ArrayList<Contact> database = new ArrayList<>();
    private Path dbPath = Paths.get("phonebook.db");

    private Database() {
        loadDatabase();
    }

    public ArrayList<Contact> getDatabase() {
        return database;
    }

    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }

        return instance;
    }

    private void loadDatabase() {
        try (BufferedReader reader = Files.newBufferedReader(dbPath)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] params = line.split("-");
                database.add(new Contact(params[0].trim(), params[1].trim()));
            }
        } catch (NoSuchFileException e) {
            System.err.println("File " + dbPath + " not found");
        } catch (Exception e) {
            System.out.println("Error when reading database");
            e.printStackTrace();
        }
        System.out.println("Loaded database: " + database);
    }

    private void updateDatabase() {
        Collections.sort(database, Comparator.comparing(Contact::getName));
        cleanupDB();
        try (BufferedWriter writer = Files.newBufferedWriter(dbPath, StandardCharsets.UTF_8,
                StandardOpenOption.WRITE)) {
            for (Contact contact : database) {
                writer.write(contact.getName() + " - " + contact.getPhone());
                writer.newLine();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public boolean insert(Contact newContact) {
        for (Contact contact : database) {
            if (contact.getName().equals(newContact.getName())) {
                System.out.println("Contact with name " + newContact.getName() + " already exist!");
                return false;
            }
        }
        database.add(newContact);
        updateDatabase();

        return true;
    }

    public boolean delete(String name) {
        for (Contact contact : database) {
            if (contact.getName().equals(name)) {
                database.remove(contact);
                System.out.println("Removed contact with name: " + name);
                updateDatabase();

                return true;
            }
        }
        System.err.printf("Contact with name %s is not exist\n", name);
        return false;
    }

    public void update(String name, String phone) {
        for (Contact contact : database) {
            if (contact.getName().equals(name)) {
                contact.setPhone(phone);
            }
        }
        updateDatabase();
    }

    public void cleanupDB() {
        PrintWriter writer;
        try {
            writer = new PrintWriter("phonebook.db");
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
