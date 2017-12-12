package pwr.groupproject.vouchers.services;

import org.springframework.security.access.prepost.PreAuthorize;
import pwr.groupproject.vouchers.bean.dto.SurveyStatisticsDto;
import pwr.groupproject.vouchers.bean.dto.answered.AnsweredSurveyDto;
import pwr.groupproject.vouchers.bean.model.AnsweredSurvey;

import java.util.Collection;

public interface StatisticsService {
    /*Collection<User> getAllCompanyRespondents(int companyId);
    Collection<User> getSurveyRespondents(int surveyId);
    AnsweredSurvey getAnsweredSurveyById(int answeredSurveyId);
    Collection<AnsweredSurvey> getAllAnsweredSurveysWithDetails(int surveyId);*/
    @PreAuthorize("hasRole('COMPANY')")
    SurveyStatisticsDto getSurveysStatistics(int surveyId);

    @PreAuthorize("hasRole('COMPANY')")
    Collection<AnsweredSurvey> getAllAnsweredSurveysWithDetails(int surveyId);

    @PreAuthorize("hasRole('COMPANY')")
    Collection<AnsweredSurveyDto> getAllAnsweredSurveys(int surveyId);
}
