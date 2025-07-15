package ro.academyplus.swingy.utils;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class ValidationUtils {

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public static <T> boolean validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            System.out.println("Validation FAILED");
            for (ConstraintViolation<T> violation : violations) {
                System.out.println("Validation error: " + violation.getMessage());
            }
            return false;
        }
        System.out.println("Validation PASSED");
        return true;
    }

    // Optional: if I want the messages
    public static <T> Set<ConstraintViolation<T>> getViolations(T object) {
        return validator.validate(object);
    }
}
