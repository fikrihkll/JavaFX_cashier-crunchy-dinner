package ui.list_order;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.util.StringConverter;
import data.model.ItemOrderModel;
import data.model.OrderModel;
import data.db.AppDatabase;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ListOrderController implements Initializable {

    @FXML
    private ListView<OrderModel> list_order;
    @FXML
    private JFXTextField et_search;

    @FXML
    private JFXButton btn_search;

    @FXML
    private JFXButton btn_desc;

    @FXML
    private JFXButton btn_load_more;

    @FXML
    private JFXButton btn_asc;

    @FXML
    private DatePicker datepicker;

    @FXML
    private ListView<ItemOrderModel> list_item;

    @FXML
    private JFXTimePicker time_picker;

    @FXML
    private Label tv_info_query;

    @FXML
    private JFXButton btn_clear;

    private int loadLimit = 5;

    private boolean isAsc=false;
    private boolean isDate=false;
    private boolean isTime=false;
    private boolean isSearch=false;

    private String keyword="";
    private String selectedDate = null;
    private String selectedTime = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupList();
        onClick();
    }

    private void setupList(){

        String finalDateTime = null;
        if(selectedDate!=null && selectedTime!=null)
            finalDateTime = selectedDate+" "+selectedTime;
        else if(selectedDate!=null && selectedTime == null){
            finalDateTime = selectedDate;
        }


        list_order.setItems(getData(finalDateTime));
        list_order.setCellFactory(listItem-> new ListAdapter());

        String orderType = isAsc ? "ASC" : "DESC";
        tv_info_query.setText("Data is loaded from "+finalDateTime==null?finalDateTime:"only"+" as "+orderType);
    }

    private void onClick(){
        btn_search.setOnMouseClicked(v->{
            //reset limit
            loadLimit = 5;

            keyword=et_search.getText();
            isSearch=true;
            setupList();
        });

        btn_load_more.setOnMouseClicked(v->{
            loadLimit+=loadLimit;

            setupList();
        });

        btn_clear.setOnMouseClicked(v->{
            //reset limit
            loadLimit = 5;

            clearFieldSearch();
            clearTimePickerField();
            clearDatepicker();

            isSearch=false;
            isAsc = false;
            isTime= false;
            isDate = false;

            keyword="";
            selectedTime = null;
            selectedDate = null;
            setupList();
        });

        time_picker.setOnAction(event -> {
            //reset limit
            loadLimit = 5;

            String finalTime = time_picker.getValue().toString();
            selectedTime = finalTime;

            System.out.println(selectedTime);

            isTime = true;
            setupList();
        });

        btn_asc.setOnMouseClicked(v->{
            //reset limit
            loadLimit = 5;

            isAsc = true;
            setupList();
        });

        btn_desc.setOnMouseClicked(v->{
            //reset limit
            loadLimit = 5;

            isAsc = false;
            setupList();
        });

        list_order.setOnMouseClicked(event -> {
            setOrderItemList(getItemData(list_order.getSelectionModel().getSelectedItem().getOrderId()));
        });
        datepicker.setConverter(new StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                datepicker.setPromptText(pattern.toLowerCase());
            }

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        datepicker.setOnAction(event ->{
            LocalDate date = datepicker.getValue();

            String finalDate = date.toString();


            isDate=true;

            selectedDate = finalDate;

            setupList();
        });
    }

    private void setOrderItemList(ObservableList<ItemOrderModel> newData){
        list_item.setItems(newData);
        list_item.setCellFactory(listItem-> new ItemOrderAdapter());
    }

    private void clearTimePickerField(){
        time_picker.setValue(null);
    }

    private void clearDatepicker(){
        datepicker.setValue(null);
    }

    private void clearFieldSearch(){
        if(!isSearch){
            et_search.setText("");
        }
    }

    private ObservableList<OrderModel> getData(String date){

        Connection con = AppDatabase.getInstance();

        String query = "";
        Statement statement = null;
        ObservableList<OrderModel> newData= FXCollections.observableArrayList();
        try {
            statement = con.createStatement();

            query = generateQuery(date);


            ResultSet res = statement.executeQuery(query);

            while(res.next()){
                newData.add(new OrderModel(res.getString(1),res.getString(2),res.getString(3),Integer.parseInt(res.getString(4)),res.getString(5),res.getString(6)));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return newData;
    }

    private String generateQuery(String date){

        if(!isAsc){
            if(isDate && date!=null){
                //Descending from selected date
                //Why 23:59:59 ? because it will sort from the end of the day to the bottom
                if(isSearch)
                    return "SELECT * FROM orders WHERE cashier_name LIKE '%"+keyword+"%' AND date <= '"+date+" 23:59:59' LIMIT "+loadLimit;
                else
                    return "SELECT * FROM orders WHERE date <= '"+date+" 23:59:59' LIMIT "+loadLimit;
            }else if(isDate && isTime && date!=null){
                //Descending from selected date time
                if(isSearch)
                    return "SELECT * FROM orders WHERE cashier_name LIKE '%"+keyword+"%' AND date <= '"+date+"' LIMIT "+loadLimit;
                else
                    return "SELECT * FROM orders WHERE date <= '"+date+"' LIMIT "+loadLimit;
            }else{
                //Only Descending from now time
                if(isSearch)
                    return "SELECT * FROM orders WHERE cashier_name LIKE '%"+keyword+"%' ORDER BY date DESC LIMIT "+loadLimit;
                else
                    return "SELECT * FROM orders ORDER BY date DESC LIMIT "+loadLimit;
            }
        }else{
            if(isDate && date!=null){
                //Ascending from selected date
                //Why 00:00:0 ? because it will sort from the beginning of the day to the top
                if(isSearch)
                    return "SELECT * FROM orders WHERE cashier_name LIKE '%"+keyword+"%' AND date >= '"+date+" 00:00:00' LIMIT "+loadLimit;
                else
                    return "SELECT * FROM orders WHERE date >= '"+date+" 00:00:00' LIMIT "+loadLimit;
            }else if(isDate && isTime && date!=null){
                //Ascending from selected date time
                if(isSearch)
                    return "SELECT * FROM orders WHERE cashier_name LIKE '%"+keyword+"%' AND date >= '"+date+"' LIMIT "+loadLimit;
                else
                    return "SELECT * FROM orders WHERE date >= '"+date+"' LIMIT "+loadLimit;
            }else{
                //Only Ascending from now time
                if(isSearch)
                    return "SELECT * FROM orders WHERE cashier_name LIKE '%"+keyword+"%' ORDER BY date ASC LIMIT "+loadLimit;
                else
                    return "SELECT * FROM orders ORDER BY date ASC LIMIT "+loadLimit;
            }
        }

    }

    private ObservableList<ItemOrderModel> getItemData(String orderId){
        Connection con = AppDatabase.getInstance();

        String query = "";
        Statement statement = null;
        ObservableList<ItemOrderModel> newData= FXCollections.observableArrayList();
        try {
            statement = con.createStatement();

            query = "SELECT * FROM item_order WHERE order_id = "+orderId+"";


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
