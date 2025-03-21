package mystats.mystats.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Date {
    private final String txt;
    private final int annee;
    private final int mois;
    private final int jour;
    private final int heure;
    private final int minutes;

    public Date(String s) {
        txt = s;
        annee = Integer.parseInt(s.split(" ")[0].split("-")[0]);
        mois = Integer.parseInt(s.split(" ")[0].split("-")[1]);
        jour = Integer.parseInt(s.split(" ")[0].split("-")[2]);
        heure = Integer.parseInt(s.split(" ")[1].split(":")[0]);
        minutes = Integer.parseInt(s.split(" ")[1].split(":")[1]);
    }

    public int getAnnee() {
        return annee;
    }

    public int getMois() {
        return mois;
    }

    public int getJour() {
        return jour;
    }

    public int getHeure() {
        return heure;
    }

    public int getMinutes() {
        return minutes;
    }

    public boolean avant(Date d2) {
        for (int i = 0; i < txt.length(); i++)
            if (d2.toNorme().charAt(i) > toNorme().charAt(i)) return true;
            else if (d2.toNorme().charAt(i) < toNorme().charAt(i)) return false;

        return true;
    }

    public boolean memeDate(Date d2) {
        return jour == d2.getJour() && mois == d2.getMois() && annee == d2.getAnnee();
    }

    public long joursEntre(Date d2) {
        LocalDate dateDebut = LocalDate.of(annee, mois, jour);
        LocalDate dateFin = LocalDate.of(d2.getAnnee(), d2.getMois(), d2.getJour());
        return ChronoUnit.DAYS.between(dateDebut, dateFin);
    }

    public String toNorme() { return txt; }

    public String toString() {
        return DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH).format(LocalDate.of(annee,mois,jour));
    }
}
