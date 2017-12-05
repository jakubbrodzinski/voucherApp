package pwr.groupproject.vouchers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pwr.groupproject.vouchers.bean.dto.SurveyStatisticsDto;
import pwr.groupproject.vouchers.bean.model.AnsweredSurvey;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@PreAuthorize("hasRole('COMPANY')")
public class StatisticsServiceImpl implements StatisticsService {
    final private CompanySurveyService companySurveyService;

    @Autowired
    public StatisticsServiceImpl(CompanySurveyService companySurveyService) {
        this.companySurveyService = companySurveyService;
    }

    @Override
    public SurveyStatisticsDto getSurveysStatistics(int surveyId) {
        Collection<AnsweredSurvey> answeredSurveys=companySurveyService.getAllAnsweredSurveys(surveyId);
        SurveyStatisticsDto surveyStatisticsDto=new SurveyStatisticsDto();
        OptionalDouble averageAge=answeredSurveys.stream().mapToInt(a -> a.getUser().getAge()).average();
        averageAge.ifPresent(surveyStatisticsDto::setAge);

        Optional<Map.Entry<String, Long>> country=answeredSurveys.stream().map(a -> a.getUser().getCountry()).collect(Collectors.groupingBy(Function.identity(),Collectors.counting())).entrySet().stream().max(Comparator.comparingLong(Map.Entry::getValue));
        surveyStatisticsDto.setCountry(country.get().getKey());


        return surveyStatisticsDto;
    }
}
