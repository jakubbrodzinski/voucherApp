package pwr.groupproject.vouchers.bean.enums;

public enum DiscountType {
    SUM(0), PERCENTEGE(1),OTHER(2);

    private int type;

    private DiscountType(int i) {
        type = i;
    }
}
