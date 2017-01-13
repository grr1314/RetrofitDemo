package com.fragment.admin.retrofitdemo.http;

/**
 *
 * Created by admin on 2017/1/12.
 */
public class ApiException extends RuntimeException {
    private static final int USER_NOT_EXICT = 100;
    private static final int WRONG_PASSWORD = 101;

    public ApiException(String message) {
        super(message);
    }

    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    /**
     *由于错误信息始于服务器直接传递过来的，用户肯定是看不懂的。因此在这里对错误信息做一个处理
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code) {
        String message = "";
        switch (code) {
            case USER_NOT_EXICT: {
                message = "用户名不存在";
            }
            break;
            case WRONG_PASSWORD: {
                message = "密码错误";
            }
            break;
            default:
                message = "未知错误";
        }
        return message;
    }
}
