import java.util.ArrayList;
import java.util.List;

class MenuItem {
    String name;
    float price;
    String category;

    public MenuItem(String name, float price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }
}

class Order {
    ArrayList<MenuItem> order;

    public Order() {
        this.order = ArrayList < MenuItem >;
    }

    void addOrder() {

    }

    void removeOrder() {

    }
}

class Customer {
    String name;
    String phone;
    ArrayList<Order> orderHistory;

    public Customer() {
        this.name = name;
        this.phone = phone;
        this.orderHistory = ArrayList <Order>;
    }

    void addorderHistory() {

    }
}

class ICafe {
    void addMenuItem(MenuItem item) {

    }

    void removeMenuItem() {

    }

    List<MenuItem> getMenuItems() {

    }

    Order createOrder(Customer customer, List<MenuItem> items) {

    }
}

public class CafeApp {
    public static void main(String[] args) {

    }
}