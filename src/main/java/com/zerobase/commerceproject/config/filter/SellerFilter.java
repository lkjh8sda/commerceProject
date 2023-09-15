package com.zerobase.commerceproject.config.filter;

import com.zerobase.commerceproject.config.JwtAuthenticationProvider;
import com.zerobase.commerceproject.domain.user.UserVo;
import com.zerobase.commerceproject.service.seller.SellerService;
import lombok.RequiredArgsConstructor;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/seller/*")
@RequiredArgsConstructor
public class SellerFilter implements Filter {
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final SellerService sellerService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("X-AUTH-TOKEN");
        if(!jwtAuthenticationProvider.validateToken(token)){
            throw new ServletException("Invalid Access");
        }
        UserVo userVo = jwtAuthenticationProvider.getUserVo(token);
        sellerService.findByIdAndEmail(userVo.getId(), userVo.getEmail())
                .orElseThrow(() -> new ServletException("Invalid access"));
        chain.doFilter(request,response);
    }
}
