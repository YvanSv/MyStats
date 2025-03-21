package mystats.mystats.utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import mystats.mystats.Frame;
import mystats.mystats.VuePrincipale;
import mystats.mystats.metier.donnees.Album;
import mystats.mystats.metier.donnees.Artiste;
import mystats.mystats.metier.donnees.Ecoute;
import mystats.mystats.metier.donnees.Musique;

import java.util.ArrayList;
import java.util.Arrays;

public class ImgPane extends HBox {
    private final static double sizeImg = 0.05 * Tailles.WIDTH_LISTE;
    private static ImgPane titresMusiques;
    private static ImgPane titresArtistes;
    private static ImgPane titresEcoutes;
    private static ImgPane titresAlbums;
    private static VuePrincipale vue;

    public static ImgPane getTitresMusiques() {
        if (titresMusiques == null) titresMusiques = new ImgPane(0);
        return titresMusiques;
    }

    public static ImgPane getTitresArtistes() {
        if (titresArtistes == null) titresArtistes = new ImgPane(1);
        return titresArtistes;
    }

    public static ImgPane getTitresEcoutes() {
        if (titresEcoutes == null) titresEcoutes = new ImgPane(2);
        return titresEcoutes;
    }

    public static ImgPane getTitresAlbums() {
        if (titresAlbums == null) titresAlbums = new ImgPane(3);
        return titresAlbums;
    }

    public static void resetTitres() {
        titresMusiques = new ImgPane(0);
        titresArtistes = new ImgPane(1);
        titresEcoutes  = new ImgPane(2);
    }

    public static void setFrame(VuePrincipale v) {
        vue = v;
    }

    public ImgPane() {
        setPadding(new Insets(10,10,10,10));
        setSpacing(10);
        setPrefSize(Tailles.WIDTH_LISTE,sizeImg);
        setBackground(Couleur.backgroundFull(Color.web("#1f1f1f")));
        setAlignment(Pos.CENTER);

        Button loadMore = new Button("Charger +");
        loadMore.getStyleClass().addAll("very-low-size","white","center","bouton");
        loadMore.setOnAction(e -> vue.loadMore());

        getChildren().addAll(loadMore);
    }

