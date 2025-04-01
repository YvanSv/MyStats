package mystats.mystats;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import mystats.mystats.utils.Filtre;
import mystats.mystats.utils.ImgPane;
import mystats.mystats.utils.Langue;
import mystats.mystats.utils.Tailles;

import java.util.ResourceBundle;

public abstract class VuePrincipale {
    @FXML protected Label cacherFiltres;
    @FXML protected VBox paneFiltres;
    @FXML protected GridPane gridFiltres;
    @FXML protected Label lblListened;
    @FXML protected Label lblSkipped;
    @FXML protected Label lblArtist;
    @FXML private Label lblMin;
    @FXML private Label lblMax;
    @FXML private Label lblFully;
    @FXML private Label lblRatio;
    @FXML protected TextField ecMin;
    @FXML protected TextField ecMax;
    @FXML protected TextField coMin;
    @FXML protected TextField coMax;
    @FXML protected TextField skMin;
    @FXML protected TextField skMax;
    @FXML protected TextField raMin;
    @FXML protected TextField raMax;
    @FXML protected TextField artName;
    @FXML protected BorderPane vuePrincipale;
    @FXML protected ScrollPane scroll;
    @FXML protected VBox content;

    protected Frame frame;
    protected int nbLigne;
    protected int printed = 0;
    protected boolean vueGraphique = true;

    public VuePrincipale(Frame frame) {
        this.frame = frame;
    }

