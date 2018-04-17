package top.wangjingxin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.wangjingxin.dao.ConfigDao;
import top.wangjingxin.model.TokenDate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AppConfig {
    @Autowired
    ConfigDao configDao;
    public static final Integer MAX_ROWS = 100;
    public static volatile String ROOT = null;
    public static volatile String HOST = null;
    private static ConfigDomain configDomain = null;
    public static Map<String, TokenDate> TOKEN_MAP = new ConcurrentHashMap<>();
    public static Map<Integer,Integer> MARK_MAP = new HashMap<>();
    public static Map<String,Integer> LEVEL_MAP = new HashMap<>();
    @PostConstruct
    public void config() {
        configDomain = configDao.config();
        ROOT = configDomain.getRoot();
        HOST = configDomain.getHost();
        MARK_MAP.put(1,1);
        MARK_MAP.put(2,2);
        MARK_MAP.put(3,5);
        LEVEL_MAP.put("Hard",3);
        LEVEL_MAP.put("Medium",2);
        LEVEL_MAP.put("Easy",1);
    }
}
