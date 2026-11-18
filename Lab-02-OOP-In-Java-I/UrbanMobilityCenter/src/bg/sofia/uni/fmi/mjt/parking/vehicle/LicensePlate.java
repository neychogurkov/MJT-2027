package bg.sofia.uni.fmi.mjt.parking.vehicle;

import java.util.Locale;

public class LicensePlate {
    private final String number;

    private LicensePlate(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return number;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        LicensePlate other = (LicensePlate) obj;

        return number.equals(other.number);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    public static LicensePlate parse(String text) {
        if (text == null) {
            return null;
        }

        text = text.replace(" ", "").replace("-", "").toUpperCase(Locale.ROOT);

        text = cyrillicToLatin(text);

        if (!isValidNumber(text)) {
            return null;
        }

        return new LicensePlate(text);
    }

    private static String cyrillicToLatin(String text) {
        String result = "";

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);

            char latin = switch (currentChar) {
                case 'А' -> 'A';
                case 'В' -> 'B';
                case 'Е' -> 'E';
                case 'К' -> 'K';
                case 'М' -> 'M';
                case 'Н' -> 'H';
                case 'О' -> 'O';
                case 'Р' -> 'P';
                case 'С' -> 'C';
                case 'Т' -> 'T';
                case 'У' -> 'Y';
                case 'Х' -> 'X';
                default -> currentChar;
            };

            result += latin;
        }

        return result;
    }

    private static boolean isValidNumber(String text) {
        int length = text.length();

        if (length < 6 || length > 8) {
            return false;
        }

        int i = 0;

        while (i < length && text.charAt(i) >= 'A' && text.charAt(i) <= 'Z') {
            i++;
        }

        int prefixLength = i;

        if (prefixLength < 1 || prefixLength > 2) {
            return false;
        }

        int digitsStart = i;

        while (i < length && text.charAt(i) >= '0' && text.charAt(i) <= '9') {
            i++;
        }

        if (i - digitsStart != 4) {
            return false;
        }

        int suffixStart = i;

        while (i < length && text.charAt(i) >= 'A' && text.charAt(i) <= 'Z') {
            i++;
        }

        int suffixLength = i - suffixStart;

        return i == length && suffixLength >= 1 && suffixLength <= 2;
    }
}
