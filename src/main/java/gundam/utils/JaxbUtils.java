package gundam.utils;

public class JaxbUtils {
    public static String getErrorDesc(String desc, Object... names) {
        if (desc == null) {
            return null;
        }
        if (names != null) {
            int length = names.length;
            for (int i = 0; i < length; i++) {
                String reg = "{"+i+"}";
                desc = desc.replace(reg, String.valueOf(names[i]));
            }
        }
        return desc;
    }
}