    private ImgPane(int tri) {
        if (tri == 3) {
            setPadding(new Insets(10,10,10,10));
            setSpacing(10);
            setPrefSize(Tailles.WIDTH_LISTE,sizeImg);
            setBackground(Couleur.backgroundFull(Color.web("#1f1f1f")));
            setAlignment(Pos.CENTER_LEFT);

            Label index = new Label();
            index.setPrefWidth(Tailles.WIDTH_LISTE*0.06);
            Label img = new Label();
            img.setPrefWidth(sizeImg);

            ArrayList<Label> lstLabel = new ArrayList<>();
            Label album = new Label("Album");
            Label artiste = new Label("Artiste ");
            Label tempsEcoute = new Label(" \uD83D\uDD50");
            album      .setOnMouseClicked(e -> tri(lstLabel,album      ,4));
            artiste    .setOnMouseClicked(e -> tri(lstLabel,artiste    ,3));
            tempsEcoute.setOnMouseClicked(e -> tri(lstLabel,tempsEcoute,8));
            album      .getStyleClass().addAll("clickable","low-size","white");
            artiste    .getStyleClass().addAll("clickable","low-size","gray");
            tempsEcoute.getStyleClass().addAll("clickable","low-size","gray");
            HBox nom = new HBox(artiste,tempsEcoute);
            VBox noms = new VBox(album,nom);
            noms.setPrefWidth(0.29*Tailles.WIDTH_LISTE);
            noms.setAlignment(Pos.CENTER_LEFT);

            Label ecoutes = new Label("Ecoutes complètes");
            Label slash = new Label();
            Label skips = new Label("Skips");
            ecoutes.setPrefWidth(0.15*Tailles.WIDTH_LISTE);
            slash  .setPrefWidth(0.01*Tailles.WIDTH_LISTE);
            skips  .setPrefWidth(0.06*Tailles.WIDTH_LISTE);
            ecoutes.setAlignment(Pos.CENTER_RIGHT);
            skips  .setAlignment(Pos.CENTER_LEFT);
            ecoutes.setOnMouseClicked(e -> tri(lstLabel,ecoutes,6));
            skips  .setOnMouseClicked(e -> tri(lstLabel,skips  ,1));
            ecoutes.getStyleClass().addAll("clickable","low-size","white");
            skips  .getStyleClass().addAll("clickable","low-size","white");

            Label total = new Label("Total");
            Label ratio = new Label("Ratio");
            total.setPrefWidth(0.1*Tailles.WIDTH_LISTE);
            total.getStyleClass().addAll("clickable","low-size","white");
            total.setAlignment(Pos.CENTER);
            total.setOnMouseClicked(e -> tri(lstLabel,total,5));
            ratio.getStyleClass().addAll("clickable","low-size","white");
            ratio.setOnMouseClicked(e -> tri(lstLabel,ratio,2));

            HBox stats = new HBox(ecoutes,slash,skips,total,ratio);
            stats.setSpacing(0.02*Tailles.WIDTH_LISTE);
            stats.setAlignment(Pos.CENTER_RIGHT);

            lstLabel.addAll(Arrays.asList(album,artiste,tempsEcoute,ecoutes,skips,total,ratio));
            getChildren().addAll(index,img,noms,stats);
        } else if (tri == 2) {
            setPadding(new Insets(10,10,10,10));
            setSpacing(10);
            setPrefSize(Tailles.WIDTH_LISTE,sizeImg);
            setBackground(Couleur.backgroundFull(Color.web("#1f1f1f")));
            setAlignment(Pos.CENTER_LEFT);

            Label index = new Label();
            index.setPrefWidth(Tailles.WIDTH_LISTE*0.06);

            Label img = new Label();
            img.setPrefWidth(sizeImg);

            ArrayList<Label> lstLabel = new ArrayList<>();
            Label musique = new Label("Musique");
            musique.getStyleClass().addAll("clickable","low-size","white");
            musique.setOnMouseClicked(e -> tri(lstLabel,musique,4));
            Label artiste = new Label("Artiste");
            artiste.getStyleClass().addAll("clickable","low-size","gray");
            artiste.setOnMouseClicked(e -> tri(lstLabel,artiste,3));
            VBox noms = new VBox(musique,artiste);
            noms.setPrefWidth(0.38*Tailles.WIDTH_LISTE);
            noms.setAlignment(Pos.CENTER_LEFT);

            Label nature = new Label("Nature");
            nature.setPrefWidth(0.1*Tailles.WIDTH_LISTE);
            nature.getStyleClass().addAll("clickable","low-size","white");
            nature.setAlignment(Pos.CENTER_RIGHT);
            nature.setOnMouseClicked(e -> tri(lstLabel,nature,6));

            Label date = new Label("Date");
            date.setPrefWidth(0.23*Tailles.WIDTH_LISTE);
            date.getStyleClass().addAll("clickable","low-size","white");
            date.setAlignment(Pos.CENTER_LEFT);
            date.setOnMouseClicked(e -> tri(lstLabel,date,1));

            HBox stats = new HBox(nature,date);
            stats.setPrefWidth(0.4*Tailles.WIDTH_LISTE);
            stats.setSpacing(0.02*Tailles.WIDTH_LISTE);
            stats.setAlignment(Pos.CENTER_RIGHT);

            lstLabel.addAll(Arrays.asList(musique,artiste,nature,date));
            getChildren().addAll(index,img,noms,stats);
        } else if (tri == 1) {
            setPadding(new Insets(10,10,10,10));
            setSpacing(10);
            setPrefSize(Tailles.WIDTH_LISTE,sizeImg);
            setBackground(Couleur.backgroundFull(Color.web("#1f1f1f")));
            setAlignment(Pos.CENTER_LEFT);

            Label index = new Label();
            index.setPrefWidth(Tailles.WIDTH_LISTE*0.06);
            Label img = new Label();
            img.setPrefWidth(sizeImg);

            ArrayList<Label> lstLabel = new ArrayList<>();
            Label artiste = new Label("Artiste");
            Label tempsEcoute = new Label("\uD83D\uDD50");
            artiste    .getStyleClass().addAll("clickable","low-size","white");
            tempsEcoute.getStyleClass().addAll("clickable","low-size","gray");
            artiste    .setOnMouseClicked(e -> tri(lstLabel,artiste,3));
            tempsEcoute.setOnMouseClicked(e -> tri(lstLabel,tempsEcoute,8));
            VBox nomArtTemps = new VBox(artiste,tempsEcoute);
            StackPane nom = new StackPane(nomArtTemps);
            nom.setPrefWidth(0.29*Tailles.WIDTH_LISTE);
            nom.setAlignment(Pos.CENTER_LEFT);

            Label ecoutes = new Label("Ecoutes complètes");
            ecoutes.setPrefWidth(0.15*Tailles.WIDTH_LISTE);
            ecoutes.getStyleClass().addAll("clickable","low-size","white");
            ecoutes.setAlignment(Pos.CENTER_RIGHT);
            ecoutes.setOnMouseClicked(e -> tri(lstLabel,ecoutes,6));
            Label slash = new Label();
            slash.setPrefWidth(0.01*Tailles.WIDTH_LISTE);
            Label skips = new Label("Skips");
            skips.setPrefWidth(0.06*Tailles.WIDTH_LISTE);
            skips.getStyleClass().addAll("clickable","low-size","white");
            skips.setAlignment(Pos.CENTER_LEFT);
            skips.setOnMouseClicked(e -> tri(lstLabel,skips,1));

            Label total = new Label("Total");
            total.setPrefWidth(0.1*Tailles.WIDTH_LISTE);
            total.getStyleClass().addAll("clickable","low-size","white");
            total.setAlignment(Pos.CENTER);
            total.setOnMouseClicked(e -> tri(lstLabel,total,5));
            Label ratio = new Label("Ratio");
            ratio.getStyleClass().addAll("clickable","low-size","white");
            ratio.setOnMouseClicked(e -> tri(lstLabel,ratio,2));

            HBox stats = new HBox(ecoutes,slash,skips,total,ratio);
            stats.setSpacing(0.02*Tailles.WIDTH_LISTE);
            stats.setAlignment(Pos.CENTER_RIGHT);

            lstLabel.addAll(Arrays.asList(artiste,tempsEcoute,ecoutes,skips,total,ratio));
            getChildren().addAll(index,img,nom,stats);
        } else if (tri == 0) {
            setPadding(new Insets(10,10,10,10));
            setSpacing(10);
            setPrefSize(Tailles.WIDTH_LISTE,sizeImg);
            setBackground(Couleur.backgroundFull(Color.web("#1f1f1f")));
            setAlignment(Pos.CENTER_LEFT);

            Label index = new Label();
            index.setPrefWidth(Tailles.WIDTH_LISTE*0.06);
            Label img = new Label();
            img.setPrefWidth(sizeImg);

            ArrayList<Label> lstLabel = new ArrayList<>();
            Label musique = new Label("Musique");
            Label artiste = new Label("Artiste ");
            Label tempsEcoute = new Label(" \uD83D\uDD50");
            musique    .setOnMouseClicked(e -> tri(lstLabel,musique    ,4));
            artiste    .setOnMouseClicked(e -> tri(lstLabel,artiste    ,3));
            tempsEcoute.setOnMouseClicked(e -> tri(lstLabel,tempsEcoute,8));
            musique    .getStyleClass().addAll("clickable","low-size","white");
            artiste    .getStyleClass().addAll("clickable","low-size","gray");
            tempsEcoute.getStyleClass().addAll("clickable","low-size","gray");
            HBox nom = new HBox(artiste,tempsEcoute);
            VBox noms = new VBox(musique,nom);
            noms.setPrefWidth(0.29*Tailles.WIDTH_LISTE);
            noms.setAlignment(Pos.CENTER_LEFT);

            Label ecoutes = new Label("Ecoutes complètes");
            Label slash = new Label();
            Label skips = new Label("Skips");
            ecoutes.setPrefWidth(0.15*Tailles.WIDTH_LISTE);
            slash  .setPrefWidth(0.01*Tailles.WIDTH_LISTE);
            skips  .setPrefWidth(0.06*Tailles.WIDTH_LISTE);
            ecoutes.setAlignment(Pos.CENTER_RIGHT);
            skips  .setAlignment(Pos.CENTER_LEFT);
            ecoutes.setOnMouseClicked(e -> tri(lstLabel,ecoutes,6));
            skips  .setOnMouseClicked(e -> tri(lstLabel,skips  ,1));
            ecoutes.getStyleClass().addAll("clickable","low-size","white");
            skips  .getStyleClass().addAll("clickable","low-size","white");

            Label total = new Label("Total");
            Label ratio = new Label("Ratio");
            total.setPrefWidth(0.1*Tailles.WIDTH_LISTE);
            total.getStyleClass().addAll("clickable","low-size","white");
            total.setAlignment(Pos.CENTER);
            total.setOnMouseClicked(e -> tri(lstLabel,total,5));
            ratio.getStyleClass().addAll("clickable","low-size","white");
            ratio.setOnMouseClicked(e -> tri(lstLabel,ratio,2));

            HBox stats = new HBox(ecoutes,slash,skips,total,ratio);
            stats.setSpacing(0.02*Tailles.WIDTH_LISTE);
            stats.setAlignment(Pos.CENTER_RIGHT);

            lstLabel.addAll(Arrays.asList(musique,artiste,tempsEcoute,ecoutes,skips,total,ratio));
            getChildren().addAll(index,img,noms,stats);
        }
    }

