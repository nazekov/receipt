package v3.impl;

import v3.*;

import java.math.BigDecimal;
import java.util.Scanner;

public class Shop implements IShop {

    private BigDecimal balance;

    private IStock stock;

    private ICashier cashier;

    private boolean isWork;

    public static Scanner sc;

    public Shop() {
        stock = new Stock();
        balance = new BigDecimal(0);
        cashier = new Cashier(this);
        isWork = true;
        sc = new Scanner(System.in);
        initialization();
    }

    @Override
    public ICashier getICashier() {
        return cashier;
    }

    @Override
    public void increaseBalance(BigDecimal money) {
        balance = balance.add(money);
    }

    public void setCashier(ICashier cashier) {
        this.cashier = cashier;
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public IStock getIStock() {
        return stock;
    }

    public void setStock(IStock stock) {
        this.stock = stock;
    }

    @Override
    public void run() {
        Util.clearConsole();
        while (isWork) {
            int cmd = getCmd();
            if (cmd < 1 || cmd > 3) {
                System.out.println("Такой команды нет" +
                                    "\nЧтобы продолжить нажмите ENTER");
                sc.nextLine();
                Util.clearConsole();
                continue;
            }

            if (cmd == 1) {
                stock.showAllInfo();
                System.out.println("Баланс: " + balance);
            } else if (cmd == 2) {
                Receipt receipt = cashier.createOrder();
                System.out.println("\nВаш чек:\n");
                receipt.showInfo();
            } else {
                finish();
            }
            System.out.println("\nЧтобы продолжить нажмите ENTER ...");
            sc.nextLine();
            Util.clearConsole();
        }
    }

    private void finish() {
        isWork = false;
        System.out.println("Конец работы програмы");
    }

    private int getCmd() {
        System.out.println("Магазин:\n" +
                "1 - Посмотреть все товары \n" +
                "2 - Сделать заказ \n" +
                "3 - Выйти\n" +
                "Введите команду: ");
        return Util.getNumber();
    }

    private void initialization() {
        // хранилище товаров в магазине
        Inventory inventory1 = new Inventory(
            new Product(1, "lorem", new BigDecimal(2), new BigDecimal(0)), 10
        );
        Inventory inventory2 = new Inventory(
            new Product(2, "ipsum", new BigDecimal(3), new BigDecimal(0)), 10
        );
        Inventory inventory3 = new Inventory(
            new Product(3, "dolor", new BigDecimal(7), new BigDecimal(10)), 10
        );
        Inventory inventory4 = new Inventory(
            new Product(4, "sit", new BigDecimal("5.5"), new BigDecimal(10)), 10
        );
        Inventory inventory5 = new Inventory(
            new Product(5, "amet", new BigDecimal("8.35"), new BigDecimal(10)), 10
        );
        Inventory inventory6 = new Inventory(
            new Product(6, "consectetur", new BigDecimal("1.35"), new BigDecimal(10)), 10
        );
        Inventory inventory7 = new Inventory(
            new Product(7, "adipisicing", new BigDecimal("2.45"), new BigDecimal(0)), 10
        );
        Inventory inventory8 = new Inventory(
            new Product(8, "elit", new BigDecimal("33.44"), new BigDecimal(0)), 10
        );
        Inventory inventory9 = new Inventory(
            new Product(9, "quas", new BigDecimal("24.67"), new BigDecimal(0)), 10
        );
        Inventory inventory10 = new Inventory(
            new Product(10, "commodi", new BigDecimal("12.33"), new BigDecimal(0)), 10
        );

        stock.addInventory(inventory1);
        stock.addInventory(inventory2);
        stock.addInventory(inventory3);
        stock.addInventory(inventory4);
        stock.addInventory(inventory5);
        stock.addInventory(inventory6);
        stock.addInventory(inventory7);
        stock.addInventory(inventory8);
        stock.addInventory(inventory9);
        stock.addInventory(inventory10);

        Product.maxSpace2 = getMaxNameLength();
        Product.maxSpace3 = getMaxPriceLength();
        Inventory.maxSpace = getMaxDiscountLength();
    }

    private int getMaxDiscountLength() {
        return stock.getInventoryItemList().stream()
                .mapToInt(value -> value.getProduct().getPercentDiscount().toString().length())
                .max()
                .getAsInt();
    }

    private int getMaxPriceLength() {
        return stock.getInventoryItemList()
                .stream()
                .mapToInt(value -> value.getProduct().getPrice().toString().length())
                .max()
                .getAsInt();
    }

    private int getMaxNameLength() {
        return stock.getInventoryItemList()
                    .stream()
                    .mapToInt(value -> value.getProduct().getName().length())
                    .max()
                    .getAsInt();
    }
}
