package top.wangjingxin.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TokenDate {
    private String id;
    private Timestamp date;

    public static TokenDate get(String id) {
        TokenDate tokenDate = new TokenDate();
        tokenDate.setId(id);
        tokenDate.setDate(new Timestamp(System.currentTimeMillis() / 1000 * 1000));
        return tokenDate;
    }
}
