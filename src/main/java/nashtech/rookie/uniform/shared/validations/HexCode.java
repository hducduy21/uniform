package nashtech.rookie.uniform.shared.validations;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {HexCodeValidator.class, HexCodeCollectionValidator.class})
public @interface HexCode {
    String message() default "Color code is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class HexCodeValidator implements ConstraintValidator<HexCode, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && value.matches("^#([0-9A-Fa-f]{3}|[0-9A-Fa-f]{6})$");
    }
}

class HexCodeCollectionValidator implements ConstraintValidator<HexCode, Collection<String>> {
    @Override
    public boolean isValid(Collection<String> collections, ConstraintValidatorContext constraintValidatorContext) {
        if(collections == null) {
            return true;
        }

        return collections.stream().allMatch(collection -> collection.matches("^#([0-9A-Fa-f]{3}|[0-9A-Fa-f]{6})$"));
    }
}


