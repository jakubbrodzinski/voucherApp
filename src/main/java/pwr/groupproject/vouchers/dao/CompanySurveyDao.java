package pwr.groupproject.vouchers.dao;

import pwr.groupproject.vouchers.bean.model.AnsweredSurvey;
import pwr.groupproject.vouchers.bean.model.Company;
import pwr.groupproject.vouchers.bean.model.Survey;

import java.util.Collection;

public interface CompanySurveyDao {
    Company getCompanyById(int id);
    Company getCompanyWithSurveys(int id);

    Survey getSurveyById(int id);
    Survey getSurveyWithQuestions(int id);

    AnsweredSurvey getAnsweredSurveyById(int id);
    AnsweredSurvey getAnsweredSurveyWithAnswers(int id);

    Collection<AnsweredSurvey> getCompanysAllAnsweredSurveys(int companyId);
    Collection<AnsweredSurvey> getAllResultsOfSurvey(int surveyId);

    Collection<Survey> getCompanysAllSurveys(int companyId);
    Collection<Survey> getAvaibleSurveys(int companyId);

    void addAnsweredSurvey(AnsweredSurvey answeredSurvey);
    void deleteAnsweredSurvey(AnsweredSurvey answeredSurvey);
    void updateAnsweredSurvey(AnsweredSurvey answeredSurvey);

    void addSurvey(Survey survey);
    void updateSurvey(Survey survey);
    void deleteSurvey(Survey survey);

    void updateCompany(Company company);
    void deleteCompany(Company company);
    void createCompany(Company company);
}
