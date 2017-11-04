package pwr.groupproject.vouchers.bean.enums;

public enum TokenStatus {
    OK(0),EXPIRED(1),WRONG(2);

    private int status;

    private TokenStatus(int i){
        status=i;
    }

    public int getStatus() {
        return status;
    }

}
