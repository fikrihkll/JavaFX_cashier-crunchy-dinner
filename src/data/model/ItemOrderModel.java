package data.model;

public class ItemOrderModel {

    private String itemOrderId;
    private String orderId;
    private String id;
    private String name;
    private int price;
    private String pict;
    private int qty;

    public ItemOrderModel(String itemOrderId, String orderId, String id, String name, int price, String pict, int qty) {
        this.itemOrderId = itemOrderId;
        this.orderId = orderId;
        this.id = id;
        this.name = name;
        this.price = price;
        this.pict = pict;
        this.qty = qty;
    }

    public String getItemOrderId() {
        return itemOrderId;
    }

    public void setItemOrderId(String itemOrderId) {
        this.itemOrderId = itemOrderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPict() {
        return pict;
    }

    public void setPict(String pict) {
        this.pict = pict;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
