package pwr.groupproject.vouchers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pwr.groupproject.vouchers.bean.dto.rest.AnsweredSurveyDtoRest;
import pwr.groupproject.vouchers.bean.exceptions.InvalidAnswerFormException;
import pwr.groupproject.vouchers.bean.model.*;
import pwr.groupproject.vouchers.bean.model.enums.QuestionType;
import pwr.groupproject.vouchers.dao.CompanySurveyDao;

import java.util.*;

@Service
@Transactional
public class RestServiceImpl implements RestService{
    private final CompanySurveyService companySurveyService;
    private final CompanySurveyDao companySurveyDao;

    @Autowired
    public RestServiceImpl(CompanySurveyService companySurveyService,CompanySurveyDao companySurveyDao) {
        this.companySurveyService = companySurveyService;
        this.companySurveyDao=companySurveyDao;
    }


    @Override
    public void addAnsweredSurvey(AnsweredSurveyDtoRest answeredSurveyDtoRest, int surveyId) {
        AnsweredSurvey answeredSurvey = new AnsweredSurvey();
        Survey survey = companySurveyService.getSurveyByIdWithQuestion(surveyId);
        answeredSurvey.setSurvey(survey);

        List<Answer> answers = new ArrayList<>(answeredSurveyDtoRest.getAnswersMap().size());
        List<Question> questions = (List<Question>) survey.getQuestions();
        Collection<String> ansewrsRaw = answeredSurveyDtoRest.getAnswersMap().values();
        Iterator<String> aIterator= ansewrsRaw.iterator();
        for (int i = 0; aIterator.hasNext(); i++) {
            Answer answer = new Answer();
            answer.setAnswer(aIterator.next());
            answer.setAnsweredSurvey(answeredSurvey);
            answer.setQuestion(questions.get(i));

            answers.add(answer);
        }
        answeredSurvey.setAnswersList(answers);

        User user = new User();
        user.setCountry(answeredSurveyDtoRest.getCountry());
        user.setAge(answeredSurveyDtoRest.getAge());

        answeredSurvey.setUser(user);

        answeredSurvey.setDate(new Date());
        companySurveyDao.addAnsweredSurvey(answeredSurvey);
    }

    public TreeMap<Integer, String> validateAnsweredSurveyDtoRest(AnsweredSurveyDtoRest answeredSurveyDtoRest, int surveyId) throws InvalidAnswerFormException {
        TreeMap<Integer, String> errors=new TreeMap<>();

        Collection<Question> questions=companySurveyService.getSurveyByIdWithQuestion(surveyId).getQuestions();
        Collection<String> answerCollection=answeredSurveyDtoRest.getAnswersMap().values();
        if(questions.size()!=answerCollection.size())
            throw new InvalidAnswerFormException();

        Iterator<Question> questionIterator=questions.iterator();
        Iterator<String> ansIterator=answerCollection.iterator();

        int index=0;
        while(questionIterator.hasNext()){
            Question q=questionIterator.next();
            String a=ansIterator.next();

            if(q.getQuestionType()== QuestionType.RANGED && (Integer.parseInt(a)<0 || Integer.parseInt(a)>10 )){
                errors.put(index,"RANGED_ERR");
            }else if(q.getQuestionType()==QuestionType.SINGLE_CHOICE && !(a.equals("A") || a.equals("B") || a.equals("C") || a.equals("D"))){
                errors.put(index,"SINGLE_CHOICE_ERR");
            }else if(q.getQuestionType()==QuestionType.MULTIPLE_CHOICE){
                String[] ansSplitted=a.split(",");
                long count=Arrays.stream(ansSplitted).filter(ans -> !(ans.equals("A") || ans.equals("B") || ans.equals("C") || ans.equals("D"))).count();
                if(count!=0){
                    errors.put(index,"MULTPL_CHOICE_ERR");
                }
            }else if(q.getQuestionType()==QuestionType.OPEN && a.matches(".*[#;'/\\\\{}].*")){
                errors.put(index,"OPEN_ERR");
            }

            index++;
        }
        return errors;
    }
}
