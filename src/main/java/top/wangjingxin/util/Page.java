package top.wangjingxin.util;

import lombok.Data;

import static top.wangjingxin.config.AppConfig.MAX_ROWS;

/**
 * Created by wjx on 17-7-8.
 */
@Data
public class Page {
    public Page(){}
    public Page(String offsets, Integer rows){
        this.offsets = offsets;
        this.rows = rows;
    }
    private Integer page;
    private Integer rows;
    private Integer orderType;
    private String offsets;
    public Page conversion() {
        if(rows==null){
            throw new IllegalArgumentException("So stupid!!!There is no page argument please check your request!!!");
        }
        if(page!=null)
            page = (page - 1) * rows;
        return this;
    }
    public void setRows(Integer rows){
        this.rows = rows>MAX_ROWS?MAX_ROWS:rows;
    }
}
