package v3;

import java.io.IOException;
import java.util.Scanner;

public class Util {

    private static final Scanner sc = new Scanner(System.in);

    public static int getNumber() {
        boolean isNotNumber = true;
        int result = 0;
        while (isNotNumber) {
            try {
                result = Integer.parseInt(sc.nextLine());
                isNotNumber = false;
            } catch (NumberFormatException e) {
                System.out.println("Введите число");
            }
        }
        return result;
    }

    public static void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCalcSpaceToMaxElem(int maxSpace, String fromElem) {
        int calcSpace = maxSpace - fromElem.length();
        return calcSpace <= 0 ? "" : String.format("%" + calcSpace + "s", "");
    }

    public static String getCalcSpace(int count, char symbol) {
        return String.format("%" + count + "s", "").replace(' ', symbol);
    }
}
