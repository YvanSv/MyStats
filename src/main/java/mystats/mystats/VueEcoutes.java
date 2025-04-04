package mystats.mystats;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import mystats.mystats.metier.DataReader;
import mystats.mystats.metier.donnees.Ecoute;
import mystats.mystats.utils.Filtre;
import mystats.mystats.utils.ImgPane;
import mystats.mystats.utils.Langue;
import mystats.mystats.utils.Tailles;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class VueEcoutes extends VuePrincipale {
    @FXML private CheckBox cbEcoutees;
    @FXML private CheckBox cbSkippees;

    public VueEcoutes(Frame f) {
        super(f);
    }

    @FXML private void initialize() {
        ResourceBundle language = Langue.bundle;
        lblListened.setText(language.getString("listened"));
        lblSkipped.setText(language.getString("skipped"));
        lblArtist.setText(language.getString("artist"));
        // setupFilters();
    }

    public void actualiser() {
        ImgPane.setFrame(this);
        setParam();

        ArrayList<Ecoute> res = DataReader.getInstance().filtrerEcoutes();
        int nbAffiche = numberToPrint(res.size());

        // Supprimer la dernière ligne (charger plus)
        if (content.getChildren().size() > 0)
            content.getChildren().remove(content.getChildren().size()-1);
        // Ajouter les titres (tris) si c'est la première fois qu'on affiche quelque chose
        if (content.getChildren().size() == 0)
            content.getChildren().add(ImgPane.getTitresEcoutes());
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

    @FXML private void nature() {
        if (cbEcoutees.isSelected() && cbSkippees.isSelected() || !cbEcoutees.isSelected() && !cbSkippees.isSelected())
            Filtre.getInstance().setNature(null);
        else Filtre.getInstance().setNature(cbEcoutees.isSelected());
        reset();
        actualiser();
    }
}
