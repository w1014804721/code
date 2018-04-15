package top.wangjingxin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.wangjingxin.base.Result;
import top.wangjingxin.dao.AnswerDao;
import top.wangjingxin.model.dto.AnswerDTO;
import top.wangjingxin.util.Page;

import static top.wangjingxin.cache.ResultCache.getCache;
import static top.wangjingxin.cache.ResultCache.getDataOk;
import static top.wangjingxin.config.AppConfig.MARK_MAP;
import static top.wangjingxin.util.SessionUtil.user;

@Service
public class AnswerService {
    @Autowired
    AnswerDao answerDao;
    public Result get(Page page) {
        return getDataOk(answerDao.get(page.conversion()));
    }

    public Result getByTitle(Page page, String title) {
        return getDataOk(answerDao.getByTitle(page.conversion(),title));
    }

    public Result getByUser(Page page, String id) {
        return getDataOk(answerDao.getByUser(page.conversion(),id));
    }

    public Result getCount() {
        return getDataOk(answerDao.getCount());
    }

    public Result getByTitleCount(String title) {
        return getDataOk(answerDao.getByTitleCount(title));
    }

    public Result getByUserCount(String id) {
        return getDataOk(answerDao.getByUserCount(id));
    }

    public Result publish(AnswerDTO answerDTO) {
        answerDTO.setUserId(user());
        int count = answerDao.queryCount(answerDTO);
        int insert = answerDao.insert(answerDTO);
        if(count==0&&insert==1){
            answerDao.day(answerDTO.getUserId(),getMark(answerDao.getMark(answerDTO.getNumber())));
        }
        return getCache(insert);
    }

    private int getMark(int mark) {
        return MARK_MAP.get(mark);
    }

    public Result query(String slug) {
        
    }
}
