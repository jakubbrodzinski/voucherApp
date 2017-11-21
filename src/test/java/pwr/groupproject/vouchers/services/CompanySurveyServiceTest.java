package pwr.groupproject.vouchers.services;

import net.bytebuddy.utility.RandomString;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pwr.groupproject.vouchers.bean.exceptions.NoAvaibleVouchersException;
import pwr.groupproject.vouchers.bean.model.*;
import pwr.groupproject.vouchers.bean.model.enums.QuestionType;
import pwr.groupproject.vouchers.configuration.HibernateConfiguration;
import pwr.groupproject.vouchers.dao.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfiguration.class,CompanySurveyServiceImpl.class, VoucherDaoImpl.class,CompanySurveyDaoImpl.class})
public class CompanySurveyServiceTest {
    @Autowired
    private CompanySurveyService companySurveyService;
    @Autowired
    private CompanySurveyDao companySurveyDao;
    @Autowired
    private VoucherDao voucherDao;

    @Test
    @Transactional
    public void unBlockAllBlockedVouchersForLongerThan() throws Exception {
        VoucherCodeDate voucherCodeDate1=new VoucherCodeDate();
        LocalDateTime localDateTime1= LocalDateTime.now().minusMinutes(30);
        Date date1= Date.from(localDateTime1.atZone(ZoneId.systemDefault()).toInstant());
        voucherCodeDate1.setUseDate(date1);

        VoucherCodeDate voucherCodeDate2=new VoucherCodeDate();
        LocalDateTime localDateTime2= LocalDateTime.now().minusHours(1).minusMinutes(5);
        Date date2= Date.from(localDateTime2.atZone(ZoneId.systemDefault()).toInstant());
        voucherCodeDate2.setUseDate(date2);

        VoucherCodeDate voucherCodeDate3=new VoucherCodeDate();
        LocalDateTime localDateTime3= LocalDateTime.now().minusHours(1).minusMinutes(50);
        Date date3= Date.from(localDateTime3.atZone(ZoneId.systemDefault()).toInstant());
        voucherCodeDate3.setUseDate(date3);

        VoucherCodeDate voucherCodeDate4=new VoucherCodeDate();
        LocalDateTime localDateTime4= LocalDateTime.now().minusHours(2);
        Date date4= Date.from(localDateTime4.atZone(ZoneId.systemDefault()).toInstant());
        voucherCodeDate4.setUseDate(date4);

        voucherDao.addVoucherCodeDate(voucherCodeDate1);
        voucherDao.addVoucherCodeDate(voucherCodeDate2);
        voucherDao.addVoucherCodeDate(voucherCodeDate3);
        voucherDao.addVoucherCodeDate(voucherCodeDate4);

        int id1=voucherCodeDate1.getId();
        int id2=voucherCodeDate2.getId();
        int id3=voucherCodeDate3.getId();
        int id4=voucherCodeDate4.getId();

        companySurveyService.unBlockAllBlockedVouchersForLongerThan(1,40);

        VoucherCodeDate v1=voucherDao.getVoucherCodeDateById(id1);
        VoucherCodeDate v2=voucherDao.getVoucherCodeDateById(id2);
        VoucherCodeDate v3=voucherDao.getVoucherCodeDateById(id3);
        VoucherCodeDate v4=voucherDao.getVoucherCodeDateById(id4);

        Assert.assertNotNull(v1);
        Assert.assertNotNull(v2);
        Assert.assertNull(v3);
        Assert.assertNull(v4);
    }

    @Test(expected = Throwable.class)
    @Transactional
    public void getVoucherCodeForSurvey() throws Throwable {
        VoucherCode voucherCode1=new VoucherCode();
        voucherCode1.setVoucherCode(RandomString.make(10));
        VoucherCode voucherCode2=new VoucherCode();
        voucherCode2.setVoucherCode(RandomString.make(10));
        voucherCode2.setAmmountOfUses(0);
        VoucherCode voucherCode3=new VoucherCode();
        voucherCode3.setVoucherCode(RandomString.make(10));
        voucherCode3.setAmmountOfUses(10);
        VoucherCode voucherCode4=new VoucherCode();
        voucherCode4.setVoucherCode(RandomString.make(10));
        voucherCode4.setAmmountOfUses(0);

        Voucher voucher1=new Voucher();
        voucher1.getCodes().add(voucherCode1);
        voucher1.getCodes().add(voucherCode2);
        voucher1.getCodes().add(voucherCode3);
        voucher1.getCodes().add(voucherCode4);

        Survey survey=new Survey();
        survey.setVoucher(voucher1);

        companySurveyDao.addSurvey(survey);
        try {
            Assert.assertNotNull(companySurveyService.getVoucherCodeForSurvey(survey.getId()));
            throw new Throwable();
        }catch(NoAvaibleVouchersException e){
            e.printStackTrace();
        }
    }

    @Test(expected = NoAvaibleVouchersException.class)
    @Transactional
    public void getVoucherCodeForSurvey2() throws NoAvaibleVouchersException {
        VoucherCode voucherCode1=new VoucherCode();
        voucherCode1.setVoucherCode(RandomString.make(10));
        voucherCode1.setAmmountOfUses(0);
        VoucherCode voucherCode2=new VoucherCode();
        voucherCode2.setVoucherCode(RandomString.make(10));
        voucherCode2.setAmmountOfUses(0);
        VoucherCode voucherCode3=new VoucherCode();
        voucherCode3.setVoucherCode(RandomString.make(10));
        voucherCode3.setAmmountOfUses(0);
        VoucherCode voucherCode4=new VoucherCode();
        voucherCode4.setVoucherCode(RandomString.make(10));
        voucherCode4.setAmmountOfUses(0);

        Voucher voucher1=new Voucher();
        voucher1.getCodes().add(voucherCode1);
        voucher1.getCodes().add(voucherCode2);
        voucher1.getCodes().add(voucherCode3);
        voucher1.getCodes().add(voucherCode4);

        Survey survey=new Survey();
        survey.setVoucher(voucher1);

        companySurveyDao.addSurvey(survey);

        companySurveyService.getVoucherCodeForSurvey(survey.getId());
    }

