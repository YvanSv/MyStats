package mystats.mystats.utils;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

public class Couleur {
    public static Color getAverageColor(Image image) {
        if (image == null) {
            return Color.web("#1f1f1f");
        }
        PixelReader pixelReader = image.getPixelReader();
        if (pixelReader == null) return Color.web("#1f1f1f");

        // Dimensions de l'image
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        // Accumulateurs pour les canaux de couleur
        double totalRed = 0;
        double totalGreen = 0;
        double totalBlue = 0;

        // Parcourir tous les pixels de l'image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Récupérer la couleur du pixel
                Color color = pixelReader.getColor(x, y);
                totalRed += color.getRed();
                totalGreen += color.getGreen();
                totalBlue += color.getBlue();
            }
        }

        // Nombre total de pixels
        int numPixels = width * height;

        // Calculer la moyenne pour chaque canal de couleur
        double avgRed = totalRed / numPixels;
        double avgGreen = totalGreen / numPixels;
        double avgBlue = totalBlue / numPixels;

        // Retourner la couleur moyenne
        return new Color(avgRed, avgGreen, avgBlue, 1.0);
    }

    public static Background background(Color color) {
        double r = color.getRed();
        double v = color.getGreen();
        double b = color.getBlue();
        if (r > 0.5 || v > 0.5 || b > 0.5) {
            r /= 2;
            v /= 2;
            b /= 2;
        }
        if (r < 0.1 && v < 0.1 && b < 0.1) {
            r *= 2;
            v *= 2;
            b *= 2;
        }
        color = new Color(r,v,b,1);
        Color endColor = Color.web("#1f1f1f");
        RadialGradient gradient = new RadialGradient(
                0, 0, 0.5, 0.5, 0.5,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0.0, endColor),
                new Stop(0.5, color),
                new Stop(1.0, endColor)
        );
        /*LinearGradient gradient = new LinearGradient(
                0, 0, 0, 1,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0, endColor),
                new Stop(0.5, color),
                new Stop(1, endColor)
        );*/
        //return new Background(new BackgroundFill(color,CornerRadii.EMPTY, Insets.EMPTY));
        return new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY));
    }

    public static Background backgroundFull(Color color) {
        return new Background(new BackgroundFill(color,CornerRadii.EMPTY, Insets.EMPTY));
    }
}
