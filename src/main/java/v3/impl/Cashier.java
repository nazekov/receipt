package v3.impl;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import v3.Contact;
import v3.ICashier;
import v3.IShop;
import v3.IStock;
import v3.Item;
import v3.Receipt;
import v3.Util;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@NoArgsConstructor
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cashier implements ICashier {

    static Scanner sc = new Scanner(System.in);

    String name;

    IShop shop;

    IStock stock;

    Receipt receipt;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Receipt getReceipt() {
        return receipt;
    }

    @Override
    public IShop getShop() {
        return shop;
    }

    @Override
    public IStock getShopStock() {
        return stock;
    }

    @Override
    public Receipt createOrder() {
        Util.clearConsole();
        Contact contact = new Contact(
                "SUPERMARKET 123",
                "12, MILKYWAY Galaxy/ Earth",
                "123-456-7890");
        List<Item> itemList = chooseProducts();
        Order order = new Order(itemList);
        shop.increaseBalance(order.getTotal()); // пополняем баланс магазина

        Receipt receipt = Receipt.builder()
            .contact(contact)
            .cashier(this)
            .date(new Date())
            .order(order)
            .build();
        receipt.calculateSpacesForReceipt();
        return receipt;
    }

    private List<Item> chooseProducts() {
        List<Item> itemList = new ArrayList<>();
        boolean isChoose = true;
        while (isChoose) {
            System.out.println("Создание заказа: ");
            stock.showAllInfo();
            System.out.println("Выберите товар");
            System.out.print("Введите номер id товара: ");
            int id = Util.getNumber();
            System.out.print("Введите кол-во желаемых товаров: ");
            int count = Util.getNumber();

            Inventory inventory = (Inventory) stock.getInventory(id);

            if (inventory == null) {
                System.out.println("Такого товара нет в магазине.");
                isChoose = isContinueForChoose();
                continue;
            }

            if (count <= 0) {
                System.out.println("Неверный ввод данных");
                isChoose = isContinueForChoose();
                continue;
            }

            if (count > inventory.getCount()) {
                System.out.println("Товара в таком количестве нет. Продолжить ENTER");
                sc.nextLine();
                Util.clearConsole();
                continue;
            }
             // уменьшается число продуктов в хранилище магазина
            inventory.setCount(inventory.getCount() - count);

            Item itemFound = getItem(itemList, id);

            if (itemFound == null) {
                itemList.add(new Item(count, inventory.getProduct()));
            } else {
                itemFound.setCount(itemFound.getCount() + count);
            }

            isChoose = isContinueForChoose();
        }
        return itemList;
    }

    private Item getItem(List<Item> itemList, int id) {
        return itemList.stream()
                .filter(item -> item.getProduct().getId() == id)
                .findFirst()
                .orElse(null);
    }

    private boolean isContinueForChoose() {
        System.out.println("Продолжить выбор товара дальше? (ENTER - да / 0 - нет)");
        boolean rsl = !sc.nextLine().equals("0");
        Util.clearConsole();
        return rsl;
    }

    @Override
    public String toString() {
        return "CASHIER: " + name;
    }
}
