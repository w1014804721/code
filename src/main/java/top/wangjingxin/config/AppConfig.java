package top.wangjingxin.config;

import org.springframework.beans.factory.annotation.Autowired;
import top.wangjingxin.dao.ConfigDao;

import javax.annotation.PostConstruct;

public class AppConfig {
    @Autowired
    ConfigDao configDao;
    public static final Integer MAX_ROWS = 100;
    public static volatile String ROOT = null;
    @PostConstruct
    public void config(){
        ROOT = configDao.root();
    }
}
