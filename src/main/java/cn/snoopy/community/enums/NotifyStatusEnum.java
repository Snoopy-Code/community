package cn.snoopy.community.enums;

public enum NotifyStatusEnum {
    UMREAD(0),
    READ(1);
    private int status;

    public int getStatus() {
        return status;
    }

    NotifyStatusEnum(int status) {
        this.status = status;
    }
}
