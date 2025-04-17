package nashtech.rookie.uniform.configs.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateTokenFromClaimsSet(JWTClaimsSet claimsSet) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Error signing JWT token");
        }
    }

    public String generateToken(User user) {
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("phoneNumber", user.getPhoneNumber())
                .issuer("uniform.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .build();

        return generateTokenFromClaimsSet(jwtClaimsSet);
    }

    public String generateToken() {
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .issuer("uniform.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(7, ChronoUnit.DAYS).toEpochMilli()
                ))
                .build();

        return generateTokenFromClaimsSet(jwtClaimsSet);
    }

    public JWTClaimsSet extractAllClaims(String token) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            JWSVerifier verifier = new MACVerifier(secretKey.getBytes());

            if (!jwsObject.verify(verifier)) {
                throw new RuntimeException("Token signature invalid");
            }

            Map<String, Object> jsonPayload = jwsObject.getPayload().toJSONObject();
            JWTClaimsSet claims = JWTClaimsSet.parse(jsonPayload);

            Date expiration = claims.getExpirationTime();
            if (expiration != null && expiration.before(new Date())) {
                throw new RuntimeException("Token expired");
            }
            return claims;
        } catch (Exception e) {
            throw new RuntimeException("Error extracting JWT claims: " + e.getMessage(), e);
        }
    }

    public String extractClaim(String token, String claimKey) {
        JWTClaimsSet claims = extractAllClaims(token);
        try{
            return claims.getStringClaim(claimKey);
        } catch (Exception e) {
            return null;
        }
    }

}
