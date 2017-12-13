package pwr.groupproject.vouchers.bean.enums;

public enum ErrorCode {
    WRONG_USERNAME(0), NOT_ACTIVATED(1), WRONG_PASSWORD(2);

    private int status;

    private ErrorCode(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static ErrorCode getCodeByStatus(int status) {
        switch (status) {
            case 0:
                return WRONG_USERNAME;
            case 1:
                return NOT_ACTIVATED;
            case 2:
                return WRONG_PASSWORD;
            default:
                return null;
        }
    }
}
