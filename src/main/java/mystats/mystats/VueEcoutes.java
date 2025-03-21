package mystats.mystats;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import mystats.mystats.metier.DataReader;
import mystats.mystats.metier.donnees.Ecoute;
import mystats.mystats.utils.Filtre;
import mystats.mystats.utils.ImgPane;

import java.util.ArrayList;

public class VueEcoutes extends VuePrincipale {
    @FXML private CheckBox cbEcoutees;
    @FXML private CheckBox cbSkippees;

    public VueEcoutes(Frame f) {
        super(f);
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
        for (int i = printed; i < printed + nbAffiche; i++)
            content.getChildren().add(new ImgPane(i+1,res.get(i),vueGraphique,frame));
        // Rajouter la dernière ligne (charger plus)
        if (nbAffiche >= 50)
            content.getChildren().add(new ImgPane());
    }

    @FXML private void nature() {
        if (cbEcoutees.isSelected() && cbSkippees.isSelected() || !cbEcoutees.isSelected() && !cbSkippees.isSelected())
            Filtre.getInstance().setNature(null);
        else Filtre.getInstance().setNature(cbEcoutees.isSelected());
        reset();
        actualiser();
    }
}
