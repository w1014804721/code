package top.wangjingxin.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.wangjingxin.model.dto.AnswerDTO;
import top.wangjingxin.model.dto.QuestionDTO;
import top.wangjingxin.model.vo.AnswerVO;
import top.wangjingxin.model.vo.QuestionVO;
import top.wangjingxin.util.Page;

import java.util.List;

@Repository
public interface AnswerDao {
    List<AnswerVO> get(Page conversion);

    List<AnswerVO> getByTitle(@Param("p") Page conversion,@Param("t") String title);

    List<AnswerVO> getByUser(@Param("p") Page conversion,@Param("id") String id);

    int getCount();

    int getByTitleCount(String title);

    int getByUserCount(String id);

    int queryCount(AnswerDTO answerDTO);

    int insert(AnswerDTO answerDTO);

    int getMark(Integer number);

    int day(@Param("userId") String userId,@Param("mark") int mark);

    QuestionVO query(String slug);

    int insertQuestion(QuestionDTO dto);
}
