package mystats.mystats;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import mystats.mystats.utils.Langue;
import mystats.mystats.utils.Tailles;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class App extends Application {
    // public static final String CLIENT_ID = "074ca6ac424b4475aa544a69cb0f8e3e";
    // public static final String CLIENT_SECRET = "59e8ced3168e43f99466f36df9640626";
    public static Stage stage;
    private static Pane root;

    @Override
    public void start(Stage stage) throws IOException {
        App.stage = stage;
        Langue.english();
        Langue.language = "English";
        AtomicReference<Frame> far = new AtomicReference<>();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("frame-view.fxml"));
        fxmlLoader.setControllerFactory(iC -> {
            Frame f = new Frame();
            far.set(f);
            return f;
        });
        root = fxmlLoader.load();
        Scene scene = new Scene(root, 480, 480);
        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            Tailles.setTailles(root.getWidth(),root.getHeight());
            far.get().setTitlesSize();
        });
        scene.heightProperty().addListener((observable, oldValue, newValue) -> Tailles.setTailles(root.getWidth(),root.getHeight()));
        scene.getStylesheets().add(getClass().getResource("/stylesheets/styles_main.css").toExternalForm());
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/logo-micro.png")));
        stage.setMaximized(true);
        stage.setTitle("MyStats");
        stage.setScene(scene);
        stage.show();
    }

    public static void addFond(Rectangle overlay) {
        root.getChildren().add(overlay);
        overlay.widthProperty().bind(root.widthProperty());
        overlay.heightProperty().bind(root.heightProperty());
    }

    public static void removeFond(Rectangle overlay) {
        root.getChildren().remove(overlay);
    }

    public static void main(String[] args) {
        launch();
    }
}
