package cn.snoopy.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2002,"你想知道的问题不存在，要不要换一个呗？！"),
    TARGET_PARAM_NOT_FOUND(2003,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2004,"未登陆，不能进行评论！请先登录"),
    SYS_ERROR(2005,"服务冒烟了，要不然稍后再试一试！！！"),
    TYPE_PARAM_WRONG(2006,"评论类型错误或者不存在！"),
    COMMENT_NOT_FOUND(2007,"回复的评论不存在"),
    CONTENT_IS_EMPTY(2008,"回复内容不能为空"),
    READ_RECEIVER_FAIL(2009,"不是你自己的信息啊，读错了吧亲？！"),
    NOTIFY_NOT_FOUND(2010,"通知已经不在了，不早点来？！")
    ;

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code,String message) {
        this.message = message;
        this.code = code;
    }

    @Override
   public String getMessage(){
        return message;
   }

    @Override
    public Integer getCode() {
        return code;
    }

}
