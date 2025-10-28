package fi.ishtech.practice.oms.security.jwt;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import fi.ishtech.base.service.BaseService;
import fi.ishtech.practice.oms.payload.out.JwtResponse;
import fi.ishtech.practice.oms.security.userdetails.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Muneer Ahmed Syed
 */
@Component
@Slf4j
public class JwtService implements BaseService {

	private static final String AUTHORIZATION = "Authorization";
	private static final String BEARER = "Bearer ";

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.experition-ms:60000}")
	private Integer jwtExpirationMs;

	@Value("${jwt.issuer:ishtech.is}")
	private String issuer;

	private String generateToken(String sub, Date iat, Date exp, List<String> roles, Long userId, String name,
			String lang) {
		// @formatter:off
		return Jwts.builder()
				.subject(sub)
				.issuedAt(iat)
				.expiration(exp)
				.claim("roles", roles)
				.claim("uid", userId)
				.claim("name", name)
				.claim("lang", lang)
				.issuer(issuer)
				.header().add("typ", "JWT").and()
				.signWith(jwtKey())
				.compact();
		// @formatter:on
	}

	public boolean validateToken(String authToken) throws JwtException {
		try {
			// @formatter:off
			Jwts.parser()
				.verifyWith(jwtKey())
				.build()
				.parseSignedClaims(authToken);
			// @formatter:on

			return true;
		} catch (ExpiredJwtException e) {
			log.error("JWT is expired: {}", e.getMessage());
			throw e;
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT: {}", e.getMessage());
			throw e;
		} catch (SignatureException e) {
			log.error("Invalid Signature for JWT: {}", e.getMessage());
			throw e;
		} catch (SecurityException e) {
			log.error("JWT decryption failed: {}", e.getMessage());
			throw e;
		} catch (JwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
			throw e;
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
			throw new JwtException("JWT claims string is empty", e);
		} catch (Exception e) {
			log.error("Exception in validating token: {}", e.getMessage());
			throw new JwtException("Exception in validating token", e);
		}
	}

	public String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader(AUTHORIZATION);

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(BEARER)) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		// @formatter:off
		return Jwts.parser()
				.verifyWith(jwtKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
		// @formatter:on
	}

	private SecretKey jwtKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	public JwtResponse generateJwtResponse(UserDetails userDetails) {
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) userDetails;

		Date iat = new Date();
		Date exp = new Date(iat.getTime() + jwtExpirationMs);

		String jwt = generateToken(userDetailsImpl.getUsername(), iat, exp, userDetailsImpl.getScopes(),
				userDetailsImpl.getId(), userDetailsImpl.getFullName(), userDetailsImpl.getLang().name());

		return JwtResponse.of(jwt);
	}

}