package top.wangjingxin.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.wangjingxin.dao.UserDao;
import top.wangjingxin.model.to.UserTO;

import java.util.List;

import static top.wangjingxin.config.AppConfig.HOST;

@Component
public class TasksDay {
    @Autowired
    UserDao userDao;
    @Autowired
    ThreadPoolTaskExecutor poolTaskExecutor;
    @Autowired
    JavaMailSenderImpl javaMailSender;

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void everyDay() {
        userDao.add();
    }
    @Scheduled(cron = "0 0 18 * * ?")
    @Transactional
    public void send(){
        userDao.queryAllUser().forEach(this::send);
    }
    private void send(UserTO to) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("ingzonecode@163.com");
        mailMessage.setTo(to.getMail());
        mailMessage.setSubject("温馨提示");
        mailMessage.setText("尊敬的" + to.getNickName() + "山东大学ING工作室提醒您，你已经超过三天没有刷题了.");
        poolTaskExecutor.execute(() -> javaMailSender.send(mailMessage));
    }
}
