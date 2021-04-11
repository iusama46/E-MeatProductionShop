package co.example.meatshop.activities.consumer.models;

/**
 * Created by Ussama Iftikhar on 22-Mar-2021.
 * Email iusama46@gmail.com
 * Email iusama466@gmail.com
 * Github https://github.com/iusama46
 */
public class Cart {
    String id;
    String pId;
    String name;
    String qty;
    String img;
    String price;

    public Cart(String id, String pId, String name, String qty, String price) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    public Cart() {
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
