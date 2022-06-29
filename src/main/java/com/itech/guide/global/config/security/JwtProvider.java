package com.itech.guide.global.config.security;

import com.itech.guide.domain.member.entity.Roles;
import com.itech.guide.domain.member.service.CustomMemberDetailService;
import com.itech.guide.global.common.redis.RedisService;
import com.itech.guide.global.error.exception.BadRequestException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final RedisService redisService;
    private final CustomMemberDetailService customMemberDetailService;
    @Value("${spring.jwt.secret-key}")
    private  String SECRET_KEY;
    @Value("${spring.jwt.blacklist.access-token}")
    private String blackListATPrefix;
    @Value("${spring.jwt.access-token-invalid-hour}")
    private String accessTokenInvalidHour;
    @Value("${spring.jwt.refresh-token-invalid-hour}")
    private String refreshTokenInvalidHour;


    private static final Long TOKEN_VALID_TIME = 1000L * 60 * 3; // 3m

    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }


    public void logout(String userId, String accessToken) {
        long expiredAccessTokenTime = getExpiredTime(accessToken).getTime() - new Date().getTime();
        redisService.setValues(blackListATPrefix + accessToken, userId, Duration.ofMillis(expiredAccessTokenTime));
        redisService.deleteValues(userId); // Delete RefreshToken In Redis
    }


    public String createToken(String userId, Collection<Roles> roles,Long tokenInvalidTime){
        Claims claims = Jwts.claims().setSubject(userId); // claims 생성 및 payload 설정
        claims.put("roles", mergeStream(roles)); // 권한 설정, key/ value 쌍으로 저장
        claims.setId(UUID.randomUUID().toString());
        claims.put("https://itech.com/jwt_claims",true);
        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims) // 발행 유저 정보 저장
                .setIssuedAt(date) // 발행 시간 저장
                .setExpiration(new Date(date.getTime() + tokenInvalidTime)) // 토큰 유효 시간 저장
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 해싱 알고리즘 및 키 설정
                .compact(); // 생성
    }

    private String mergeStream(Collection<Roles> roles) {
        return roles.stream().map(role -> role.getRole().name()).collect(Collectors.joining(", "));
    }

    public Authentication validateToken(HttpServletRequest request, String token) {
        String exception = "exception";
        try {
            String expiredAT = redisService.getValues(blackListATPrefix + token);
            if (expiredAT != null) {
                throw new ExpiredJwtException(null, null, null);
            }
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return getAuthentication(token);
        } catch (MalformedJwtException | SignatureException | UnsupportedJwtException e) {
            request.setAttribute(exception, "토큰의 형식을 확인하세요");
        } catch (ExpiredJwtException e) {
            request.setAttribute(exception, "토큰이 만료되었습니다.");
        } catch (IllegalArgumentException e) {
            request.setAttribute(exception, "JWT compact of handler are invalid");
        }
        return null;
    }

    private Authentication getAuthentication(String token) {
        UserDetails userDetails = customMemberDetailService.loadUserByUsername(getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUserEmail(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public String createAccessToken(String userId, Collection<Roles> roles) {

        long tokenInvalidTime = 1000L * 60 * 60 * Integer.parseInt(accessTokenInvalidHour);
       // Long tokenInvalidTime = 1000L * 60 * 1; // 1m
        return this.createToken(userId, roles, tokenInvalidTime);
    }

    public String createRefreshToken(String userId, Collection<Roles> roles) {
        long tokenInvalidTime = 1000L * 60 * 60 * Integer.parseInt(refreshTokenInvalidHour);
        String refreshToken = this.createToken(userId, roles, tokenInvalidTime);
        redisService.setValues(userId, refreshToken, Duration.ofMillis(tokenInvalidTime));
        return refreshToken;
    }

    public void checkRefreshToken(String name, String refreshToken) {
        String redisRT = redisService.getValues(name);
        if (!refreshToken.equals(redisRT)) {
            throw new BadRequestException("토큰이 만료되었습니다.");
        }
    }
    private Date getExpiredTime(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration();
    }
}
