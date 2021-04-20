package sample.ui.list_order;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import sample.general.data.model.OrderModel;
import sample.general.db.AppDatabase;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ListOrderController implements Initializable {

    @FXML
    private ListView<OrderModel> list_order;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupList();
    }

    private void setupList(){
        list_order.setItems(getData());
        list_order.setCellFactory(listItem-> new ListAdapter());
    }

    private ObservableList<OrderModel> getData(){
        Connection con = AppDatabase.getInstance();

        String query = "";
        Statement statement = null;
        ObservableList<OrderModel> newData= FXCollections.observableArrayList();
        try {
            statement = con.createStatement();
            System.out.println("Connection Success");
            query = "SELECT * FROM orders";


            ResultSet res = statement.executeQuery(query);


            while(res.next()){
                newData.add(new OrderModel(res.getString(1),res.getString(2),res.getString(3),Integer.parseInt(res.getString(4)),res.getString(5),res.getString(6)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return newData;
    }

}
