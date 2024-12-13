import java.util.*;

interface ICafe {
    void addMenuItem(MenuItem item);

    void removeMenuItem(String name);

    List<MenuItem> getMenuItems();

    Order createOrder(Customer customer, List<MenuItem> items);
}

class MenuItem {
    private String name;
    private double price;
    private String category;

    public MenuItem(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Название: " + name + ", Цена: " + price + ", Категория: " + category;
    }
}

class Customer {
    private int id;
    private String name;
    private String phone;

    public Customer(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Клиент ID: " + id + ", Имя: " + name + ", Телефон: " + phone;
    }
}

class Order {
    private Customer customer;
    private List<MenuItem> items;

    public Order(Customer customer, List<MenuItem> items) {
        this.customer = customer;
        this.items = items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Заказ для клиента: " + customer.getName() + "\nПозиции:\n");
        for (MenuItem item : items) {
            sb.append(item).append("\n");
        }
        return sb.toString();
    }
}

class Cafe implements ICafe {
    private List<MenuItem> menuItems;
    private Map<Integer, Customer> customers;

    public Cafe() {
        this.menuItems = new ArrayList<>();
        this.customers = new HashMap<>();
    }

    @Override
    public void addMenuItem(MenuItem item) {
        menuItems.add(item);
        System.out.println("Добавлена позиция меню: " + item.getName());
    }

    @Override
    public void removeMenuItem(String name) {
        boolean removed = menuItems.removeIf(item -> item.getName().equals(name));
        if (removed) {
            System.out.println("Позиция " + name + " удалена из меню.");
        } else {
            System.out.println("Позиция " + name + " не найдена в меню.");
        }
    }

    @Override
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    @Override
    public Order createOrder(Customer customer, List<MenuItem> items) {
        if (!customers.containsKey(customer.getId())) {
            customers.put(customer.getId(), customer);
            System.out.println("Клиент зарегистрирован: " + customer);
        }
        return new Order(customer, items);
    }

    public void displayCustomers() {
        System.out.println("\nСписок клиентов:");
        for (Customer customer : customers.values()) {
            System.out.println(customer);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cafe cafe = new Cafe();
        Map<Integer, Customer> customers = new HashMap<>();
        int customerIdCounter = 1;

        while (true) {
            System.out.println("\n--- Меню кафе ---");
            System.out.println("1. Добавить элемент в меню");
            System.out.println("2. Удалить элемент из меню");
            System.out.println("3. Просмотреть меню");
            System.out.println("4. Создать заказ");
            System.out.println("5. Просмотреть историю заказов клиента");
            System.out.println("6. Выйти");

            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Очищаем ввод

            switch (choice) {

                case 1:
                    try {
                        System.out.print("Введите название: ");
                        String name = scanner.nextLine();

                        System.out.print("Введите цену: ");
                        String priceInput = scanner.nextLine();

                        priceInput = priceInput.replace(",", ".");
                        double price = Double.parseDouble(priceInput);

                        System.out.print("Введите категорию: ");
                        String category = scanner.nextLine();

                        cafe.addMenuItem(new MenuItem(name, price, category));
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: цена должна быть числом. Попробуйте снова.");
                    }
                    break;

                case 2:

                    System.out.print("Введите название элемента, который нужно удалить: ");
                    String itemNameToRemove = scanner.nextLine();
                    cafe.removeMenuItem(itemNameToRemove);
                    break;

                case 3:

                    System.out.println("\n--- Меню ---");
                    List<MenuItem> menu = cafe.getMenuItems();
                    if (menu.isEmpty()) {
                        System.out.println("Меню пустое.");
                    } else {
                        for (MenuItem item : menu) {
                            System.out.println(item);
                        }
                    }
                    break;

                case 4:
                    System.out.print("Введите имя клиента: ");
                    String customerName = scanner.nextLine();
                    System.out.print("Введите телефон клиента: ");
                    String customerPhone = scanner.nextLine();

                    Customer customer = customers.getOrDefault(customerIdCounter,
                            new Customer(customerIdCounter, customerName, customerPhone));
                    if (!customers.containsKey(customerIdCounter)) {
                        customers.put(customerIdCounter++, customer);
                    }

                    List<MenuItem> orderItems = new ArrayList<>();
                    while (true) {
                        System.out.print("Введите название элемента из меню для добавления в заказ (или 'стоп' для завершения): ");
                        String itemName = scanner.nextLine();
                        if (itemName.equalsIgnoreCase("стоп")) {
                            break;
                        }
                        Optional<MenuItem> menuItem = cafe.getMenuItems().stream()
                                .filter(item -> item.getName().equalsIgnoreCase(itemName))
                                .findFirst();
                        if (menuItem.isPresent()) {
                            orderItems.add(menuItem.get());
                            System.out.println("Добавлено в заказ: " + itemName);
                        } else {
                            System.out.println("Элемент с названием " + itemName + " не найден в меню.");
                        }
                    }

                    if (!orderItems.isEmpty()) {
                        Order order = cafe.createOrder(customer, orderItems);
                        System.out.println("Заказ успешно создан:\n" + order);
                    } else {
                        System.out.println("Заказ не был создан, так как не выбраны элементы.");
                    }
                    break;

                case 5:
                    System.out.print("Введите ID клиента: ");
                    int customerId = scanner.nextInt();
                    scanner.nextLine();
                    Customer selectedCustomer = customers.get(customerId);
                    if (selectedCustomer != null) {
                        System.out.println("\n--- История заказов клиента: " + selectedCustomer.getName() + " ---");
                        cafe.displayCustomers();
                    } else {
                        System.out.println("Клиент с ID " + customerId + " не найден.");
                    }
                    break;

                case 6:
                    System.out.println("Выход из приложения...");
                    return;

                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }
}

