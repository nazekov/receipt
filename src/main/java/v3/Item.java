package v3;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item {

    public static int MAX_COUNT_LENGTH;

    public static int MAX_DESCRIPTION_LENGTH;

    public static int MAX_PRICE_LENGTH;

    public static int MAX_DISCOUNT_LENGTH;

    public static int MAX_TOTAL_LENGTH;

    public static final String QTY_STR = "QTY";

    public static final String DESCRIPTION_STR = "DESCRIPTION";

    public static final String PRICE_STR = "PRICE";

    public static final String DISCOUNT_STR = "DISCOUNT";

    public static final String TOTAL_STR = "TOTAL";

    public static final String CURRENCY_SYMBOL = "$";

    public static final String BETWEEN_BLANK = "  ";

    int count;

    Product product;

    BigDecimal discount;

    BigDecimal total;

    public Item(int count,
                Product product) {
        this.count = count;
        this.product = product;
    }

    public void showInfo(String middleSpace) {
        String item = String.format("%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s",
            Receipt.BORDER_LINE,
            Receipt.BORDER_BLANK,
            count,
            Util.getCalcSpaceToMaxElem(MAX_COUNT_LENGTH, String.valueOf(count)),
            BETWEEN_BLANK,

            product.getName(),
            Util.getCalcSpaceToMaxElem(MAX_DESCRIPTION_LENGTH, product.getName()),

            middleSpace,

            Util.getCalcSpaceToMaxElem(MAX_PRICE_LENGTH, Item.CURRENCY_SYMBOL + product.getPrice().toString()),
            Item.CURRENCY_SYMBOL,
            product.getPrice().toString(),
            BETWEEN_BLANK,

            Util.getCalcSpaceToMaxElem(MAX_DISCOUNT_LENGTH, Item.CURRENCY_SYMBOL + discount.toString()),
            Item.CURRENCY_SYMBOL,
            discount.toString(),
            BETWEEN_BLANK,

            Util.getCalcSpaceToMaxElem(MAX_TOTAL_LENGTH, Item.CURRENCY_SYMBOL + total.toString()),
            Item.CURRENCY_SYMBOL,
            total.toString(),

            Receipt.BORDER_BLANK,
            Receipt.BORDER_LINE
        );
        System.out.println(item);
    }
}
