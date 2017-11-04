package pwr.groupproject.vouchers.configuration.handlers;

import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import pwr.groupproject.vouchers.bean.enums.ErrorCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        int errorCode;
        if(exception instanceof UsernameNotFoundException){
            errorCode= ErrorCode.WRONG_USERNAME.getStatus();
        }else if(exception instanceof AccountStatusException){
            errorCode= ErrorCode.NOT_ACTIVATED.getStatus();
        }else {
            errorCode=ErrorCode.WRONG_PASSWORD.getStatus();
        }
        getRedirectStrategy().sendRedirect(request,response,"/sign_in?error="+Integer.toString(errorCode));
    }
}
