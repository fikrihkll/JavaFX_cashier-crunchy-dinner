package ui.add_item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import data.model.ItemModel;
import data.db.AppDatabase;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AddItemController implements Initializable {

    public AddItemController(String id) {
        this.id = id;
    }

    public AddItemController() { }

    @FXML
    private TextField txtItemID;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtItemPrice;

    @FXML
    private TextField txtItemPicture;

    @FXML
    private Button btnItemUpdate;


    @FXML
    private Label tv_status;

    @FXML
    private ComboBox<String> cb_type;

    @FXML
    private ComboBox<String> cb_cat;

    private String id="";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtItemID.setDisable(true);
        prepareComboBox();
        checkAddOrEdit();

        btnItemUpdate.setOnMouseClicked(event -> {

            if(isDataValid()){
                insertOrUpdateData();
            }

        });
    }

    private void insertOrUpdateData(){
        if(id.isEmpty())
            insertData();
        else
            updateData();
    }

    private void showDialog(boolean isSuccess,String msg){
        if(isSuccess)
            new Alert(Alert.AlertType.INFORMATION,msg,ButtonType.OK).show();
        else
            new Alert(Alert.AlertType.ERROR,msg,ButtonType.OK).show();
    }

    private void updateData(){
        Connection connection= AppDatabase.getInstance();

        String sql="UPDATE item SET name = '"+txtItemName.getText()+
                "', price = "+Integer.parseInt(txtItemPrice.getText())+
                ",pict = '"+txtItemPicture.getText()+
                "',category = '"+cb_cat.getSelectionModel().getSelectedItem()+
                "',type = '"+cb_type.getSelectionModel().getSelectedItem()+"' " +
                "WHERE id = "+id+"";
        Statement statement= null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

            showDialog(true,"Data successfully updated");

        } catch (SQLException throwables) {
            showDialog(false,throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    private void insertData(){
        Connection connection= AppDatabase.getInstance();

        String sql="INSERT INTO item VALUES(null,'"+txtItemName.getText()+"',"+Integer.parseInt(txtItemPrice.getText())+",'"+txtItemPicture.getText()+"','"+cb_cat.getSelectionModel().getSelectedItem()+"', '"+cb_type.getSelectionModel().getSelectedItem()+"' )";
        Statement statement= null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

            showDialog(true,"Data successfully inserted");
            clearField();
        } catch (SQLException throwables) {
            showDialog(false,throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    private void clearField(){
        txtItemName.setText("");
        txtItemPicture.setText("");
        txtItemPrice.setText("");
    }

    private boolean isDataValid(){
        if(!txtItemName.getText().isEmpty()&&
        !txtItemPrice.getText().isEmpty()&&
        !txtItemPicture.getText().isEmpty()){
            return true;
        }
        return false;
    }

    private void checkAddOrEdit(){
        if(!id.isEmpty()){
            tv_status.setText("Edit Data");
            btnItemUpdate.setText("Save Edit");

            ItemModel data = getSingleData();

            txtItemID.setText(data.getId());
            txtItemName.setText(data.getName());
            txtItemPrice.setText(Integer.toString(data.getPrice()));
            txtItemPicture.setText(data.getPict());
            cb_cat.setValue(data.getCategory());
            cb_type.setValue(data.getType());
        }
    }

    private ItemModel getSingleData(){
        Connection con = AppDatabase.getInstance();

        String query = "";
        Statement statement = null;
        ItemModel newData= null;
        try {
            statement = con.createStatement();
            System.out.println("Connection Success");
            query = "SELECT * FROM item WHERE id = '"+id+"'";

            ResultSet res = statement.executeQuery(query);

            while(res.next()){
                newData=new ItemModel(res.getString(1),res.getString(2),res.getInt(3), res.getString(4),res.getString(5),res.getString(6));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return newData;
    }

    private void prepareComboBox(){
        ObservableList<String> listType = FXCollections.observableArrayList();
        listType.add("Food");
        listType.add("Drink");

        ObservableList<String> listCat = FXCollections.observableArrayList();
        listCat.add("Main Dish");
        listCat.add("Chicken");
        listCat.add("C-Cafe");

        cb_type.setItems(listType);
        cb_cat.setItems(listCat);
        cb_type.setValue(listType.get(0));
        cb_cat.setValue(listCat.get(0));
    }
}


