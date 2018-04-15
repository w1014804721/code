package top.wangjingxin.util;

import java.util.UUID;

/**
 * Created by 王镜鑫 on 17-10-9.
 * 请关注一下他的个人博客 wangjingxin.top
 */
public class UuidUtil {
    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
