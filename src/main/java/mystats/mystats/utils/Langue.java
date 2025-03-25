package mystats.mystats.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class Langue {
    public static ResourceBundle bundle;
    private static int language = 0;
    public static String album = "";
    public static String albums = "";
    public static String artist = "";
    public static String artists = "";
    public static String listening = "";
    public static String historic = "";
    public static String music = "";
    public static String musics = "";
    public static String charts = "";
    public static String trees = "";
    public static String parameters = "";
    public static String noFileImported = "";
    public static String importDatas = "";
    public static String infosDatas = "";
    public static String listenings = "";
    public static String fullyListened = "";
    public static String skips = "";

    public static void english() {
        loadLanguage(Locale.ENGLISH);
    }

    public static void french() {
        loadLanguage(Locale.FRENCH);
    }

    private static void loadLanguage(int value) {
        language = value;
        album = get("Album%%Album");
        albums = get("Albums%%Albums");
        artist = get("Artist%%Artiste");
        artists = get("Artists%%Artistes");
        listening = get("Listening%%Ecoute");
        historic = get("Historic%%Historique");
        music = get("Music%%Musique");
        musics = get("Musics%%Musiques");
        charts = get("\uD83D\uDCCA Charts%%\uD83D\uDCCA Graphiques");
        trees = get("\uD83C\uDF32 Trees%%\uD83C\uDF32 Arbres");
        parameters = get("⚙️Parameters%%⚙️Paramètres");
        noFileImported = get("No file imported, go to parameters or click here :%%Aucun fichier importé, rendez-vous dans les paramètres ou cliquez ici :");
        importDatas = get("Import datas%%Importer des données");
        infosDatas = get("To know more about datas%%En savoir plus sur les données");
        listenings = get("listenings%%écoutes");
        fullyListened = get("fully listened%%écoutes complètes");
        skips = get("Skips%%Skips");
    }

    private static String get(String lstWords) {
        return lstWords.split("%%")[language];
    }

    private static void loadLanguage(Locale l) {
        bundle = ResourceBundle.getBundle("messages", l);
    }
}
