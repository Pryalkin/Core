package lesson2.task3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        long time = System.nanoTime();
        System.out.println(fuzzySearch("car", "ca6$$#_rtwheel")); // true
        System.out.println(fuzzySearch("cwhl", "cartwheel")); // true
        System.out.println(fuzzySearch("cwhee", "cartwheel")); // true
        System.out.println(fuzzySearch("cartwheel", "cartwheel")); // true
        System.out.println(fuzzySearch("cwheeel", "cartwheel")); // false
        System.out.println(fuzzySearch("lw", "cartwheel")); // false
        System.out.println("Время выполнения метода fuzzySearch: " + (System.nanoTime() - time) + " наносекунд");
        System.out.println("------------------------------------");
        long time2 = System.nanoTime();
        System.out.println(fuzzySearch2("car", "ca6$$#_rtwheel")); // true
        System.out.println(fuzzySearch2("cwhl", "cartwheel")); // true
        System.out.println(fuzzySearch2("cwhee", "cartwheel")); // true
        System.out.println(fuzzySearch2("cartwheel", "cartwheel")); // true
        System.out.println(fuzzySearch2("cwheeel", "cartwheel")); // false
        System.out.println(fuzzySearch2("lw", "cartwheel")); // false
        System.out.println("Время выполнения метода fuzzySearch2: " + (System.nanoTime() - time2) + " наносекунд");
    }

    private static boolean fuzzySearch(String pattern, String expression) {
        char[] patternCharacterArray = pattern.toCharArray();
        char[] expressionCharacterArray = expression.toCharArray();
        int k = 0;
        boolean flag = false;
        for (int i = 0; i < patternCharacterArray.length; i++) {
            for (int j = k; j < expressionCharacterArray.length; j++) {
                if (patternCharacterArray[i] == expressionCharacterArray[j]){
                    k = j+1;
                    flag = true;
                    break;
                }
            }
            if (flag){
                if (i+1 == patternCharacterArray.length) return true;
                else flag = false;
            } else return false;
        }
        return false;
    }

    private static boolean fuzzySearch2(String pat, String expression) {
        char[] patternCharacterArray = pat.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char ch: patternCharacterArray) {
            sb.append("[" + ch + "]" + ".*");
        }
        Pattern pattern = Pattern.compile(sb.toString());
        Matcher matcher = pattern.matcher(expression);
        if(matcher.find()) return true;
        return false;
    }


}
