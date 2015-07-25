package com.microsoft.safetravel.SmsParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    public static String firstMatch(Pattern pattern, String text) {
        Matcher matcher = pattern.matcher(text);
        if(matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
