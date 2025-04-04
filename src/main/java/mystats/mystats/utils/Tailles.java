package mystats.mystats.utils;

public class Tailles {
    private static boolean hidden_filters = false;
    public static double WIDTH_SCREEN;
    public static double HEIGHT_SCREEN;
    public static double WIDTH_HEADER;
    public static double HEIGHT_HEADER;
    public static double HEIGHT_LOGO = 108;
    public static double HEIGHT_MINI_LOGO = 22;
    public static double WIDTH_FILTRES;
    public static double HEIGHT_FILTRES;
    public static double WIDTH_LISTE;
    public static double HEIGHT_LISTE;
    public static double WIDTH_SCROLL;
    public static double HEIGHT_SCROLL;
    public static final int NB_LIGNE_MAX_GRAPHIQUE = 50;
    public static final int NB_LIGNE_MAX_TEXTE = 600;

    public static void setTailles(double width, double height) {
        WIDTH_SCREEN = width;
        HEIGHT_SCREEN = height;
        WIDTH_HEADER = WIDTH_SCREEN;
        HEIGHT_HEADER = HEIGHT_SCREEN * 0.15;
        HEIGHT_LOGO = HEIGHT_SCREEN * 0.1;
        HEIGHT_MINI_LOGO = HEIGHT_LOGO * 0.2;
        if (!hidden_filters) {
            WIDTH_FILTRES = WIDTH_SCREEN * 0.175;
            WIDTH_LISTE = WIDTH_SCREEN - WIDTH_FILTRES;
            WIDTH_SCROLL = WIDTH_LISTE - 15;
        } else {
            WIDTH_FILTRES = 0;
            WIDTH_LISTE = WIDTH_SCREEN - WIDTH_FILTRES;
            WIDTH_SCROLL = WIDTH_LISTE - 15;
        }
        HEIGHT_FILTRES = HEIGHT_SCREEN - HEIGHT_HEADER;
        HEIGHT_LISTE = HEIGHT_FILTRES;
        HEIGHT_SCROLL = (HEIGHT_LISTE - 10) * 0.90;
    }

    public static void changeFiltresHidden() {
        hidden_filters = !hidden_filters;
    }

    public static boolean areFiltersHidden() {
        return hidden_filters;
    }
}
