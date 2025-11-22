// This file controls the entire program. It loads books from a file,
// lets the user add books, shows totals and averages, prints last 3 books,
// saves changes back into the file, and displays all books.

import java.io.*;
import java.util.Scanner;

public class ApplicationTest {

    // This array stores every book loaded into the system.
    static Book[] allBooks = new Book[1000];
    static int count = 0; // Running total

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Gets the party started with asking the user
        // for the file name so it can be uploaded
        String file = "";
        boolean loaded = false;

        while (!loaded) {
            System.out.print("Enter filename to load: ");
            file = sc.nextLine();

            if (loadFromFile(file)) {
                loaded = true;
            } else {
                System.out.println("File not found or unreadable. Please try again.\n");
            }
        }

        int choice = 0;

        // Menu for the user to choose from
        do {
            System.out.println("\n------------- MENU -------------");
            System.out.println("1. Show all the books");
            System.out.println("2. Show last 6 books");
            System.out.println("3. Show totals and averages");
            System.out.println("4. Show last 3 audio books");
            System.out.println("5. Show last 3 printed books");
            System.out.println("6. Add audio book");
            System.out.println("7. Add printed book");
            System.out.println("8. Save to file");
            System.out.println("9. Exit");
            System.out.print("Enter your choice (1-9): ");

            // Safe menu input
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Enter a number 1-9.");
                continue;
            }

