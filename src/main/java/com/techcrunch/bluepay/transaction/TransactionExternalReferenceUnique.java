package com.techcrunch.bluepay.transaction;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Validate that the externalReference value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = TransactionExternalReferenceUnique.TransactionExternalReferenceUniqueValidator.class
)
public @interface TransactionExternalReferenceUnique {

    String message() default "{Exists.transaction.externalReference}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class TransactionExternalReferenceUniqueValidator implements ConstraintValidator<TransactionExternalReferenceUnique, String> {

        private final TransactionService transactionService;
        private final HttpServletRequest request;

        public TransactionExternalReferenceUniqueValidator(
                final TransactionService transactionService, final HttpServletRequest request) {
            this.transactionService = transactionService;
            this.request = request;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equalsIgnoreCase(transactionService.get(Long.parseLong(currentId)).getExternalReference())) {
                // value hasn't changed
                return true;
            }
            return !transactionService.externalReferenceExists(value);
        }

    }

}
