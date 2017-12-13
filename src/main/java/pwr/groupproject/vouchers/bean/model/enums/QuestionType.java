package pwr.groupproject.vouchers.bean.model.enums;

public enum QuestionType {
    OPEN(1), MULTIPLE_CHOICE(2), SINGLE_CHOICE(3), RANGED(4);

    private int Id;

    private QuestionType(int Id) {
        this.Id = Id;
    }
}
