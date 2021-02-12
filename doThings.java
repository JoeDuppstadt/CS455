/**
 *
 * @author nikki
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class doThings {

    boolean remove = false;
    boolean save = false;

    public static void main(String[] args) throws ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        //make empty event to create an empty stack.
        Event endOfStack = new Event();
        {
            endOfStack.initializeEventName("endOfStack");
        }

        Stack eventStack = new Stack(endOfStack);
        ArrayList<Person> contacts = new ArrayList();

        System.out.println("Welcome to your ToDo List!");
        System.out.println("What would you like to do?");

        int choice;
        choice = getChoice();
        check(choice, eventStack, contacts);

    }

    //Choice 1
    public static void check(int choice, Stack eventStack, ArrayList contacts) throws ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        int stackCounter = eventStack.getSize();
        if (choice == 0) {
            System.exit(0);
        }
        if (stackCounter == 1) {
            if (choice == 1 || choice == 3) {
                System.out.println("You must Create an Event in order to Complete or Print."
                        + "To add an Event, enter 2. To modify Settings, enter 4. To load saved Events, enter 6.");
                choice = in.nextInt();
                check(choice, eventStack, contacts);
            }
        }

        if (choice == 1) {
            removeEvent(eventStack);            
        } else if (choice == 2) {
            addEvent(choice, eventStack, contacts);
        } else if (choice == 3) {
           printEvents(eventStack);
        } else if (choice == 4) {
            modifySettings(eventStack, contacts);
        } else if (choice == 5) {
            saveEvents(eventStack);
            saveContacts(contacts);
        } else if (choice == 6) {
            eventStack = LoadEvents();
            if (eventStack == null) {
                eventStack = new Stack(new Event());
            }
            contacts = LoadContacts();
        }
        choice = getChoice();
        check(choice, eventStack, contacts);
    }
    
    
    // Choice 1
       public static Stack removeEvent(Stack eventStack) {
        Scanner in = new Scanner(System.in);
        Event endOfStack = new Event();
        endOfStack.initializeEventName("endOfStack");
        
            System.out.println("Please find which Event you would you like to complete.");
            printEvents(eventStack);
            System.out.println("Enter the number of the Event you would you like to complete.");
            int event = in.nextInt();

        Stack temp = new Stack(endOfStack);
        if (event >= 1 || event <= eventStack.getSize()) {
            if (event == 1) {
                eventStack.pop();
                return eventStack;
            } else {
                for (int i = event; i > 0; i--) {
                    temp.addEventToStack(eventStack.pop());
                }
                temp.pop();
                for (int i = event - 1; i > 0; i--) {
                    eventStack.addEventToStack(temp.pop());
                }
            }
        } else {
            System.out.println("Please enter valid Event number.");
            event = in.nextInt();
            removeEvent(eventStack);
        }
        System.out.println("Event remove success.");
        System.out.println("Your current events are: "); 
        printAll(eventStack);
        return eventStack;
    }

    //Choice 2
    public static Stack addEvent(int choice, Stack eventStack, ArrayList contacts) throws ClassNotFoundException {
        Scanner in = new Scanner(System.in);

        Event current = new Event();
        current.setEventName();
        current.setDetails();
        current.setPriority();

        //Option to search for a contact, create a new contact, or n/a
        System.out.println("Would you like to associate a contact with this event? Enter yes or no.");
        while (option()) {
            System.out.println("Great! Please enter e for an existing contact or n to create a new contact.");
            char c = in.nextLine().charAt(0);
            if (c == 'n') {
                addContact(eventStack, contacts, current);
            } else if (c == 'e') {

                if (contacts.isEmpty()) {
                    System.out.println("You must have contacts in order to add them to an event. Would you like to add a new contact? (y/n)");
                    if (option()) {
                        addContact(eventStack, contacts, current);
                    } else {
                        break;
                    }
                } else {
                    Person found = searchContactList(contacts);
                    System.out.println(found.getFname() + " " + found.getLname());
                    String nullString = null;
                    if (!found.getFname().equals(nullString)) {
                        current.setContactList(found);
                    } else {
                        System.out.println("That contact does not exist. If you would like to add a new contact, enter n. If you would like to print your contacts, enter p. If you would like to search again, enter s. "
                                + "To continue adding to your event without a contact, enter c.");
                        c = in.nextLine().charAt(0);
                        if (c == 'n') {
                            addContact(eventStack, contacts, current);
                            break;
                        } else if (c == 'p') {
                            formatContactList(contacts);
                            System.out.println("Did you find who you were looking for? Enter a to add an existing contact to your Event. Enter n to create a new contact. Enter c to continue without a contact.");
                            c = in.nextLine().charAt(0);
                            if (c == 'a') {
                                found = searchContactList(contacts);
                                if (contacts.equals(found)) {
                                    current.setContactList(found);
                                    break;
                                }
                            } else if (c == 'n') {
                                addContact(eventStack, contacts, current);
                                break;
                            } else if (c == 'c') {
                                break;
                            }
                        } else if (c == 's') {
                            found = searchContactList(contacts);
                            if (contacts.equals(found)) {
                                current.setContactList(found);
                                break;
                            }

                        } else if (c == 'c') {
                            break;
                        }
                    }
                }

            }
            System.out.println("Would you like to add another contact to your Event? (y/n) ");
        }

        //Option to add a date to the program
        System.out.println("Would you like to apply a due date? Enter yes or no.");

        if (option()) {
            current.setDate();
            eventStack.addEventToStack(current);
        } else {
            int tempC = getChoice();
            eventStack.addEventToStack(current);
            check(tempC, eventStack, contacts);
        }

        return eventStack;
    }

     public static boolean addContact(Stack eventStack, ArrayList contacts, Event current) {
        Person append = new Person();
        // create the new person in the existing list of contacts
        contacts.add(append);
        // add new person to event
        current.setContactList(append);

        if (!contacts.contains(append)) {
            return false;
        } else {
            return true;
        }
    }

     //Choice 3
     
    public static void printEvents(Stack eventStack) {
        Scanner in = new Scanner(System.in);
        char choice = 'z';
        do {
            System.out.println("Enter 'a' to print all Events. Enter 's' to search your Events.");
            choice = in.nextLine().charAt(0);
        } while (!((choice == 'a') || (choice == 's')));

        if (choice == 'a') {
            printAll(eventStack);
        } else if (choice == 's') {
            System.out.println("Enter 'n' to search by the Name of the Event. Enter 'p' to search by Priority."
                    + " Enter 'c' to search by Contacts associated with Events. Enter 't' to search by Tags. "
                    + "Enter 'd' to search by Date. Enter 'e' to exit this menu.");
            choice = in.nextLine().charAt(0);
            if (choice == 'n') {
                printByEventName(eventStack);
            } else if (choice == 'p') {
                printByPriority(eventStack);
            } else if (choice == 'c') {
                printByPerson(eventStack);
            } else if (choice == 't') {
                System.out.println("Feature not yet available. Would you like to search by something else? (y/n)");
                if (doThings.option()) {
                    printEvents(eventStack);
                } else {
                    System.out.println("Thank you for not crashing our app!");
                    System.exit(0);
                }
            } else if (choice == 'd') {
                printByDate(eventStack);
            } else if (choice == 'e') {
                doThings.getChoice();
            }
        }
    }

    public static void printAll(Stack eventStack) {
        Event[] printRow = new Event[eventStack.getSize()];
        printRow = eventStack.toArray(eventStack);
        Event tempEvent;
        for (int i = 0; i < (eventStack.getSize() - 1); i++) {
            tempEvent = printRow[i];
        }
        format(printRow);
    }

    public static void printByEventName(Stack eventStack) {
        Scanner in = new Scanner(System.in);
        int counter = 0;
        Event arr[] = eventStack.toArray(eventStack);
        Event[] foundArr = new Event[arr.length];
        String searchTerm;
        
        System.out.println("What name would you like to search for?");
        searchTerm = in.nextLine();

        for (int j = 0; j < arr.length; j++) {
            Event findTemp = arr[j];
            if (searchTerm.equals(findTemp.getEventName())) {
                foundArr[counter] = arr[j];
                counter++;
            }
        }
        Event[] validated = new Event[counter];
        for (int i = 0; i < counter; i++) {
            validated[i] = foundArr[i];
        }
        format(validated);
    }

    public static void printByPriority(Stack eventStack) {
        Scanner in = new Scanner(System.in);
        Event arr[] = eventStack.toArray(eventStack);
        int counter = 0;
        Event[] foundArr = new Event[arr.length];
        String searchTerm;

        System.out.println("What priority would you like to search for?");
        searchTerm = in.nextLine();

        for (int j = 0; j < arr.length; j++) {
            Event findTemp = arr[j];
            if (searchTerm.equals(findTemp.getPriority())) {
                foundArr[counter] = arr[j];
                counter++;
            }
        }
        Event[] validated = new Event[counter];
        for (int i = 0; i < counter; i++) {
            validated[i] = foundArr[i];
        }
        format(validated);
    }

    public static void printByPerson(Stack eventStack) {
        Scanner in = new Scanner(System.in);
        Event arr[] = eventStack.toArray(eventStack);
        int counter = 0;
        Event[] foundArr = new Event[arr.length];
        String[] searchTerm;

        System.out.println("Enter the full name of the person you would like to search for.");
        searchTerm = in.nextLine().split(" ");
        in.nextLine();

        for (int j = 0; j < arr.length; j++) {
            Event findTemp = arr[j];
            if (searchTerm.equals(findTemp.getContactList())) {
                foundArr[counter] = arr[j];
                counter++;
            }
        }
        Event[] validated = new Event[counter];
        for (int i = 0; i < counter; i++) {
            validated[i] = foundArr[i];
        }
        format(validated);
    }

    public static void printByTag(Stack eventStack) {
        Scanner in = new Scanner(System.in);
        Event arr[] = eventStack.toArray(eventStack);
        int counter = 0;
        Event[] foundArr = new Event[arr.length];
        String searchTerm;

        System.out.println("What tag would you like to search for?");
        searchTerm = in.nextLine();

        for (int j = 0; j < arr.length; j++) {
            Event findTemp = arr[j];
            if (searchTerm.equals(findTemp.getTag())) {
                foundArr[counter] = arr[j];
                counter++;
            }
        }
        Event[] validated = new Event[counter];
        for (int i = 0; i < counter; i++) {
            validated[i] = foundArr[i];
        }
        format(validated);
    }

    public static void printByDate(Stack eventStack) {
        Scanner in = new Scanner(System.in);
        Event arr[] = eventStack.toArray(eventStack);
        int counter = 0;
        Event[] foundArr = new Event[arr.length];
        String searchTerm;

        System.out.println("What date would you like to search for?");
        searchTerm = in.nextLine();

        for (int j = 0; j < arr.length; j++) {
            Event findTemp = arr[j];
            if (searchTerm.equals(findTemp.getDate())) {
                foundArr[counter] = arr[j];
                counter++;
            }
        }
        Event[] validated = new Event[counter];
        for (int i = 0; i < counter; i++) {
            validated[i] = foundArr[i];
        }
        format(validated);
    }

    public static void format(Event foundArr[]) {
        int lEventName = 10;
        int lDetails = 6;
        int lPriority = 8;
        int lPerson = 10, pTemp = 0;
        int lTag = 6;
        int lDate = 4;
        String formatEN, formatDe, formatPr, formatPe, formatT, formatDa;

        //Find the longest string in the array for each part of the event data
        for (int i = 0; i < foundArr.length; i++) {
            Event countTemp = foundArr[i];

            if (countTemp.getEventName().length() > lEventName) {
                lEventName = countTemp.getEventName().length();
            }
            if (countTemp.getDetails().length() > lDetails) {
                lDetails = countTemp.getDetails().length();
            }
            if (countTemp.getPriority().length() > lPriority) {
                lPriority = countTemp.getPriority().length();
            }
            for (int j = 0; j < countTemp.getContactList().length; j++) {
                pTemp = pTemp + countTemp.getContactList()[j].length();
                if (j >= 1) {
                    pTemp += 2;
                }
            }
            if (pTemp > lPerson) {
                lPerson = pTemp;
            }
            if (countTemp.getTag().length() > lTag) {
                lTag = countTemp.getTag().length();
            }
            if (countTemp.getDate().length() > lDate) {
                lDate = countTemp.getDate().length();
            }
        }

        //Gives padding no matter the size of input
        lEventName += 5;
        lDetails += 5;
        lPriority += 5;
        lPerson += 5;
        lTag += 5;
        lDate += 5;

        // Variables for the formatting of each event feature
        formatEN = "%-" + lEventName + "." + lEventName + "s";
        formatDe = "%-" + lDetails + "." + lDetails + "s";
        formatPr = "%-" + lPriority + "." + lPriority + "s";
        formatPe = "%-" + lPerson + "." + lPerson + "s";
        formatT = "%-" + lTag + "." + lTag + "s";
        formatDa = "%-" + lDate + "." + lDate + "s";
        String endl = "%n";

        System.out.printf("%-5.5s" + formatEN + formatDe + formatPr + formatPe + formatT + formatDa + endl, "     ", "Event Name", "Details", "Priority", "Contact(s)", "Tag(s)", "Date");
        System.out.printf("%-5.5s" + formatEN + formatDe + formatPr + formatPe + formatT + formatDa + endl, "     ", "----------", "----------", "----------", "----------", "----------", "----------");
        String allPeeps = "";
        for (int j = 0; j < foundArr.length; j++) {
            for (int i = 0; i < foundArr[j].getContactList().length; i++) {
                if (i >= 1 && i < foundArr[j].getContactList().length) {
                    allPeeps = allPeeps + ", ";
                }
                allPeeps = allPeeps + foundArr[j].getContactList()[i];
            }
            System.out.printf("%-5.5s" + formatEN + formatDe + formatPr + formatPe + formatT + formatDa + endl,
                    j + 1, foundArr[j].getEventName(), foundArr[j].getDetails(), foundArr[j].getPriority(), allPeeps, foundArr[j].getTag(), foundArr[j].getDate());

            allPeeps = "";
        }

    }


    //Choice 4
    public static boolean modifySettings(Stack eventStack, ArrayList contacts) throws ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        char choice;
        do {
            System.out.println("Enter 'c' to modify your Contacts, enter 't' to modify your Tags");
            choice = in.nextLine().charAt(0);
        } while (!((choice == 'c') || (choice == 't')));

        if (choice == 'c') {
            editContacts(eventStack, contacts);
        } else if (choice == 't') {
            System.out.println("Feature not available yet.");
        }
        return true;
    }

    public static boolean option() {
        char d;
        Scanner in = new Scanner(System.in);

        d = in.nextLine().charAt(0);
        if (d == 'y') {
            return true;
        } else if (d == 'n') {
            return false;
        } else {
            System.out.println("Please enter yes or no.");
            option();
        }
        return false;
    }

    public static int getChoice() {
        Scanner in = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Enter 1 to Complete an Event. Enter 2 to Add an Event. "
                    + "Enter 3 to Print your Events. Enter 4 to Modify your Settings.\nEnter 5 to Save your ToDo List and Contact List. Enter 6 to Load your existing ToDo List and Contact List. Enter 0 to Exit this Menu.");
            choice = in.nextInt();
        } while (choice <= -1 || choice >= 7);
        return choice;
    }

    public static void editContacts(Stack eventStack, ArrayList contacts) throws ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        int choice, tempC;
        do {
            System.out.println("To create a new contact, enter '1', to edit an existing contact, enter '2', to delete a contact, enter '3', to print all contacts, enter '4'.");
            choice = in.nextInt();
        } while ((choice <= 0) || (choice >= 5));

        if (choice == 1) { //new contact
            contacts.add(new Person());

            System.out.println("Would you like to continue modifying your contacts? (y/n)");
            if (option()) {
                editContacts(eventStack, contacts);
            } else {
                tempC = getChoice();
                check(tempC, eventStack, contacts);
            }

        } else if (choice == 2) { //edit contact
            editPerson(contacts);

            System.out.println("Would you like to continue modifying your contacts? (y/n)");
            if (option()) {
                editContacts(eventStack, contacts);
            } else {
                tempC = getChoice();
                check(tempC, eventStack, contacts);
            }

        } else if (choice == 3) { //delete contact (search first)
            System.out.println("Enter the first and last name of the contact you would like to delete.");
            Person temp;
            if (searchContactList(contacts) != null) {
                temp = searchContactList(contacts);
                contacts.remove(temp);
            }

            System.out.println("Would you like to continue modifying your contacts? (y/n)");
            if (option()) {
                editContacts(eventStack, contacts);
            } else {
                tempC = getChoice();
                check(tempC, eventStack, contacts);
            }

        } else if (choice == 4) { //print all contacts
            formatContactList(contacts);

            System.out.println("Would you like to continue modifying your contacts? (y/n)");
            if (option()) {
                editContacts(eventStack, contacts);
            } else {
                tempC = getChoice();
                check(tempC, eventStack, contacts);
            }
        }
    }

    public static void editPerson(ArrayList contacts) {

        Person temp;
        temp = searchContactList(contacts);

        System.out.println("Edit contact name? (y/n)");
        if (option()) {
            temp.setName();
        }
        System.out.println("Edit address? (y/n)");
        if (option()) {
            temp.setAddress();
        }
        System.out.println("Edit phone number number? (y/n)");
        if (option()) {
            temp.setPhone();
        }
        System.out.println("Edit email? (y/n)");
        if (option()) {
            temp.setEmail();
        }

    }

    public static Person searchContactList(ArrayList contacts) {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the full name of the person you would like to search for.");
        String[] name = in.nextLine().split(" ");
        Person temp = null;
        char input = 's';
        // compare user input to names in contacts to find correct contact
        if (name[0].equals("") || name[1].equals("")) {
            System.out.println("Please enter s to search by contact's first and last name. If you would like to print your contact list and search again, enter p.");
            input = in.nextLine().charAt(0);
        }

        if (input == 'p') {
            formatContactList(contacts);
            input = 's';
        } else if (input == 's') {
            for (int i = 0; i < contacts.size(); i++) {
                temp = (Person) contacts.get(i);
                if (name[0].equals(temp.getFname())) {
                    if (name[1].equals(temp.getLname())) {
                        break;
                    }
                }
            }
        }

        return temp;
    }

    public static void formatContactList(ArrayList contacts) {

        int lFName = 10;
        int lLName = 10;
        int lAdd = 10;
        int lRest = 15;
        int lPhone = 10;
        int lEmail = 10;
        String formatFN, formatLN, formatA, formatR, formatP, formatE;

        //Find the longest string in the array for each part of the event data
        for (int i = 0; i < contacts.size(); i++) {
            Person countTemp = (Person) contacts.get(i);

            if (countTemp.getFname().length() > lFName) {
                lFName = countTemp.getFname().length();
            }
            if (countTemp.getLname().length() > lLName) {
                lLName = countTemp.getLname().length();
            }
            if (countTemp.getAdd().length() > lAdd) {
                lAdd = countTemp.getAdd().length();
            }
            if (countTemp.getRest().length() > lRest) {
                lRest = countTemp.getRest().length();
            }
            if (countTemp.getPhone().length() > lPhone) {
                lPhone = countTemp.getPhone().length();
            }
            if (countTemp.getEmail().length() > lEmail) {
                lEmail = countTemp.getEmail().length();
            }
        }

        //Gives padding no matter the size of input
        lFName += 5;
        lLName += 5;
        lAdd += 5;
        lRest += 5;
        lPhone += 5;
        lEmail += 5;

        // Variables for the formatting of each event feature
        formatFN = "%-" + lFName + "." + lFName + "s";
        formatLN = "%-" + lLName + "." + lLName + "s";
        formatA = "%-" + lAdd + "." + lAdd + "s";
        formatR = "%-" + lRest + "." + lRest + "s";
        formatP = "%-" + lPhone + "." + lPhone + "s";
        formatE = "%-" + lEmail + "." + lEmail + "s";
        String endl = "%n";

        System.out.printf("%-5.5s" + formatFN + formatLN + formatA + formatR + formatP + formatE + endl, "     ", "First Name", "Last Name", "Address", "City, State Zip", "Phone", "Email");
        System.out.printf("%-5.5s" + formatFN + formatLN + formatA + formatR + formatP + formatE + endl, "     ", "----------", "----------", "----------", "---------------", "----------", "----------");

        for (int j = 0; j < contacts.size(); j++) {
            Person temp = (Person) contacts.get(j);
            if (temp == null) {
                System.out.print("");
            }

            System.out.printf("%-5.5s" + formatFN + formatLN + formatA + formatR + formatP + formatE + endl,
                    j + 1, temp.getFname(), temp.getLname(), temp.getAdd(), temp.getRest(), temp.getPhone(), temp.getEmail());

        }

    }

    public static void saveEvents(Stack eventStack) {
        File fin = new File("saveEvents.dat");
        try {
            ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(fin));
            fout.writeObject(eventStack);
            System.out.println("Save Successful");
            fout.close();
        } catch (IOException e) {
            System.out.println("File not found" + e);
        }
    }

    public static void saveContacts(ArrayList contacts) {
        File fin = new File("saveContacts.dat");
        try {
            ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(fin));
            fout.writeObject(contacts);
            System.out.println("Save Successful");
            fout.close();
        } catch (IOException e) {
            System.out.println("File not found" + e);
        }
    }

    public static Stack LoadEvents() throws ClassNotFoundException {
        File fout = new File("saveEvents.dat");
        Stack eventStack = null;
        try {
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(fout));
            eventStack = (Stack) reader.readObject();
            System.out.println("Load successful");
            reader.close();
        } catch (IOException e) {
            System.out.println("Can not complete task");
        }

        return eventStack;
    }

    public static ArrayList LoadContacts() throws ClassNotFoundException {
        File fout = new File("saveContacts.dat");
        ArrayList contacts = new ArrayList();

        try {
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(fout));
            contacts = (ArrayList) reader.readObject();
            System.out.println("Load successful");
            reader.close();
        } catch (IOException e) {
            System.out.println("Can not complete task");
        }

        return contacts;
    }

}
