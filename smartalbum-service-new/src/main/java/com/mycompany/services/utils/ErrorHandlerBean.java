package com.mycompany.services.utils;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class ErrorHandlerBean {
    private List<String> errors = Lists.newArrayList();
    
    public List<String> getErrors() {
        return errors;
    }
    
    public boolean isErrorExist() {
        return errors.size() > 0;
    }
    
    /**
     * Convenience method that observes. 
     * After error occured add error to the list of erors andon
     * rerendering modal panel with all errors will be showed.
     * 
     * @param e - string representation of error.
     */
    public void addToErrors(String e) {
        errors.add(e);
    }
}
