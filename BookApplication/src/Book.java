// Abstract parent class holding shared fields

public abstract class Book implements BookInterface {

    // Basic fields stored in ALL books
    protected String title;
    protected String author;
    protected String genre;
    protected double cost;

    // Constructor: initializes all the shared fields
    public Book(String t, String a, String g, double c) {
        title = t;
        author = a;
        genre = g;
        cost = c;
    }

    // Getter methods
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public double getCost()  { return cost; }

    // Method to print details
    public void displayInfo() {
        System.out.println(title + " | " + author + " | " + genre + " | $" + cost);
    }

    // Abstract method AudioBook and PrintedBook class must implement
    public abstract double computeCost();

    // Returns total cost
    public abstract double getTotalCost();
}