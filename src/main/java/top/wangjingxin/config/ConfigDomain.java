package top.wangjingxin.config;

import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class ConfigDomain {
    private String root;
    private String host;
}
