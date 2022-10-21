package ui.home;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import ui.add_item.AddItemController;
import ui.edit_item.EditItemController;


public class HomeController implements Initializable,OnEditDataListener {
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

    @FXML
    private JFXButton btn_queue;

    @FXML
    private ImageView btn_exit;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        onClick();
        setView("queue_order","queue_list_order");

    }

    private void onClick(){
        btn_exit.setOnMouseClicked(v->{
            System.exit(0);
        });

        btn_queue.setOnMouseClicked(v->{
            setView("queue_order","queue_list_order");
        });
        btn_search_order.setOnMouseClicked(v->{
            setView("list_order","list_order");
        });
        btn_add_item.setOnMouseClicked(v->{
            goToAddOrEditPage(null);
        });
        btn_employee.setOnMouseClicked(v->{
            setView("employee","add_employee");
        });
        btn_edit_item.setOnMouseClicked(v->{
            setView("edit_item","listview");
            goToListItem();
        });
    }

    private void goToListItem(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("ui/edit_item/listview.fxml"));
            EditItemController ctr = new EditItemController(this);
            loader.setController(ctr);
            Pane view = loader.load();

            mainPane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setView(String packageName,String fileName){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("ui/" +packageName+"/"+fileName+".fxml"));

            Pane view = loader.load();

            mainPane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goToAddOrEditPage(String itemId){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("ui/add_item/add_item.fxml"));
            AddItemController ctr = new AddItemController(itemId);
            loader.setController( itemId!=null ? new AddItemController(itemId) : new AddItemController() );

            Pane view = loader.load();

            mainPane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void goToEditPage(String itemId) {
        goToAddOrEditPage(itemId);
    }
}
