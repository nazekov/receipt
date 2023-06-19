package v3;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Receipt {

    private static final String name = "CASH RECEIPT";

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

    public static final int WIDTH = 50; // ширина чека постоянное значение

    public static final String BORDER_LINE = "|";

    public static final String BORDER_BLANK = " ";

    private Contact contact;

    private ICashier cashier;

    private Date date;

    private IOrder order;

    public Contact getContact() {
        return contact;
    }

    public ICashier getCashier() {
        return cashier;
    }

    public Date getDate() {
        return date;
    }

    public IOrder getOrder() {
        return order;
    }

    public void calculateSpacesForReceipt() {
        List<Item> itemList = order.getItemList();
        calculateMaxCountLength(itemList);
        calculateMaxDescriptionLength(itemList);
        calculateMaxPriceLength(itemList);
        calculateMaxDiscountLength(itemList);
        calculateMaxTotalLength(itemList);
    }

    private void calculateMaxTotalLength(List<Item> itemList) {
        Item.MAX_TOTAL_LENGTH = itemList.stream()
                .mapToInt(item -> item.getTotal().toString().length() + Item.CURRENCY_SYMBOL.length())
                .max()
                .getAsInt();
        Item.MAX_TOTAL_LENGTH = Math.max(Item.TOTAL_STR.length(), Item.MAX_TOTAL_LENGTH);
    }

    private void calculateMaxDiscountLength(List<Item> itemList) {
        Item.MAX_DISCOUNT_LENGTH = itemList.stream()
                .mapToInt(item -> item.getDiscount().toString().length() + Item.CURRENCY_SYMBOL.length())
                .max()
                .getAsInt();
        Item.MAX_DISCOUNT_LENGTH = Math.max(Item.DISCOUNT_STR.length(), Item.MAX_DISCOUNT_LENGTH);
    }

    private void calculateMaxPriceLength(List<Item> itemList) {
        Item.MAX_PRICE_LENGTH = itemList.stream()
                .mapToInt(item -> item.getProduct().getPrice().toString().length() + Item.CURRENCY_SYMBOL.length())
                .max()
                .getAsInt();
        Item.MAX_PRICE_LENGTH = Math.max(Item.PRICE_STR.length(), Item.MAX_PRICE_LENGTH);
    }

    private void calculateMaxDescriptionLength(List<Item> itemList) {
        Item.MAX_DESCRIPTION_LENGTH = itemList.stream()
                .mapToInt(item -> item.getProduct().getName().length())
                .max()
                .getAsInt();
        Item.MAX_DESCRIPTION_LENGTH = Math.max(Item.DESCRIPTION_STR.length(), Item.MAX_DESCRIPTION_LENGTH);
    }

    private void calculateMaxCountLength(List<Item> itemList) {
        Item.MAX_COUNT_LENGTH = itemList.stream()
                .mapToInt(item -> String.valueOf(item.getCount()).length())
                .max()
                .getAsInt();
        Item.MAX_COUNT_LENGTH = Math.max(Item.QTY_STR.length(), Item.MAX_COUNT_LENGTH);
    }

    public void showInfo() {
        String topAndDownBorder = getViewTopDown();
        String dateStr = "DATE: " + DATE_FORMAT.format(date);
        String timeStr = "TIME: " + TIME_FORMAT.format(date);
        String cashierDateStr = getViewStrTwoPosition(cashier.toString(), dateStr);

        System.out.println(topAndDownBorder + "\n"
                            + getViewOneStr(name) + "\n"
                            + getViewOneStr(contact.getName()) + "\n"
                            + getViewOneStr(contact.getAddress()) + "\n"
                            + getViewOneStr("Tel :" + contact.getPhone()) + "\n"
                            + getViewEmptySpace() + "\n"
                            + cashierDateStr + "\n"
                            + getViewStrTime(cashierDateStr, timeStr) + "\n"
                            + getViewOneLine('-')
        );
        order.showInfo();
    }

    public static String getViewEmptySpace() {
        int count = WIDTH - 2 * BORDER_LINE.length() - 2 * BORDER_BLANK.length();
        return String.format("%s%s%s%s%s", BORDER_LINE,
                                            BORDER_BLANK,
                                            Util.getCalcSpace(count, ' '),
                                            BORDER_BLANK,
                                            BORDER_LINE);
    }

    public static String getViewOneLine(char sym) {
        int count = WIDTH - 2 * BORDER_LINE.length() - 2 * BORDER_BLANK.length();
        String calcSpaceLine = Util.getCalcSpace(count, sym);
        return String.format("%s%s%s%s%s", BORDER_LINE,
                                            BORDER_BLANK,
                                            calcSpaceLine,
                                            BORDER_BLANK,
                                            BORDER_LINE);
    }

    public static String getViewTopDown() {
        return Util.getCalcSpace(WIDTH, '_');
    }

    private static String getViewStrTime(String cashierDateStr, String timeStr) {
        int index = cashierDateStr.indexOf("D");
        int count = index - BORDER_LINE.length() - BORDER_BLANK.length() ;
        String calcSpaceToTimeStr = Util.getCalcSpace(count, ' ');
        count = WIDTH - timeStr.length() - count - 2 * BORDER_BLANK.length() - 2 * BORDER_BLANK.length();
        String calcSpaceAfterTimeStr = Util.getCalcSpace(count, ' ');
        return String.format("%s%s%s%s%s%s%s", BORDER_LINE,
                                        BORDER_BLANK,
                                        calcSpaceToTimeStr,
                                        timeStr,
                                        calcSpaceAfterTimeStr,
                                        BORDER_BLANK,
                                        BORDER_LINE);
    }

    public static String getViewStrTwoPosition(String str1, String str2) {
        int count = WIDTH - 2 * BORDER_LINE.length()
                            - 2 * BORDER_BLANK.length()
                            - str1.length()
                            - str2.length();
        String middleBlanks = Util.getCalcSpace(count, ' ');
        return String.format("%s%s%s%s%s%s%s", BORDER_LINE, BORDER_BLANK,
                                        str1, middleBlanks, str2,
                                        BORDER_BLANK, BORDER_LINE);
    }

    private static String getViewOneStr(String elem) {
        int count = (WIDTH - 2 * BORDER_LINE.length() - 2 * BORDER_BLANK.length() - elem.length()) / 2;
        String leftBlanksFromName = Util.getCalcSpace(count, ' ');
        String str = String.format("%s%s%s%s", BORDER_LINE, BORDER_BLANK, leftBlanksFromName, elem);
        count = WIDTH - BORDER_LINE.length() - BORDER_BLANK.length() - str.length();
        String rightBlanksFromName = Util.getCalcSpace(count, ' ');
        return String.format("%s%s%s%s", str, rightBlanksFromName, BORDER_BLANK, BORDER_LINE);
    }

    public static class Builder {

        private final Receipt receipt;

        public Builder() {
            receipt = new Receipt();
        }

        public Builder contact(Contact contact) {
            receipt.contact = contact;
            return this;
        }

        public Builder cashier(ICashier cashier) {
            receipt.cashier = cashier;
            return this;
        }

        public Builder date(Date date) {
            receipt.date = date;
            return this;
        }

        public Builder order(IOrder order) {
            receipt.order = order;
            return this;
        }

        public Receipt build() {
            return receipt;
        }
    }
}
