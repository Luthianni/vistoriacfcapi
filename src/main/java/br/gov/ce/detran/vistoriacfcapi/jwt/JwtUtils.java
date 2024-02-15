package br.gov.ce.detran.vistoriacfcapi.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("deprecation")
@Slf4j
public class JwtUtils {
	
	public static final String JWT_BEARER = "Bearer";
	public static final String JWT_AUTHORIZATION = "Authorization";
	public static final String SECRET_KEY = "0123456789-0123456789-0123456789";
	public static final long EXPIRE_DAYS = 0;
	public static final long EXPIRE_HOURS = 0;
	public static final long EXPIRE_MINUTES = 30;
	public static final long REFRESH_EXPIRE_MINUTES = 60 * 24 * 7;
	
	private JwtUtils() {
		
	}
	
	private static Key generatekey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	}
	
	private static Date toExpireDate(Date start, long expirateMinutes) {
		LocalDateTime dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime end = dateTime.plusDays(EXPIRE_DAYS).plusHours(EXPIRE_HOURS).plusMinutes(EXPIRE_MINUTES);
		return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static JwtToken createToken(String username, String role) {
    String usernameString = String.valueOf(username);

    Date issuedAt = new Date();
    Date accessTokenExpiration = toExpireDate(issuedAt, EXPIRE_MINUTES);
    Date refreshTokenExpiration = toExpireDate(issuedAt, REFRESH_EXPIRE_MINUTES);

    String accessToken = buildToken(usernameString, issuedAt, accessTokenExpiration, role, false);
    String refreshToken = buildToken(usernameString, issuedAt, refreshTokenExpiration, null, true);

    return new JwtToken(accessToken, refreshToken);
}

private static String buildToken(String subject, Date issuedAt, Date expiration, String role, boolean isRefreshToken) {
    JwtBuilder jwtBuilder = Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setSubject(subject)
            .setIssuedAt(issuedAt)
            .setExpiration(expiration)
            .signWith(generatekey(), SignatureAlgorithm.HS256);

    if (role != null) {
        jwtBuilder.claim("role", role);
    }

    if (isRefreshToken) {
        jwtBuilder.claim("refresh", true);
    }

    return jwtBuilder.compact();
}
	public static Claims getClaimsFromToken(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(generatekey()).build()
				.parseClaimsJws(refactorToken(token)).getBody();	
			
		} catch (JwtException ex) {
			log.error(String.format("Token invalido %s", ex.getMessage()));
		}
		return null;
	}

	public static String getUsernameFromToken(String token) {
		return getClaimsFromToken(token).getSubject();
	}

	public static boolean isTokenValid(String token) {
			try {
			Jwts.parserBuilder()
				.setSigningKey(generatekey()).build()
				.parseClaimsJws(refactorToken(token));	
			return true;
		} catch (JwtException ex) {
			log.error(String.format("Token invalido %s", ex.getMessage()));
		}
		return false;
	}

	private static String refactorToken(String token) {
		if (token.contains(JWT_BEARER)) {
			return token.substring(JWT_BEARER.length());
		}
		return token;
	}

	public static boolean isRefreshToken(String refreshToken) {
		Claims claims = getClaimsFromToken(refreshToken);
		return claims != null && claims.get("refresh", Boolean.class);
	}
	
	public static boolean isRefreshTokenValid(String refreshToken) {
		try {
			Jwts.parserBuilder()
					.setSigningKey(generatekey()).build()
					.parseClaimsJws(refactorToken(refreshToken));
			return true;
		} catch (JwtException ex) {
			log.error(String.format("Refresh Token inválido %s", ex.getMessage()));
		}
		return false;
	}
	
	
}
