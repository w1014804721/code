package top.wangjingxin.config;

import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class ConfigDomain {
    private String root;
    private String host;

    public static void main(String[] args) {
        String s = "<a class='mm' href='http://baidu.com'>test1</a><a class='mm' href='http://baidu.com' id=\"aa\">test2</a>";
        Pattern p = Pattern.compile("<a\\s+.*?href\\s*=\\s*[\"'](.*?)[\"'].*?>");
        Matcher matcher = p.matcher(s);
        while (matcher.find()){
            System.out.println(matcher.group());
            System.out.println(matcher.group(1));
        }
    }
}
