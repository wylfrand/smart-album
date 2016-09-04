package com.mycompany.smartalbum.back.form.validator.constraint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    
    private static final String PASSWORD_REGEX = "[a-zA-Z0-9]+";
    
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    
    // XXX refactoring
    @Override
    public void initialize(final Password constraintAnnotation) {
    }
    
    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext constraintContext) {
        
        if (StringUtils.isNotBlank(value)) {
            return true;
        }
        final Matcher matcher = PASSWORD_PATTERN.matcher(value);
        return matcher.matches();
    }
    
}
