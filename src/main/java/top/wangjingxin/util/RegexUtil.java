package top.wangjingxin.util;

import java.util.regex.Pattern;

public class RegexUtil {
    public static Pattern mailPattern = Pattern.compile("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$");
    public static boolean mail(String mail){
        return mailPattern.matcher(mail).find();
    }
}
