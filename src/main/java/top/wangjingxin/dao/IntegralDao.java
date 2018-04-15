package top.wangjingxin.dao;

import org.springframework.stereotype.Repository;
import top.wangjingxin.model.vo.RankVO;

import java.util.List;

@Repository
public interface IntegralDao {
    List<RankVO> day(String type);

    List<RankVO> all(String type);

    List<RankVO> check(String type);
}
