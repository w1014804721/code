package top.wangjingxin.dao;

import org.springframework.stereotype.Repository;
import top.wangjingxin.config.ConfigDomain;

@Repository
public interface ConfigDao {
    ConfigDomain config();
}
