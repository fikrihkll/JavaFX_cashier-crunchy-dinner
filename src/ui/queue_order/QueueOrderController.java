package ui.queue_order;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.util.Duration;
import data.model.EmployeeModel;
import data.model.ItemOrderModel;
import data.model.OrderModel;
import data.db.AppDatabase;
import ui.list_order.ItemOrderAdapter;
import ui.list_order.ListAdapter;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class QueueOrderController implements Initializable {
    @FXML
    private ListView<OrderModel> list_order_queue;

    @FXML
    private JFXButton btn_process;

    @FXML
    private ListView<ItemOrderModel> list_item_queue;

    @FXML
    private ComboBox<String> cb_cashier;

    @FXML
    private ComboBox<Integer> cb_table;

    private ObservableList<EmployeeModel> listEmployee = FXCollections.observableArrayList();
    private int selectedItem=-1;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupCombo();
        setupList();
        listenNewOrder();
        onClick();
    }

    private void onClick(){
        btn_process.setOnMouseClicked(v->{
            if(selectedItem!=-1)
                processOrder();

        });
    }

    private void processOrder(){
        Connection con = AppDatabase.getInstance();

        String query= "UPDATE orders SET employee_id = '"+ listEmployee.get(selectedItem).getEmployeeId()+
                "', buyer = "+cb_table.getSelectionModel().getSelectedItem()+
                ", cashier_name = '"+listEmployee.get(selectedItem).getName()+
                "' WHERE order_id = "+list_order_queue.getItems().get(selectedItem).getOrderId()+";";

        try {
            Statement st = con.createStatement();
            st.executeUpdate(query);
            setupList();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setupList(){
        list_order_queue.setItems(getData());
        list_order_queue.setCellFactory(listItem-> new ListAdapter());

        list_order_queue.setOnMouseClicked(event -> {
            System.out.println("invoked");
            setOrderItemList(getItemData(list_order_queue.getSelectionModel().getSelectedItem().getOrderId()));
            selectedItem=list_order_queue.getSelectionModel().getSelectedIndex();
        });
    }

    private void setupCombo(){
        ObservableList<Integer> list = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40);
        cb_table.setItems(list);

        cb_cashier.setItems(getEmployee());
        cb_cashier.setValue(cb_cashier.getItems().get(0));
    }

    private void setOrderItemList(ObservableList<ItemOrderModel> newData){
        list_item_queue.setItems(newData);
        list_item_queue.setCellFactory(listItem-> new ItemOrderAdapter());
    }

    private void listenNewOrder(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), ev -> {
            if(list_order_queue!=null){
                list_order_queue.setItems(getData());
                if(selectedItem!=-1){
                    list_order_queue.getSelectionModel().select(selectedItem);
                }

            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    private ObservableList<String> getEmployee(){
        Connection con = AppDatabase.getInstance();

        String query= "SELECT * FROM employee";
        ObservableList<String> listEmployee = FXCollections.observableArrayList();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                this.listEmployee.add(new EmployeeModel(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
                listEmployee.add(rs.getString(2));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listEmployee;
    }

    private ObservableList<OrderModel> getData(){
        Connection con = AppDatabase.getInstance();

        String query = "";
        Statement statement = null;
        ObservableList<OrderModel> newData= FXCollections.observableArrayList();
        try {
            statement = con.createStatement();
            System.out.println("Connection Success"+Calendar.getInstance().getTime().toString());
            query = "SELECT * FROM orders WHERE cashier_name IS NULL AND employee_id IS NULL";

            ResultSet res = statement.executeQuery(query);

            while(res.next()){
                newData.add(new OrderModel(res.getString(1),res.getString(2),res.getString(3),Integer.parseInt(res.getString(4)),res.getString(5),res.getString(6)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return newData;
    }

    private ObservableList<ItemOrderModel> getItemData(String orderId){
        Connection con = AppDatabase.getInstance();

        String query = "";
        Statement statement = null;
        ObservableList<ItemOrderModel> newData= FXCollections.observableArrayList();
        try {
            statement = con.createStatement();

            query = "SELECT * FROM item_order WHERE order_id = "+orderId+";";


            ResultSet res = statement.executeQuery(query);


            while(res.next()){
                newData.add(new ItemOrderModel(res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getInt(5),res.getString(6),res.getInt(7)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return newData;
    }
}
