package co.example.meatshop.activities.consumer.models;

/**
 * Created by Ussama Iftikhar on 23-Mar-2021.
 * Email iusama46@gmail.com
 * Email iusama466@gmail.com
 * Github https://github.com/iusama46
 */
public class Promotion {
    String id;
    String title;
    String date;
    int img;
    int index;
    String cat;
    int days;

    public Promotion(String id, String title, String date, int img, int index, String cat) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.img = img;
        this.index = index;
        this.cat = cat;
    }

    public Promotion(String title, int img, String cat, int days, int index) {
        this.title = title;
        this.index = index;
        this.img = img;
        this.cat = cat;
        this.days = days;
    }

    public Promotion() {
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }
}
