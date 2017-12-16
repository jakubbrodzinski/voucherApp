package pwr.groupproject.vouchers.bean.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Embeddable
public class PossibleAnswers implements Serializable {

    private static final long serialVersionUID = -1241014771227730014L;
    @Pattern(regexp = "[\\pLA-Za-z0-9\\-/ ,.!?:]*")
    @NotBlank
    private String possibleAnswerA;
    @Pattern(regexp = "[\\pLA-Za-z0-9\\-/ ,.!?:]*")
    @NotBlank
    private String possibleAnswerB;
    @Pattern(regexp = "[\\pLA-Za-z0-9\\-/ ,.!?:]*")
    @NotBlank
    private String possibleAnswerC;
    @Pattern(regexp = "[\\pLA-Za-z0-9\\-/ ,.!?:]*")
    @NotBlank
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
