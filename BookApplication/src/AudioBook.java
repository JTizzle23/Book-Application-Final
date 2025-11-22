// Stores total minutes and last 3 audiobooks

public class AudioBook extends Book {

    private int minutes;

    // Static totals
    private static int totalMinutes = 0;
    private static int audioCount = 0;
    private static double audioCostTotal = 0.0;

    private static AudioBook[] lastAudio = new AudioBook[3];

    public AudioBook(String t, String a, String g, int m) {
        // cost = minutes * $5 (half of page cost $10)
        super(t, a, g, m * 5.0);
        minutes = m;

        audioCount++;
        totalMinutes += m;
        audioCostTotal += cost;

        updateLastAudio(this);
    }

    private static void updateLastAudio(AudioBook ab) {
        lastAudio[2] = lastAudio[1];
        lastAudio[1] = lastAudio[0];
        lastAudio[0] = ab;
    }

    public int getMinutes() { return minutes; }

    public static double getAveMinutes() {
        if (audioCount == 0){
            return 0.0;
        }
        return (double) totalMinutes / audioCount;
    }

    // Static getters for totals
    public static int getTotalMinutes() { return totalMinutes; }
    public static int getAudioCount() { return audioCount; }
    public static double getTotalAudioCost() { return audioCostTotal; }

    public static void displayLast3Audio() {
        System.out.println("\n--- Last 3 Audio Books ---");
        boolean any = false;
        for (int i = 0; i < 3; i++) {
            if (lastAudio[i] != null) {
                lastAudio[i].displayInfo();
                any = true;
            }
        }
        if (!any) System.out.println("No audio books available.");
    }

    public double computeCost() { return cost; }

    // Overrides to show minutes
    @Override
    public void displayInfo() {
        System.out.println("Audio: " + title + " | " + author + " | " + genre
                + " | Minutes: " + minutes + " | Cost: $" + cost);
    }

    public double getTotalCost() { return audioCostTotal; }

    public int getGenreCount(String g) {
        if (genre.equalsIgnoreCase(g)) {
            return 1;
        } else {
            return 0;
        }
    }
}