    public ImgPane(int indice, Artiste art, boolean graphics, Frame frame) {
        if (graphics) {
            setPadding(new Insets(10,10,10,10));
            setSpacing(10);
            setPrefSize(Tailles.WIDTH_LISTE,sizeImg);
            Color bgColor = Couleur.getAverageColor(art.getImage().getImage());
            String textColor = getMainTextColor(bgColor);
            setBackground(Couleur.backgroundFull(bgColor));
            setAlignment(Pos.CENTER_LEFT);

            Label index = new Label("#"+indice);
            index.getStyleClass().addAll("big-size",textColor);
            StackPane indexPane = new StackPane(index);
            indexPane.setPrefWidth(Tailles.WIDTH_LISTE*0.06);
            indexPane.setAlignment(Pos.CENTER);

            ImageView copy = new ImageView(art.getImage().getImage());
            copy.setPreserveRatio(true);
            if (copy.getImage() != null) {
                if (copy.getImage().getHeight() > copy.getImage().getWidth())
                    copy.setFitHeight(sizeImg);
                else copy.setFitWidth(sizeImg);
            }
            StackPane imgPane = new StackPane(copy);
            imgPane.setPrefWidth(sizeImg);
            imgPane.setAlignment(Pos.CENTER);
            this.setOnClick(imgPane,Filtre.TYPE_MUSIQUE,art.getNom(),null,null,-1,frame);

            Label artName = new Label(art.getNom());
            artName.getStyleClass().addAll("big-size",textColor);
            this.setOnClick(artName,Filtre.TYPE_MUSIQUE,art.getNom(),null,null,-1,frame);
            Label tempsEcoute = new Label("\uD83D\uDD50 " + art.getTempsEcoute() + " minutes");
            tempsEcoute.getStyleClass().addAll("low-size",getSecondaryTextColor(bgColor));
            VBox nomArtTemps = new VBox(artName,tempsEcoute);
            StackPane nom = new StackPane(nomArtTemps);
            nom.setPrefWidth(0.38*Tailles.WIDTH_LISTE);
            nom.setAlignment(Pos.CENTER_LEFT);


            Label ecoutes = new Label(art.getNbEcoutesCompletes() + "");
            ecoutes.setPrefWidth(0.06*Tailles.WIDTH_LISTE);
            ecoutes.getStyleClass().addAll("medium-size",textColor);
            ecoutes.setAlignment(Pos.CENTER_RIGHT);
            this.setOnClick(ecoutes,Filtre.TYPE_ECOUTE,art.getNom(),null,null,1,frame);
            Label slash = new Label("/");
            slash.setPrefWidth(0.01*Tailles.WIDTH_LISTE);
            slash.getStyleClass().addAll("medium-size",textColor);
            slash.setAlignment(Pos.CENTER);
            Label skips = new Label(art.getNbSkips()+"");
            skips.setPrefWidth(0.06*Tailles.WIDTH_LISTE);
            skips.getStyleClass().addAll("medium-size",textColor);
            skips.setAlignment(Pos.CENTER_LEFT);
            this.setOnClick(skips,Filtre.TYPE_ECOUTE,art.getNom(),null,null,0,frame);

            Label total = new Label(art.getNbEcoutes()+"");
            total.setPrefWidth(0.1*Tailles.WIDTH_LISTE);
            total.getStyleClass().addAll("medium-size",textColor);
            total.setAlignment(Pos.CENTER);
            this.setOnClick(total,Filtre.TYPE_ECOUTE,art.getNom(),null,null,-1,frame);
            Label ratio = new Label(String.format("(%2.2f",art.getRatio())+"%)");
            ratio.getStyleClass().addAll("medium-size",textColor);

            HBox stats = new HBox(ecoutes,slash,skips,total,ratio);
            stats.setSpacing(0.02*Tailles.WIDTH_LISTE);
            stats.setAlignment(Pos.CENTER_RIGHT);

            getChildren().addAll(indexPane,imgPane,nom,stats);
        }
    }

