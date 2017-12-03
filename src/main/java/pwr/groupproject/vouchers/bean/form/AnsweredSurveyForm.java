package pwr.groupproject.vouchers.bean.form;

import pwr.groupproject.vouchers.bean.dto.AnswerDto;
import pwr.groupproject.vouchers.bean.model.AnsweredSurvey;
import pwr.groupproject.vouchers.bean.model.Question;
import pwr.groupproject.vouchers.bean.model.Survey;
import pwr.groupproject.vouchers.bean.model.User;

import java.io.Serializable;
import java.util.List;

public class AnsweredSurveyForm implements Serializable {

    private static final long serialVersionUID = -2959738256673744710L;
    private AnswerDto[] answers;


    public AnsweredSurveyForm() {
        super();
    }

    public AnsweredSurveyForm(Survey survey) {
        System.out.println("ASDAD");
        AnswerDto[] answers = new AnswerDto[survey.getQuestions().size()];
        for(int i = 0, size = survey.getQuestions().size(); i < size; i++) {
            AnswerDto answerDto = new AnswerDto();
            answerDto.setAnswerBody("");
            answers[i] = answerDto;
        }
        this.answers = answers;

    }

    public AnswerDto[] getAnswers() {
        return answers;
    }

    public void setAnswers(AnswerDto[] answers) {
        this.answers = answers;
    }

}
