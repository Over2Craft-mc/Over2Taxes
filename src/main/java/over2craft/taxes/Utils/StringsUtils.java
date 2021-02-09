package over2craft.taxes.Utils;

public class StringsUtils {
    public static String format(String str, Object... args) {
        return String.format("[Over2Taxes] " + str, args);
    }

    public static String formatNumber(double number) {
        return String.format("%,d", (int) number).replace(',', ' ');
    }

}
