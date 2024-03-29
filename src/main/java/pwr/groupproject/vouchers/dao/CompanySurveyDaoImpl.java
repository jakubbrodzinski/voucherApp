package pwr.groupproject.vouchers.dao;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;
import pwr.groupproject.vouchers.bean.exceptions.WrongAnsweredSurveyIdException;
import pwr.groupproject.vouchers.bean.model.*;
import pwr.groupproject.vouchers.bean.model.security.UserCompany;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class CompanySurveyDaoImpl implements CompanySurveyDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Company getUsersCompany(int userCompanyId) {
        return entityManager.createQuery("SELECT uc.company FROM " + UserCompany.class.getName() + " uc WHERE uc.Id=" + userCompanyId, Company.class).getSingleResult();
    }


    @Override
    public Company getCompanyById(int id) {
        return entityManager.find(Company.class, id);
    }

    @Override
    public Company getCompanyWithSurveys(Company company) {
        Hibernate.initialize(company.getCompanysSurveys());
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
    public AnsweredSurvey getAnsweredSurveyWithAnswers(int id) throws WrongAnsweredSurveyIdException {
        AnsweredSurvey answeredSurvey = entityManager.find(AnsweredSurvey.class, id);
        if(answeredSurvey==null)
            throw new WrongAnsweredSurveyIdException();
        Hibernate.initialize(answeredSurvey.getAnswersList());
        Hibernate.initialize(answeredSurvey.getSurvey().getQuestions());
        return answeredSurvey;
    }

    @Override
    public Collection<AnsweredSurvey> getCompanysAllAnsweredSurveys(int companyId) {
        return entityManager.createQuery("SELECT ans FROM " + AnsweredSurvey.class.getName() + " ans JOIN " + Survey.class.getName() + " s ON ans.survey=s.Id WHERE s.company='" + companyId + "'", AnsweredSurvey.class).getResultList();
    }

    @Override
    public Collection<AnsweredSurvey> getAllResultsOfSurveyWithDetails(int surveyId) {
        try {
            Collection<AnsweredSurvey> result= entityManager.createQuery("FROM " + AnsweredSurvey.class.getName() + " WHERE survey='" + surveyId + "'", AnsweredSurvey.class).getResultList();
            result.forEach(u->Hibernate.initialize(u.getAnswersList()));
            return  result;
        }catch(NoResultException e){
            return new ArrayList<>();
        }
    }

    @Override
    public Collection<AnsweredSurvey> getAllResultsOfSurvey(int surveyId) {
        try {
            return entityManager.createQuery("FROM " + AnsweredSurvey.class.getName() + " WHERE survey='" + surveyId + "'", AnsweredSurvey.class).getResultList();
        }catch(NoResultException e){
            return new ArrayList<>();
        }
    }

    @Override
    public Collection<Survey> getCompanysAllSurveys(int companyId) {
        return entityManager.createQuery("FROM " + Survey.class.getName() + " WHERE company='" + companyId + "'", Survey.class).getResultList();
    }

    @Override
    public Collection<Survey> getAvailableSurveys(int companyId) {
        entityManager.find(Company.class, companyId);
        return entityManager.createQuery("SELECT DISTINCT s FROM " + Survey.class.getName() + " s JOIN " + Voucher.class.getName() + " v ON s.Id=v.survey JOIN " + VoucherCode.class.getName() + " vc ON v.Id=vc.voucher WHERE s.company=" + companyId + " AND vc.ammountOfUses>0", Survey.class).getResultList();

    }

    @Override
    public Collection<Company> getAllActiveCompanies() {
        return entityManager.createQuery("SELECT DISTINCT s.company FROM " + Survey.class.getName() + " s JOIN " + Voucher.class.getName() + " v ON s.Id=v.survey JOIN " + VoucherCode.class.getName() + " vc ON v.Id=vc.voucher WHERE vc.ammountOfUses>0", Company.class).getResultList();
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
    public AnsweredSurvey updateAnsweredSurvey(AnsweredSurvey answeredSurvey) {
        return entityManager.merge(answeredSurvey);
    }

    @Override
    public void addSurvey(Survey survey) {
        entityManager.persist(survey);
    }

    @Override
    public Survey updateSurvey(Survey survey) {
        return entityManager.merge(survey);
    }

    @Override
    public void deleteSurvey(Survey survey) {
        entityManager.remove(survey);
    }

    @Override
    public Company updateCompany(Company company) {
        return entityManager.merge(company);
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
