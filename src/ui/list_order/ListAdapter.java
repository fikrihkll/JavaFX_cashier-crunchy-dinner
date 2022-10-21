package ui.list_order;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import data.model.OrderModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ListAdapter extends ListCell<OrderModel> {

    @FXML
    private AnchorPane root;

    @FXML
    private Label tv_id;

    @FXML
    private Label tv_buyer;

    @FXML
    private Label tv_total;

    @FXML
    private Label tv_cashier;

    @FXML
    private Label tv_date;


    @FXML
    private Label tv_cashier_id;

    private FXMLLoader loader=null;

    @Override
    protected void updateItem(OrderModel item, boolean empty) {
        super.updateItem(item, empty);

        if(item==null &&  empty){
            setText(null);
            setGraphic(null);
        }else{
            if(loader==null){
                try {
                    loader = new FXMLLoader();
                    loader.setLocation(getClass().getClassLoader().getResource("ui/list_order/list_order_item.fxml"));
                    loader.setController(this);
                    loader.load();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            tv_id.setText(item.getOrderId());
            tv_buyer.setText(item.getBuyer());
            tv_total.setText("Rp."+item.getTotal());
            tv_date.setText(item.getDate());
            tv_cashier.setText(item.getCashierName());
            tv_cashier_id.setText(item.getEmployeeId());


            setText(null);
            setGraphic(root);
        }

    }
}
