package com.acasema.inventory.utils;

import java.util.regex.Pattern;

/**
 *  Esta clase no se puede heredar. se indica con un final
 */
public final class commonUtils {


    /**este metodo comprueba que la contraseñamin 8 y una mayuscula y un numero*/
    public static boolean checkPatternPassword(String pw) {
        final String PATTERN_PASSWORD="^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z]).{8,12}$";
        Pattern pattern = Pattern.compile(PATTERN_PASSWORD);
        return pattern.matcher(pw).matches();
    }
    public static boolean checkPatternShortName(String sn) {
        final String PATTERN_PASSWORD="^(a-zA-Z0-9){3,25}$";
        Pattern pattern = Pattern.compile(PATTERN_PASSWORD);
        return pattern.matcher(sn).matches();
    }
}
