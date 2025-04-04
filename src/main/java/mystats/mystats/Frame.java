package mystats.mystats;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import mystats.mystats.metier.DataReader;
import mystats.mystats.utils.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.awt.Desktop;

public class Frame {
    @FXML private BorderPane frame;
    @FXML private HBox boxLogoTitre;
    @FXML private Label logo;
    @FXML private StackPane musiques;
    @FXML private StackPane artistes;
    @FXML private StackPane albums;
    @FXML private StackPane historique;
    @FXML private Label lblMusics;
    @FXML private Label lblArtists;
    @FXML private Label lblAlbums;
    @FXML private Label lblHistoric;
    private Label graphiques;
    private Label arbres;
    private Label parametres;
    @FXML private Pane accueil;
    @FXML private ComboBox<Label> hamburger;
    private final ArrayList<StatistiquePane> stats = new ArrayList<>();
    private boolean clear = true;

    @FXML private void initialize() {
        ResourceBundle language = Langue.bundle;
        lblMusics.setText(language.getString("musics"));
        lblAlbums.setText(language.getString("albums"));
        lblArtists.setText(language.getString("artists"));
        lblHistoric.setText(language.getString("historic"));

        boxLogoTitre.setAlignment(Pos.CENTER_LEFT);
        musiques.setAlignment(Pos.CENTER);
        albums.setAlignment(Pos.CENTER);
        artistes.setAlignment(Pos.CENTER);
        historique.setAlignment(Pos.CENTER);

        if (graphiques == null) {
            Label defaultSelec = new Label("≡");
            graphiques = new Label(language.getString("charts"));
            arbres = new Label(language.getString("trees"));
            parametres = new Label(language.getString("parameters"));
            defaultSelec.getStyleClass().addAll("medium-size", "white", "center", "clickable");
            graphiques.getStyleClass().addAll("low-size", "white", "center", "clickable");
            arbres.getStyleClass().addAll("low-size", "white", "center", "clickable");
            parametres.getStyleClass().addAll("low-size", "white", "center", "clickable");
            hamburger.getItems().addAll(defaultSelec,graphiques,arbres,parametres);
        } else {
            graphiques.setText(language.getString("charts"));
            arbres.setText(language.getString("trees"));
            parametres.setText(language.getString("parameters"));
        }
        hamburger.setOnAction(e -> {
            if (hamburger.getValue() == graphiques) vueGraphique();
            else if (hamburger.getValue() == arbres) vueArbres();
            else if (hamburger.getValue() == parametres) vueParametres();
            hamburger.getSelectionModel().select(0);
            hamburger.getItems().removeAll(graphiques,arbres,parametres);
            hamburger.getItems().addAll(graphiques,arbres,parametres);
        });

        Label noFile = new Label(language.getString("noFileImported"));
        noFile.getStyleClass().addAll("medium-size","white","center");

        Button importFile = new Button(language.getString("importDatas"));
        importFile.setOnAction(e -> importer());
        importFile.getStyleClass().addAll("low-size","white","bouton","clickable");

        Button infosDatas = new Button(language.getString("infosDatas"));
        infosDatas.setOnAction(e -> {
            try { popupInfos(); } catch (Exception ignored) {}
        });
        infosDatas.getStyleClass().addAll("low-size","white","bouton","clickable");
        accueil.getChildren().addAll(noFile,importFile,infosDatas);
    }

    public void setTitlesSize() {
        ImageView imgLogo = new ImageView(new Image(getClass().getResourceAsStream("/img/logo-micro.png")));
        imgLogo.setFitHeight(Tailles.HEIGHT_LOGO * 2.25);
        imgLogo.setPreserveRatio(true);
        logo.setGraphic(imgLogo);
        boxLogoTitre.setPrefWidth(Tailles.WIDTH_SCREEN * 0.2);
        musiques.setPrefWidth(Tailles.WIDTH_SCREEN * 0.175);
        albums.setPrefWidth(Tailles.WIDTH_SCREEN * 0.175);
        artistes.setPrefWidth(Tailles.WIDTH_SCREEN * 0.175);
        historique.setPrefWidth(Tailles.WIDTH_SCREEN * 0.175);
        hamburger.setPrefWidth(Tailles.WIDTH_SCREEN * 0.1);
    }

    public void actualiser() {
        clear = false;
        int type = Filtre.getInstance().getType();
        if (type == 0) {
            vueMusiques();
            setSelected(lblMusics);
        } else if (type == 1) {
            vueArtistes();
            setSelected(lblArtists);
        } else if (type == 2) {
            vueHistorique();
            setSelected(lblHistoric);
        } else if (type == 3) {
            vueAlbums();
            setSelected(lblAlbums);
        }
        //if (frame.getCenter() == graphiques) vueGraphique();
        clear = true;
    }

    public void changeLanguage() {
        initialize();
        creerStats();
    }

