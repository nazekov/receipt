package v3;

import java.math.BigDecimal;
import java.util.List;

public interface IOrder {

    BigDecimal PERCENT_VAT = new BigDecimal(17);

    BigDecimal PERCENT_DISCOUNT = new BigDecimal(30);

    List<Item> getItemList();

    BigDecimal getTaxableTotal();

    BigDecimal getTotalDiscount();

    BigDecimal getVat();

    BigDecimal getTotal();

    void addItem(Item item);

    void showInfo();
}
