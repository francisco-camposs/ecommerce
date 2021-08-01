package br.ufrn.imd.ecommerce.validators;

import org.apache.commons.lang3.StringUtils;


public class CommentsProductValidator {

    public static final int MAX_CHARACTER_SIZE = 350;

    public static boolean isValidComment(String comment) {
        if(comment.length() > MAX_CHARACTER_SIZE){
            return false;
        }
        return true;
    }

    public static boolean isBlankComment(String comment) {
        return StringUtils.isAllBlank(comment);
    }
}
