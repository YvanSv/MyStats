package mystats.mystats.metier.donnees;

import javafx.scene.image.ImageView;
import java.util.ArrayList;

public class Musique extends Donnee{
    private final ArrayList<Ecoute> lstEcoutes = new ArrayList<>();
    private final Album album;
    private final Artiste artiste;
    private int duree;
    private final String uri;
    private long tempsEcoute;

    public Musique(String nom, Artiste artiste, Album album, String uri) {
        this.nom = nom;
        this.artiste = artiste;
        this.album = album;
        this.uri = uri;
    }

    public void add(Ecoute e) {
        lstEcoutes.add(e);
        duree = Math.max(e.getDuree(), this.duree);
        tempsEcoute += e.getDuree();
    }

    public void remove(Ecoute e) {
        lstEcoutes.remove(e);
        tempsEcoute -= e.getDuree();
    }

    public int getDuree() {
        return duree;
    }

    public Artiste getArtiste() {
        return artiste;
    }

    public Musique getMusique(String nom) {
        return this;
    }

    public Album getAlbum() {
        return album;
    }

    public int getNbEcoutes() {
        return lstEcoutes.size();
    }

    public int getNbEcoutesCompletes() {
        int cpt = 0;
        for (Ecoute e : lstEcoutes)
            if (e.getNature()) cpt++;
        return cpt;
    }

    public int getNbSkips() {
        int cpt = 0;
        for (Ecoute e : lstEcoutes)
            if (!e.getNature()) cpt++;
        return cpt;
    }

    public ImageView getImage() {
        return album.getImage();
    }

    public String getURI() {
        return uri;
    }

    public int getTempsEcoute() {
        return (int)(tempsEcoute / 60000);
    }

    @Override public float getRating() {
        if (getNbEcoutes() == 0) return 0;
        return ((int)(getRatio() / 100.0 * getTempsEcoute() / getNbEcoutes() * 100) / (float)100 + (int)(getTempsEcoute() / (float)100) / (float)10) / 2;
    }

    public String toString() {
        return nom + " - " + artiste.getNom();
    }
}
