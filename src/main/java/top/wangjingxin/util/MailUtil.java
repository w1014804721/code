package top.wangjingxin.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Properties;

@Configuration
public class MailUtil {

    @Bean
    public JavaMailSenderImpl get(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.163.com");
        javaMailSender.setDefaultEncoding("utf-8");
        javaMailSender.setPort(25);
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth","true");
        properties.setProperty("mail.smtp.timeout","25000");
        javaMailSender.setJavaMailProperties(properties);
        javaMailSender.setUsername("ingzonecode@163.com");
        javaMailSender.setPassword("ingzone2017");
        return javaMailSender;
    }
    @Bean
    public ThreadPoolTaskExecutor threadPool(){
        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
        poolTaskExecutor.setCorePoolSize(5);
        poolTaskExecutor.setKeepAliveSeconds(200);
        poolTaskExecutor.setMaxPoolSize(10);
        poolTaskExecutor.setQueueCapacity(100);
        return poolTaskExecutor;
    }

    public static void main(String[] args) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("ingzonecode@163.com");
        mailMessage.setTo("1014804721@qq.com");
        mailMessage.setSubject("温馨提示");
        mailMessage.setText("尊敬的" + "aaa" + "山东大学ING工作室提醒您，你已经超过三天没有刷题了.");
        new MailUtil().get().send(mailMessage);
    }
}