    public ImgPane(int indice, Musique mus, boolean graphics, Frame frame) {
        if (graphics) {
            setPadding(new Insets(10,10,10,10));
            setSpacing(10);
            setPrefSize(Tailles.WIDTH_LISTE,sizeImg);
            Color bgColor = Couleur.getAverageColor(mus.getImage().getImage());
            String textColor = getMainTextColor(bgColor);
            setBackground(Couleur.backgroundFull(bgColor));
            setAlignment(Pos.CENTER_LEFT);

            Label index = new Label("#"+indice);
            index.getStyleClass().addAll("big-size",textColor);
            StackPane indexPane = new StackPane(index);
            indexPane.setPrefWidth(Tailles.WIDTH_LISTE*0.06);
            indexPane.setAlignment(Pos.CENTER);

            ImageView copy = new ImageView(mus.getImage().getImage());
            copy.setPreserveRatio(true);
            if (copy.getImage() != null) {
                if (copy.getImage().getHeight() > copy.getImage().getWidth())
                    copy.setFitHeight(sizeImg);
                else copy.setFitWidth(sizeImg);
            }
            StackPane imgPane = new StackPane(copy);
            imgPane.setPrefWidth(sizeImg);
            imgPane.setAlignment(Pos.CENTER);
            this.setOnClick(imgPane,Filtre.TYPE_MUSIQUE,mus.getArtiste().getNom(),null,mus.getAlbum().getNom(),-1,frame);

            Label musName = new Label(mus.getNom());
            Label artName = new Label(mus.getArtiste().getNom());
            Label tempsEcoute = new Label("  \uD83D\uDD50 "+mus.getTempsEcoute()+" minutes");
            musName.getStyleClass().addAll("medium-size",textColor);
            artName.getStyleClass().addAll("low-size",getSecondaryTextColor(bgColor));
            tempsEcoute.getStyleClass().addAll("very-low-size",getSecondaryTextColor(bgColor));
            this.setOnClick(musName,Filtre.TYPE_ECOUTE,mus.getArtiste().getNom(),mus.getNom(),null,-1,frame);
            this.setOnClick(artName,Filtre.TYPE_MUSIQUE,mus.getArtiste().getNom(),null,null,-1,frame);
            HBox nom = new HBox(artName,tempsEcoute);
            VBox noms = new VBox(musName,nom);
            noms.setPrefWidth(0.38*Tailles.WIDTH_LISTE);
            noms.setAlignment(Pos.CENTER_LEFT);

            Label ecoutes = new Label(mus.getNbEcoutesCompletes() + "");
            ecoutes.setPrefWidth(0.06*Tailles.WIDTH_LISTE);
            ecoutes.getStyleClass().addAll("medium-size",textColor);
            ecoutes.setAlignment(Pos.CENTER_RIGHT);
            this.setOnClick(ecoutes,Filtre.TYPE_ECOUTE,mus.getArtiste().getNom(),mus.getNom(),null,1,frame);
            Label slash = new Label("/");
            slash.setPrefWidth(0.01*Tailles.WIDTH_LISTE);
            slash.getStyleClass().addAll("medium-size",textColor);
            slash.setAlignment(Pos.CENTER);
            Label skips = new Label(mus.getNbSkips()+"");
            skips.setPrefWidth(0.06*Tailles.WIDTH_LISTE);
            skips.getStyleClass().addAll("medium-size",textColor);
            skips.setAlignment(Pos.CENTER_LEFT);
            this.setOnClick(skips,Filtre.TYPE_ECOUTE,mus.getArtiste().getNom(),mus.getNom(),null,0,frame);

            Label total = new Label(mus.getNbEcoutes()+"");
            total.setPrefWidth(0.1*Tailles.WIDTH_LISTE);
            total.getStyleClass().addAll("medium-size",textColor);
            total.setAlignment(Pos.CENTER);
            this.setOnClick(total,Filtre.TYPE_ECOUTE,mus.getArtiste().getNom(),mus.getNom(),null,-1,frame);

            Label ratio = new Label(String.format("(%2.2f",mus.getRatio())+"%)");
            ratio.getStyleClass().addAll("medium-size",textColor);

            HBox stats = new HBox(ecoutes,slash,skips,total,ratio);
            stats.setSpacing(0.02*Tailles.WIDTH_LISTE);
            stats.setAlignment(Pos.CENTER_RIGHT);

            getChildren().addAll(indexPane,imgPane,noms,stats);
        }
    }

