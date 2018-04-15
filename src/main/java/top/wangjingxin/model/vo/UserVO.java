package top.wangjingxin.model.vo;

import lombok.Data;

@Data
public class UserVO {
    private String nickName;
    private Integer aims;
    private Integer check;
    private Integer day = 0;
    private Integer continuous;

    public void setDay(Integer day) {
        if (day == null) {
            this.day = 0;
        } else {
            this.day = day;
        }
    }
}
