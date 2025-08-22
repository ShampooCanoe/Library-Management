import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;



public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    public int getDaysRented() {
        return daysRented;
    }

    public void setDaysRented(int daysRented) {
        this.daysRented = daysRented;
    }

    private int id;
    private String title;
    private String author;
    private float rentalPrice;
    private int renterID;
    private int daysRented;

    public Book(int id, int rentalPrice, String title, String author, int renterID) {
        this.id = id;
        this.rentalPrice = rentalPrice;
        this.title = title;
        this.renterID = renterID;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(float rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public int getRenterID() {
        return renterID;
    }

    public void setRenterID(int renterID) {
        this.renterID = renterID;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author=" + author + ", rentalPrice=" + rentalPrice
                + ", renterID=" + renterID + ", daysRented=" + daysRented + "]";
    }

    public static void saveBook(ArrayList<Book> books, String Filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(Filename))) {
            out.writeObject(books);
            System.out.println("Book List Saved!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Book> load(String filename) throws Exception{
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (ArrayList<Book>) in.readObject();
        }
    }

}
