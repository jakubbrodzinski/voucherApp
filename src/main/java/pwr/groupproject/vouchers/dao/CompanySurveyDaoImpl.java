package pwr.groupproject.vouchers.dao;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;
import pwr.groupproject.vouchers.bean.model.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Collection;

@Component
public class CompanySurveyDaoImpl implements CompanySurveyDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Company getCompanyById(int id) {
        return entityManager.find(Company.class, id);
    }

    @Override
    public Company getCompanyWithSurveys(int id) {
        Company company = entityManager.find(Company.class, id);
        Hibernate.initialize(company.getCompanysSurveys());
        return company;
    }

    @Override
    public Company getCompanyWithSurveysAndQuestions(int id) {
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

    @Override
    public Collection<AnsweredSurvey> getCompanysAllAnsweredSurveys(int companyId) {
        return entityManager.createQuery("SELECT ans FROM "+AnsweredSurvey.class.getName()+" ans JOIN "+Survey.class.getName()+" s ON ans.survey=s.Id WHERE s.company='"+companyId+"'",AnsweredSurvey.class).getResultList();
    }

    @Override
    public Collection<AnsweredSurvey> getAllResultsOfSurvey(int surveyId) {
        return entityManager.createQuery("FROM "+AnsweredSurvey.class.getName() + " WHERE survey='"+surveyId+"'",AnsweredSurvey.class).getResultList();
    }

    @Override
    public Collection<Survey> getCompanysAllSurveys(int companyId) {
        return entityManager.createQuery("FROM "+Survey.class.getName()+" WHERE company='"+companyId+"'",Survey.class).getResultList();
    }

    @Override
    public Collection<Survey> getAvailableSurveys(int companyId) {
        return entityManager.createQuery("SELECT DISTINCT s FROM "+ Survey.class.getName()+" s JOIN "+Voucher.class.getName()+" v ON s.Id=v.survey JOIN "+VoucherCode.class.getName()+" vc ON v.Id=vc.voucher WHERE s.company="+companyId+" AND vc.ammountOfUses>0",Survey.class).getResultList();

    }

    @Override
    public Collection<Company> getAllActiveCompanies() {
        return entityManager.createQuery("SELECT DISTINCT s.company FROM "+ Survey.class.getName()+" s JOIN "+Voucher.class.getName()+" v ON s.Id=v.survey JOIN "+VoucherCode.class.getName()+" vc ON v.Id=vc.voucher WHERE vc.ammountOfUses>0",Company.class).getResultList();
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
    public void addCompany(Company company) {
        entityManager.persist(company);
    }
}
