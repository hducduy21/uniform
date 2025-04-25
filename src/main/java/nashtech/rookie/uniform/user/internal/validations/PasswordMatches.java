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
@Constraint(validatedBy = PasswordMatchesValidator.class)
public @interface PasswordMatches{
    String message() default "Passwords do not match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

//    java.lang.annotation.ElementType[] value() default {java.lang.annotation.ElementType.TYPE};
}

class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        try{
            Field passwordField = obj.getClass().getDeclaredField("password");
            Field confirmPasswordField = obj.getClass().getDeclaredField("confirmPassword");

            passwordField.setAccessible(true);
            confirmPasswordField.setAccessible(true);

            String password = (String) passwordField.get(obj);
            String confirmPassword = (String) confirmPasswordField.get(obj);

            if(StringUtils.isNotBlank(password) && StringUtils.equals(password, confirmPassword)){
                return true;
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }
}