            switch (choice) {

                case 1:
                    showAllBooks();
                    break;

                case 2:
                    showLastSixBooks();
                    break;

                case 3:
                    showTotals();
                    break;

                case 4:
                    AudioBook.displayLast3Audio();
                    break;

                case 5:
                    PrintedBook.displayLast3Printed();
                    break;

                case 6:
                    addAudio(sc);
                    break;

                case 7:
                    addPrinted(sc);
                    break;

                case 8:
                    saveToFile(file);
                    break;

                case 9:
                    System.out.println("Thanks for using the Book App. Cya Later!");
                    break;

                default:
                    System.out.println("Invalid menu option. Please choose 1-9.");
                    break;
            }

        }
        while (choice != 9);
    }

    // The file loading function reads the book
    // creates objects for each row data file and
    private static boolean loadFromFile(String name) {
        BufferedReader br = null; // Checks to see if the file has already been opened
        try {
            br = new BufferedReader(new FileReader(name)); // Opens the file for reading

            br.readLine(); // Skips the header line

            String line;
            // Allows the file to be read line by line
            while ((line = br.readLine()) != null) {
                line = line.trim();
                // Skips the empty lines
                if (line.equals("")){
                    continue;
                }
                // Splits the separate fields of an array by a comma
                String[] parts = line.split(",");
                // If the row doesn't have enough pieces, skip it
                if (parts.length < 7) {
                    System.out.println("Skipping incomplete row: " + line);
                    continue;
                }
                // Reads the basic info from the row
                String title = parts[0].trim();
                String author = parts[1].trim();
                String genre = parts[2].trim();
                // These depend on the book type
                String costStr = parts[3].trim();
                String lengthStr = parts[4].trim(); // Length is for Audio books
                String pagesStr = parts[5].trim(); // Pages is for the Printed books
                String type = parts[6].trim().toLowerCase(); // This tell which type of book it is
                // Creates the correct type of book object
                if (type.contains("printed")) {
                    int pages = parseIntSafe(pagesStr);
                    allBooks[count++] = new PrintedBook(title, author, genre, pages);
                }
                else if (type.contains("audio")) {
                    int minutes = parseIntSafe(lengthStr);
                    allBooks[count++] = new AudioBook(title, author, genre, minutes);
                }
            }

            System.out.println("Loaded books: " + count);
            return true;
        }
        catch (Exception e) {
            return false;
        }
        finally {
            // Closes the file with the safety of the try-catch loop
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                System.out.println("Could not close file: " + e.getMessage());
            }
        }
    }

    //Converts the string to an integer safely and returns 0 if it's bad
    private static int parseIntSafe(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return 0;
        }
    }

    // Shows total costs and averages from both PrintedBook and AudioBook classes
    private static void showTotals() {
        System.out.println("\n------------- TOTALS -------------");
        System.out.printf("Printed cost total: $%.2f\n", PrintedBook.getTotalPrintedCost());
        System.out.printf("Audio cost total: $%.2f\n", AudioBook.getTotalAudioCost());
        System.out.printf("Total cost of all Books: $%.2f\n", (PrintedBook.getTotalPrintedCost() +
                AudioBook.getTotalAudioCost()));
        System.out.printf("Average pages: %.2f\n", PrintedBook.getAvePages());
        System.out.printf("Average minutes: %.2f\n", AudioBook.getAveMinutes());
    }

    // Prints every book in the full system
    private static void showAllBooks() {
        if (count == 0) {
            System.out.println("No books loaded.");
            return;
        }
        // Loops through all the books and prints them
        System.out.println("\n------------------------ ALL BOOKS --------------------------");
        for (int i = 0; i < count; i++) {
            allBooks[i].displayInfo();
        }
    }
    private static void showLastSixBooks() {
        // If no books exist it tells the user
        if (count == 0) {
            System.out.println("No books loaded.");
            return;
        }

        // Uses any book object to call the interface's default method.
        // So it uses the first book in the list.
        Book first = allBooks[0];
        // Calls the default method from the BookInterface
        first.displayLastSixBooks(allBooks, count);
    }

    // Adds a printed book manually using the user input
    private static void addPrinted(Scanner sc) {
        try {
            System.out.println("----- Add a Printed Book -----");
            System.out.print("Title: "); String t = sc.nextLine();
            System.out.print("Author: "); String a = sc.nextLine();
            System.out.print("Genre: "); String g = sc.nextLine();
            System.out.print("Pages: "); int p = Integer.parseInt(sc.nextLine()); // changes "300" to 300

            allBooks[count++] = new PrintedBook(t, a, g, p);
            System.out.println("Printed Book added successfully!");

        }
        catch (Exception e) {
            System.out.println("Invalid printed book entry.");
        }
    }

    // Adds an audiobook manually using the user input
    private static void addAudio(Scanner sc) {
        try {
            System.out.println("----- Add a Audio Book -----");
            System.out.print("Title: "); String t = sc.nextLine();
            System.out.print("Author: "); String a = sc.nextLine();
            System.out.print("Genre: "); String g = sc.nextLine();
            System.out.print("Minutes: "); int m = Integer.parseInt(sc.nextLine());

            allBooks[count++] = new AudioBook(t, a, g, m);
            System.out.println("Audio Book added successfully!");

        }
        catch (Exception e) {
            System.out.println("Invalid audio book entry.");
        }
    }



    // Saves all books in allBooks[] back into the same CSV file
    private static void saveToFile(String name) {
        PrintWriter pw = null; // Checks to see if the file has already been opened

        try {
            pw = new PrintWriter(new FileWriter(name)); // Opens the file to be overwritten

            // Write the first line  with the colum names
            pw.println("title,author,genre,cost,length,pages,booktype");

            // Loop through all books and write the correct format for each type
            for (int i = 0; i < count; i++) {
                if (allBooks[i] instanceof PrintedBook) { // "instanceof" Checks type of book in allBooks[] array
                    PrintedBook p = (PrintedBook) allBooks[i];
                    pw.println(p.getTitle() + "," + p.getAuthor() + "," + p.getGenre()
                            + "," + p.getCost() + ",N/A," + p.getPages() + ",printedBook");
                }
                else if (allBooks[i] instanceof AudioBook) { // "instanceof" Checks type of book in allBooks[] array
                    AudioBook a = (AudioBook) allBooks[i];
                    pw.println(a.getTitle() + "," + a.getAuthor() + "," + a.getGenre()        // Converts a book object
                            + "," + a.getCost() + "," + a.getMinutes() + ",N/A,audioBook");   // back into a CSV line.
                }
            }

            System.out.println("Successfully saved to file");

        }
        catch (Exception e) {
            System.out.println("Error saving: " + e.getMessage());
        }
        finally {
            // Always try to close the writer, even if saving failed
            try {
                if (pw != null) pw.close();
            }
            catch (Exception e) {
                System.out.println("Could not close writer: " + e.getMessage());
            }
        }
    }
}