    private void setSelected(Label l) {
        lblMusics.getStyleClass().remove("selected");
        lblArtists.getStyleClass().remove("selected");
        lblAlbums.getStyleClass().remove("selected");
        lblHistoric.getStyleClass().remove("selected");
        graphiques.getStyleClass().remove("selected");
        arbres.getStyleClass().remove("selected");
        parametres.getStyleClass().remove("selected");
        if (l != null) l.getStyleClass().add("selected");
    }

    @FXML private void accueil() {
        Filtre.getInstance().clear();
        frame.setCenter(accueil);
        setSelected(null);
        if (DataReader.getInstance().getFichiers().size() != 0) {
            HBox accueil = new HBox();
            accueil.setStyle("-fx-spacing: " + Tailles.WIDTH_SCREEN * 0.01);
            frame.setCenter(accueil);
            int[] indiceChoisis = new int[3];
            for (int i = 0; i < 3; i++) {
                boolean canAdd = true;
                indiceChoisis[i] = (int) (Math.random() * stats.size());
                for (int j = 0; j < i; j++)
                    if (indiceChoisis[i] == indiceChoisis[j]) {
                        canAdd = false;
                        i--;
                        break;
                    }
                if (canAdd) accueil.getChildren().add(stats.get(indiceChoisis[i]).getPane());
            }
            accueil.setAlignment(Pos.CENTER);
            this.accueil = accueil;
        } else if (accueil.getChildren().size() != 2) {
            ResourceBundle language = Langue.bundle;
            Label txt = new Label(language.getString("noFileImported"));
            txt.getStyleClass().addAll("medium-size","white","center");
            Button btn = new Button(language.getString("importDatas"));
            btn.getStyleClass().addAll("low-size","white","bouton");
            btn.setOnAction(e -> importer());
            Button infos = new Button(language.getString("infosDatas"));
            infos.getStyleClass().addAll("low-size","white","bouton");
            infos.setOnAction(e -> {
                try { popupInfos(); } catch (Exception ignored) {}
            });
            accueil = new VBox(txt,btn,infos);
            accueil.getStyleClass().add("accueil");
            frame.setCenter(accueil);
        }
        ImgPane.resetTitres();
    }

    private void popupInfos() {
        Popup popup = new Popup();
        BorderPane root = new BorderPane();
        ResourceBundle language = Langue.bundle;

        Label vide = new Label();
        Label titre = new Label(language.getString("informations"));
        Button quitter = new Button("X");
        vide.setPrefWidth(Tailles.WIDTH_SCREEN * 0.1);
        titre.setPrefWidth(Tailles.WIDTH_SCREEN * 0.5);
        quitter.setPrefWidth(Tailles.WIDTH_SCREEN * 0.1);
        titre.setAlignment(Pos.CENTER);
        quitter.setAlignment(Pos.CENTER_RIGHT);
        titre.getStyleClass().addAll("low-size","white","center");
        quitter.getStyleClass().addAll("clickable","fermer");
        HBox top = new HBox(vide,titre,quitter);
        top.setPrefWidth(Tailles.WIDTH_SCREEN * 0.7);
        top.getStyleClass().add("box-titre");

        VBox center = new VBox();
        center.setSpacing(5);
        center.setPadding(new Insets(20,20,20,20));
        Label lbl1 = new Label(language.getString("infosDatasL1"));
        Hyperlink lbl2 = new Hyperlink("https://www.spotify.com/ca-fr/account/privacy/");
        lbl2.setOnMouseClicked(e -> {
            try { Desktop.getDesktop().browse(new URI("https://www.spotify.com/ca-fr/account/privacy/")); } catch (Exception ignored) {}
        });
        Label lbl3 = new Label(language.getString("infosDatasL3"));
        Label lbl4 = new Label(language.getString("infosDatasL4"));
        Label lbl5 = new Label(language.getString("infosDatasL5"));
        Label lbl6 = new Label(language.getString("infosDatasL6"));
        lbl1.getStyleClass().add("fichier");
        lbl2.getStyleClass().add("lien");
        lbl3.getStyleClass().add("fichier");
        lbl4.getStyleClass().add("fichier");
        lbl5.getStyleClass().add("fichier");
        lbl6.getStyleClass().add("fichier");
        center.getChildren().addAll(lbl1,lbl2,lbl3,lbl4,lbl5,lbl6);
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

        if (!popup.isShowing()) {
            App.addFond(overlay);
            popup.show(App.stage);
        }
    }

