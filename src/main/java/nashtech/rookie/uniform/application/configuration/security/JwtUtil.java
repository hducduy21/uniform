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

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    @Value("${jwt.access.expirationTime}")
    private int jwtAccessExpirationTime;

    @Value("${jwt.refresh.expirationTime}")
    private int jwtRefreshExpirationTime;

    @Value("${jwt.issuer}")
    private String issuer;

    private final UserInfoProvider userInfoProvider;

    public String generateTokenFromClaimsSet(JWTClaimsSet claimsSet) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(jwtSecretKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new InternalServerErrorException(ErrorCode.JWT_GENEREATE_ERROR.getCode());
        }
    }

    public String generateAccessToken(UserInfoDto user) {
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
            .subject(user.getId().toString())
            .claim("email", user.getEmail())
            .claim("phoneNumber", user.getPhoneNumber())
            .issuer(issuer)
            .issueTime(new Date())
            .expirationTime(new Date(
                    Instant.now().plusMillis(jwtAccessExpirationTime).getEpochSecond()
            ))
            .build();

        return generateTokenFromClaimsSet(jwtClaimsSet);
    }

    public String generateRefreshToken(String email) {
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
            .issuer(issuer)
            .claim("email", email)
            .issueTime(new Date())
            .expirationTime(new Date(
                    Instant.now().plusMillis(jwtRefreshExpirationTime).getEpochSecond()
            ))
            .build();

        return generateTokenFromClaimsSet(jwtClaimsSet);
    }

    public String extractClaim(String token, String claimKey) {
        JWTClaimsSet claims = extractAllClaims(token);
        try{
            return claims.getStringClaim(claimKey);
        } catch (Exception e) {
            throw new InternalServerErrorException(ErrorCode.JWT_EXTRACT_CLAIM.getCode());
        }
    }

    public JWTClaimsSet extractAllClaims(String token) {
        try {
            JWSObject jwsObject = parseToken(token);
            verifyTokenSignature(jwsObject);
            JWTClaimsSet claims = extractClaims(jwsObject);
            validateExpiration(claims);
            return claims;
        } catch (Exception e) {
            throw new InternalServerErrorException(ErrorCode.JWT_EXTRACT_CLAIM.getCode());
        }
    }

    private JWSObject parseToken(String token) throws ParseException {
        return JWSObject.parse(token);
    }

    private void verifyTokenSignature(JWSObject jwsObject) throws JOSEException {
        JWSVerifier verifier = new MACVerifier(jwtSecretKey.getBytes());
        if (!jwsObject.verify(verifier)) {
            throw new BadRequestException("Your authentication session is invalid. Please log in again to continue!");
        }
    }

    private JWTClaimsSet extractClaims(JWSObject jwsObject) throws ParseException {
        Map<String, Object> jsonPayload = jwsObject.getPayload().toJSONObject();
        return JWTClaimsSet.parse(jsonPayload);
    }

    private void validateExpiration(JWTClaimsSet claims) {
        Date expiration = claims.getExpirationTime();
        if (expiration != null && expiration.before(new Date())) {
            throw new BadRequestException("Your session has expired. Please log in again to continue!");
        }
    }

}
