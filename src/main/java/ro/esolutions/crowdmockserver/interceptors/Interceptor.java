package ro.esolutions.crowdmockserver.interceptors;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import ro.esolutions.crowdmockserver.utilities.CheckCredentials;
import ro.esolutions.crowdmockserver.utilities.Authorization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class Interceptor implements HandlerInterceptor {
    private final CheckCredentials checkCredentials;
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        final String requestURI = request.getRequestURI();
        if (requestURI.equals("/error"))
            return true;
        final Authorization authorization = new Authorization(request.getHeader("authorization"));
        if (!authorization.getType().equals("Basic"))
            throw throwError();
        final String username = authorization.getUsername();
        final String password = authorization.getPassword();
        if (requestURI.indexOf("appmanagement") != -1) {
            if (!checkCredentials.checkAdmin(username, password))
                throw throwError();
        }
        else if (!checkCredentials.checkApplication(username, password))
            throw throwError();

        return true;
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                           @NonNull Object handler, @Nullable ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                @NonNull Object handler, Exception exception) {

    }

    private ResponseStatusException throwError() {
        return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
    }
}
