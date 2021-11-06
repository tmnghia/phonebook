package javacore.demo;

public class Contact {
    private String name;
    private String phone;

    Contact() {
    };

    Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return this.name;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString() {
        return name + "-" + phone;
    }

    public void input() {
        try {
            System.out.print("Enter name: ");
            name = PhoneBook.userInput.nextLine();
            System.out.print("Enter phone: ");
            phone = PhoneBook.userInput.nextLine();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void show() {
        System.out.println("Contact name: " + getName());
        System.out.println("Contact phone: " + getPhone());
    }
}
