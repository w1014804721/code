package top.wangjingxin.service;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.pegdown.PegDownProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wangjingxin.base.Result;
import top.wangjingxin.dao.AnswerDao;
import top.wangjingxin.model.dto.AnswerDTO;
import top.wangjingxin.model.dto.QuestionDTO;
import top.wangjingxin.model.vo.QuestionVO;
import top.wangjingxin.util.Page;

import java.io.*;
import java.nio.file.Files;

import static java.lang.Integer.parseInt;
import static top.wangjingxin.cache.ResultCache.getCache;
import static top.wangjingxin.cache.ResultCache.getDataOk;
import static top.wangjingxin.config.AppConfig.LEVEL_MAP;
import static top.wangjingxin.config.AppConfig.MARK_MAP;
import static top.wangjingxin.config.AppConfig.ROOT;
import static top.wangjingxin.util.SessionUtil.user;

@Service
public class AnswerService {
    @Autowired
    AnswerDao answerDao;

    public Result get(Page page) {
        return getDataOk(answerDao.get(page.conversion()));
    }

    public Result getByTitle(Page page, String title) {
        return getDataOk(answerDao.getByTitle(page.conversion(), title));
    }

    public Result getByUser(Page page, String id) {
        return getDataOk(answerDao.getByUser(page.conversion(), id));
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

    @Transactional
    public Result publish(AnswerDTO answerDTO) {
        answerDTO.setUserId(user());
        int count = answerDao.queryCount(answerDTO);
        int insert = answerDao.insert(answerDTO);
        if (insert == 1) {
            if (count == 0) {
                answerDao.day(answerDTO.getUserId(), getMark(answerDao.getMark(answerDTO.getNumber())));
            }
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(ROOT + File.separator + "html" + File.separator + answerDTO.getId() + ".html"))))) {
                bw.write(new PegDownProcessor(Integer.MAX_VALUE).markdownToHtml(answerDTO.getContent()));
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return getCache(insert);
    }

    private int getMark(int mark) {
        return MARK_MAP.get(mark);
    }

    @Transactional
    public Result query(String slug) {
        String[] ss = slug.split("/");
        if ("description".equals(ss[ss.length - 1])) {
            slug = ss[ss.length - 2];
        } else {
            slug = ss[ss.length - 1];
        }
        QuestionVO vo = answerDao.query(slug);
        if (vo == null) {
            QuestionDTO dto = getQuestion(slug);
            answerDao.insertQuestion(dto);
            vo = dto.getVO();
        }
        return getDataOk(vo);
    }

    private QuestionDTO getQuestion(String slug) {
        QuestionDTO dto = new QuestionDTO();
        String path = "https://leetcode.com/graphql?variables={\"titleSlug\":\"#####\"}&query=query%20getQuestionDetail(%24titleSlug%3A%20String!)%20%7B%0A%20%20isCurrentUserAuthenticated%0A%20%20question(titleSlug%3A%20%24titleSlug)%20%7B%0A%20%20%20%20questionId%0A%20%20%20%20questionFrontendId%0A%20%20%20%20questionTitle%0A%20%20%20%20translatedTitle%0A%20%20%20%20questionTitleSlug%0A%20%20%20%20content%0A%20%20%20%20translatedContent%0A%20%20%20%20difficulty%0A%20%20%20%20stats%0A%20%20%20%20contributors%0A%20%20%20%20similarQuestions%0A%20%20%20%20discussUrl%0A%20%20%20%20mysqlSchemas%0A%20%20%20%20randomQuestionUrl%0A%20%20%20%20sessionId%0A%20%20%20%20categoryTitle%0A%20%20%20%20submitUrl%0A%20%20%20%20interpretUrl%0A%20%20%20%20codeDefinition%0A%20%20%20%20sampleTestCase%0A%20%20%20%20enableTestMode%0A%20%20%20%20metaData%0A%20%20%20%20enableRunCode%0A%20%20%20%20enableSubmit%0A%20%20%20%20judgerAvailable%0A%20%20%20%20infoVerified%0A%20%20%20%20envInfo%0A%20%20%20%20urlManager%0A%20%20%20%20article%0A%20%20%20%20questionDetailUrl%0A%20%20%20%20discussCategoryId%0A%20%20%20%20discussSolutionCategoryId%0A%20%20%20%20libraryUrl%0A%20%20%20%20companyTags%20%7B%0A%20%20%20%20%20%20name%0A%20%20%20%20%20%20slug%0A%20%20%20%20%20%20translatedName%0A%20%20%20%20%7D%0A%20%20%20%20topicTags%20%7B%0A%20%20%20%20%20%20name%0A%20%20%20%20%20%20slug%0A%20%20%20%20%20%20translatedName%0A%20%20%20%20%7D%0A%20%20%7D%0A%20%20interviewed%20%7B%0A%20%20%20%20interviewedUrl%0A%20%20%20%20companies%20%7B%0A%20%20%20%20%20%20id%0A%20%20%20%20%20%20name%0A%20%20%20%20%20%20slug%0A%20%20%20%20%7D%0A%20%20%20%20timeOptions%20%7B%0A%20%20%20%20%20%20id%0A%20%20%20%20%20%20name%0A%20%20%20%20%7D%0A%20%20%20%20stageOptions%20%7B%0A%20%20%20%20%20%20id%0A%20%20%20%20%20%20name%0A%20%20%20%20%7D%0A%20%20%7D%0A%20%20subscribeUrl%0A%20%20isPremium%0A%20%20loginUrl%0A%7D%0A&".replace("#####", slug);
        try {
            String body = Jsoup.connect(path)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .execute()
                    .body();
            JSONObject object = JSONObject.parseObject(body)
                    .getJSONObject("data").getJSONObject("question");
            dto.setId(parseInt(object.getString("questionId")));
            dto.setSlug(slug);
            dto.setContent(object.getString("content"));
            dto.setTitle(object.getString("questionTitle"));
            dto.setDifficulty(getLevel(object.getString("difficulty")));
            dto.setTags(object.getJSONArray("topicTags").stream().reduce("", (a, b) -> a + "," + ((JSONObject) b).getString("name")).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dto;
    }

    private Integer getLevel(String difficulty) {
        return LEVEL_MAP.get(difficulty);
    }
}
