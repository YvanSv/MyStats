package mystats.mystats;

import mystats.mystats.metier.DataReader;
import mystats.mystats.metier.donnees.Musique;
import mystats.mystats.utils.ImgPane;

import java.util.ArrayList;

public class VueMusiques extends VuePrincipale {
    public VueMusiques(Frame f) {
        super(f);
    }

    public void actualiser() {
        vue();
    }

    protected void vue() {
        ImgPane.setFrame(this);
        setParam();

        ArrayList<Musique> res = DataReader.getInstance().filtrerMusiques();
        int nbAffiche = numberToPrint(res.size());

        // Supprimer la dernière ligne (charger plus)
        if (content.getChildren().size() > 0)
            content.getChildren().remove(content.getChildren().size()-1);
        // Ajouter les titres (tris) si c'est la première fois qu'on affiche quelque chose
        if (content.getChildren().size() == 0)
            content.getChildren().add(ImgPane.getTitresMusiques());
        // Ajouter toutes les lignes
        for (int i = printed; i < printed + nbAffiche; i++)
            content.getChildren().add(new ImgPane(i+1,res.get(i),vueGraphique));
        // Rajouter la dernière ligne (charger plus)
        if (nbAffiche >= 50)
            content.getChildren().add(new ImgPane());
    }
}
