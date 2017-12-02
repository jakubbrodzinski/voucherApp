package pwr.groupproject.vouchers.configuration.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pwr.groupproject.vouchers.bean.model.VoucherCode;
import pwr.groupproject.vouchers.bean.model.VoucherCodeDate;
import pwr.groupproject.vouchers.services.CompanySurveyService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class VoucherCodeFilter implements Filter {
    @Autowired
    private CompanySurveyService companySurveyService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();
        Integer vDateId = (Integer) session.getAttribute("vCode");
        if (vDateId != null) {
            try {
                VoucherCodeDate voucherCodeDate = companySurveyService.getVoucherCodeDateById(vDateId);
                LocalDateTime localDateTime = LocalDateTime.now().minusMinutes(20);
                if (Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()).compareTo(voucherCodeDate.getUseDate()) > 0) {
                    session.setAttribute("vCode", null);
                    companySurveyService.deleteVoucherCodeDate(vDateId);
                }
            } catch (Exception e) {
                session.setAttribute("vCode", null);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
