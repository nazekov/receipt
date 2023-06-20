package v3.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import v3.ICashier;
import v3.IShop;
import v3.IStock;
import v3.Receipt;
import v3.Util;
import java.math.BigDecimal;
import java.util.Scanner;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Shop implements IShop {

    static Scanner sc = new Scanner(System.in);

    BigDecimal balance;

    IStock stock;

    ICashier cashier;

    boolean isWork;

    @Override
    public ICashier getICashier() {
        return cashier;
    }

    @Override
    public void increaseBalance(BigDecimal money) {
        balance = balance.add(money);
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public IStock getIStock() {
        return stock;
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
}
