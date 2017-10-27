package pwr.groupproject.vouchers.services;

import pwr.groupproject.vouchers.bean.model.AnsweredSurvey;
import pwr.groupproject.vouchers.bean.model.User;

import java.util.Collection;

public interface StatisticsService {
    Collection<User> getAllCompanyRespondents(int companyId);
    Collection<User> getSurveyRespondents(int surveyId);

    AnsweredSurvey getAnsweredSurveyById(int answeredSurveyId);
    Collection<AnsweredSurvey> getAllAnsweredSurveys(int surveyId);
}
