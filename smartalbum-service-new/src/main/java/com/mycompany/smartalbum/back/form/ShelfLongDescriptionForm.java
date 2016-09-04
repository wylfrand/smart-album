/*
 * Créé le 19 mars 10
 */
package com.mycompany.smartalbum.back.form;

import java.io.Serializable;

/**
 * @author amvou
 */
public class ShelfLongDescriptionForm implements Serializable{
        
    private static final long serialVersionUID = -1026509274529890699L;
        String text;
        String operation;
        /**
         * @return
         */
        public String getText() {
                return text;
        }

        /**
         * @param string
         */
        public void setText(String string) {
                text = string;
        }

        /**
         * @return
         */
        public String getOperation() {
                return operation;
        }

        /**
         * @param string
         */
        public void setOperation(String string) {
                operation = string;
        }

}