    public ImgPane(int indice, Album alb, boolean graphics,Frame frame) {
        if (graphics) {
            setPadding(new Insets(10,10,10,10));
            setSpacing(10);
            setPrefSize(Tailles.WIDTH_LISTE,sizeImg);
            Color bgColor = Couleur.getAverageColor(alb.getImage().getImage());
            String textColor = getMainTextColor(bgColor);
            setBackground(Couleur.backgroundFull(bgColor));
            setAlignment(Pos.CENTER_LEFT);

            Label index = new Label("#"+indice);
            index.getStyleClass().addAll("big-size",textColor);
            StackPane indexPane = new StackPane(index);
            indexPane.setPrefWidth(Tailles.WIDTH_LISTE*0.06);
            indexPane.setAlignment(Pos.CENTER);

            ImageView copy = new ImageView(alb.getImage().getImage());
            copy.setPreserveRatio(true);
            if (copy.getImage() != null) {
                if (copy.getImage().getHeight() > copy.getImage().getWidth())
                    copy.setFitHeight(sizeImg);
                else copy.setFitWidth(sizeImg);
            }
            StackPane imgPane = new StackPane(copy);
            imgPane.setPrefWidth(sizeImg);
            imgPane.setAlignment(Pos.CENTER);
            this.setOnClick(imgPane,Filtre.TYPE_MUSIQUE,alb.getArtiste().getNom(),null,alb.getNom(),-1,frame);

            Label albName = new Label(alb.getNom());
            albName.getStyleClass().addAll("medium-size",textColor);
            this.setOnClick(albName,Filtre.TYPE_MUSIQUE,alb.getArtiste().getNom(),null,alb.getNom(),-1,frame);
            Label artName = new Label(alb.getArtiste().getNom());
            artName.getStyleClass().addAll("low-size",getSecondaryTextColor(bgColor));
            this.setOnClick(artName,Filtre.TYPE_MUSIQUE,alb.getArtiste().getNom(),null,null,-1,frame);
            Label tempsEcoute = new Label(" \uD83D\uDD50"+alb.getTempsEcoute()+" minutes");
            tempsEcoute.getStyleClass().addAll("very-low-size",getSecondaryTextColor(bgColor));
            HBox nom = new HBox(artName,tempsEcoute);
            VBox noms = new VBox(albName,nom);
            noms.setPrefWidth(0.38*Tailles.WIDTH_LISTE);
            noms.setAlignment(Pos.CENTER_LEFT);

            Label ecoutes = new Label(alb.getNbEcoutesCompletes() + "");
            ecoutes.setPrefWidth(0.06*Tailles.WIDTH_LISTE);
            ecoutes.getStyleClass().addAll("medium-size",textColor);
            ecoutes.setAlignment(Pos.CENTER_RIGHT);
            this.setOnClick(ecoutes,Filtre.TYPE_ECOUTE,alb.getArtiste().getNom(),null,alb.getNom(),1,frame);
            Label slash = new Label("/");
            slash.setPrefWidth(0.01*Tailles.WIDTH_LISTE);
            slash.getStyleClass().addAll("medium-size",textColor);
            slash.setAlignment(Pos.CENTER);
            Label skips = new Label(alb.getNbSkips()+"");
            skips.setPrefWidth(0.06*Tailles.WIDTH_LISTE);
            skips.getStyleClass().addAll("medium-size",textColor);
            skips.setAlignment(Pos.CENTER_LEFT);
            this.setOnClick(skips,Filtre.TYPE_ECOUTE,alb.getArtiste().getNom(),null,alb.getNom(),0,frame);

            Label total = new Label(alb.getNbEcoutes()+"");
            total.setPrefWidth(0.1*Tailles.WIDTH_LISTE);
            total.getStyleClass().addAll("medium-size",textColor);
            total.setAlignment(Pos.CENTER);
            this.setOnClick(total,Filtre.TYPE_ECOUTE,alb.getArtiste().getNom(),null,alb.getNom(),-1,frame);
            Label ratio = new Label(String.format("(%2.2f",alb.getRatio())+"%)");
            ratio.getStyleClass().addAll("medium-size",textColor);

            HBox stats = new HBox(ecoutes,slash,skips,total,ratio);
            stats.setSpacing(0.02*Tailles.WIDTH_LISTE);
            stats.setAlignment(Pos.CENTER_RIGHT);

            getChildren().addAll(indexPane,imgPane,noms,stats);
        }
    }

