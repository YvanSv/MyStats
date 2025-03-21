package mystats.mystats;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import mystats.mystats.metier.DataReader;
import mystats.mystats.utils.Fichier;
import mystats.mystats.utils.Filtre;
import mystats.mystats.utils.ImgPane;
import mystats.mystats.utils.Tailles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Frame {
    @FXML private BorderPane frame;
    @FXML private Label logo;
    @FXML private HBox box_titre;
    @FXML private Label titre;
    @FXML private Label musiques;
    @FXML private Label artistes;
    @FXML private Label albums;
    @FXML private Label historique;
    @FXML private Label graphiques;
    @FXML private Label arbres;
    @FXML private Label parametres;
    @FXML private Pane accueil;
    private final ArrayList<StatistiquePane> stats = new ArrayList<>();
    private boolean clear = true;

    @FXML private void initialize() {
        box_titre.spacingProperty().bind(App.stage.widthProperty().multiply(0.5/(double)(box_titre.getChildren().size())));
        ImageView img = new ImageView(new Image(getClass().getResourceAsStream("img/logo-micro.png")));
        img.setFitHeight(Tailles.HEIGHT_LOGO);
        img.setPreserveRatio(true);
        logo.setGraphic(img);
        img = new ImageView(new Image(getClass().getResourceAsStream("img/parametres.png")));
        img.setFitHeight(Tailles.HEIGHT_LOGO / 2);
        img.setPreserveRatio(true);
        parametres.setGraphic(img);
        titre.setAlignment(Pos.CENTER);
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
            //accueil.setPrefSize(Tailles.WIDTH_SCREEN,Tailles.HEIGHT_LISTE);
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
            Label txt = new Label("Aucun fichier importé, rendez-vous dans les paramètres ou cliquez ici :");
            txt.getStyleClass().addAll("medium-size","white","center");
            Button btn = new Button("Importer des données");
            btn.getStyleClass().addAll("low-size","white","bouton");
            btn.setOnAction(e -> importer());
            Button infos = new Button("En savoir plus sur les données");
            infos.getStyleClass().addAll("low-size","white","bouton");
            infos.setOnAction(e -> infos());
            accueil = new VBox(txt,btn,infos);
            accueil.getStyleClass().add("accueil");
            frame.setCenter(accueil);
        }
    }

    @FXML private void vueMusiques() {
        if (clear) Filtre.getInstance().clear();
        setSelected(musiques);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Frame.class.getResource("musiques-view.fxml"));
        loader.setControllerFactory(iC -> new VueMusiques(this));
        try { frame.setCenter(loader.load()); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML private void vueArtistes() {
        if (clear) Filtre.getInstance().clear();
        setSelected(artistes);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Frame.class.getResource("artistes-view.fxml"));
        loader.setControllerFactory(iC -> new VueArtistes(this));
        try { frame.setCenter(loader.load()); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML private void vueAlbums() {
        if (clear) Filtre.getInstance().clear();
        setSelected(albums);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Frame.class.getResource("albums-view.fxml"));
        loader.setControllerFactory(iC -> new VueAlbums(this));
        try { frame.setCenter(loader.load()); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML private void vueHistorique() {
        if (clear) Filtre.getInstance().clear();
        setSelected(historique);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Frame.class.getResource("ecoutes-view.fxml"));
        loader.setControllerFactory(iC -> new VueEcoutes(this));
        try { frame.setCenter(loader.load()); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML private void vueGraphique() {
        System.out.println("vueGraphique");
    }

    @FXML private void vueArbres() {
        if (clear) Filtre.getInstance().clear();
        setSelected(arbres);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Frame.class.getResource("arbres-view.fxml"));
        loader.setControllerFactory(iC -> new VueArbres(this));
        try { frame.setCenter(loader.load()); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML private void vueParametres() {
        setSelected(parametres);
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
        else System.out.println("Aucun fichier sélectionné");
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
        if (dr.getFichiers().size() != 0) {
            stats.clear();
            stats.add(new StatistiquePane(dr.getNbEcoutes()+"","écoutes"));
            stats.add(new StatistiquePane(dr.getNbArtistes()+"","artistes"));
            stats.add(new StatistiquePane(dr.getNbMusiques()+"","musiques"));
            stats.add(new StatistiquePane(dr.getNbEcoutesCompletes()+"","écoutes complètes"));
            stats.add(new StatistiquePane(dr.getNbSkips()+"","skips"));
            stats.add(new StatistiquePane(dr.getPourcentEcoutesCompletes()+"","% d'écoutes complètes"));
            stats.add(new StatistiquePane(dr.getPourcentSkips(),"% de skips"));
            stats.add(new StatistiquePane(dr.getTopMusiqueEcoutes().getImage(),"#1" , dr.getTopMusiqueEcoutes().getNom(), "des écoutes"));
            stats.add(new StatistiquePane(dr.getTopMusiqueEcoutesCompletes().getImage(),"#1" , dr.getTopMusiqueEcoutesCompletes().getNom(), "des écoutes complètes"));
            stats.add(new StatistiquePane(dr.getTopMusiqueSkips().getImage(),"#1" , dr.getTopMusiqueSkips().getNom(), "des skips"));
            stats.add(new StatistiquePane(dr.getTopArtisteEcoutes().getImage(),"#1" , dr.getTopArtisteEcoutes().getNom(), "des écoutes"));
            stats.add(new StatistiquePane(dr.getTopArtisteEcoutesCompletes().getImage(),"#1" , dr.getTopArtisteEcoutesCompletes().getNom(), "des écoutes complètes"));
            stats.add(new StatistiquePane(dr.getTopArtisteSkips().getImage(),"#1" , dr.getTopArtisteSkips().getNom(), "des skips"));
            stats.add(new StatistiquePane(dr.getTopAlbumEcoutes().getImage(),"#1" , dr.getTopAlbumEcoutes().getNom(), "des écoutes"));
            stats.add(new StatistiquePane(dr.getTopAlbumEcoutesCompletes().getImage(),"#1" , dr.getTopAlbumEcoutesCompletes().getNom(), "des écoutes complètes"));
            stats.add(new StatistiquePane(dr.getTopAlbumSkips().getImage(),"#1" , dr.getTopAlbumSkips().getNom(), "des skips"));
        }
    }
}
