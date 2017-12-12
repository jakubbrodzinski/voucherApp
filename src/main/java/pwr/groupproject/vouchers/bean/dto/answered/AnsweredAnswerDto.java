package pwr.groupproject.vouchers.bean.dto.answered;

public class AnsweredAnswerDto {
    private String answersBody;
    private boolean wasPicked = false;

    public String getAnswersBody() {
        return answersBody;
    }

    public void setAnswersBody(String answersBody) {
        this.answersBody = answersBody;
    }

    public boolean isWasPicked() {
        return wasPicked;
    }

    public void setWasPicked(boolean wasPicked) {
        this.wasPicked = wasPicked;
    }
}
