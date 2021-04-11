package co.example.meatshop.activities.admin.models;

/**
 * Created by Ussama Iftikhar on 15-Mar-2021.
 * Email iusama46@gmail.com
 * Email iusama466@gmail.com
 * Github https://github.com/iusama46
 */
public class Home {
    String itemName;
    int imageId;

    public Home(String itemName, int imageId) {
        this.itemName = itemName;
        this.imageId = imageId;
    }

    public Home() {
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
