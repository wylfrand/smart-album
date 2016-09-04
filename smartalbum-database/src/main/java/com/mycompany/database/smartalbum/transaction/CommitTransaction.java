package com.mycompany.database.smartalbum.transaction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Définit une transaction commitant les modifications pour la couche modèle
 * 
 * @author amv
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED)
public @interface CommitTransaction{

}