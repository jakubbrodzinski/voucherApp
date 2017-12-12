package pwr.groupproject.vouchers.dao;

import pwr.groupproject.vouchers.bean.model.AnsweredSurvey;
import pwr.groupproject.vouchers.bean.model.Company;
import pwr.groupproject.vouchers.bean.model.Survey;

import java.util.Collection;

public interface CompanySurveyDao {
    Company getUsersCompany(int userCompanyId);
    Company getCompanyById(int id);
    Company getCompanyWithSurveys(Company company);

    Survey getSurveyById(int id);
    Survey getSurveyWithQuestions(int id);

    AnsweredSurvey getAnsweredSurveyWithAnswers(int id);

    Collection<AnsweredSurvey> getCompanysAllAnsweredSurveys(int companyId);
    Collection<AnsweredSurvey> getAllResultsOfSurveyWithDetails(int surveyId);
    Collection<AnsweredSurvey> getAllResultsOfSurvey(int surveyId);

    Collection<Survey> getCompanysAllSurveys(int companyId);
    Collection<Survey> getAvailableSurveys(int companyId);

    Collection<Company> getAllActiveCompanies();

    void addAnsweredSurvey(AnsweredSurvey answeredSurvey);
    void deleteAnsweredSurvey(AnsweredSurvey answeredSurvey);
    AnsweredSurvey updateAnsweredSurvey(AnsweredSurvey answeredSurvey);

    void addSurvey(Survey survey);
    Survey updateSurvey(Survey survey);
    void deleteSurvey(Survey survey);

    Company updateCompany(Company company);
    void deleteCompany(Company company);
    void addCompany(Company company);
}
