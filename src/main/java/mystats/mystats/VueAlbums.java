package mystats.mystats;

import mystats.mystats.metier.DataReader;
import mystats.mystats.metier.donnees.Album;
import mystats.mystats.utils.ImgPane;

import java.util.ArrayList;

public class VueAlbums  extends VuePrincipale {
    public VueAlbums(Frame f) {
        super(f);
    }

    public void actualiser() {
        vue();
    }

    private void vue() {
        ImgPane.setFrame(this);
        setParam();

        ArrayList<Album> res = DataReader.getInstance().filtrerAlbums();
        int nbAffiche = numberToPrint(res.size());

        // Supprimer la dernière ligne (charger plus)
        if (content.getChildren().size() > 0)
            content.getChildren().remove(content.getChildren().size()-1);
        // Ajouter les titres (tris) si c'est la première fois qu'on affiche quelque chose
        if (content.getChildren().size() == 0)
            content.getChildren().add(ImgPane.getTitresAlbums());
        // Ajouter toutes les lignes
        for (int i = printed; i < printed + nbAffiche; i++)
            content.getChildren().add(new ImgPane(i+1,res.get(i),vueGraphique,frame));
        // Rajouter la dernière ligne (charger plus)
        if (nbAffiche >= 50)
            content.getChildren().add(new ImgPane());
    }
}