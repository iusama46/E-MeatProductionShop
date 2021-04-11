package co.example.meatshop.activities.admin.models;

/**
 * Created by Ussama Iftikhar on 19-Mar-2021.
 * Email iusama46@gmail.com
 * Email iusama466@gmail.com
 * Github https://github.com/iusama46
 */
public class Product {
    String id;
    String pName;
    int category;
    int type;
    String pDescription;
    String pImg;
    String price;
    String expiryDate;
    String stock;
    String qty;

    public Product(String pName, int category, int type, String pDescription, String pImg, String expiryDate, String stock, String qty) {
        this.pName = pName;
        this.category = category;
        this.type = type;
        this.pDescription = pDescription;
        this.pImg = pImg;
        this.expiryDate = expiryDate;
        this.stock = stock;
        this.qty = qty;
    }

    public Product(String id, String pName, int category, int type, String pDescription, String pImg, String expiryDate, String stock, String qty) {
        this.id = id;
        this.pName = pName;
        this.category = category;
        this.type = type;
        this.pDescription = pDescription;
        this.pImg = pImg;
        this.expiryDate = expiryDate;
        this.stock = stock;
        this.qty = qty;
    }

    public Product() {
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }

    public String getpImg() {
        return pImg;
    }

    public void setpImg(String pImg) {
        this.pImg = pImg;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
