import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Library extends Application {
    public static void main(String[] args) throws Exception {
       launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL resource = getClass().getResource("/view/Login.fxml");
        Parent root = FXMLLoader.load(resource);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Library Manager");
        primaryStage.getIcons().add(new Image("/image/image.png"));
        primaryStage.show();
    }

    
}
