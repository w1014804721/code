package top.wangjingxin.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.wangjingxin.model.vo.RankVO;

import java.util.List;

@Repository
public interface IntegralDao {
    List<RankVO> day(@Param("t") String type);

    List<RankVO> all(@Param("t") String type);

    List<RankVO> check(@Param("t") String type);
}
