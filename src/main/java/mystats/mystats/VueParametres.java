package mystats.mystats;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import mystats.mystats.metier.DataReader;
import mystats.mystats.utils.Fichier;
import mystats.mystats.utils.Langue;
import mystats.mystats.utils.Parametres;
import mystats.mystats.utils.Tailles;

import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

public class VueParametres {
    @FXML private Label lblParam;
    @FXML private VBox listeFichiers;
    @FXML private VBox content;
    @FXML private ScrollPane scroll;
    @FXML private ScrollPane scrollFichiers;
    @FXML private Button btnLoad;
    @FXML private Button btnDelete;
    @FXML private ImageView charger;
    @FXML private ImageView supprimer;
    @FXML private Label lblThreshold;
    @FXML private TextField valueSeuil;
    @FXML private Label lblLanguage;
    @FXML private ComboBox<String> langues;
    private final Frame frame;

    public VueParametres(Frame frame) {
        this.frame = frame;
    }

    private void actualiser() {
        resetListe();

        listeFichiers.setPrefSize(Tailles.WIDTH_SCROLL * 0.3, Tailles.HEIGHT_SCROLL * 0.3);
        content.setPrefSize(Tailles.WIDTH_SCREEN, Tailles.HEIGHT_LISTE);

        for (Fichier f : DataReader.getInstance().getFichiers()) {
            HBox hbox = new HBox();
            hbox.getStyleClass().add("ligne-fichier");

            Label supprimer = new Label();
            ImageView img = new ImageView(new Image(getClass().getResourceAsStream("img/moins2.png")));
            img.setFitHeight(Tailles.HEIGHT_MINI_LOGO);
            img.setPreserveRatio(true);
            supprimer.setGraphic(img);
            supprimer.getStyleClass().add("bouton-fichier");
            supprimer.getStyleClass().add("clickable");
            supprimer.setOnMouseClicked(e -> {
                DataReader.getInstance().removeFichier(f);
                actualiser();
            });

            Label nom = new Label(f.getNom());
            nom.getStyleClass().add("fichier");
            nom.getStyleClass().add("clickable");

            ajouterPopupClick(nom,f);

            hbox.getChildren().addAll(supprimer,nom);
            listeFichiers.getChildren().add(hbox);
        }

        if (DataReader.getInstance().getFichiers().size() == 0) {
            ResourceBundle language = Langue.bundle;
            Label aucun = new Label(language.getString("noFileLoaded"));
            aucun.getStyleClass().addAll("texte-titre","white","low-size");
            listeFichiers.getChildren().add(aucun);
        }
    }

    private void resetListe() {
        listeFichiers.getChildren().clear();
    }

