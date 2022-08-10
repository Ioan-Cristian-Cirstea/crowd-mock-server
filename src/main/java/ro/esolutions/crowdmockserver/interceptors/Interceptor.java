package ro.esolutions.crowdmockserver.interceptors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

@Component
public class Interceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        String authorization = request.getHeader("authorization");
        if (authorization == null)
            return false;
        authorization = authorization.substring(authorization.indexOf(' ') + 1);
        authorization = new String(Base64.getDecoder().decode(authorization));
        int semicolonIndex = authorization.indexOf(':');
        String username = authorization.substring(0, semicolonIndex);
        String password = authorization.substring(semicolonIndex + 1);
        if (!username.equals("estimator") || !password.equals("ooDicah4"))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unathorized");

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
}