    /*  SCENARIO:
            Company has 3 surveys. One has only active codes, second one has only inactive codes and the third one
            has both active and inactive codes.
     */
    @Test
    @Transactional
    public void getAllActiveSurveys() throws Exception {
        Company company=new Company();
        company.setCompanyName("company1");
        // First Survey
        Survey survey1=new Survey();
        survey1.setCompany(company);

        Question question1=new Question();
        question1.setQuestionType(QuestionType.OPEN);
        question1.setQuestionBody("abc?");
        question1.setSurvey(survey1);
        survey1.getQuestions().add(question1);

        Voucher voucher1=new Voucher();
        survey1.setVoucher(voucher1);
        voucher1.setSurvey(survey1);

        VoucherCode voucherCode1=new VoucherCode();
        voucherCode1.setVoucher(voucher1);
        voucher1.getCodes().add(voucherCode1);
        voucher1.setSurvey(survey1);
        survey1.setVoucher(voucher1);
        //Second survey
        Survey survey2= new Survey();
        survey2.setCompany(company);

        Question question2=new Question();
        question2.setQuestionType(QuestionType.OPEN);
        question2.setQuestionBody("abcabc?");
        question2.setSurvey(survey2);
        Question question3=new Question();
        question3.setQuestionType(QuestionType.OPEN);
        question3.setQuestionBody("abcabcabc?");
        question3.setSurvey(survey2);

        survey2.getQuestions().add(question2);
        survey2.getQuestions().add(question3);

        Voucher voucher2=new Voucher();
        survey2.setVoucher(voucher2);
        voucher2.setSurvey(survey2);

        VoucherCode voucherCode2=new VoucherCode();
        voucherCode2.setAmmountOfUses(0);
        voucherCode2.setVoucher(voucher2);
        voucherCode2.setVoucher(voucher2);
        VoucherCode voucherCode3=new VoucherCode();
        voucherCode3.setVoucherCode(":)");
        voucherCode3.setVoucher(voucher2);

        voucher2.getCodes().add(voucherCode2);
        voucher2.getCodes().add(voucherCode3);
        //Third survey
        Survey survey3=new Survey();
        survey3.setCompany(company);

        Question question4=new Question();
        question4.setQuestionType(QuestionType.RANGED);
        question4.setQuestionBody("xyz?");
        question4.setSurvey(survey3);
        survey3.getQuestions().add(question4);

        Voucher voucher3=new Voucher();
        survey3.setVoucher(voucher3);
        voucher3.setSurvey(survey3);

        VoucherCode voucherCode4=new VoucherCode();
        voucherCode4.setAmmountOfUses(0);
        voucherCode4.setVoucher(voucher3);
        voucher3.getCodes().add(voucherCode4);
        //End of surveys
        company.getCompanysSurveys().add(survey1);
        company.getCompanysSurveys().add(survey2);
        company.getCompanysSurveys().add(survey3);

        companySurveyDao.addCompany(company);
        Collection<Survey> activeSurveys=companySurveyService.getAllActiveSurveys(company.getId());

        Assert.assertEquals(activeSurveys.size(),2);
        int[] arr=activeSurveys.stream().mapToInt(Survey::getId).sorted().toArray();
        Assert.assertEquals(survey1.getId(),arr[0]);
        Assert.assertEquals(survey2.getId(),arr[1]);

    }

    @Test
    @Transactional
    public void getAllActiveCompanies() throws Exception {
        VoucherCode voucherCode1=new VoucherCode();
        voucherCode1.setVoucherCode("code1");
        voucherCode1.setAmmountOfUses(0);
        VoucherCode voucherCode2=new VoucherCode();
        voucherCode2.setVoucherCode("code2");
        voucherCode2.setAmmountOfUses(0);

        Voucher voucher1=new Voucher();
        voucher1.getCodes().add(voucherCode1);
        voucher1.getCodes().add(voucherCode2);
        voucherCode1.setVoucher(voucher1);
        voucherCode2.setVoucher(voucher1);

        Survey survey1=new Survey();
        survey1.setVoucher(voucher1);
        voucher1.setSurvey(survey1);

        Company company1=new Company();
        company1.setCompanyName("xyz1");

        company1.getCompanysSurveys().add(survey1);
        survey1.setCompany(company1);

        VoucherCode voucherCode3=new VoucherCode();
        voucherCode3.setVoucherCode("code3");
        VoucherCode voucherCode4=new VoucherCode();
        voucherCode4.setVoucherCode("code4");
        voucherCode4.setAmmountOfUses(0);

        Voucher voucher2=new Voucher();
        voucher2.getCodes().add(voucherCode3);
        voucher2.getCodes().add(voucherCode4);
        voucherCode3.setVoucher(voucher2);
        voucherCode4.setVoucher(voucher2);

        Survey survey2=new Survey();
        survey2.setVoucher(voucher2);
        voucher2.setSurvey(survey2);

        Company company2=new Company();
        company2.setCompanyName("xyz1");

        company2.getCompanysSurveys().add(survey2);
        survey2.setCompany(company2);

        companySurveyDao.addCompany(company2);
        companySurveyDao.addCompany(company1);

        Assert.assertEquals(1,companySurveyService.getAllActiveCompanies().size());
    }

}