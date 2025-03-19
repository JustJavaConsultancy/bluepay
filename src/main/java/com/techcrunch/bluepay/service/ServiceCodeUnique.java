package com.techcrunch.bluepay.service;

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
 * Validate that the code value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = ServiceCodeUnique.ServiceCodeUniqueValidator.class
)
public @interface ServiceCodeUnique {

    String message() default "{Exists.service.code}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class ServiceCodeUniqueValidator implements ConstraintValidator<ServiceCodeUnique, String> {

        private final ServiceService serviceService;
        private final HttpServletRequest request;

        public ServiceCodeUniqueValidator(final ServiceService serviceService,
                final HttpServletRequest request) {
            this.serviceService = serviceService;
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
            if (currentId != null && value.equalsIgnoreCase(serviceService.get(Long.parseLong(currentId)).getCode())) {
                // value hasn't changed
                return true;
            }
            return !serviceService.codeExists(value);
        }

    }

}
