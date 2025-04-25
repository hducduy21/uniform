package nashtech.rookie.uniform.user.internal.validations;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RequirePhoneNumberOrEmailValidator.class)
public @interface RequirePhoneNumberOrEmail {
    String message() default "Passwords do not match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class RequirePhoneNumberOrEmailValidator implements ConstraintValidator<RequirePhoneNumberOrEmail, Object> {

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        try{
            Field emailField = obj.getClass().getDeclaredField("email");
            Field phoneNumberField = obj.getClass().getDeclaredField("phoneNumber");

            emailField.setAccessible(true);
            phoneNumberField.setAccessible(true);

            String email = (String) emailField.get(obj);
            String phoneNumber = (String) phoneNumberField.get(obj);

            if(StringUtils.isNotBlank(email) || StringUtils.isNotBlank(phoneNumber)){
                return true;
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }
}

