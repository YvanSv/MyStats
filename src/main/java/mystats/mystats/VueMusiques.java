package mystats.mystats;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import mystats.mystats.metier.DataReader;
import mystats.mystats.metier.donnees.Musique;
import mystats.mystats.utils.ImgPane;
import mystats.mystats.utils.Tailles;

import java.util.ArrayList;

public class VueMusiques extends VuePrincipale {
    public VueMusiques(Frame f) {
        super(f);
    }

    public void actualiser() {
        vue();
    }

    @FXML private void initialize() {
        classicFilters();
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
        for (int i = 0; i < nbAffiche; i++) {
            if (printed + i >= res.size()) break;
            content.getChildren().add(new ImgPane(i + printed + 1, res.get(i + printed), vueGraphique, this.frame));
        }
        // Rajouter la dernière ligne (charger plus)
        if (res.size() > printed + nbAffiche)
            content.getChildren().add(new ImgPane());
        content.setPrefSize(Tailles.WIDTH_LISTE, Tailles.HEIGHT_LISTE);
        scroll.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
    }
}
