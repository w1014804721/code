package top.wangjingxin.model.vo;

import lombok.Data;

@Data
public class QuestionVO {
    private Integer id;
    private String title;
    private String content;
    private Integer difficulty;
    private String tags;
}
