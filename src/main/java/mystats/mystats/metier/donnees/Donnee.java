package mystats.mystats.metier.donnees;

import javafx.scene.image.ImageView;

public abstract class Donnee {
    protected ImageView img;
    protected String nom;
    public abstract int getNbEcoutes();
    public abstract int getNbEcoutesCompletes();
    public abstract int getNbSkips();
    public abstract Artiste getArtiste();
    public abstract Musique getMusique(String nom);
    public abstract ImageView getImage();
    public abstract int getTempsEcoute();

    public double getRatio() {
        if (getNbEcoutes() == 0) return 0;
        return (double)getNbEcoutesCompletes() / (double)getNbEcoutes() * 100;
    }

    public float getRating() {
        if (getNbEcoutes() == 0) return 0;
        return (float) (getRatio() / 100.0 * getTempsEcoute() / getNbEcoutes());
    }

    public String getNom() {
        return nom;
    }
}
