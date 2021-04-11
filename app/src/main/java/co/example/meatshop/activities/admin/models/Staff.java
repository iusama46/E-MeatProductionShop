package co.example.meatshop.activities.admin.models;

/**
 * Created by Ussama Iftikhar on 27-Mar-2021.
 * Email iusama46@gmail.com
 * Email iusama466@gmail.com
 * Github https://github.com/iusama46
 */
public class Staff {
    String id;
    String name;
    String location;
    String role;

    public Staff(String id, String name, String location, String role) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.role = role;
    }

    public Staff() {

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
