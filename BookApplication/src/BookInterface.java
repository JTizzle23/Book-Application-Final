public interface BookInterface {

    // Default method to show last 6 books (system-wide)
    default void displayLastSixBooks(Book[] allBooks, int count) {
        System.out.println("\n--- Last 6 Books Added ---");

        int start = count - 6;
        // Avoids negative indexes
        if (start < 0){
            start = 0;
        }

        // Loops from the start to the most recent book
        // added to show all the books that were downloaded
        for (int i = start; i < count; i++) {
            if (allBooks[i] != null) {
                allBooks[i].displayInfo();
            }
        }
    }

    // Returns the number of books in a certain genre
    int getGenreCount(String genre);

    // Returns the total cost of all books
    double getTotalCost();
}