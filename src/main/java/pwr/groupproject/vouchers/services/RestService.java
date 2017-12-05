package pwr.groupproject.vouchers.services;


import pwr.groupproject.vouchers.bean.dto.rest.AnsweredSurveyDtoRest;

import java.util.TreeMap;

public interface RestService {
    void addAnsweredSurvey(AnsweredSurveyDtoRest answeredSurveyForm, int surveyId);
    TreeMap<Integer, String> validateAnsweredSurveyDtoRest(AnsweredSurveyDtoRest answeredSurveyDtoRest, int surveyId);
}
