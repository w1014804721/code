package top.wangjingxin.model.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDTO {
    private String id;
    private String mail;
    private String nickName;
    private Timestamp date;
    private String password;
    private Integer aims;
    public void setAims(Integer aims){
        if(aims==null||aims==0){
            this.aims = 1;
        }
    }
}
