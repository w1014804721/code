package top.wangjingxin.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.wangjingxin.model.vo.QuestionVO;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionDTO extends QuestionVO {
    private String slug;

    public QuestionVO getVO() {
        QuestionVO vo = new QuestionVO();
        vo.setContent(this.getContent());
        vo.setId(this.getId());
        vo.setTitle(this.getTitle());
        vo.setDifficulty(this.getDifficulty());
        vo.setTags(this.getTags());
        return vo;
    }
}
