package br.ufrn.imd.ecommerce.validators;

public class ProductValidator {

    public static final int MAX_NAME_SIZE = 50;

    public static final int MAX_DESCRIPTION_SIZE = 350;

    public static boolean isValidName(String name) {
       if (!hasJustNumbers(name)){
           if(!(name.length() > MAX_NAME_SIZE) ){
                return true;
           }
       }
       return false;
    }

    public static boolean isValidDescription(String description) {
        if (!hasJustNumbers(description)){
            if(!(description.length() > MAX_DESCRIPTION_SIZE) ){
                return true;
            }
        }
        return false;
    }

    public static boolean isValidPrice(double price) {
        if(price > 0){
            return true;
        }
        return false;
    }

    public static boolean isValidImgLink(String imgLink) {
        if (!hasJustNumbers(imgLink)){
            return true;
        }
        return false;
    }

    public static boolean hasJustNumbers(String string) {
        return string.matches("[0-9]+");
    }
}
