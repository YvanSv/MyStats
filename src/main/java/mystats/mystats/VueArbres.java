package mystats.mystats;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import mystats.mystats.metier.DataReader;
import mystats.mystats.metier.donnees.Album;
import mystats.mystats.metier.donnees.Artiste;
import mystats.mystats.metier.donnees.Donnee;
import mystats.mystats.metier.donnees.Musique;
import mystats.mystats.utils.*;

import java.util.Collections;
import java.util.List;

public class VueArbres extends VuePrincipale {
    @FXML private VBox accueil;

    public VueArbres(Frame f) {
        super(f);
    }

    @FXML private void initialize() {
        accueil.setPrefSize(Tailles.WIDTH_SCREEN,Tailles.HEIGHT_SCROLL);
    }

    @Override public void actualiser() {
        accueil.getChildren().clear();
        Filtre f = Filtre.getInstance();
        f.clear();
        f.setMethodeTri(5);
    }

    @FXML private void autoloadMusiques() {
        actualiser();
        Filtre.getInstance().setNbEcoutesCompletesMin((int)(DataReader.getInstance().getNbEcoutesCompletes() * 0.00075));
        creerArbre(DataReader.getInstance().filtrerMusiques());
    }

    @FXML private void autoloadAlbums() {
        actualiser();
        Filtre.getInstance().setNbEcoutesCompletesMin((int)(DataReader.getInstance().getNbEcoutesCompletes() * 0.0025));
        creerArbre(DataReader.getInstance().filtrerAlbums());
    }
    @FXML private void autoloadArtistes() {
        actualiser();
        Filtre.getInstance().setNbEcoutesCompletesMin((int)(DataReader.getInstance().getNbEcoutesCompletes() * 0.006));
        creerArbre(DataReader.getInstance().filtrerArtistes());
    }

    private void creerArbre(List lst) {
        //Nombre d'éléments par duel
        int nbDonneesParDuel = 2;
        if (!verif(lst,nbDonneesParDuel)) return;

        // Calculer le nombre d'étages qu'il faudra dans l'arbre
        int x = 0;
        while (Math.pow(nbDonneesParDuel,x+1) < lst.size()) x++;
        if (x < 0) x = 0;
        else if (x > 7) x = x - 2;
        else if (x > 5) x--;

        // Récupérer le nombre de musiques qu'on peut utiliser au max
        lst = lst.subList(0,(int)Math.pow(nbDonneesParDuel,x));
        // On mélange la liste pour générer des duels aléatoires
        Collections.shuffle(lst);
        // On crée un arbre
        Arbre.getInstance().init(this,nbDonneesParDuel,lst).next(null);
    }

    public void setDuel(Duel duel,String txt) {
        accueil.getChildren().clear();

        Label titre = new Label(txt);
        Label nomDuel = new Label(duel.getNom());
        titre.getStyleClass().addAll("low-size","white","center");
        nomDuel.getStyleClass().addAll("low-size","white","center");

        HBox choix = new HBox();
        choix.setAlignment(Pos.CENTER);
        choix.setSpacing(50);

        for (int i = 0; i < duel.size(); i++) {
            Donnee d;
            try { d = (Musique) duel.getDonnee(i); }
            catch (Exception ignored) {
                try { d = (Album) duel.getDonnee(i); }
                catch (Exception ignored2) { d = (Artiste) duel.getDonnee(i); }
            }
            Label nom = new Label(d.toString());
            nom.getStyleClass().addAll("low-size","white","center");
            ImageView img = new ImageView(d.getImage().getImage());
            img.setPreserveRatio(true);
            //// REDIMENSIONNER L'IMAGE POUR QU'ELLE FASSE 1/3 SOIT DE LA HAUTEUR SOIT DE LA LARGEUR ////
            if (img.getFitWidth() > img.getFitHeight())
                img.setFitWidth(Tailles.WIDTH_SCREEN / 3);
            else img .setFitHeight(Tailles.HEIGHT_SCROLL / 2);
            ////////

            VBox x = new VBox(img,nom);
            x.setAlignment(Pos.CENTER);
            x.setPrefSize(Tailles.WIDTH_SCREEN / 3, Tailles.HEIGHT_SCROLL / 2);
            Donnee finalD = d;
            if (img.getImage() == null) {
                x.setOnMouseEntered(e -> x.setBackground(Couleur.background(Color.GRAY)));
                x.setOnMouseExited (e -> x.setBackground(null));
                x.getStyleClass().addAll("clickable");
                x.setOnMouseClicked(e -> {
                    duel.setChoix(finalD);
                    Arbre.getInstance().next(duel);
                });
            } else {
                img.setOnMouseEntered(e -> x.setBackground(Couleur.background(Couleur.getAverageColor(img.getImage()))));
                img.setOnMouseExited (e -> x.setBackground(null));
                img.getStyleClass().addAll("clickable");
                img.setOnMouseClicked(e -> {
                    duel.setChoix(finalD);
                    Arbre.getInstance().next(duel);
                });
            }
            choix.getChildren().add(x);
        }

        Button autre = new Button("\uD83D\uDD00");
        autre.setOnAction(e -> Arbre.getInstance().other());
        autre.getStyleClass().addAll("low-size","white","center","bouton");

        Button previous = new Button("⬅ Duel précédent");
        previous.setOnAction(e -> Arbre.getInstance().previous());
        previous.getStyleClass().addAll("low-size","white","center","bouton");

        if (Arbre.getInstance().getPrevious() == null) previous.setVisible(false);

        accueil.getChildren().addAll(titre,nomDuel,choix,autre,previous);
        Arbre.Node node = duel.getNode();
        drawTree(accueil,node, 600,600,1320,1000);
    }

    private void drawTree(Pane pane, Arbre.Node node, int x0,int y0,int x1,int y1) {
        if (node.getParent() == null) {
            for (Arbre.Node n : node.getChildren()) {
                drawNode(n);
                drawLine();
            }
            drawNode(node);
        } else {
            for (Arbre.Node n : node.getParent().getChildren()) {
                drawNode(n);
                drawLine();
            }
            drawNode(node.getParent());
        }
    }

    private void drawNode(Arbre.Node n) {

    }

    private void drawLine() {

    }

    private boolean verif(List lst, int min) {
        if (lst.size() < min) {
            frame.actualiser();
            return false;
        }
        return true;
    }

    public void champion(Donnee d) {
        accueil.getChildren().clear();
        Label titre = new Label("Champion");
        Label nom = new Label(d.toString());
        titre.getStyleClass().addAll("low-size","white","center");
        nom.getStyleClass().addAll("low-size","white","center");
        accueil.getChildren().addAll(titre,d.getImage(),nom);
    }

    @FXML private void importer() {}
}