    public ImgPane(int indice, Ecoute eco, boolean graphics, Frame frame) {
        if (graphics) {
            setPadding(new Insets(10,10,10,10));
            setSpacing(10);
            setPrefSize(Tailles.WIDTH_LISTE,sizeImg);
            Color bgColor = Couleur.getAverageColor(eco.getImage().getImage());
            String textColor = getMainTextColor(bgColor);
            setBackground(Couleur.backgroundFull(bgColor));
            setAlignment(Pos.CENTER_LEFT);

            Label index = new Label("#"+indice);
            index.getStyleClass().addAll("big-size",textColor);
            StackPane indexPane = new StackPane(index);
            indexPane.setPrefWidth(Tailles.WIDTH_LISTE*0.06);
            indexPane.setAlignment(Pos.CENTER);

            ImageView copy = new ImageView(eco.getImage().getImage());
            copy.setPreserveRatio(true);
            if (copy.getImage() != null) {
                if (copy.getImage().getHeight() > copy.getImage().getWidth())
                    copy.setFitHeight(sizeImg);
                else copy.setFitWidth(sizeImg);
            }
            StackPane imgPane = new StackPane(copy);
            imgPane.setPrefWidth(sizeImg);
            imgPane.setAlignment(Pos.CENTER);
            this.setOnClick(imgPane,Filtre.TYPE_MUSIQUE,eco.getArtiste().getNom(),null,eco.getAlbum().getNom(),-1,frame);

            Label musName = new Label(eco.getNom());
            musName.getStyleClass().addAll("medium-size",textColor);
            this.setOnClick(musName,Filtre.TYPE_ECOUTE,eco.getArtiste().getNom(),eco.getNom(),null,-1,frame);
            Label artName = new Label(eco.getArtiste().getNom());
            artName.getStyleClass().addAll("low-size",getSecondaryTextColor(bgColor));
            this.setOnClick(artName,Filtre.TYPE_ECOUTE,eco.getArtiste().getNom(),null,null,-1,frame);
            VBox noms = new VBox(musName,artName);
            noms.setPrefWidth(0.38*Tailles.WIDTH_LISTE);
            noms.setAlignment(Pos.CENTER_LEFT);

            Label nature = new Label(eco.getNature() ? "écoutée" : "skippée");
            nature.setPrefWidth(0.1*Tailles.WIDTH_LISTE);
            nature.getStyleClass().addAll("medium-size",textColor);
            nature.setAlignment(Pos.CENTER_RIGHT);
            this.setOnClick(nature,Filtre.TYPE_ECOUTE,eco.getArtiste().getNom(),eco.getNom(),null,eco.getNature() ? 1 : 0,frame);

            Label date = new Label(eco.getDate().toString());
            date.setPrefWidth(0.23*Tailles.WIDTH_LISTE);
            date.getStyleClass().addAll("medium-size",textColor);
            date.setAlignment(Pos.CENTER_LEFT);

            HBox stats = new HBox(nature,date);
            stats.setPrefWidth(0.4*Tailles.WIDTH_LISTE);
            stats.setSpacing(0.02*Tailles.WIDTH_LISTE);
            stats.setAlignment(Pos.CENTER_RIGHT);

            getChildren().addAll(indexPane,imgPane,noms,stats);
        }
    }

