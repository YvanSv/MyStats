package mystats.mystats.utils;

public class SpotifyID {
    public static String parseID(String uri) {
        return uri.split(":")[uri.split(":").length-1];
    }
}
