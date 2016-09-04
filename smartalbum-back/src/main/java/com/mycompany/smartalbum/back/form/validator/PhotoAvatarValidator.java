package com.mycompany.smartalbum.back.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mycompany.services.model.commun.UploadedFile;

@Component
public class PhotoAvatarValidator implements Validator{
    
  
  
 @Override  
 public void validate(Object uploadedFile, Errors errors) {  
  
  UploadedFile file = (UploadedFile) uploadedFile;  
  
  if (file.getFile().getSize() == 0) {  
   errors.rejectValue("file", "uploadForm.salectFile",  
     "Please select a file!");  
  }  
  
 }

 @Override
 public boolean supports(Class<?> clazz) {
     return getClass().isAssignableFrom(clazz);
 }
  
} 