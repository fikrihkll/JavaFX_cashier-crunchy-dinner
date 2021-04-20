package sample.ui.home;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import sample.ui.list_order.ListOrderController;


public class HomeController implements Initializable {
    @FXML
    private BorderPane mainPane;

    @FXML
    private AnchorPane drawer_panel;

    @FXML
    private JFXButton btn_search_order;

    @FXML
    private JFXButton btn_add_item;

    @FXML
    private JFXButton btn_edit_item;

    @FXML
    private JFXButton btn_employee;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        onClick();
    }

    private void onClick(){
        btn_search_order.setOnMouseClicked(v->{
            setView("list_order","list_order");
        });
        btn_add_item.setOnMouseClicked(v->{
            setView("add_item","add_item");
        });
    }

    private void setView(String packageName,String fileName){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("sample/ui/"+packageName+"/"+fileName+".fxml"));
            ListOrderController controller = new ListOrderController();
            loader.setController(controller);

            Pane view = loader.load();
            mainPane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
