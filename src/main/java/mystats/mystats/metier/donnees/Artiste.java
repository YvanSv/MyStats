package mystats.mystats.metier.donnees;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mystats.mystats.utils.SpotifyAuth;
import mystats.mystats.utils.SpotifyID;
import mystats.mystats.utils.SpotifyTrack;

import java.util.ArrayList;

public class Artiste extends Donnee {
    private final String nom;
    private final ArrayList<Musique> musiques = new ArrayList<>();

    public Artiste(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public ImageView getImage() {
        if (img == null) {
            String musiqueURI = musiques.get(0).getURI();
            String accessToken = SpotifyAuth.getValidAccessToken();

            img = new ImageView();
            if (accessToken != null) {
                String trackId = SpotifyID.parseID(musiqueURI);
                String imageUrl = SpotifyTrack.getArtistImage(trackId, accessToken);
                if (imageUrl != null) {
                    Image image = new Image(imageUrl);
                    img = new ImageView(image);
                    img.setFitHeight(300);
                    img.setPreserveRatio(true);
                }// else System.out.println("Impossible de récupérer l'image du morceau.");
            } // else System.out.println("Impossible d'obtenir un token d'accès.");
        }
        return img;
    }

    public void add(Musique musique) {
        musiques.add(musique);
    }

    public int getNbEcoutes() {
        int res = 0;
        for (Musique m : musiques)
            res += m.getNbEcoutes();
        return res;
    }

    public int getNbEcoutesCompletes() {
        int res = 0;
        for (Musique m : musiques)
            res += m.getNbEcoutesCompletes();
        return res;
    }

    public int getNbSkips() {
        int res = 0;
        for (Musique m : musiques)
            res += m.getNbSkips();
        return res;
    }

    public double getRatio() {
        if (getNbEcoutes() == 0) return 0;
        return (double)getNbEcoutesCompletes() / (double)(getNbEcoutes()) * 100;
    }

    public Artiste getArtiste() {
        return this;
    }

    public Musique getMusique(String nom) {
        for (Musique m : musiques)
            if (m.getNom().equals(nom))
                return m;
        return null;
    }

    public int getTempsEcoute() {
        int res = 0;
        for (Musique m : musiques)
            res += m.getTempsEcoute();
        return res;
    }

    public String toString() {
        return nom;
    }
}
