package top.wangjingxin.model.dto;

import lombok.Data;


@Data
public class AnswerDTO  {
    private Integer id;
    private String userId;
    private Integer number;
    private String content;
}
