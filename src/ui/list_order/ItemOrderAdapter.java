package ui.list_order;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import data.model.ItemOrderModel;

public class ItemOrderAdapter extends ListCell<ItemOrderModel> {

    @FXML
    private AnchorPane root;

    @FXML
    private Label tv_name;

    @FXML
    private Label tv_qty;

    @FXML
    private Label tv_price;

    private FXMLLoader loader=null;

    @Override
    protected void updateItem(ItemOrderModel item, boolean empty) {
        super.updateItem(item, empty);

        if(item==null || empty){
            setText(null);
            setGraphic(null);
        }else{
            if(loader==null){
                try {
                    loader = new FXMLLoader();
                    loader.setLocation(getClass().getClassLoader().getResource("ui/list_order/order_item.fxml"));
                    loader.setController(this);
                    loader.load();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            tv_name.setText(item.getName());
            tv_qty.setText(Integer.toString(item.getQty()));
            tv_price.setText("Rp."+item.getPrice());


            setText(null);
            setGraphic(root);
        }

    }

}
