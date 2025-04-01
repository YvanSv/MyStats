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
import mystats.mystats.metier.donnees.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ImgPane extends HBox {
    // private final static double sizeImg = 0.05 * Tailles.WIDTH_LISTE;
    private final static double widthIndex = Tailles.WIDTH_LISTE*0.06;
    private final static double widthImg = Tailles.WIDTH_LISTE*0.05;
    private final static Label[] lblTitresIndex = {new Label("🔗"),new Label("🔗"),new Label("🔗"),new Label("🔗")};
    private static ImgPane titresMusiques;
    private static ImgPane titresArtistes;
    private static ImgPane titresEcoutes;
    private static ImgPane titresAlbums;
    private static VuePrincipale vue;

    public static  void addLabelFilters() {
        for (Label l : lblTitresIndex)
            l.setVisible(true);
    }

    public static  void removeLabelFilters() {
        for (Label l : lblTitresIndex)
            l.setVisible(false);
    }

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
        titresAlbums   = new ImgPane(3);
    }

    public static void setFrame(VuePrincipale v) {
        vue = v;
    }

    public ImgPane() {
        setPadding(new Insets(10,10,10,10));
        setSpacing(10);
        setPrefSize(Tailles.WIDTH_LISTE,widthImg);
        setBackground(Couleur.backgroundFull(Color.web("#1f1f1f")));
        setAlignment(Pos.CENTER);

        ResourceBundle language = Langue.bundle;
        Button loadMore = new Button(language.getString("loadMore"));
        loadMore.getStyleClass().addAll("very-low-size","white","center","bouton");
        loadMore.setOnAction(e -> vue.loadMore());

        getChildren().addAll(loadMore);
    }

    private ImgPane(int tri) {
        ResourceBundle language = Langue.bundle;
        setPadding(new Insets(10,10,10,10));
        setPrefSize(Tailles.WIDTH_LISTE,widthImg);
        setBackground(Couleur.backgroundFull(Color.web("#1f1f1f")));
        setAlignment(Pos.CENTER_LEFT);
        Label lblIndex = lblTitresIndex[tri];
        lblIndex.setPrefWidth(widthIndex);
        lblIndex.getStyleClass().addAll("medium-size","white","clickable");
        lblIndex.setOnMouseClicked(e -> vue.cacherFiltres());
        VBox indexPane = new VBox(lblIndex);
        indexPane.setPrefWidth(Tailles.WIDTH_LISTE*0.06);
        indexPane.setAlignment(Pos.CENTER);
        indexPane.setPadding(new Insets(0,10,0,0));
        Label img = new Label();
        img.setPrefSize(widthImg,widthImg);
        StackPane imgPane = new StackPane(img);
        imgPane.setPrefWidth(widthImg);
        imgPane.setAlignment(Pos.CENTER);
        ArrayList<Label> lstLabel = new ArrayList<>();
        Label rating = new Label(language.getString("rating"));
        rating.setPrefWidth(0.06*Tailles.WIDTH_LISTE);
        rating.setAlignment(Pos.CENTER);
        rating.setOnMouseClicked(e -> tri(lstLabel,rating,9));
        rating.getStyleClass().addAll("clickable","low-size","white");
        VBox noms = new VBox();
        noms.setPadding(new Insets(0,0,0,10));

        if (tri == 2) {
            Label musique = new Label(language.getString("music"));
            musique.getStyleClass().addAll("clickable","low-size","white");
            musique.setOnMouseClicked(e -> tri(lstLabel,musique,4));
            Label artiste = new Label(language.getString("artist"));
            artiste.getStyleClass().addAll("clickable","low-size","gray");
            artiste.setOnMouseClicked(e -> tri(lstLabel,artiste,3));
            noms.getChildren().addAll(musique,artiste);
            noms.setPrefWidth(0.38*Tailles.WIDTH_LISTE);
            noms.setAlignment(Pos.CENTER_LEFT);

            Label nature = new Label(language.getString("kind"));
            nature.setPrefWidth(0.1*Tailles.WIDTH_LISTE);
            nature.getStyleClass().addAll("clickable","low-size","white");
            nature.setAlignment(Pos.CENTER_RIGHT);
            nature.setOnMouseClicked(e -> tri(lstLabel,nature,6));

            Label date = new Label(language.getString("date"));
            date.setPrefWidth(0.23*Tailles.WIDTH_LISTE);
            date.getStyleClass().addAll("clickable","low-size","white");
            date.setAlignment(Pos.CENTER_LEFT);
            date.setOnMouseClicked(e -> tri(lstLabel,date,1));

            rating.setPrefWidth(0.07*Tailles.WIDTH_LISTE);

            HBox stats = new HBox(nature,date,rating);
            stats.setPrefWidth(0.4*Tailles.WIDTH_LISTE);
            stats.setSpacing(0.02*Tailles.WIDTH_LISTE);
            stats.setAlignment(Pos.CENTER_RIGHT);

            lstLabel.addAll(Arrays.asList(musique,artiste,nature,date,rating));
        } else {
            Label tempsEcoute = new Label(" \uD83D\uDD50");
            tempsEcoute.setOnMouseClicked(e -> tri(lstLabel,tempsEcoute,8));
            tempsEcoute.getStyleClass().addAll("clickable","low-size","gray");

            Label ecoutes = new Label(language.getString("fullyListened"));
            Label slash = new Label();
            Label skips = new Label(language.getString("skips"));
            ecoutes.setPrefWidth(0.14*Tailles.WIDTH_LISTE);
            slash  .setPrefWidth(0.01*Tailles.WIDTH_LISTE);
            skips  .setPrefWidth(0.05*Tailles.WIDTH_LISTE);
            ecoutes.setAlignment(Pos.CENTER_RIGHT);
            skips  .setAlignment(Pos.CENTER_LEFT);
            ecoutes.setOnMouseClicked(e -> tri(lstLabel,ecoutes,6));
            skips  .setOnMouseClicked(e -> tri(lstLabel,skips  ,1));
            ecoutes.getStyleClass().addAll("clickable","low-size","white");
            skips  .getStyleClass().addAll("clickable","low-size","white");

            Label total = new Label(language.getString("total"));
            Label ratio = new Label(language.getString("ratio"));
            total.setPrefWidth(0.07*Tailles.WIDTH_LISTE);
            ratio.setPrefWidth(0.1*Tailles.WIDTH_LISTE);
            total.setAlignment(Pos.CENTER);
            ratio.setAlignment(Pos.CENTER);
            total.setOnMouseClicked(e -> tri(lstLabel,total,5));
            ratio.setOnMouseClicked(e -> tri(lstLabel,ratio,2));
            total.getStyleClass().addAll("clickable","low-size","white");
            ratio.getStyleClass().addAll("clickable","low-size","white");

            if (tri == 3) {
                Label album = new Label(language.getString("album"));
                Label artiste = new Label(language.getString("artist")+" ");
                album      .setOnMouseClicked(e -> tri(lstLabel,album      ,4));
                artiste    .setOnMouseClicked(e -> tri(lstLabel,artiste    ,3));
                album      .getStyleClass().addAll("clickable","low-size","white");
                artiste    .getStyleClass().addAll("clickable","low-size","gray");
                HBox nom = new HBox(artiste,tempsEcoute);
                noms.getChildren().addAll(album,nom);
                noms.setPrefWidth(0.3*Tailles.WIDTH_LISTE);
                noms.setAlignment(Pos.CENTER_LEFT);

                lstLabel.addAll(Arrays.asList(album,artiste,tempsEcoute));
            } else if (tri == 1) {
                Label artiste = new Label(language.getString("artist"));
                artiste    .getStyleClass().addAll("clickable","low-size","white");
                artiste    .setOnMouseClicked(e -> tri(lstLabel,artiste,3));
                VBox nomArtTemps = new VBox(artiste,tempsEcoute);
                noms.getChildren().addAll(nomArtTemps);
                noms.setPrefWidth(0.3*Tailles.WIDTH_LISTE);
                noms.setAlignment(Pos.CENTER_LEFT);
                tempsEcoute.setText("\uD83D\uDD50");

                lstLabel.addAll(Arrays.asList(artiste,tempsEcoute));
            } else if (tri == 0) {
                Label musique = new Label(language.getString("music"));
                Label artiste = new Label(language.getString("artist")+" ");
                musique    .setOnMouseClicked(e -> tri(lstLabel,musique    ,4));
                artiste    .setOnMouseClicked(e -> tri(lstLabel,artiste    ,3));
                musique    .getStyleClass().addAll("clickable","low-size","white");
                artiste    .getStyleClass().addAll("clickable","low-size","gray");
                HBox nom = new HBox(artiste,tempsEcoute);
                noms.getChildren().addAll(musique,nom);
                noms.setPrefWidth(0.3*Tailles.WIDTH_LISTE);
                noms.setAlignment(Pos.CENTER_LEFT);

                lstLabel.addAll(Arrays.asList(musique,artiste,tempsEcoute));
            }

            lstLabel.addAll(Arrays.asList(ecoutes,skips,total,ratio,rating));
            HBox stats = new HBox(ecoutes,slash,skips,total,ratio,rating);
            stats.setSpacing(0.02*Tailles.WIDTH_LISTE);
            stats.setAlignment(Pos.CENTER_RIGHT);
            getChildren().addAll(indexPane,imgPane,noms,stats);
        }
    }

    public ImgPane(int indice, Artiste art, boolean graphics, Frame frame) {
        if (graphics) {
            setPadding(new Insets(10,10,10,10));
            setPrefSize(Tailles.WIDTH_LISTE,widthImg+15);
            Color bgColor = Couleur.getAverageColor(art.getImage().getImage());
            String textColor = getMainTextColor(bgColor);
            setBackground(Couleur.backgroundFull(bgColor));
            setAlignment(Pos.CENTER_LEFT);

            Label index = new Label("#"+indice);
            index.getStyleClass().addAll("big-size",textColor);
            StackPane indexPane = new StackPane(index);
            indexPane.setPrefWidth(Tailles.WIDTH_LISTE*0.06);
            indexPane.setAlignment(Pos.CENTER);
            indexPane.setPadding(new Insets(0,10,0,0));

            ImageView copy = new ImageView(art.getImage().getImage());
            copy.setPreserveRatio(true);
            if (copy.getImage() != null) {
                if (copy.getImage().getHeight() > copy.getImage().getWidth())
                    copy.setFitHeight(widthImg);
                else copy.setFitWidth(widthImg);
            }
            StackPane imgPane = new StackPane(copy);
            imgPane.setPrefWidth(widthImg);
            imgPane.setAlignment(Pos.CENTER);
            setOnClick(imgPane,Filtre.TYPE_MUSIQUE,art.getNom(),null,null,-1,frame);

            Label artName = new Label(art.getNom());
            artName.getStyleClass().addAll("medium-size",textColor);
            setOnClick(artName,Filtre.TYPE_MUSIQUE,art.getNom(),null,null,-1,frame);
            Label tempsEcoute = new Label("\uD83D\uDD50 " + getStringTempsEcoute(art.getTempsEcoute()));
            tempsEcoute.getStyleClass().addAll("very-low-size",getSecondaryTextColor(bgColor));
            VBox nomArtTemps = new VBox(artName,tempsEcoute);
            StackPane noms = new StackPane(nomArtTemps);
            noms.setPrefWidth(0.38*Tailles.WIDTH_LISTE);
            noms.setAlignment(Pos.CENTER_LEFT);
            noms.setPadding(new Insets(0,0,0,10));

            /*Label ecoutes = new Label(art.getNbEcoutesCompletes() + "");
            Label slash = new Label("/");
            Label skips = new Label(art.getNbSkips()+"");
            ecoutes.setPrefWidth(0.06*Tailles.WIDTH_LISTE);
            slash.setPrefWidth(0.01*Tailles.WIDTH_LISTE);
            skips.setPrefWidth(0.06*Tailles.WIDTH_LISTE);
            ecoutes.setAlignment(Pos.CENTER_RIGHT);
            slash.setAlignment(Pos.CENTER);
            skips.setAlignment(Pos.CENTER_LEFT);
            ecoutes.getStyleClass().addAll("medium-size",textColor);
            slash.getStyleClass().addAll("medium-size",textColor);
            skips.getStyleClass().addAll("medium-size",textColor);
            setOnClick(ecoutes,Filtre.TYPE_ECOUTE,art.getNom(),null,null,1,frame);
            setOnClick(skips,Filtre.TYPE_ECOUTE,art.getNom(),null,null,0,frame);

            Label total = new Label(art.getNbEcoutes()+"");
            Label ratio = new Label(String.format("(%2.2f",art.getRatio())+"%)");
            Label rating = new Label(String.format("%1.2f",art.getRating()));
            total.setPrefWidth(0.05*Tailles.WIDTH_LISTE);
            ratio.setPrefWidth(0.1*Tailles.WIDTH_LISTE);
            rating.setPrefWidth(0.07*Tailles.WIDTH_LISTE);
            total.setAlignment(Pos.CENTER);
            rating.setAlignment(Pos.CENTER);
            total.getStyleClass().addAll("medium-size",textColor);
            ratio.getStyleClass().addAll("medium-size",textColor);
            rating.getStyleClass().addAll("medium-size",textColor);
            setOnClick(total,Filtre.TYPE_ECOUTE,art.getNom(),null,null,-1,frame);*/
            getChildren().addAll(indexPane,imgPane,noms,getStats(art,frame,textColor));
        }
    }

    public ImgPane(int indice, Musique mus, boolean graphics, Frame frame) {
        if (graphics) {
            setPadding(new Insets(10,10,10,10));
            setPrefSize(Tailles.WIDTH_LISTE,widthImg);
            Color bgColor = Couleur.getAverageColor(mus.getImage().getImage());
            String textColor = getMainTextColor(bgColor);
            setBackground(Couleur.backgroundFull(bgColor));
            setAlignment(Pos.CENTER_LEFT);

            Label index = new Label("#"+indice);
            index.getStyleClass().addAll("big-size",textColor);
            StackPane indexPane = new StackPane(index);
            indexPane.setPrefWidth(Tailles.WIDTH_LISTE*0.06);
            indexPane.setAlignment(Pos.CENTER);
            indexPane.setPadding(new Insets(0,10,0,0));

            ImageView copy = new ImageView(mus.getImage().getImage());
            copy.setPreserveRatio(true);
            if (copy.getImage() != null) {
                if (copy.getImage().getHeight() > copy.getImage().getWidth())
                    copy.setFitHeight(widthImg);
                else copy.setFitWidth(widthImg);
            }
            StackPane imgPane = new StackPane(copy);
            imgPane.setPrefWidth(widthImg);
            imgPane.setAlignment(Pos.CENTER);
            this.setOnClick(copy,Filtre.TYPE_MUSIQUE,mus.getArtiste().getNom(),null,mus.getAlbum().getNom(),-1,frame);

            Label musName = new Label(mus.getNom());
            Label artName = new Label(mus.getArtiste().getNom());
            Label tempsEcoute = new Label(" \uD83D\uDD50 "+getStringTempsEcoute(mus.getTempsEcoute()));
            musName.getStyleClass().addAll("medium-size",textColor);
            artName.getStyleClass().addAll("low-size",getSecondaryTextColor(bgColor));
            tempsEcoute.getStyleClass().addAll("very-low-size",getSecondaryTextColor(bgColor));
            setOnClick(musName,Filtre.TYPE_ECOUTE,mus.getArtiste().getNom(),mus.getNom(),null,-1,frame);
            setOnClick(artName,Filtre.TYPE_MUSIQUE,mus.getArtiste().getNom(),null,null,-1,frame);
            HBox nom = new HBox(artName,tempsEcoute);
            VBox noms = new VBox(musName,nom);
            noms.setPadding(new Insets(0,0,0,10));
            noms.setPrefWidth(0.38*Tailles.WIDTH_LISTE);
            noms.setAlignment(Pos.CENTER_LEFT);

            getChildren().addAll(indexPane,imgPane,noms,getStats(mus,frame,textColor));
        }
    }

    public ImgPane(int indice, Album alb, boolean graphics,Frame frame) {
        if (graphics) {
            setPadding(new Insets(10,10,10,10));
            setPrefSize(Tailles.WIDTH_LISTE,widthImg);
            Color bgColor = Couleur.getAverageColor(alb.getImage().getImage());
            String textColor = getMainTextColor(bgColor);
            setBackground(Couleur.backgroundFull(bgColor));
            setAlignment(Pos.CENTER_LEFT);

            Label index = new Label("#"+indice);
            index.getStyleClass().addAll("big-size",textColor);
            StackPane indexPane = new StackPane(index);
            indexPane.setPrefWidth(Tailles.WIDTH_LISTE*0.06);
            indexPane.setAlignment(Pos.CENTER);
            indexPane.setPadding(new Insets(0,10,0,0));

            ImageView copy = new ImageView(alb.getImage().getImage());
            copy.setPreserveRatio(true);
            if (copy.getImage() != null) {
                if (copy.getImage().getHeight() > copy.getImage().getWidth())
                    copy.setFitHeight(widthImg);
                else copy.setFitWidth(widthImg);
            }
            StackPane imgPane = new StackPane(copy);
            imgPane.setPrefWidth(widthImg);
            imgPane.setAlignment(Pos.CENTER);
            this.setOnClick(imgPane,Filtre.TYPE_MUSIQUE,alb.getArtiste().getNom(),null,alb.getNom(),-1,frame);

            Label albName = new Label(alb.getNom());
            albName.getStyleClass().addAll("medium-size",textColor);
            this.setOnClick(albName,Filtre.TYPE_MUSIQUE,alb.getArtiste().getNom(),null,alb.getNom(),-1,frame);
            Label artName = new Label(alb.getArtiste().getNom());
            artName.getStyleClass().addAll("low-size",getSecondaryTextColor(bgColor));
            this.setOnClick(artName,Filtre.TYPE_MUSIQUE,alb.getArtiste().getNom(),null,null,-1,frame);
            Label tempsEcoute = new Label(" \uD83D\uDD50 "+getStringTempsEcoute(alb.getTempsEcoute()));
            tempsEcoute.getStyleClass().addAll("very-low-size",getSecondaryTextColor(bgColor));
            HBox nom = new HBox(artName,tempsEcoute);
            VBox noms = new VBox(albName,nom);
            noms.setPrefWidth(0.38*Tailles.WIDTH_LISTE);
            noms.setAlignment(Pos.CENTER_LEFT);
            noms.setPadding(new Insets(0,0,0,10));

            getChildren().addAll(indexPane,imgPane,noms,getStats(alb,frame,textColor));
        }
    }

    public ImgPane(int indice, Ecoute eco, boolean graphics, Frame frame) {
        if (graphics) {
            setPadding(new Insets(10,10,10,10));
            setPrefSize(Tailles.WIDTH_LISTE,widthImg);
            Color bgColor = Couleur.getAverageColor(eco.getImage().getImage());
            String textColor = getMainTextColor(bgColor);
            setBackground(Couleur.backgroundFull(bgColor));
            setAlignment(Pos.CENTER_LEFT);

            Label index = new Label("#"+indice);
            index.getStyleClass().addAll("big-size",textColor);
            StackPane indexPane = new StackPane(index);
            indexPane.setPrefWidth(Tailles.WIDTH_LISTE*0.06);
            indexPane.setAlignment(Pos.CENTER);
            indexPane.setPadding(new Insets(0,10,0,0));

            ImageView copy = new ImageView(eco.getImage().getImage());
            copy.setPreserveRatio(true);
            if (copy.getImage() != null) {
                if (copy.getImage().getHeight() > copy.getImage().getWidth())
                    copy.setFitHeight(widthImg);
                else copy.setFitWidth(widthImg);
            }
            StackPane imgPane = new StackPane(copy);
            imgPane.setPrefWidth(widthImg);
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
            noms.setPadding(new Insets(0,0,0,10));

            ResourceBundle language = Langue.bundle;
            Label nature = new Label(eco.getNature() ? language.getString("listened") : language.getString("skipped"));
            nature.setPrefWidth(0.1*Tailles.WIDTH_LISTE);
            nature.getStyleClass().addAll("medium-size",textColor);
            nature.setAlignment(Pos.CENTER_RIGHT);
            this.setOnClick(nature,Filtre.TYPE_ECOUTE,eco.getArtiste().getNom(),eco.getNom(),null,eco.getNature() ? 1 : 0,frame);

            Label date = new Label(eco.getDate().toString());
            date.setPrefWidth(0.23*Tailles.WIDTH_LISTE);
            date.setAlignment(Pos.CENTER_LEFT);
            date.getStyleClass().addAll("medium-size",textColor);

            Label rating = new Label(String.format("%1.2f",eco.getRating()));
            rating.setPrefWidth(0.07*Tailles.WIDTH_LISTE);
            rating.setAlignment(Pos.CENTER);
            rating.getStyleClass().addAll("medium-size",getColorRating(eco));

            HBox stats = new HBox(nature,date,rating);
            stats.setPrefWidth(0.4*Tailles.WIDTH_LISTE);
            stats.setSpacing(0.02*Tailles.WIDTH_LISTE);
            stats.setAlignment(Pos.CENTER_RIGHT);

            getChildren().addAll(indexPane,imgPane,noms,stats);
        }
    }

    private HBox getStats(Donnee d, Frame frame, String textColor) {
        Label ecoutes = new Label(d.getNbEcoutesCompletes()+"");
        Label slash   = new Label("/");
        Label skips   = new Label(d.getNbSkips()+"");
        ecoutes.setPrefWidth(0.06*Tailles.WIDTH_LISTE);
        slash  .setPrefWidth(0.01*Tailles.WIDTH_LISTE);
        skips  .setPrefWidth(0.05*Tailles.WIDTH_LISTE);
        ecoutes.setAlignment(Pos.CENTER_RIGHT);
        slash.setAlignment(Pos.CENTER);
        skips.setAlignment(Pos.CENTER_LEFT);
        setOnClick(ecoutes,Filtre.TYPE_ECOUTE,d.getArtiste().getNom(),null,d.getNom(),1,frame);
        setOnClick(skips,Filtre.TYPE_ECOUTE,d.getArtiste().getNom(),null,d.getNom(),0,frame);
        ecoutes.getStyleClass().addAll("medium-size",textColor);
        slash.getStyleClass().addAll("medium-size",textColor);
        skips.getStyleClass().addAll("medium-size",textColor);

        Label total = new Label(d.getNbEcoutes()+"");
        Label ratio = new Label(String.format("(%2.2f",d.getRatio())+"%)");
        Label rating = new Label(String.format("%1.2f",d.getRating()));
        total.setPrefWidth(0.07*Tailles.WIDTH_LISTE);
        ratio.setPrefWidth(0.1*Tailles.WIDTH_LISTE);
        rating.setPrefWidth(0.06*Tailles.WIDTH_LISTE);
        total.setAlignment(Pos.CENTER);
        ratio.setAlignment(Pos.CENTER);
        rating.setAlignment(Pos.CENTER);
        total.getStyleClass().addAll("medium-size",textColor);
        ratio.getStyleClass().addAll("medium-size",textColor);
        rating.getStyleClass().addAll("medium-size",getColorRating(d));
        // setOnClick(total,Filtre.TYPE_ECOUTE,d.getArtiste().getNom(),null,d.getNom(),-1,frame);

        HBox stats = new HBox(ecoutes,slash,skips,total,ratio,rating);
        stats.setSpacing(0.02*Tailles.WIDTH_LISTE);
        stats.setAlignment(Pos.CENTER_RIGHT);
        return stats;
    }

    private String getColorRating(Donnee d) {
        float rating = d.getRating();
        if (d instanceof Ecoute) {
            if (rating < 0.6) return "red";
            else if (rating > 0.8) return "green";
        } else if ( d instanceof Artiste || d instanceof Album) {
            if (rating < 0.8) return "red";
            else if (rating > 1.2) return "green";
        } else if (d instanceof Musique) {
            if (rating < 0.75) return "red";
            else if (rating > 1.1) return "green";
        } return "gray";
    }

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
            ImgPane.resetTitres();
        });
    }

    private void tri(ArrayList<Label> toUnselect, Label toSelect, int triValue) {
        unselected(toUnselect);
        selected(toSelect,triValue);
        vue.reset();
        vue.actualiser();
    }

    private String getMainTextColor(Color bgColor) {
        if (bgColor.getRed() > 0.88 && bgColor.getGreen() > 0.88 && bgColor.getBlue() > 0.88)
            return "black";
        else return "white";
    }

    private String getSecondaryTextColor(Color bgColor) {
        double r = bgColor.getRed()   * 255;
        double v = bgColor.getGreen() * 255;
        double b = bgColor.getBlue()  * 255;
        int threshold = 40;
        if (r > 90 && r < 220 &&
            v > 90 && v < 220 &&
            b > 90 && b < 220 &&
            Math.abs(r-v) < threshold && Math.abs(v-b) < threshold && Math.abs(b-r) < threshold)
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

    private String getStringTempsEcoute(int tempsEcoute) {
        ResourceBundle language = Langue.bundle;
        String res = tempsEcoute + " " + language.getString("minutes");
        if (tempsEcoute >= 120) res += " (= " + toHours(tempsEcoute) + " " + language.getString("hours").toLowerCase() + ")";
        return res;
    }

    private int toHours(int minutes) {
        return minutes / 60;
    }
}
