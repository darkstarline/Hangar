package gundam.utils;

public class StringUtils {
    public static boolean isEmail(String str){
        String regex = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$"; // (注：\为转义字符）
        return str.matches(regex);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
