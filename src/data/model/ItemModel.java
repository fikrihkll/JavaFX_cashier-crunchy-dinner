package data.model;

public class ItemModel {

    public ItemModel(String id, String name, int price, String pict, String category, String type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.pict = pict;
        this.category = category;
        this.type = type;
    }

    private String id;
    private String name;
    private int price;
    private String pict;
    private String category;
    private String type;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

