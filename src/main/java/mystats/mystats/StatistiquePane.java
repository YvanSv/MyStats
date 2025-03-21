package mystats.mystats;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import mystats.mystats.utils.Couleur;

public class StatistiquePane {
    private final VBox main;

    public StatistiquePane(String mainTxt, String secondaryTxt) {
        Label main1 = new Label(mainTxt);
        main1.getStyleClass().addAll("stats-grand");
        Label secondary1 = new Label(secondaryTxt);
        secondary1.getStyleClass().addAll("stats-secondary");
        main = new VBox(main1, secondary1);
        main.setPrefWidth(600);
        main.setAlignment(Pos.CENTER);
        main.setBackground(Couleur.background(Color.GRAY));
    }

    public StatistiquePane(ImageView img, String position, String nom, String type) {
        Label pos = new Label(position);
        pos.getStyleClass().add("stats-grand");
        Label nom1 = new Label(nom);
        nom1.getStyleClass().add("stats-moyen");
        Label type1 = new Label(type);
        type1.getStyleClass().add("stats-petit");
        GridPane tab = new GridPane();
        tab.setPrefWidth(300);
        tab.setAlignment(Pos.CENTER);
        tab.addColumn(0,pos);
        tab.addColumn(1,nom1,type1);
        GridPane.setRowSpan(pos,2);
        ImageView imgCopy = new ImageView(img.getImage());
        imgCopy.setFitHeight(300);
        imgCopy.setPreserveRatio(true);
        main = new VBox(imgCopy,tab);
        main.setPrefWidth(600);
        main.setAlignment(Pos.CENTER);
        main.setBackground(Couleur.background(Couleur.getAverageColor(imgCopy.getImage())));
    }

    public VBox getPane() {
        return main;
    }
}
