import java.io.*;
import java.time.LocalDate;

public class DateUpdater {

    private static final String FILE = "Last_Updated_date.txt";

    public static LocalDate loadDate() {
        File f = new File(FILE);
        if (!f.exists()) {
            System.err.println("Date file not found. Using today's date.");
            LocalDate today = LocalDate.now();
            saveDate(today);
            return LocalDate.now();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String dateString = br.readLine();
            if (dateString != null && !dateString.trim().isEmpty()) {
                return LocalDate.parse(dateString.trim());
            } else {
                System.err.println("Date file is empty. Using today's date.");
                LocalDate today = LocalDate.now();
                saveDate(today);
                return LocalDate.now();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error reading date from file. Using today's date.");
            return LocalDate.now();
        }
    }

    public static void saveDate(LocalDate date) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE))) {
            bw.write(date.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}