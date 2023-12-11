package org.example.utils;

public class StringUtils {

    public static String repeat(String in, int times) {
        if (times == 0) {
            return "";
        } else if (times == 1) {
            return in;
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < times; ++i) {
            output.append(in);
        }
        return output.toString();
    }
}
