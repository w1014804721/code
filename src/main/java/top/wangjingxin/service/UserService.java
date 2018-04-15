package top.wangjingxin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import top.wangjingxin.base.Result;
import top.wangjingxin.dao.UserDao;
import top.wangjingxin.model.dto.UserDTO;
import top.wangjingxin.util.Hash;
import top.wangjingxin.util.RegexUtil;

import java.sql.Timestamp;

import static top.wangjingxin.cache.ResultCache.FAILURE;
import static top.wangjingxin.cache.ResultCache.OK;
import static top.wangjingxin.cache.ResultCache.getCache;
import static top.wangjingxin.config.AppConfig.HOST;
import static top.wangjingxin.util.Hash.getSha256;
import static top.wangjingxin.util.UuidUtil.uuid;

@Service
public class UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    ThreadPoolTaskExecutor poolTaskExecutor;
    @Autowired
    JavaMailSenderImpl javaMailSender;

    public Result exist(String mail) {
        if (!RegexUtil.mail(mail)) {
            return FAILURE;
        }
        return getCache(userDao.exist(mail));
    }

    public Result register(UserDTO user) {
        if (!OK.equals(exist(user.getMail()))) {
            return FAILURE;
        }
        user.setId(uuid());
        user.setDate(new Timestamp(System.currentTimeMillis() / 1000 * 1000));
        user.setPassword(getSha256(user.getPassword() + user.getDate() + user.getId()));
        if (userDao.insert(user) != 1) {
            return FAILURE;
        }
        String token = getSha256(uuid()) + getSha256(uuid());
        send(user.getMail(), token);
        return OK;
    }

    public void send(String mail, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("ingzonecode@163.com");
        mailMessage.setTo(mail);
        mailMessage.setSubject("验证链接");
        mailMessage.setText("山东大学ING工作室提醒您，请点击链接进行邮箱验证，链接地址：\n" + HOST + "/api/v1/user/verification?token=" + token);
        poolTaskExecutor.execute(() -> {
            javaMailSender.send(mailMessage);
        });
    }
}
