package top.wangjingxin.base;

import lombok.Getter;

/**
 * Created by wjx on 17-7-8.
 */
public class Success {
    @Getter
    private int success;
    public static final Success SUCCESS = new Success(1);
    public static final Success UNSUCCESS = new Success(0);

    public static final Success getSuccess(int success) {
        if (success == 0 || success == 1)
            return new Success(success);
        return null;
    }

    private Success(int success) {
        this.success = success;
    }

    @Override
    public int hashCode() {
        return success;
    }

    @Override
    public boolean equals(Object obj) {
        Success s = (Success) obj;
        return s.success == success;
    }
}
