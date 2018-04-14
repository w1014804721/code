package top.wangjingxin.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by 王镜鑫 on 17-7-24.
 * 请关注一下他的个人博客 wangjingxin.top
 */
@Component
public class BeanUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    public static <T> T getBean(Class<T> clazz) {
        int i = 0,j = 0;
        System.out.println(i+++j);
        return applicationContext.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanUtil.applicationContext = applicationContext;
    }
}
