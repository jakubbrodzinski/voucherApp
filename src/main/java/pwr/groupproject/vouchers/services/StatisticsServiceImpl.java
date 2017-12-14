package pwr.groupproject.vouchers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pwr.groupproject.vouchers.bean.dto.QuestionStatisticsDto;
import pwr.groupproject.vouchers.bean.dto.SurveyStatisticsDto;
import pwr.groupproject.vouchers.bean.dto.answered.AnsweredAnswerDto;
import pwr.groupproject.vouchers.bean.dto.answered.AnsweredQuestionDto;
import pwr.groupproject.vouchers.bean.dto.answered.AnsweredSurveyDto;
import pwr.groupproject.vouchers.bean.exceptions.WrongAnsweredSurveyIdException;
import pwr.groupproject.vouchers.bean.model.*;
import pwr.groupproject.vouchers.bean.model.enums.QuestionType;
import pwr.groupproject.vouchers.dao.CompanySurveyDao;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional(readOnly = true)
@PreAuthorize("hasRole('COMPANY')")
public class StatisticsServiceImpl implements StatisticsService {
    final private CompanySurveyService companySurveyService;
    final private CompanySurveyDao companySurveyDao;

    @Autowired
    public StatisticsServiceImpl(CompanySurveyService companySurveyService,CompanySurveyDao companySurveyDao) {
        this.companySurveyService = companySurveyService;
        this.companySurveyDao=companySurveyDao;
    }

