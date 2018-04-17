package top.wangjingxin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wangjingxin.base.Result;
import top.wangjingxin.base.Success;
import top.wangjingxin.dao.UserDao;
import top.wangjingxin.model.dto.UserDTO;
import top.wangjingxin.model.to.LoginTO;
import top.wangjingxin.model.vo.UserVO;
import top.wangjingxin.util.RegexUtil;

import java.sql.Timestamp;

import static top.wangjingxin.cache.ResultCache.*;
import static top.wangjingxin.config.AppConfig.HOST;
import static top.wangjingxin.config.AppConfig.TOKEN_MAP;
import static top.wangjingxin.util.Hash.getSha256;
import static top.wangjingxin.util.SessionUtil.setAttribute;
import static top.wangjingxin.util.SessionUtil.user;
import static top.wangjingxin.util.UuidUtil.uuid;
import static top.wangjingxin.model.TokenDate.*;

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
        return getDataOk(Success.getSuccess(userDao.exist(mail)));
    }

    @Transactional
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
        TOKEN_MAP.put(token, get(user.getId()));
        send(user.getMail(), token);
        return OK;
    }

    public Result login(String mail, String password) {
        LoginTO to = userDao.login(mail);
        if (to == null || to.getPassword().equals(getSha256(password + to.getDate() + to.getId()))) {
            return getDataFail("error1");
        }
        if (to.getCertified() == 0) {
            String token = getSha256(uuid()) + getSha256(uuid());
            TOKEN_MAP.put(token, get(to.getId()));
            send(mail, token);
            return getDataFail("error0");
        }
        setAttribute("id",to.getId());
        return getDataOk(to.getId());
    }

    private void send(String mail, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("ingzonecode@163.com");
        mailMessage.setTo(mail);
        mailMessage.setSubject("验证链接");
        mailMessage.setText("山东大学ING工作室提醒您，请点击链接进行邮箱验证，链接地址：\n" + HOST + "/api/v1/user/verification?token=" + token);
        poolTaskExecutor.execute(() -> javaMailSender.send(mailMessage));
    }

    @Transactional
    public Result verification(String token) {
        if (userDao.certified(TOKEN_MAP.get(token).getId()) == 1) {
            TOKEN_MAP.remove(token);
            return getDataOk("验证成功，请重新登录！");
        }
        return FAILURE;
    }

    public Result info(String id) {
        return getDataOk(userDao.info(id));
    }

    @Transactional
    public Result check() {
        UserVO vo = (UserVO) info(user()).getData();
        if(vo.getCheck()==0&&vo.getAims()<=vo.getDay()){
            return getCache(userDao.check(user()));
        }
        return FAILURE;
    }
}
