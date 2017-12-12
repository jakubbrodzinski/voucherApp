package pwr.groupproject.vouchers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pwr.groupproject.vouchers.bean.dto.QuestionStatisticsDto;
import pwr.groupproject.vouchers.bean.dto.SurveyStatisticsDto;
import pwr.groupproject.vouchers.bean.dto.answered.AnsweredSurveyDto;
import pwr.groupproject.vouchers.bean.model.Answer;
import pwr.groupproject.vouchers.bean.model.AnsweredSurvey;
import pwr.groupproject.vouchers.bean.model.Question;
import pwr.groupproject.vouchers.bean.model.Survey;
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

            if (qType == QuestionType.OPEN) {
                Arrays.stream(aIteratorArray).forEach(Iterator::next);
                questionStatisticsDto.setAnswers(null);
            } else if (qType == QuestionType.RANGED) {
                double average = 0;
                for (int i = 0; i < aIteratorArray.length; i++) {
                    String temp = aIteratorArray[i].next().getAnswer();
                    average += Double.parseDouble(temp);
                }
                questionStatisticsDto.getAnswers()[0].setAnswersStat(Double.toString(average / answersSize));
            } else {
                double[] apperances = new double[4];
                for (int i = 0; i < aIteratorArray.length; i++) {
                    Answer a = aIteratorArray[i].next();
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
            }

            questionStatisticsDtoList.add(questionStatisticsDto);
        }
        return surveyStatisticsDto;
    }

    @Override
    public Collection<AnsweredSurvey> getAllAnsweredSurveysWithDetails(int surveyId) {
        return companySurveyDao.getAllResultsOfSurveyWithDetails(surveyId);
    }

    @Override
    public Collection<AnsweredSurveyDto> getAllAnsweredSurveys(int surveyId) {
        return companySurveyDao.getAllResultsOfSurvey(surveyId).stream().map(r->new AnsweredSurveyDto(r.getId(),r.getUser().getAge(),r.getUser().getCountry(),r.getDate())).collect(Collectors.toList());
    }
}
