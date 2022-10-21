package data.model;

public class OrderModel {

    private String orderId;
    private String date;
    private String buyer;
    private int total;
    private String cashierName;
    private String employeeId;

    public OrderModel(String orderId, String date, String buyer, int total, String cashierName, String employeeId) {
        this.orderId = orderId;
        this.date = date;
        this.buyer = buyer;
        this.total = total;
        this.cashierName = cashierName;
        this.employeeId = employeeId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
