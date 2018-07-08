package controls;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        new Login().show(primaryStage);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
