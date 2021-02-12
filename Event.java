import java.io.Serializable;
/**
 *
 * @author nikki
 */
import java.util.*;

public class Event implements Serializable {

    String eventNum = "", eventName = "", details = "", priority = "", tags = "", date = "";
    ArrayList <Person> modContactList;
    
   

    private int day = -1, month = -1, year = -1;
    transient Scanner in = new Scanner(System.in);
    
    public  Event() {
        modContactList = new ArrayList(0);
    }
    
    String getNum() {
        return eventNum;
    }

    void initializeEventName(String name) {
        this.eventName = name;
    }

    //Getter and Setter for event name
    void setEventName() {
        System.out.println("Set a name for your task.");
        eventName = in.nextLine();
    }

    String getEventName() {
        return eventName;
    }

    //Getter and Setter for event details
    void setDetails() {
        System.out.println("Please enter details about your event. Enter 'n' if not applicable.");
        details = in.nextLine();
        while (details == "n") {
            in.nextLine();
        }
    }

    String getDetails() {
        return details;
    }

    //Getter and Setter for priority level
    void setPriority() {
        do {
            System.out.println("Please enter a numeric priority value between 1 and 4. Enter 0 if not applicable.");
            priority = in.nextLine();
        } while (priority.equals(0) || priority.equals(1) || priority.equals(2) || priority.equals(3) || priority.equals(4) || priority.equals(5));
    }

    String getPriority() {
        return priority;
    }
    
    //Getter and Setter for searching through contact list and adding or creating new contacts
    void setContactList(Person append) {
        modContactList.add(append);

    }
    
    
    String[] getContactList() {
        String[] contactName = new String[modContactList.size()];
        for (int i = 0; i < modContactList.size(); i++) {
           contactName[i] = modContactList.get(i).getFname() + " " +  modContactList.get(i).getLname();
        }
        return contactName;
    }

    //Getter and Setter for tags
    String getTag() {
        return ("Feature not available at this time.");
    }

    //Getter and Setter for date by day, month, and year
    void setDate() {
        System.out.println("Please enter day in dd format.");
        int d = in.nextInt();
        while (d < 1 || d > 31) {
            System.out.println("Please enter a valid day.");
            d = in.nextInt();
        }
        day = d;
        System.out.println("Please enter month in mm format.");
        int m = in.nextInt();
        while (m < 1 || m > 12) {
            System.out.println("Please enter a valid month.");
            m = in.nextInt();
        }
        month = m;
        System.out.println("Please enter year in yyyy format.");
        int y = in.nextInt();
        while (y < 1800) {
            System.out.println("Please enter a valid day.");
            y = in.nextInt();
        }
        year = y;
    }

    int getDay() {
        return day;
    }

    int getMonth() {
        return month;
    }

    int getYear() {
        return year;
    }
    
    String getDate() {
        return month + "-" + day + "-" +year;
    }

}
