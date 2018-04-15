package top.wangjingxin.model.to;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class LoginTO {
    private String id;
    private String password;
    private Timestamp date;
    private Integer certified;
}
