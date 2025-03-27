package mystats.mystats.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class Langue {
    public static ResourceBundle bundle;
    public static String language;

    public static void english() {
        loadLanguage(Locale.ENGLISH);
    }

    public static void french() {
        loadLanguage(Locale.FRENCH);
    }

    private static void loadLanguage(Locale l) {
        bundle = ResourceBundle.getBundle("messages", l);
    }
}
