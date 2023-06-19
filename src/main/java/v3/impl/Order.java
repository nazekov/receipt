package v3.impl;

import v3.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Order implements IOrder {

    private List<Item> itemList;

    private BigDecimal taxableTotal;

    private BigDecimal totalDiscount;

    private BigDecimal vat;

    private BigDecimal total;

    private static Scanner sc;

    public Order() {
        sc = new Scanner(System.in);
    }

    public Order(List<Item> itemList) {
        sc = new Scanner(System.in);
        this.itemList = itemList;
        calculateTotalPriceForEveryItem();
        this.taxableTotal = calculateTaxableTotal();
        this.vat = calculateVat();
        this.total = taxableTotal.add(vat).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal calculateVat() {
        BigDecimal unit = IOrder.PERCENT_VAT.divide(new BigDecimal(100));
        return taxableTotal.multiply(unit)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal calculateTaxableTotal() {
        BigDecimal taxableTotal = new BigDecimal(0);
        List<BigDecimal> collectTotalList = itemList.stream()
                .map(Item::getTotal)
                .collect(Collectors.toList());
        for (BigDecimal total : collectTotalList) {
            taxableTotal = taxableTotal.add(total);
        }

        System.out.println("Есть ли у вас скидочная карта: 1 - Да / Любая клав. - Нет: ");
        if (sc.nextLine().equals("1")) {
            BigDecimal unit = IOrder.PERCENT_DISCOUNT.divide(new BigDecimal(100));
            unit = new BigDecimal(1).subtract(unit);
            taxableTotal = taxableTotal.multiply(unit);
        }

        return taxableTotal;
    }

    @Override
    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public BigDecimal getTaxableTotal() {
        return taxableTotal;
    }

    public void setTaxableTotal(BigDecimal taxableTotal) {
        this.taxableTotal = taxableTotal;
    }

    @Override
    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    @Override
    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    @Override
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public void addItem(Item item) {
        itemList.add(item);
    }

    @Override
    public void showInfo() {
        String left = String.format("%s%s%s%s%s%s%s",
            Receipt.BORDER_LINE,
            Receipt.BORDER_BLANK,
            Item.QTY_STR,
            Util.getCalcSpaceToMaxElem(Item.MAX_COUNT_LENGTH, Item.QTY_STR),
            Item.BETWEEN_BLANK,
            Item.DESCRIPTION_STR,
            Util.getCalcSpaceToMaxElem(Item.MAX_DESCRIPTION_LENGTH, Item.DESCRIPTION_STR)
        );
        String right = String.format("%s%s%s%s%s%s%s%s%s%s",
            Util.getCalcSpaceToMaxElem(Item.MAX_PRICE_LENGTH, Item.PRICE_STR),
            Item.PRICE_STR,
            Item.BETWEEN_BLANK,
            Util.getCalcSpaceToMaxElem(Item.MAX_DISCOUNT_LENGTH, Item.DISCOUNT_STR),
            Item.DISCOUNT_STR,
            Item.BETWEEN_BLANK,
            Util.getCalcSpaceToMaxElem(Item.MAX_TOTAL_LENGTH, Item.TOTAL_STR),
            Item.TOTAL_STR,
            Receipt.BORDER_BLANK,
            Receipt.BORDER_LINE
        );
        String middleSpace = Util.getCalcSpace(Receipt.WIDTH - left.length() - right.length(), ' ');
        String headTable = String.format("%s%s%s", left, middleSpace, right);
        String viewEmptySpace = Receipt.getViewEmptySpace();
        System.out.println(viewEmptySpace + "\n"
                            + headTable + "\n"
                            + viewEmptySpace);
        itemList.forEach(item -> item.showInfo(middleSpace));

        String totalStr = String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s",
            viewEmptySpace,
            Receipt.getViewOneLine('='),
            viewEmptySpace,
            Receipt.getViewStrTwoPosition("TAXABLE TOT.", Item.CURRENCY_SYMBOL + taxableTotal.toString()),
            Receipt.getViewStrTwoPosition("VAT17%", Item.CURRENCY_SYMBOL + vat.toString()),
            Receipt.getViewStrTwoPosition("TOTAL", Item.CURRENCY_SYMBOL + total.toString()),
            Receipt.getViewTopDown()
        );
        System.out.println(totalStr);
    }

    private void calculateTotalPriceForEveryItem() {
        for (Item item : itemList) {
            Product product = item.getProduct();
            BigDecimal price = product.getPrice();
            BigDecimal percentDiscount = product.getPercentDiscount();

            BigDecimal sum = price.multiply(new BigDecimal(item.getCount()));
            BigDecimal discount = new BigDecimal(0);

            if (isDiscount(item.getCount(), percentDiscount)) {
                BigDecimal unit = percentDiscount.divide(new BigDecimal(100));
                discount = sum.multiply(unit).setScale(2, BigDecimal.ROUND_HALF_UP);
            }

            BigDecimal totalSum = sum.subtract(discount)
                    .setScale(2, BigDecimal.ROUND_HALF_UP);

            item.setDiscount(discount);
            item.setTotal(totalSum);
        }
    }

    private boolean isDiscount(int count, BigDecimal percentDiscount) {
        return count >= Product.COUNT_FOR_DISCOUNT && containsDiscount(percentDiscount);
    }

    private boolean containsDiscount(BigDecimal percentDiscount) {
        return percentDiscount.compareTo(BigDecimal.valueOf(0)) != 0;
    }
}
