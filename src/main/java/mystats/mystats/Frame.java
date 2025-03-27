package mystats.mystats;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import mystats.mystats.metier.DataReader;
import mystats.mystats.utils.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Frame {
    @FXML private BorderPane frame;
    @FXML private Label logo;
    @FXML private HBox box_titre;
    @FXML private Label titre;
    @FXML private Label musiques;
    @FXML private Label artistes;
    @FXML private Label albums;
    @FXML private Label historique;
    private Label graphiques;
    private Label arbres;
    private Label parametres;
    @FXML private Pane accueil;
    @FXML private ComboBox<Label> hamburger;
    private final ArrayList<StatistiquePane> stats = new ArrayList<>();
    private boolean clear = true;

    @FXML private void initialize() {
        // Récupérer l'écran principal
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getBounds();
        // Obtenir la largeur et la hauteur de l'écran
        double largeur = bounds.getWidth();

        ResourceBundle language = Langue.bundle;
        musiques.setText(language.getString("musics"));
        artistes.setText(language.getString("artists"));
        albums.setText(language.getString("albums"));
        historique.setText(language.getString("historic"));
        if (Langue.language.equals("English"))
            box_titre.spacingProperty().bind(App.stage.widthProperty().multiply((largeur / 4374) / (box_titre.getChildren().size() - 2)));
        else if (Langue.language.equals("Français"))
            box_titre.spacingProperty().bind(App.stage.widthProperty().multiply((largeur / 4983) / (box_titre.getChildren().size() - 2)));
        ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/img/logo-micro.png")));
        img.setFitHeight(Tailles.HEIGHT_LOGO);
        img.setPreserveRatio(true);
        logo.setGraphic(img);
        img = new ImageView(new Image(getClass().getResourceAsStream("/img/parametres.png")));
        img.setFitHeight(Tailles.HEIGHT_LOGO / 2);
        img.setPreserveRatio(true);
        titre.setAlignment(Pos.CENTER);

        Label defaultSelec = new Label("≡");
        graphiques = new Label(language.getString("charts"));
        arbres = new Label(language.getString("trees"));
        parametres = new Label(language.getString("parameters"));
        defaultSelec.getStyleClass().addAll("medium-size","white","center","clickable");
        graphiques.getStyleClass().addAll("low-size","white","center","clickable");
        arbres.getStyleClass().addAll("low-size","white","center","clickable");
        parametres.getStyleClass().addAll("low-size","white","center","clickable");
        hamburger.getStyleClass().addAll("transparent");
        hamburger.getItems().addAll(defaultSelec,graphiques,arbres,parametres);
        hamburger.setOnAction(e -> {
            if (hamburger.getValue() == graphiques) vueGraphique();
            else if (hamburger.getValue() == arbres) vueArbres();
            else if (hamburger.getValue() == parametres) vueParametres();
            hamburger.getSelectionModel().select(0);
            hamburger.getItems().removeAll(graphiques,arbres,parametres);
            hamburger.getItems().addAll(graphiques,arbres,parametres);
        });
        hamburger.getSelectionModel().select(defaultSelec);

        Label noFile = new Label(language.getString("noFileImported"));
        noFile.getStyleClass().addAll("medium-size","white","center");

        Button importFile = new Button(language.getString("importDatas"));
        importFile.setOnAction(e -> importer());
        importFile.getStyleClass().addAll("low-size","white","bouton","clickable");

        Button infosDatas = new Button(language.getString("infosDatas"));
        infosDatas.setOnAction(e -> infos());
        infosDatas.getStyleClass().addAll("low-size","white","bouton","clickable");
        accueil.getChildren().addAll(noFile,importFile,infosDatas);
    }

    public void actualiser() {
        clear = false;
        int type = Filtre.getInstance().getType();
        if (type == 0) {
            vueMusiques();
            setSelected(musiques);
        } else if (type == 1) {
            vueArtistes();
            setSelected(artistes);
        } else if (type == 2) {
            vueHistorique();
            setSelected(historique);
        } else if (type == 3) {
            vueAlbums();
            setSelected(albums);
        }
        //if (frame.getCenter() == graphiques) vueGraphique();
        ImgPane.resetTitres();
        clear = true;
    }

    public void changeLanguage() {
        box_titre.getChildren().remove(hamburger);
        hamburger.getItems().clear();
        box_titre.getChildren().add(hamburger);
        hamburger.getItems().clear();
        initialize();
    }

    private void setSelected(Label l) {
        musiques.getStyleClass().remove("selected");
        artistes.getStyleClass().remove("selected");
        albums.getStyleClass().remove("selected");
        historique.getStyleClass().remove("selected");
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
            infos.setOnAction(e -> infos());
            accueil = new VBox(txt,btn,infos);
            accueil.getStyleClass().add("accueil");
            frame.setCenter(accueil);
        }
        ImgPane.resetTitres();
    }

    @FXML private void vueMusiques() {
        if (clear) Filtre.getInstance().clear();
        setSelected(musiques);
        ImgPane.resetTitres();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Frame.class.getResource("musiques-view.fxml"));
        loader.setControllerFactory(iC -> new VueMusiques(this));
        try { frame.setCenter(loader.load()); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML private void vueArtistes() {
        if (clear) Filtre.getInstance().clear();
        setSelected(artistes);
        ImgPane.resetTitres();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Frame.class.getResource("artistes-view.fxml"));
        loader.setControllerFactory(iC -> new VueArtistes(this));
        try { frame.setCenter(loader.load()); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML private void vueAlbums() {
        if (clear) Filtre.getInstance().clear();
        setSelected(albums);
        ImgPane.resetTitres();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Frame.class.getResource("albums-view.fxml"));
        loader.setControllerFactory(iC -> new VueAlbums(this));
        try { frame.setCenter(loader.load()); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML private void vueHistorique() {
        if (clear) Filtre.getInstance().clear();
        setSelected(historique);
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
                new FileChooser.ExtensionFilter("Fichiers JSON", "*.json")
        );
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(App.stage);
        if (selectedFiles != null)
            for (File file : selectedFiles)
                dr.addFichier(new Fichier(file.getName(),file.getAbsolutePath()));
        // else System.out.println("Aucun fichier sélectionné");
        creerStats();
        accueil();
    }

    @FXML private void infos() {
        System.out.println("Pour télécharger vos données d'historiques Spotify, rendez-vous sur cette page :");
        System.out.println("https://www.spotify.com/ca-fr/account/privacy/");
        System.out.println("Connectez-vous à votre compte Spotify, et cochez l'option \"Historique prolongé des diffusions en continu\" en bas de la page.");
        System.out.println("Confirmez ensuite votre demande, à savoir que vous recevrez vos fichiers via un mail envoyé à la boîte associée à votre compte.");
        System.out.println("Les données arrivent sous 30 jours, régulièrement 2 semaines. Pensez à regarder de temps en temps votre boîte mail.");
        System.out.println("Il est aussi régulier que le lien fourni dans le mail soit expiré avant d'avoir été utilisé, dans ce cas demander de l'aide auprès de leur support, ils sont rapides.");
    }

    public void creerStats() {
        DataReader dr = DataReader.getInstance();
        ResourceBundle language = Langue.bundle;
        if (dr.getFichiers().size() != 0) {
            stats.clear();
            stats.add(new StatistiquePane(dr.getNbEcoutes()+"",language.getString("listenings").toLowerCase()));
            stats.add(new StatistiquePane(dr.getNbArtistes()+"",language.getString("artists").toLowerCase()));
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
