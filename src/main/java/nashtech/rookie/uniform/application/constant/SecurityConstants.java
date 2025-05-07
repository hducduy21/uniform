package nashtech.rookie.uniform.application.constant;

public class SecurityConstants {
    public static final String[] PERMIT_ALL_ENDPOINTS =
            {
                    "/api/*/auth/login",
                    "/api/*/auth/refresh",
                    "/api/*/users/register",
            };

    public static final String[] DOCUMENTATION_ENDPOINTS =
            {
                    "/v3/api-docs/**",
                    "/swagger-ui/**"
            };

    public static final String[] PERMIT_ONLY_GET_ENDPOINTS =
            {
                    "/api/*/categories/**",
                    "/api/*/products/**",
                    "/api/*/sizes/**",
                    "/api/*/inventories/**",
            };

    public static final String[] ADMIN_ENDPOINTS =
            {
                    "/api/*/admin/**",
            };

    public static final String[] ADMIN_WRITE_ENDPOINTS =
            {
                    "/api/*/categories/**",
                    "/api/*/sizes/**",
                    "/api/*/inventories/**",
                    "/api/*/products/**",
            };
}
