package nashtech.rookie.uniform.application.configuration.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.shared.enums.ErrorCode;
import nashtech.rookie.uniform.shared.exceptions.BadRequestException;
import nashtech.rookie.uniform.shared.exceptions.InternalServerErrorException;
import nashtech.rookie.uniform.user.api.UserInfoProvider;
import nashtech.rookie.uniform.user.dto.UserInfoDto;
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

    private final UserInfoProvider userInfoProvider;

    public String generateTokenFromClaimsSet(JWTClaimsSet claimsSet) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new InternalServerErrorException(ErrorCode.JWT_GENEREATE_ERROR.getCode());
        }
    }

    public String generateToken(UserInfoDto user) {
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

    public String generateToken(String email) {
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .issuer("uniform.com")
                .claim("email", email)
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
                throw new BadRequestException("Your authentication session is invalid. Please log in again to continue!");
            }

            Map<String, Object> jsonPayload = jwsObject.getPayload().toJSONObject();
            JWTClaimsSet claims = JWTClaimsSet.parse(jsonPayload);

            Date expiration = claims.getExpirationTime();
            if (expiration != null && expiration.before(new Date())) {
                throw new BadRequestException("Your session has expired. Please log in again to continue!");
            }
            return claims;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalServerErrorException(ErrorCode.JWT_EXTRACT_CLAIM.getCode());
        }
    }

    public String extractClaim(String token, String claimKey) {
        JWTClaimsSet claims = extractAllClaims(token);
        try{
            return claims.getStringClaim(claimKey);
        } catch (Exception e) {
            throw new InternalServerErrorException(ErrorCode.JWT_EXTRACT_CLAIM.getCode());
        }
    }

}
