import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;



public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private int age;
    private boolean isStudent;
    private float amountDue;
    private float dailyDue;

    public float getDailyDue() {
        return dailyDue;
    }

    public void setDailyDue(float dailyDue) {
        this.dailyDue = dailyDue;
    }

    public int getBooksRented() {
        return booksRented;
    }

    public void setBooksRented(int booksRented) {
        this.booksRented = booksRented;
    }

    private int booksRented;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean isStudent) {
        this.isStudent = isStudent;
    }

    public Person(int id, String name, int age, boolean isStudent, int booksRented, float amountDue) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.isStudent = isStudent;
        this.booksRented = booksRented;
        this.amountDue = amountDue;
    }

    public static ArrayList<Person> personLoad(String filename) throws Exception{
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (ArrayList<Person>) in.readObject();
        }
    }

    public static void savePerson(ArrayList<Person> persons, String Filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(Filename))) {
            out.writeObject(persons);
            System.out.println("Person List Saved!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {

        return "Person [id=" + id + ", name=" + name + ", age=" + age + ", isStudent=" + isStudent + ", amountDue="
            + amountDue + ", dailyDue=" + dailyDue + ", booksRented=" + booksRented + "]"; 





    }

    public float getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(float amountDue) {
        this.amountDue = amountDue;
    }

}
