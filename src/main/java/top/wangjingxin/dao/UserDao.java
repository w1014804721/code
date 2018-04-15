package top.wangjingxin.dao;

import org.springframework.stereotype.Repository;
import top.wangjingxin.model.dto.UserDTO;

@Repository
public interface UserDao {
    int exist(String mail);

    int insert(UserDTO user);
}
