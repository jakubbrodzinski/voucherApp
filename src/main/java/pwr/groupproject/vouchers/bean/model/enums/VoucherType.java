package pwr.groupproject.vouchers.bean.model.enums;

public enum VoucherType {
    NOTREUSABLE(0), REUSABLE(1);

    private int value;

    private VoucherType(int i) {
        value = i;
    }
}
