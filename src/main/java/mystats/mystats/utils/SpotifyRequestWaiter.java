package mystats.mystats.utils;

public class SpotifyRequestWaiter {
    public static SpotifyRequestWaiter instance;
    private int slotsLeft = 60;

    public static SpotifyRequestWaiter getInstance() {
        if (instance == null) instance = new SpotifyRequestWaiter();
        return instance;
    }

    public boolean reserveRequest() {
        return slotsLeft != 0;
    }

    public void askForRequest() {
        slotsLeft--;
        try { Thread.sleep(30000); } catch (Exception ignored) {}
        slotsLeft++;
    }
}
