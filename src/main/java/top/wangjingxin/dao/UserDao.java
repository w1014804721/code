package top.wangjingxin.dao;

import org.springframework.stereotype.Repository;
import top.wangjingxin.model.dto.UserDTO;
import top.wangjingxin.model.to.LoginTO;
import top.wangjingxin.model.vo.UserVO;

@Repository
public interface UserDao {
    int exist(String mail);

    int insert(UserDTO user);

    LoginTO login(String mail);

    int certified(String id);

    UserVO info(String id);

    int check(String id);
}
