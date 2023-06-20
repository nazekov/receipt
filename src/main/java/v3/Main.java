package v3;

import v3.impl.Cashier;
import v3.impl.Inventory;
import v3.impl.Shop;
import v3.impl.Stock;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Stock stock = new Stock();
        stock.setInventoryItemList(new ArrayList<>());
        initialization(stock);

        Cashier cashier = new Cashier();
        IShop shop = new Shop(new BigDecimal(0), stock, cashier, true);
        cashier.setName("E1520");
        cashier.setShop(shop);
        cashier.setStock(stock);

        shop.run();
    }

    private static void initialization(IStock stock) {
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

        Product.maxSpace2 = getMaxNameLength(stock);
        Product.maxSpace3 = getMaxPriceLength(stock);
        Inventory.maxSpace = getMaxDiscountLength(stock);
    }

    private static int getMaxDiscountLength(IStock stock) {
        return stock.getInventoryItemList().stream()
                .mapToInt(value -> value.getProduct().getPercentDiscount().toString().length())
                .max()
                .getAsInt();
    }

    private static int getMaxPriceLength(IStock stock) {
        return stock.getInventoryItemList()
                .stream()
                .mapToInt(value -> value.getProduct().getPrice().toString().length())
                .max()
                .getAsInt();
    }

    private static int getMaxNameLength(IStock stock) {
        return stock.getInventoryItemList()
                .stream()
                .mapToInt(value -> value.getProduct().getName().length())
                .max()
                .getAsInt();
    }
}