    // ARTISTES
    /*private void setOnClick(Label l,int type, String nomArtiste, int nature) {
        Filtre f = Filtre.getInstance();
        l.setOnMouseClicked( e -> {
            f.clear();
            f.setType(type);
            f.setArtiste(nomArtiste);
            f.setNature(nature == -1 ? null : nature == 1);
            frame.actualiser();
        });
    }

    // MUSIQUES
    /*private void setOnClick(Label l, int type, String nomArtiste, String nomMusique, int nature) {
        Filtre f = Filtre.getInstance();
        l.setOnMouseClicked( e -> {
            f.clear();
            f.setType(type);
            f.setArtiste(nomArtiste);
            f.setMusique(nomMusique);
            f.setNature(nature == -1 ? null : nature == 1);
            frame.actualiser();
        });
    }*/

    private void setOnClick(Node n, int type, String nomArtiste, String nomMusique, String nomAlbum, int nature, Frame frame) {
        Filtre f = Filtre.getInstance();
        n.getStyleClass().add("clickable");
        n.setOnMouseClicked(e -> {
            f.clear();
            f.setType(type);
            if (nature != -1)
                f.setNature(nature != 0);
            if (nomArtiste != null)
                f.setArtiste(nomArtiste);
            if (nomMusique != null)
                f.setMusique(nomMusique);
            if (nomAlbum != null)
                f.setAlbum(nomAlbum);
            frame.actualiser();
        });
    }

