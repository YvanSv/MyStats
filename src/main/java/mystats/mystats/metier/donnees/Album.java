package mystats.mystats.metier.donnees;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mystats.mystats.utils.SpotifyAuth;
import mystats.mystats.utils.SpotifyID;
import mystats.mystats.utils.SpotifyTrack;

import java.util.ArrayList;

public class Album extends Donnee {
    private final String nom;
    private final Artiste artiste;
    private final ArrayList<Musique> lstMusiques = new ArrayList<>();

    public Album(String nom, Artiste artiste) {
        this.nom = nom;
        this.artiste = artiste;
    }

    public void add(Musique m) {
        lstMusiques.add(m);
    }

    @Override
    public int getNbEcoutes() {
        int cpt = 0;
        for (Musique m : lstMusiques)
            cpt += m.getNbEcoutes();
        return cpt;
    }

    @Override
    public int getNbEcoutesCompletes() {
        int cpt = 0;
        for (Musique m : lstMusiques)
            cpt += m.getNbEcoutesCompletes();
        return cpt;
    }

    @Override
    public int getNbSkips() {
        int cpt = 0;
        for (Musique m : lstMusiques)
            cpt += m.getNbSkips();
        return cpt;
    }

    @Override
    public double getRatio() {
        return (double)getNbEcoutesCompletes() / (double)getNbEcoutes() * 100;
    }

    @Override
    public Artiste getArtiste() {
        return artiste;
    }

    public Musique getMusique(String nom) {
        for (Musique m : lstMusiques)
            if (m.getNom().equals(nom))
                return m;
        return null;
    }

    @Override
    public String getNom() {
        return nom;
    }

    public ImageView getImage() {
        if (img == null) {
            this.img = new ImageView();
            String accessToken = SpotifyAuth.getValidAccessToken();
            if (accessToken != null) {
                String trackId = SpotifyID.parseID(lstMusiques.get(0).getURI());
                String imageUrl = SpotifyTrack.getTrackImage(trackId, accessToken);

                if (imageUrl != null) {
                    Image image = new Image(imageUrl);
                    img = new ImageView(image);
                    img.setFitWidth(300);
                    img.setPreserveRatio(true);
                }//  else System.out.println("Impossible de récupérer l'image du morceau.");
            } // else System.out.println("Impossible d'obtenir un token d'accès.");
        }
        return img;
    }

    public int getTempsEcoute() {
        int res = 0;
        for (Musique m : lstMusiques)
            res += m.getTempsEcoute();
        return res;
    }

    public String toString() {
        return nom + " - " + artiste.getNom();
    }
}