    @FXML private void vueMusiques() {
        if (clear) Filtre.getInstance().clear();
        Filtre.getInstance().setType(0);
        setSelected(lblMusics);
        ImgPane.resetTitres();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Frame.class.getResource("musiques-view.fxml"));
        loader.setControllerFactory(iC -> new VueMusiques(this));
        try { frame.setCenter(loader.load()); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML private void vueArtistes() {
        if (clear) Filtre.getInstance().clear();
        Filtre.getInstance().setType(1);
        setSelected(lblArtists);
        ImgPane.resetTitres();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Frame.class.getResource("artistes-view.fxml"));
        loader.setControllerFactory(iC -> new VueArtistes(this));
        try { frame.setCenter(loader.load()); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML private void vueAlbums() {
        if (clear) Filtre.getInstance().clear();
        Filtre.getInstance().setType(3);
        setSelected(lblAlbums);
        ImgPane.resetTitres();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Frame.class.getResource("albums-view.fxml"));
        loader.setControllerFactory(iC -> new VueAlbums(this));
        try { frame.setCenter(loader.load()); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML private void vueHistorique() {
        if (clear) Filtre.getInstance().clear();
        Filtre.getInstance().setType(2);
        setSelected(lblHistoric);
        ImgPane.resetTitres();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Frame.class.getResource("ecoutes-view.fxml"));
        loader.setControllerFactory(iC -> new VueEcoutes(this));
        try { frame.setCenter(loader.load()); }
        catch (Exception e) { e.printStackTrace(); }
    }

    private void vueGraphique() {
        // System.out.println("vueGraphique");
    }

    private void vueArbres() {
        if (clear) Filtre.getInstance().clear();
        setSelected(arbres);
        ImgPane.resetTitres();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Frame.class.getResource("arbres-view.fxml"));
        loader.setControllerFactory(iC -> new VueArbres(this));
        try { frame.setCenter(loader.load()); }
        catch (Exception e) { e.printStackTrace(); }
    }

    private void vueParametres() {
        setSelected(parametres);
        ImgPane.resetTitres();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Frame.class.getResource("parametres-view.fxml"));
        loader.setControllerFactory(iC -> new VueParametres(this));
        try { frame.setCenter(loader.load()); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML private void importer() {
        DataReader dr = DataReader.getInstance();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fichiers JSON ou ZIP", "*.json","*.zip")
        );
        dr.importDatas(fileChooser.showOpenMultipleDialog(App.stage));
        creerStats();
        accueil();
    }

    public void creerStats() {
        DataReader dr = DataReader.getInstance();
        ResourceBundle language = Langue.bundle;
        if (dr.getFichiers().size() != 0) {
            stats.clear();
            stats.add(new StatistiquePane(dr.getNbEcoutes()+"",language.getString("listenings").toLowerCase()));
            stats.add(new StatistiquePane(dr.getNbArtistes()+"",language.getString("artists").toLowerCase()));
            stats.add(new StatistiquePane(dr.getNbAlbums()+"",language.getString("albums").toLowerCase()));
            stats.add(new StatistiquePane(dr.getNbMusiques()+"",language.getString("musics").toLowerCase()));
            stats.add(new StatistiquePane(dr.getNbEcoutesCompletes()+"",language.getString("fullyListened").toLowerCase()));
            stats.add(new StatistiquePane(dr.getNbSkips()+"",language.getString("skips").toLowerCase()));
            stats.add(new StatistiquePane(dr.getPourcentEcoutesCompletes()+"",language.getString("%fullyListened")));
            stats.add(new StatistiquePane(dr.getPourcentSkips(),language.getString("%skips")));
            stats.add(new StatistiquePane(dr.getTopMusiqueEcoutes().getImage(),"#1" , dr.getTopMusiqueEcoutes().getNom(), language.getString("fromListenings")));
            stats.add(new StatistiquePane(dr.getTopMusiqueEcoutesCompletes().getImage(),"#1" , dr.getTopMusiqueEcoutesCompletes().getNom(), language.getString("fromFullyListened")));
            stats.add(new StatistiquePane(dr.getTopMusiqueSkips().getImage(),"#1" , dr.getTopMusiqueSkips().getNom(), language.getString("fromSkips")));
            stats.add(new StatistiquePane(dr.getTopArtisteEcoutes().getImage(),"#1" , dr.getTopArtisteEcoutes().getNom(), language.getString("fromListenings")));
            stats.add(new StatistiquePane(dr.getTopArtisteEcoutesCompletes().getImage(),"#1" , dr.getTopArtisteEcoutesCompletes().getNom(), language.getString("fromFullyListened")));
            stats.add(new StatistiquePane(dr.getTopArtisteSkips().getImage(),"#1" , dr.getTopArtisteSkips().getNom(), language.getString("fromSkips")));
            stats.add(new StatistiquePane(dr.getTopAlbumEcoutes().getImage(),"#1" , dr.getTopAlbumEcoutes().getNom(), language.getString("fromListenings")));
            stats.add(new StatistiquePane(dr.getTopAlbumEcoutesCompletes().getImage(),"#1" , dr.getTopAlbumEcoutesCompletes().getNom(), language.getString("fromFullyListened")));
            stats.add(new StatistiquePane(dr.getTopAlbumSkips().getImage(),"#1" , dr.getTopAlbumSkips().getNom(), language.getString("fromSkips")));
        }
    }
}
