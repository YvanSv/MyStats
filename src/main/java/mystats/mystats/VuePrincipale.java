package mystats.mystats;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import mystats.mystats.utils.Filtre;
import mystats.mystats.utils.Tailles;

public abstract class VuePrincipale {
    @FXML protected GridPane gridFiltres;
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

    @FXML private void initialize() {
        gridFiltres.setAlignment(Pos.CENTER);
        for (Node n : gridFiltres.getChildren())
            if (n instanceof TextField) ((TextField)n).setPrefSize(50,20);

        GridPane.setColumnSpan(artName, 2);

        scroll.fitToWidthProperty().set(true);
        scroll.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        content.setAlignment(Pos.CENTER);

        actualiser();
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
