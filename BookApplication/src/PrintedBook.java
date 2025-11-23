public class PrintedBook extends Book {

    private int pages;

    // Static totals are used in all PrintedBooks
    private static int totalPages = 0;
    private static int printedCount = 0;
    private static double printedCostTotal = 0.0;

    // Array to store last 3 printed books (most recent at index 0)
    private static PrintedBook[] lastPrinted = new PrintedBook[3];

    public PrintedBook(String t, String a, String g, int p) {
        // cost = pages * $10 according to spec
        super(t, a, g, p * 10.0);
        pages = p;

        // static running totals
        printedCount++;     // Tracks how many printed books we have
        totalPages += p;    // Add this book's pages to total pages
        printedCostTotal += cost;   // Add cost to total printed cost

        updateLastPrinted(this);   // Store this book in the last 3 list
    }

    // Updates rotating last-3 list
    private static void updateLastPrinted(PrintedBook pb) {
        lastPrinted[2] = lastPrinted[1];
        lastPrinted[1] = lastPrinted[0];
        lastPrinted[0] = pb;
    }

    public int getPages() { return pages; }

    // Average pages across all printed books
    public static double getAvePages() {
        if (printedCount == 0){
            return 0.0;
        }
        return (double) totalPages / printedCount;
    }

    // Static getters for running totals
    public static int getTotalPages() { return totalPages; }
    public static int getPrintedCount() { return printedCount; }
    public static double getTotalPrintedCost() { return printedCostTotal; }

    // Displays the last three printed books
    public static void displayLast3Printed() {
        System.out.println("\n--- Last 3 Printed Books ---");
        boolean any = false;
        for (int i = 0; i < 3; i++) {
            if (lastPrinted[i] != null) {
                lastPrinted[i].displayInfo();
                any = true;
            }
        }
        if (!any) System.out.println("No printed books available.");
    }

    // Needs to be here because of the abstract class
    public double computeCost() { return cost; }

    // Overrides to show pages
    @Override
    public void displayInfo() {
        System.out.println("Printed: " + title + " | " + author + " | " + genre
                + " | Pages: " + pages + " | Cost: $" + cost);
    }

    // Not used for class totals, but needed because of abstract
    public double getTotalCost() { return printedCostTotal; }

    public int getGenreCount(String g) {
        if (genre.equalsIgnoreCase(g)) {
            return 1;
        } else {
            return 0;
        }
    }
}