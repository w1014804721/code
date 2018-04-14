package top.wangjingxin.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by wjx on 17-7-10.
 */
public class SessionUtil {
    public static String user() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession().getAttribute("id") + "";
    }

    public static Integer role() {
        return (Integer) ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession().getAttribute("role");
    }

    public static HttpSession setAttribute(String a, Object b) {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        session.setAttribute(a, b);
        return session;
    }

    public static void setTime(int t) {
        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession().setMaxInactiveInterval(t * 60);
    }

    public static Object getAttribute(String a) {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession().getAttribute(a);
    }

    public static String path() {
        return SessionUtil.getSession().getServletContext().getRealPath("files");
    }

    public static HttpSession getSession() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
    }

    public static HttpServletResponse response() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    }

    public static boolean testExist(String a, Object b) {
        return b != null && b.equals(getAttribute(a));
    }

    public static void destroySession() {
        HttpSession session = getSession();
        if (session != null) {
            System.out.println("The session " + session.getId() + " is invalidate at " + new Date());
            session.invalidate();
        }
    }
}

