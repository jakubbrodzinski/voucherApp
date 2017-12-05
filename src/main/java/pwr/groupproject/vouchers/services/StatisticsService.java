package pwr.groupproject.vouchers.services;

import pwr.groupproject.vouchers.bean.dto.SurveyStatisticsDto;

public interface StatisticsService {
    /*Collection<User> getAllCompanyRespondents(int companyId);
    Collection<User> getSurveyRespondents(int surveyId);
    AnsweredSurvey getAnsweredSurveyById(int answeredSurveyId);
    Collection<AnsweredSurvey> getAllAnsweredSurveys(int surveyId);*/
    SurveyStatisticsDto getSurveysStatistics(int surveyId);
}
