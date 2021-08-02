package br.ufrn.imd.ecommerce.validators;

public class ProductValidator {

    public static final int MAX_NAME_SIZE = 50;

    public static final int MAX_DESCRIPTION_SIZE = 350;

    public static boolean isValidName(String name) {
       if (!hasJustNumbers(name)){
           return !(name.length() > MAX_NAME_SIZE);
       }
       return false;
    }

    public static boolean isValidDescription(String description) {
        if (!hasJustNumbers(description)){
            return !(description.length() > MAX_DESCRIPTION_SIZE);
        }
        return false;
    }

    public static boolean isValidPrice(double price) {
        return price > 0;
    }

    public static boolean isValidImgLink(String imgLink) {
        return !hasJustNumbers(imgLink);
    }

    public static boolean hasJustNumbers(String string) {
        return string.matches("[0-9]+");
    }
}
