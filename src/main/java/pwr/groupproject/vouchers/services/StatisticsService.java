package pwr.groupproject.vouchers.services;

import org.springframework.security.access.prepost.PreAuthorize;
import pwr.groupproject.vouchers.bean.dto.SurveyStatisticsDto;
import pwr.groupproject.vouchers.bean.dto.answered.AnsweredSurveyDto;
import pwr.groupproject.vouchers.bean.exceptions.WrongAnsweredSurveyIdException;
import pwr.groupproject.vouchers.bean.model.AnsweredSurvey;

import java.util.Collection;

public interface StatisticsService {
    @PreAuthorize("hasRole('COMPANY')")
    SurveyStatisticsDto getSurveysStatistics(int surveyId);

    @PreAuthorize("hasRole('COMPANY')")
    Collection<AnsweredSurvey> getAllAnsweredSurveysWithDetails(int surveyId);

    @PreAuthorize("hasRole('COMPANY')")
    Collection<AnsweredSurveyDto> getAllAnsweredSurveys(int surveyId);

    @PreAuthorize("hasRole('COMPANY')")
    AnsweredSurveyDto getResultDetails(int answeredSurveyId) throws WrongAnsweredSurveyIdException;

    @PreAuthorize("hasRole('COMPANY')")
    AnsweredSurvey getSingleAnsweredSurveyById(int answeredSurveyId) throws WrongAnsweredSurveyIdException;
}
