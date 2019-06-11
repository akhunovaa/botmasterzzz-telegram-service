package com.botmasterzzz.telegram.provider;

import com.botmasterzzz.telegram.dto.UserPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class JwtTokenProvider {

    @Value("${auth.tokenSecret}")
    private String secretKey;

    @Value("${auth.expire.time}")
    private String validityInMilliseconds;

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @SuppressWarnings("unchecked")
    public Authentication getAuthentication(String token) {
        UserPrincipal userPrincipal = getAuthFromToken(token);

        Collection<? extends GrantedAuthority> authorities = setUserAuthorities((List<GrantedAuthority>) userPrincipal.getAuthorities());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(token);
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }


    @SuppressWarnings("unchecked")
    public UserPrincipal getAuthFromToken(String token) {
        UserPrincipal userPrincipal = new UserPrincipal();
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        Long id = Long.valueOf(claims.getId());
        String login = (String) claims.get("login");
        String email = (String) claims.get("email");
        String issuer = claims.getIssuer();
        Collection<? extends GrantedAuthority> authorities;
        authorities = (List<GrantedAuthority>) claims.get("authorities");
        Date issuedDate = claims.getIssuedAt();
        Date expireDate = claims.getExpiration();
        if (new Date().after(expireDate)){
            userPrincipal.setExpired(true);
        }
        userPrincipal.setId(id);
        userPrincipal.setLogin(login);
        userPrincipal.setEmail(email);
        userPrincipal.setIssuer(issuer);
        userPrincipal.setAuthorities(authorities);
        return userPrincipal;
    }

    public Collection<GrantedAuthority> setUserAuthorities(List<GrantedAuthority> auths) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        String authority = (String) ((LinkedHashMap) auths.get(0)).get("authority");
        grantedAuthorities.add(new GrantedAuthorityImpl(authority));
        return grantedAuthorities;
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}