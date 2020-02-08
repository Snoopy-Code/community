package cn.snoopy.community.enums;

public enum NotifyTypeEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论");
    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotifyTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }
    public static String nameOfType(int type){
        for (NotifyTypeEnum notifyTypeEnum : NotifyTypeEnum.values()) {
            if(notifyTypeEnum.getType() == type){
                return notifyTypeEnum.getName();
            }
        }
        return "";
    }
}
