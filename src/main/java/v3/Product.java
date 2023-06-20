package v3;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    public static int COUNT_FOR_DISCOUNT = 5;

    public static int maxSpace2;

    public static int maxSpace3;

    int id;

    String name;

    BigDecimal price;

    BigDecimal percentDiscount;
}
