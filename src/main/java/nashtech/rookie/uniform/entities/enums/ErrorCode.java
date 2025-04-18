package nashtech.rookie.uniform.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    JWT_GENEREATE_ERROR(1301,""),
    JWT_EXTRACT_CLAIM(1032, "");

    private final int code;
    private final String message;

    public static ErrorCode fromCode(int code) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.getCode() == code) {
                return errorCode;
            }
        }
        throw new IllegalArgumentException("Unknown error code: " + code);
    }
}

