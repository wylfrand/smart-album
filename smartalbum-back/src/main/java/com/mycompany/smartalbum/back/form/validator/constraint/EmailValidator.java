package com.mycompany.smartalbum.back.form.validator.constraint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

public class EmailValidator implements ConstraintValidator<Email, String> {
    
    private static final String EMAIL_REGEX = "^.+@.+\\.[a-z]+$";
    
    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    // XXX refactoring
    @Override
    public void initialize(final Email constraintAnnotation) {
    }
    
    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext constraintContext) {
        
        if (StringUtils.isBlank(value)) {
            return true;
        }
        
        final Matcher matcher = EMAIL_PATTERN.matcher(value);
        return matcher.matches();
    }
}
