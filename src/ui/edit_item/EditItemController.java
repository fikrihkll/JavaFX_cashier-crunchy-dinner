package ui.edit_item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import data.model.ItemModel;
import data.db.AppDatabase;
import ui.home.OnEditDataListener;


import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class EditItemController implements Initializable,OnDeleteListener {

    @FXML
    private ListView<ItemModel> listview_editItem;

    private ObservableList<ItemModel> listItems = null;

    private OnEditDataListener listener;

    public EditItemController(OnEditDataListener listener) {
        this.listener = listener;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listItems = getData();

        listview_editItem.setItems(listItems);
        listview_editItem.setCellFactory(listItem -> new ListEditItemController(this,listener));

    }

    private ObservableList<ItemModel> getData(){
        Connection con = AppDatabase.getInstance();

        String query = "";
        Statement statement = null;
        ObservableList<ItemModel> newData= FXCollections.observableArrayList();
        try {
            statement = con.createStatement();
            System.out.println("Connection Success");
            query = "SELECT * FROM item ";

            ResultSet res = statement.executeQuery(query);

            while(res.next()){
                newData.add(new ItemModel(res.getString(1),res.getString(2),res.getInt(3), res.getString(4),res.getString(5),res.getString(6)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return newData;
    }

    @Override
    public void onDelete(ItemModel data) {
        listItems.remove(data);
        listview_editItem.setItems(listItems);
    }
}

