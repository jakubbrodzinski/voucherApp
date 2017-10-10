package pwr.groupproject.vouchers.bean.model;

import javax.persistence.Embeddable;

@Embeddable
public class PossibleAnswers {
    private String possibleAnswerA;
    private String possibleAnswerB;
    private String possibleAnswerC;
    private String possibleAnswerD;

    public String getPossibleAnswerA() {
        return possibleAnswerA;
    }

    public void setPossibleAnswerA(String possibleAnswerA) {
        this.possibleAnswerA = possibleAnswerA;
    }

    public String getPossibleAnswerB() {
        return possibleAnswerB;
    }

    public void setPossibleAnswerB(String possibleAnswerB) {
        this.possibleAnswerB = possibleAnswerB;
    }

    public String getPossibleAnswerC() {
        return possibleAnswerC;
    }

    public void setPossibleAnswerC(String possibleAnswerC) {
        this.possibleAnswerC = possibleAnswerC;
    }

    public String getPossibleAnswerD() {
        return possibleAnswerD;
    }

    public void setPossibleAnswerD(String possibleAnswerD) {
        this.possibleAnswerD = possibleAnswerD;
    }
}
