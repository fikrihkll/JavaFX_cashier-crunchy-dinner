package sample.general;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.general.util.StackController;
import sample.ui.home.HomeController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("sample/ui/home/home.fxml"));
        HomeController controller = new HomeController();
        loader.setController(controller);

        Parent root = loader.load();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("E-Menu Crunchy Dinner");

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        StackController.addToStack(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
