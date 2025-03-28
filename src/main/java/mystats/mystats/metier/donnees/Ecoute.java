package mystats.mystats.metier.donnees;

import javafx.scene.image.ImageView;
import mystats.mystats.utils.Date;

public class Ecoute extends Donnee {
    private Artiste artiste;
    private Album album;
    private Date date;
    private int duree;
    private boolean complete;
    private String uri;

    @Override
    public ImageView getImage() {
        return album.getImage();
    }

    public Artiste getArtiste() {
        return artiste;
    }

    public Musique getMusique(String nom) {
        return artiste.getMusique(this.getNom());
    }

    public Album getAlbum() {
        return album;
    }

    public boolean getNature() {
        return complete;
    }

    public int getDuree() {
        return duree;
    }

    public Date getDate() {
        return date;
    }

    public int getNbEcoutes() {
        return 1;
    }

    public int getNbEcoutesCompletes() {
        return complete ? 1 : 0;
    }

    public int getNbSkips() {
        return complete ? 0 : 1;
    }

    @Override public double getRatio() {
        return complete ? 100 : 0;
    }
    @Override public float getRating() {
        if (getMusique(this.nom).getDuree() == 0) return 0;
        return (float)getDuree() / getMusique(this.nom).getDuree();
    }

    public String getUri() {
        return uri;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setArtiste(Artiste artiste) {
        this.artiste = artiste;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public void setNature(boolean nature) {
        this.complete = nature;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getTempsEcoute() {
        return duree / 60000;
    }

    public boolean deLaMusique(Musique musique) {
        return nom.equals(musique.getNom()) && artiste.getNom().equals(musique.getArtiste().getNom());
    }
}
