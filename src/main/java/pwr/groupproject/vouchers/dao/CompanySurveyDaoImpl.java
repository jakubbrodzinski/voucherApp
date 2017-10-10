package pwr.groupproject.vouchers.dao;

import org.hibernate.Hibernate;
import org.hibernate.query.Query;
import pwr.groupproject.vouchers.bean.model.AnsweredSurvey;
import pwr.groupproject.vouchers.bean.model.Company;
import pwr.groupproject.vouchers.bean.model.Survey;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

public class CompanySurveyDaoImpl implements CompanySurveyDao {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Company getCompanyById(int id) {
        return entityManager.find(Company.class, id);
    }

    @Override
    public Company getCompanyWithSurveys(int id) {
        Company company = entityManager.find(Company.class, id);
        Hibernate.initialize(company.getCompanysSurveys());
        company.getCompanysSurveys().stream().map(Survey::getQuestions).forEach(Hibernate::initialize);
        return company;
    }

    @Override
    public Survey getSurveyById(int id) {
        return entityManager.find(Survey.class, id);
    }

    @Override
    public Survey getSurveyWithQuestions(int id) {
        Survey survey = entityManager.find(Survey.class, id);
        Hibernate.initialize(survey.getQuestions());
        return survey;
    }

    @Override
    public AnsweredSurvey getAnsweredSurveyById(int id) {
        return entityManager.find(AnsweredSurvey.class, id);
    }

    @Override
    public AnsweredSurvey getAnsweredSurveyWithAnswers(int id) {
        AnsweredSurvey answeredSurvey = entityManager.find(AnsweredSurvey.class, id);
        Hibernate.initialize(answeredSurvey.getAnswersList());
        Hibernate.initialize(answeredSurvey.getSurvey().getQuestions());
        return answeredSurvey;
    }
    //TO-DO
    @Override
    public Collection<AnsweredSurvey> getCompanysAllAnsweredSurveys(int companyId) {
        return entityManager.createQuery("FROM ANSWEREDSURVEYS a JOIN SURVEY s ON a.surveyId=s.Id WHERE s.companyId='"+companyId+"'",AnsweredSurvey.class).getResultList();
    }

    @Override
    public Collection<AnsweredSurvey> getAllResultsOfSurvey(int surveyId) {
        return entityManager.createQuery("FROM ANSWEREDSURVEYS WHERE surveyId='"+surveyId+"'",AnsweredSurvey.class).getResultList();
    }

    @Override
    public Collection<Survey> getCompanysAllSurveys(int companyId) {
        return entityManager.createQuery("FROM SURVEY WHERE companyId='"+companyId+"'",Survey.class).getResultList();
    }

    @Override
    public void addAnsweredSurvey(AnsweredSurvey answeredSurvey) {
        entityManager.persist(answeredSurvey);
    }

    @Override
    public void deleteAnsweredSurvey(AnsweredSurvey answeredSurvey) {
        entityManager.remove(answeredSurvey);
    }

    @Override
    public void updateAnsweredSurvey(AnsweredSurvey answeredSurvey) {
        entityManager.merge(answeredSurvey);
    }

    @Override
    public void addSurvey(Survey survey) {
        entityManager.persist(survey);
    }

    @Override
    public void updateSurvey(Survey survey) {
        entityManager.merge(survey);
    }

    @Override
    public void deleteSurvey(Survey survey) {
        entityManager.remove(survey);
    }

    @Override
    public void updateCompany(Company company) {
        entityManager.merge(company);
    }

    @Override
    public void deleteCompany(Company company) {
        entityManager.remove(company);
    }

    @Override
    public void createCompany(Company company) {
        entityManager.persist(company);
    }
}
