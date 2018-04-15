package top.wangjingxin.config;

import org.springframework.beans.factory.annotation.Autowired;
import top.wangjingxin.dao.ConfigDao;
import top.wangjingxin.model.TokenDate;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AppConfig {
    @Autowired
    ConfigDao configDao;
    public static final Integer MAX_ROWS = 100;
    public static volatile String ROOT = null;
    public static volatile String HOST = null;
    private static ConfigDomain configDomain = null;
    public static Map<String, TokenDate> TOKEN_MAP = new ConcurrentHashMap<>();

    @PostConstruct
    public void config() {
        configDomain = configDao.config();
        ROOT = configDomain.getRoot();
        HOST = configDomain.getHost();
    }
}
