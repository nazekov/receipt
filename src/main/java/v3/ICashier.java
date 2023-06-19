package v3;

public interface ICashier {

    String getName();

    Receipt getReceipt();

    IShop getShop();

    IStock getShopStock();

    Receipt createOrder();
}