    @FXML private void initialize() {
        ResourceBundle language = Langue.bundle;
        lblParam.setText(language.getString("parameters"));
        btnLoad.setText(language.getString("importDatas"));
        btnDelete.setText(language.getString("deleteAllFiles"));
        charger.setImage(new Image(getClass().getResourceAsStream("img/plus2.png")));
        supprimer.setImage(new Image(getClass().getResourceAsStream("img/moins2.png")));
        lblThreshold.setText(language.getString("thresholdSkipped"));
        valueSeuil.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d*(\\.\\d*)?")) {
                valueSeuil.setText(oldValue);
            }
        });
        valueSeuil.setText(Parametres.getInstance().getTauxPourEtreFull()+"");
        lblLanguage.setText(language.getString("language"));
        scroll.fitToWidthProperty().set(true);
        scroll.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollFichiers.fitToWidthProperty().set(true);
        scrollFichiers.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        actualiser();
    }

    @FXML private void chargerFichiers() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichiers JSON", "*.json")
        );
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(App.stage);
        if (selectedFiles != null)
            for (File file : selectedFiles)
                DataReader.getInstance().addFichier(new Fichier(file.getName(),file.getAbsolutePath()));
        else System.out.println("Aucun fichier sélectionné");
        frame.creerStats();
        actualiser();
    }

    @FXML private void clear() {
        DataReader.getInstance().clear();
        actualiser();
    }

    @FXML private void changerSeuil() {
        Parametres.getInstance().setTauxPourEtreFull(Float.parseFloat(valueSeuil.getText()));
        DataReader.getInstance().setNature(DataReader.getInstance().getHistorique());
    }

    @FXML private void changeLanguage() {
        String value = langues.getValue();
        if (value.equals("Français"))
            Langue.french();
        else if (value.equals("English"))
            Langue.english();
    }

    private void ajouterPopupClick(Label nom, Fichier f) {
        Popup popup = new Popup();
        BorderPane root = new BorderPane();

        Label vide = new Label();
        Label titre = new Label(f.getNom());
        titre.getStyleClass().addAll("low-size","white","center");
        Button quitter = new Button("X");
        quitter.getStyleClass().add("fermer");
        quitter.getStyleClass().add("clickable");
        HBox top = new HBox(vide,titre,quitter);
        top.getStyleClass().add("box-titre");
        top.spacingProperty().bind(App.stage.widthProperty().multiply(0.5/(double)(top.getChildren().size())));

        VBox center = new VBox();
        center.setSpacing(30);
        center.setPadding(new Insets(20,20,20,20));
        ResourceBundle language = Langue.bundle;
        Label txtEmplacement = new Label(language.getString("fileLocation") + " : " + f.getLien());
        GridPane stats = new GridPane();
        stats.setAlignment(Pos.CENTER);

        Label statistiques = new Label(language.getString("stats"));
        Label nbEcoutes = new Label(f.getNbEcoutes() + " " + language.getString("listenings"));
        Label nbCompletes = new Label(f.getNbEcoutesCompletes() + " " + language.getString("fullyListened").toLowerCase());
        Label nbSkips = new Label(f.getNbEcoutesSkips() + " " + language.getString("skips").toLowerCase());
        Label nbMusiques = new Label(f.getNbMusiques() + " " + language.getString("musics").toLowerCase());
        Label nbArtistes = new Label(f.getNbArtistes() + " " + language.getString("artists").toLowerCase());
        Label tempsEcoute = new Label(f.getTempsEcoute() + " " + language.getString("minutes"));

        statistiques.setPadding(new Insets(10,10,10,10));
        nbMusiques.setPadding(new Insets(0,10,0,10));
        tempsEcoute.setPadding(new Insets(0,10,0,10));

        txtEmplacement.getStyleClass().add("fichier");
        statistiques.getStyleClass().add("sous-titre");
        nbEcoutes.getStyleClass().add("stats");
        nbCompletes.getStyleClass().add("stats");
        nbSkips.getStyleClass().add("stats");
        nbMusiques.getStyleClass().add("stats");
        nbArtistes.getStyleClass().add("stats");
        tempsEcoute.getStyleClass().add("stats");

        stats.add(statistiques,1,0);
        stats.add(nbEcoutes,0,1);
        stats.add(nbCompletes,0,2);
        stats.add(nbSkips,0,3);
        stats.add(nbMusiques,1,1);
        stats.add(tempsEcoute,1,2);
        stats.add(nbArtistes,2,1);

        center.getChildren().addAll(txtEmplacement,stats);

        root.setTop(top);
        root.setCenter(center);
        root.getStyleClass().add("popup");
        popup.getContent().add(root);



        Rectangle overlay = new Rectangle();
        overlay.setFill(Color.rgb(0, 0, 0, 0.75));

        overlay.setOnMouseClicked(e -> {
            popup.hide();
            App.removeFond(overlay);
        });
        quitter.setOnMouseClicked(e -> {
            popup.hide();
            App.removeFond(overlay);
        });

        nom.setOnMouseClicked(e -> {
            if (!popup.isShowing()) {
                App.addFond(overlay);
                popup.show(App.stage);
            }
        });
    }
}
