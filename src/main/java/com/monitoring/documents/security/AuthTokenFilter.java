package com.monitoring.documents.security;




import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;



import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



import com.monitoring.documents.security.jwt.JwtUtils;
import com.monitoring.documents.services.UserService;



public class AuthTokenFilter extends OncePerRequestFilter {



    @Resource
    private JwtUtils jwtUtils;



    @Resource
    private UserService userService;



    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(httpServletRequest);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUsernameFromToken(jwt);



                UserDetails userDetails = userService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));



                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }



        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }



    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");



        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }



        return null;
    }
}