    private void tri(ArrayList<Label> toUnselect, Label toSelect, int triValue) {
        unselected(toUnselect);
        selected(toSelect,triValue);
        vue.reset();
        vue.actualiser();
    }

    private String getMainTextColor(Color bgColor) {
        if (bgColor.getRed() > 0.9 && bgColor.getGreen() > 0.9 && bgColor.getBlue() > 0.9)
            return "black";
        else return "white";
    }

    private String getSecondaryTextColor(Color bgColor) {
        if (bgColor.getRed  () > 0.43 && bgColor.getRed  () < 0.57 &&
            bgColor.getGreen() > 0.43 && bgColor.getGreen() < 0.57 &&
            bgColor.getBlue () > 0.43 && bgColor.getBlue () < 0.57)
            return "black";
        else return "gray";
    }

    private void selected(Label label, int triValue) {
        Filtre.getInstance().setMethodeTri(triValue);
        String texte = label.getText();
        if (texte.charAt(texte.length()-1) == '▼' || texte.charAt(texte.length()-1) == '▲')
            texte = texte.substring(0,texte.length()-1);
        if (Filtre.getInstance().getMethodeTri() == triValue)
            label.setText(texte+"▼");
        else if (Filtre.getInstance().getMethodeTri() == -triValue)
            label.setText(texte+"▲");
    }

    private void unselected(ArrayList<Label> toUnselect) {
        for (Label l : toUnselect) {
            String texte = l.getText();
            if (texte.charAt(texte.length()-1) == '▼' || texte.charAt(texte.length()-1) == '▲')
                l.setText(texte.substring(0,texte.length()-1));
        }
    }
}
