package ru.lex.alexander.testapp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EntryChecker {
    public static boolean checkEmail(String email){
        return email.contains("@");
    }

    public static boolean checkPassword(String password){
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{6,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
