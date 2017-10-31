package pwr.groupproject.vouchers.configuration.handlers;

import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        int errorCode;
        if(exception instanceof UsernameNotFoundException){
            errorCode=1;
        }else if(exception instanceof AccountStatusException){
            errorCode=2;
        }else {
            errorCode=3;
        }
        getRedirectStrategy().sendRedirect(request,response,"/sign_in?error="+Integer.toString(errorCode));
    }
}
