package top.wangjingxin.model.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AnswerVO {
    private Integer id;
    private String userId;
    private String nickName;
    private Integer number;
    private Timestamp date;
    private Integer mark;
    private String title;
    private String slug;
    private String difficulty;
    private String tags;
}
