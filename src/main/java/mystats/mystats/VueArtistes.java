package mystats.mystats;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import mystats.mystats.metier.DataReader;
import mystats.mystats.metier.donnees.Artiste;
import mystats.mystats.utils.ImgPane;
import mystats.mystats.utils.Tailles;

import java.util.ArrayList;

public class VueArtistes extends VuePrincipale {
    public VueArtistes(Frame f) {
        super(f);
    }

    @FXML private void initialize() {
        classicFilters();
    }

    public void actualiser() {
        vue();
    }

    private void vue() {
        ImgPane.setFrame(this);
        setParam();

        ArrayList<Artiste> res = DataReader.getInstance().filtrerArtistes();
        int nbAffiche = numberToPrint(res.size());

        // Supprimer la dernière ligne (charger plus)
        if (content.getChildren().size() > 0)
            content.getChildren().remove(content.getChildren().size()-1);
        // Ajouter les titres (tris) si c'est la première fois qu'on affiche quelque chose
        if (content.getChildren().size() == 0)
            content.getChildren().add(ImgPane.getTitresArtistes());
        // Ajouter toutes les lignes
        for (int i = printed; i < printed + nbAffiche; i++)
            content.getChildren().add(new ImgPane(i+1,res.get(i),vueGraphique,frame));
        // Rajouter la dernière ligne (charger plus)
        if (nbAffiche >= 50)
            content.getChildren().add(new ImgPane());
        content.setPrefSize(Tailles.WIDTH_LISTE, Tailles.HEIGHT_LISTE);
        scroll.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
    }
}
