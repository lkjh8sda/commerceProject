package com.zerobase.commerceproject.config.Interceptor;

import com.zerobase.commerceproject.config.JwtAuthenticationProvider;
import com.zerobase.commerceproject.domain.user.UserVo;
import com.zerobase.commerceproject.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.zerobase.commerceproject.exception.ErrorCode.NOT_AUTHENTICATED;

@RequiredArgsConstructor
public class SellerInterceptor implements HandlerInterceptor {
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("X-AUTH-TOKEN");
        UserVo userVo = jwtAuthenticationProvider.getUserVo(token);

        if(userVo.getUserType().equals("CUSTOMER")){
            throw new CustomException(NOT_AUTHENTICATED);
        }

        return true;
    }
}
