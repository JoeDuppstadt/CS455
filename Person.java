
import java.io.Serializable;
import java.util.Scanner;

/**
 *
 * @author nikki
 */
public class Person implements Serializable {

    String fname = "", lname = "", add = "", city = "", state = "", zip = "", phone = "", email = "";
    BinTag association;

    transient Scanner in = new Scanner(System.in);

    public Person() {
        setName(); setAddress(); setPhone(); setEmail(); //setTag();

    }

    void setPerson() {
        setName();
        setAddress();
        setPhone();
        setEmail();
        //setTag();
    }

    String getFname() {
        return fname;
    }

    String getLname() {
        return lname;
    }

    void setFLname(String fName, String lName) {
        fname = fName;
        lname = lName;
    }

    void setName() {
        System.out.println("Please enter contact's first name.");
        fname = in.nextLine();

        System.out.println("Please enter contact's last name.");
        lname = in.nextLine();
    }

    String getAdd() {
        return add;
    }

    String getRest() {
        return city + " " + state + " " + zip;
    }

    void setAddress() {
        System.out.println("Enter y to add an address for " + fname + " " + lname + ". Enter n if not applicable.");
        char response = in.nextLine().charAt(0);
        if (response == 'n') {
            add = city = state = zip = "";

        } else if (response == 'y') {

            System.out.print("Please enter their street address: ");
            add = in.nextLine();
            System.out.print("City: ");
            city = in.nextLine() + ",";
            System.out.print("State: ");
            state = in.nextLine();
            System.out.print("Zip Code: ");
            zip = in.nextLine();
        }
    }

    String getPhone() {
        return phone;
    }

    void setPhone() {
        System.out.println("Please enter contact's phone number. Enter n if not applicable.");
        phone = in.nextLine();
        while (phone.equals("n")) {
            phone = "";
        }
    }

    String getEmail() {
        return email;
    }

    void setEmail() {
        System.out.println("Please enter contact's email address. Enter n if not applicable.");
        email = in.nextLine();
        while (email.equals("n")) {
            email = "";
        }
    }

    //test
    BinTag getTag() {
        return association;
    }

    //test
    void setTag() {
        System.out.println("Would you like to associate this contact with a relationship title? (y/n)");

    }

}