    @Cacheable("surveyStat")
    @Override
    public SurveyStatisticsDto getSurveysStatistics(int surveyId) {
        Collection<AnsweredSurvey> answeredSurveys = getAllAnsweredSurveysWithDetails(surveyId);
        Survey survey = companySurveyService.getSurveyByIdWithQuestion(surveyId);
        SurveyStatisticsDto surveyStatisticsDto = new SurveyStatisticsDto();
        surveyStatisticsDto.setAmmount(answeredSurveys.size());
        surveyStatisticsDto.setSurveyName(survey.getSurveyName());

        //average age
        double averageAge = answeredSurveys.stream().mapToInt(a -> a.getUser().getAge()).average().orElse(0.0);
        surveyStatisticsDto.setAge(averageAge);

        //average country
        List<String> countries = answeredSurveys.stream().map(a -> a.getUser().getCountry()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream().sorted(Comparator.comparingLong(Map.Entry::getValue)).limit(3).map(Map.Entry::getKey).collect(Collectors.toList());
        while (countries.size() != 3)
            countries.add("N/A");
        surveyStatisticsDto.setCountry(countries.toArray(new String[3]));

        if (answeredSurveys.size() == 0)
            return surveyStatisticsDto;

        //initialaizing iterators
        Iterator<AnsweredSurvey> answeredSurveyIterator = answeredSurveys.iterator();
        Iterator<Question> qIterator = survey.getQuestions().iterator();
        Iterator<Answer>[] aIteratorArray = new Iterator[answeredSurveys.size()];
        IntStream.range(0, aIteratorArray.length).forEach(i -> aIteratorArray[i] = answeredSurveyIterator.next().getAnswersList().iterator());

        int answersSize = answeredSurveys.size();
        List<QuestionStatisticsDto> questionStatisticsDtoList = surveyStatisticsDto.getQuestionWithAnswersList();
        while (qIterator.hasNext()) {
            Question q = qIterator.next();
            QuestionType qType = q.getQuestionType();
            QuestionStatisticsDto questionStatisticsDto = new QuestionStatisticsDto();
            questionStatisticsDto.setQuestionBody(q.getQuestionBody());
            questionStatisticsDto.setQuestionType(qType);

            switch (qType) {
                case OPEN:
                    Arrays.stream(aIteratorArray).forEach(Iterator::next);
                    questionStatisticsDto.setAnswers(null);
                    break;
                case RANGED:
                    double average = 0;
                    for (Iterator<Answer> anAIteratorArray : aIteratorArray) {
                        String temp = anAIteratorArray.next().getAnswer();
                        average += Double.parseDouble(temp);
                    }
                    questionStatisticsDto.getAnswers()[0].setAnswersStat(Double.toString(average / answersSize));
                    break;
                default:
                    double[] apperances = new double[4];
                    for (Iterator<Answer> anAIteratorArray : aIteratorArray) {
                        Answer a = anAIteratorArray.next();
                        String[] splited = a.getAnswer().split(",");
                        for (String s : splited) {
                            switch (s) {
                                case "A":
                                    apperances[0]++;
                                    break;
                                case "B":
                                    apperances[1]++;
                                    break;
                                case "C":
                                    apperances[2]++;
                                    break;
                                case "D":
                                    apperances[3]++;
                            }

                        }
                    }
                    IntStream.range(0, apperances.length).forEach(a -> questionStatisticsDto.getAnswers()[a].setAnswersStat(Double.toString(100 * apperances[a] / answersSize)));
                    questionStatisticsDto.setPossibleAnswers(q.getPossibleAnswers());
                    break;
            }

            questionStatisticsDtoList.add(questionStatisticsDto);
        }
        return surveyStatisticsDto;
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<AnsweredSurvey> getAllAnsweredSurveysWithDetails(int surveyId) {
        return companySurveyDao.getAllResultsOfSurveyWithDetails(surveyId);
    }

    @Cacheable("ansList")
    @Transactional(readOnly = true)
    @Override
    public Collection<AnsweredSurveyDto> getAllAnsweredSurveys(int surveyId) {
        return companySurveyDao.getAllResultsOfSurvey(surveyId).stream().map(r->new AnsweredSurveyDto(r.getId(),r.getUser().getAge(),r.getUser().getCountry(),r.getDate())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AnsweredSurvey getSingleAnsweredSurveyById(int answeredSurveyId) throws WrongAnsweredSurveyIdException {
        return  companySurveyDao.getAnsweredSurveyWithAnswers(answeredSurveyId);
    }

    @Override
    public AnsweredSurveyDto getResultDetails(int answeredSurveyId) throws WrongAnsweredSurveyIdException {
        //TRANSCATIONAL
        AnsweredSurvey ansSur= getSingleAnsweredSurveyById(answeredSurveyId);
        Collection<Answer> a=ansSur.getAnswersList();
        Collection<Question> q=ansSur.getSurvey().getQuestions();

        AnsweredSurveyDto resultDto=new AnsweredSurveyDto(answeredSurveyId,ansSur.getUser().getAge(),ansSur.getUser().getCountry(),ansSur.getDate());

        Iterator<Question> qIterator=q.iterator();
        Iterator<Answer> aIterator=a.iterator();
        while(qIterator.hasNext() && aIterator.hasNext()){
            AnsweredQuestionDto aqDto=new AnsweredQuestionDto();

            Question question=qIterator.next();
            Answer answer=aIterator.next();

            aqDto.setQuestionBody(question.getQuestionBody());
            aqDto.setQuestionType(question.getQuestionType());

            if(question.getQuestionType()==QuestionType.OPEN || question.getQuestionType()==QuestionType.RANGED){
                AnsweredAnswerDto aaDto=new AnsweredAnswerDto();
                aaDto.setAnswersBody(answer.getAnswer());
                aqDto.getAnswerDtos()[0]=aaDto;
            }else{
                PossibleAnswers psAns=question.getPossibleAnswers();
                AnsweredAnswerDto aaDto0=new AnsweredAnswerDto();
                aaDto0.setAnswersBody(psAns.getPossibleAnswerA());
                AnsweredAnswerDto aaDto1=new AnsweredAnswerDto();
                aaDto1.setAnswersBody(psAns.getPossibleAnswerA());
                AnsweredAnswerDto aaDto2=new AnsweredAnswerDto();
                aaDto2.setAnswersBody(psAns.getPossibleAnswerA());
                AnsweredAnswerDto aaDto3=new AnsweredAnswerDto();
                aaDto3.setAnswersBody(psAns.getPossibleAnswerA());
                aqDto.getAnswerDtos()[0]=aaDto0;
                aqDto.getAnswerDtos()[1]=aaDto1;
                aqDto.getAnswerDtos()[2]=aaDto2;
                aqDto.getAnswerDtos()[3]=aaDto3;

                String[] answers=answer.getAnswer().split(",");
                Arrays.stream(answers).forEach(u->aqDto.getAnswerDtos()[u.charAt(0)-'A'].setWasPicked(true));
            }

            resultDto.getAnswersList().add(aqDto);
        }
        return resultDto;
    }
}