    protected void classicFilters() {
        if (Tailles.areFiltersHidden()) {
            paneFiltres.getChildren().removeAll(cacherFiltres,gridFiltres);
            ImgPane.addLabelFilters();
        } else {
            paneFiltres.setPadding(new Insets(10,10,10,10));
            ImgPane.removeLabelFilters();
            cacherFiltres.setPrefSize(Tailles.WIDTH_LISTE*0.06,Tailles.WIDTH_LISTE*0.05+100);
            cacherFiltres.getStyleClass().addAll("medium-size","white","clickable");
            cacherFiltres.setPadding(new Insets(10,20,10,6));
            // Mettre à jour les label en fonction de la langue
            ResourceBundle language = Langue.bundle;
            lblListened.setText(language.getString("listenings"));
            lblSkipped.setText(language.getString("skips"));
            lblArtist.setText(language.getString("artist"));
            lblMin.setText(language.getString("minimum"));
            lblMax.setText(language.getString("maximum"));
            lblFully.setText(language.getString("fullyListened"));
            lblRatio.setText(language.getString("ratio"));

            // Mettre à jour la taille du pane et le padding
            gridFiltres.setPrefSize(Tailles.WIDTH_FILTRES, Tailles.HEIGHT_FILTRES);
            gridFiltres.setPadding(new Insets(Tailles.HEIGHT_FILTRES * 0.2, Tailles.WIDTH_FILTRES * 0.07, Tailles.HEIGHT_FILTRES * 0.2, Tailles.WIDTH_FILTRES * 0.07));
            /*gridFiltres.setHgap(Tailles.WIDTH_FILTRES * 0.03);
            gridFiltres.setVgap(Tailles.HEIGHT_FILTRES * 0.05);*/
            gridFiltres.setAlignment(Pos.CENTER);

            // Définir la taille des éléments du pane
            double largeurTxtField = Tailles.WIDTH_FILTRES * 0.15,
                    hauteurTxtField = 15,
                    largeurPane = largeurTxtField + Tailles.WIDTH_FILTRES * 0.15,
                    hauteurPane = hauteurTxtField + Tailles.HEIGHT_FILTRES * 0.05;
            for (Node n : gridFiltres.getChildren())
                if (n instanceof VBox) {
                    VBox sp = ((VBox) n);
                    sp.setPrefSize(largeurPane, hauteurPane);
                    sp.setAlignment(Pos.CENTER);

                    for (Node n2 : sp.getChildren())
                        if (n2 instanceof TextField)
                            ((TextField) n2).setPrefSize(largeurTxtField, hauteurTxtField);
                }
            GridPane.setColumnSpan(artName, 2);

            // content.setAlignment(Pos.CENTER);
            // content.setPrefSize(Tailles.WIDTH_LISTE,Tailles.HEIGHT_LISTE);
            // scroll.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        }
        actualiser();
    }

    @FXML public void cacherFiltres() {
        Tailles.changeFiltresHidden();
        Tailles.setTailles(Tailles.WIDTH_SCREEN,Tailles.HEIGHT_SCREEN);
        content.getChildren().clear();
        frame.actualiser();
    }

    @FXML private void changeEcMin() {
        if (!ecMin.getText().matches("\\d*")) ecMin.setText(ecMin.getText().replaceAll("[^\\d]", ""));
        if (ecMin.getText().equals("")) Filtre.getInstance().setNbEcoutesMin(-1);
        else Filtre.getInstance().setNbEcoutesMin(Integer.parseInt(ecMin.getText()));
        reset();
        actualiser();
    }

    @FXML private void changeEcMax() {
        if (!ecMax.getText().matches("\\d*")) ecMax.setText(ecMax.getText().replaceAll("[^\\d]", ""));
        if (ecMax.getText().equals("")) Filtre.getInstance().setNbEcoutesMax(-1);
        else Filtre.getInstance().setNbEcoutesMax(Integer.parseInt(ecMax.getText()));
        reset();
        actualiser();
    }

    @FXML private void changeCoMin() {
        if (!coMin.getText().matches("\\d*")) coMin.setText(coMin.getText().replaceAll("[^\\d]", ""));
        if (coMin.getText().equals("")) Filtre.getInstance().setNbEcoutesCompletesMin(-1);
        else Filtre.getInstance().setNbEcoutesCompletesMin(Integer.parseInt(coMin.getText()));
        reset();
        actualiser();
    }

    @FXML private void changeCoMax() {
        if (!coMax.getText().matches("\\d*")) coMax.setText(coMax.getText().replaceAll("[^\\d]", ""));
        if (coMax.getText().equals("")) Filtre.getInstance().setNbEcoutesCompletesMax(-1);
        else Filtre.getInstance().setNbEcoutesCompletesMax(Integer.parseInt(coMax.getText()));
        reset();
        actualiser();
    }

    @FXML private void changeSkMin() {
        if (!skMin.getText().matches("\\d*")) skMin.setText(skMin.getText().replaceAll("[^\\d]", ""));
        if (skMin.getText().equals("")) Filtre.getInstance().setNbSkipMin(-1);
        else Filtre.getInstance().setNbSkipMin(Integer.parseInt(skMin.getText()));
        reset();
        actualiser();
    }

    @FXML private void changeSkMax() {
        if (!skMax.getText().matches("\\d*")) skMax.setText(skMax.getText().replaceAll("[^\\d]", ""));
        if (skMax.getText().equals("")) Filtre.getInstance().setNbSkipMax(-1);
        else Filtre.getInstance().setNbSkipMax(Integer.parseInt(skMax.getText()));
        reset();
        actualiser();
    }

    @FXML private void changeRaMin() {
        if (!raMin.getText().matches("\\d*")) raMin.setText(raMin.getText().replaceAll("[^\\d]", ""));
        if (raMin.getText().equals("")) Filtre.getInstance().setRatioMin(-1);
        else Filtre.getInstance().setRatioMin(Integer.parseInt(raMin.getText()));
        reset();
        actualiser();
    }

    @FXML private void changeRaMax() {
        if (!raMax.getText().matches("\\d*")) raMax.setText(raMax.getText().replaceAll("[^\\d]", ""));
        if (raMax.getText().equals("")) Filtre.getInstance().setRatioMax(-1);
        else Filtre.getInstance().setRatioMax(Integer.parseInt(raMax.getText()));
        reset();
        actualiser();
    }

    @FXML private void changeArtName() {
        Filtre.getInstance().setArtiste(artName.getText());
        reset();
        actualiser();
    }

    public abstract void actualiser();

    protected int numberToPrint(int size) {
        gridFiltres.setPrefSize(Tailles.WIDTH_FILTRES, Tailles.HEIGHT_FILTRES);
        content.setPrefSize(Tailles.WIDTH_SCROLL,Tailles.HEIGHT_SCROLL);

        int nbAffiche;

        if (size < printed + nbLigne) nbAffiche = size;
        else nbAffiche = nbLigne;
        if (nbAffiche - (1 + printed + nbLigne) >= 30) content.setPrefSize(Tailles.WIDTH_SCROLL, Tailles.HEIGHT_SCROLL);
        else content.setPrefSize(Tailles.WIDTH_SCROLL, Tailles.HEIGHT_LISTE * 0.96);
        return nbAffiche;
    }

    protected String getCouleurText(int ind) {
        if (ind % 2 == 0) return "ligne-pair";
        return "ligne-impair";
    }

    public void reset() {
        printed = 0;
        content.getChildren().clear();
        scroll.setVvalue(0);
    }

    protected void setParam() {
        if (vueGraphique) {
            nbLigne = Tailles.NB_LIGNE_MAX_GRAPHIQUE;
            content.setSpacing(0);
        }
        else {
            nbLigne = Tailles.NB_LIGNE_MAX_TEXTE;
            content.setSpacing(10);
        }
    }

    public void loadMore() {
        printed += nbLigne;
        actualiser();
    }
}
