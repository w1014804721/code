package top.wangjingxin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.wangjingxin.base.Result;
import top.wangjingxin.dao.IntegralDao;

import static top.wangjingxin.cache.ResultCache.getDataOk;

@Service
public class IntegralService {

    @Autowired
    IntegralDao integralDao;
    public Result day(String type) {
        return getDataOk(integralDao.day(type));
    }

    public Result all(String type) {
        return getDataOk(integralDao.all(type));
    }

    public Result check(String type) {
        return getDataOk(integralDao.check(type));
    }
}
