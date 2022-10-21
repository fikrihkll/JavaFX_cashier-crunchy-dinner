package ui.edit_item;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.fxml.FXML;
import data.model.ItemModel;
import data.db.AppDatabase;
import ui.home.OnEditDataListener;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ListEditItemController extends ListCell<ItemModel> {

    public ListEditItemController(OnDeleteListener listenerDelete, OnEditDataListener listenerEdit) {
        this.listenerDelete = listenerDelete;
        this.listenerEdit = listenerEdit;
    }

    @FXML
    private AnchorPane root;

    @FXML
    private ImageView iv;

    @FXML
    private Label tv_name;

    @FXML
    private Label tv_price;

    @FXML
    private Button btn_edit;

    @FXML
    private Button btn_delete;

    private FXMLLoader loader=null;

    private ItemModel itemData = null;

    private OnDeleteListener listenerDelete;
    private OnEditDataListener listenerEdit;

    private void deleteFromDb(){
        Connection connection= AppDatabase.getInstance();

        String sql="DELETE FROM item WHERE id = '"+itemData.getId()+"'";
        Statement statement= null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            listenerDelete.onDelete(itemData);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void goToTheNextPage(){
        listenerEdit.goToEditPage(itemData.getId());
    }

    private void onClick(){
        btn_delete.setOnMouseClicked(event -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure want to delete "+itemData.getName()+"?", ButtonType.YES,ButtonType.NO);
            alert.showAndWait();

            if(alert.getResult() == ButtonType.YES){
                deleteFromDb();
            }

        });

        btn_edit.setOnMouseClicked(v->{
            goToTheNextPage();
        });
    }

    @Override
    protected void updateItem(ItemModel item, boolean empty) {
        super.updateItem(item, empty);
        itemData = item;
        if(item==null || empty){
            setText(null);
            setGraphic(null);
        }else{

            if(loader==null){
                try {
                    loader = new FXMLLoader();
                    loader.setLocation(getClass().getClassLoader().getResource("ui/edit_item/layout_edit_item.fxml"));
                    loader.setController(this);
                    loader.load();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            tv_name.setText(item.getName());
            tv_price.setText("Rp."+item.getPrice());
            Thread newThread = new Thread(() -> {
                iv.setImage(new Image(item.getPict()));
            });
            newThread.start();

            onClick();
            setText(null);
            setGraphic(root);
        }
    }
}
