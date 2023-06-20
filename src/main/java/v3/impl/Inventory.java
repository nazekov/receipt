package v3.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import v3.IInventory;
import v3.Product;
import v3.Util;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Inventory implements IInventory {

    public static int maxSpace;

    Product product;

    int count;

    @Override
    public Product getProduct() {
        return product;
    }

    @Override
    public int getCount() {
        return count;
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
