
//Required to read the URL and Extract the data to a 2D grid
import java.util.Scanner;
import java.io.*;
import java.net.*;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Beginning Extractions from 2D grid");
        System.out.println("Please enter the URL from which you want to extract the data");
        String URL = scanner.nextLine();
        extractData(URL);

        scanner.close();
    }

    public static void extractData(String url) {
        try {
            System.out.println("Extracting data from the URL: " + url);
            Scanner reader = new Scanner(URI.create(url).toURL().openStream());
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if (line.contains("tr class")) {
                    System.out.println("Found the term in the line: " + line);
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("This is not a correct format or invalid document");
        }
    }
}
