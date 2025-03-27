package mystats.mystats.metier.donnees;

import javafx.scene.image.ImageView;

public abstract class Donnee {
    protected ImageView img;
    public abstract int getNbEcoutes();
    public abstract int getNbEcoutesCompletes();
    public abstract int getNbSkips();
    public abstract double getRatio();
    public abstract Artiste getArtiste();
    public abstract Musique getMusique(String nom);
    public abstract String getNom();
    public abstract ImageView getImage();
    public abstract int getTempsEcoute();
}
