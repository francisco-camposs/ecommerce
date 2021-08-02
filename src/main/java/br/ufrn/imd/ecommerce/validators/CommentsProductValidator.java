package br.ufrn.imd.ecommerce.validators;

import org.apache.commons.lang3.StringUtils;


public class CommentsProductValidator {

    public static final int MAX_CHARACTER_SIZE = 350;

    public static boolean isValidComment(String comment) {
        return comment.length() <= MAX_CHARACTER_SIZE;
    }

    public static boolean isBlankComment(String comment) {
        return StringUtils.isAllBlank(comment);
    }
}
