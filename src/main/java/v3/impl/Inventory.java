package v3.impl;

import v3.IInventory;
import v3.Product;
import v3.Util;

public class Inventory implements IInventory {

    private Product product;

    private int count;

    public static int maxSpace;

    public Inventory() {
    }

    public Inventory(Product product, int count) {
        this.product = product;
        this.count = count;
    }

    @Override
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void showInfo() {
        String productInfo = String.format("ID:%d, Товар: %s,%s Цена: %s, %sСкидка в %%: %s ",
            product.getId(),
            product.getName(),
            Util.getCalcSpaceToMaxElem(Product.maxSpace2, product.getName()),
            product.getPrice().toString(),
            Util.getCalcSpaceToMaxElem(Product.maxSpace3, product.getPrice().toString()),
            product.getPercentDiscount()
        );

        String inventoryInfo = String.format("%s%sКоличество: %d",
            productInfo,
            Util.getCalcSpaceToMaxElem(maxSpace, product.getPercentDiscount().toString()),
            count
        );

        System.out.println(inventoryInfo);
    }
}
