package pwr.groupproject.vouchers.bean.model;

import javax.persistence.Embeddable;

@Embeddable
public class PossibleAnswers {
    private String possibleAnswerA;
    private String getPossibleAnswerB;
    private String getPossibleAnswerC;
    private String getPossibleAnswerD;

    public String getPossibleAnswerA() {
        return possibleAnswerA;
    }

    public void setPossibleAnswerA(String possibleAnswerA) {
        this.possibleAnswerA = possibleAnswerA;
    }

    public String getGetPossibleAnswerB() {
        return getPossibleAnswerB;
    }

    public void setGetPossibleAnswerB(String getPossibleAnswerB) {
        this.getPossibleAnswerB = getPossibleAnswerB;
    }

    public String getGetPossibleAnswerC() {
        return getPossibleAnswerC;
    }

    public void setGetPossibleAnswerC(String getPossibleAnswerC) {
        this.getPossibleAnswerC = getPossibleAnswerC;
    }

    public String getGetPossibleAnswerD() {
        return getPossibleAnswerD;
    }

    public void setGetPossibleAnswerD(String getPossibleAnswerD) {
        this.getPossibleAnswerD = getPossibleAnswerD;
    }
}
