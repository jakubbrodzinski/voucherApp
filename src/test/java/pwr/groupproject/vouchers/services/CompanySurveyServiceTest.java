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
import pwr.groupproject.vouchers.bean.model.Survey;
import pwr.groupproject.vouchers.bean.model.Voucher;
import pwr.groupproject.vouchers.bean.model.VoucherCode;
import pwr.groupproject.vouchers.bean.model.VoucherCodeDate;
import pwr.groupproject.vouchers.configuration.HibernateConfiguration;
import pwr.groupproject.vouchers.dao.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

}