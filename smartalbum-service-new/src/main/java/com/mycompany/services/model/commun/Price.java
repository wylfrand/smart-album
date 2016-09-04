package com.mycompany.services.model.commun;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Permet de gérer les prix
 * 
 * @author amv
 */
public class Price implements Serializable{

	private static final long serialVersionUID = 1L;

	private static transient final Logger log = LoggerFactory.getLogger(Price.class);
	
	public final static String POINT_REGEX = "\\.";
	public final static String VIRG = ",";
	public final static String ZERO = "0";
	public final static char POINT_CHAR = '.';
	    
	
	/**
	 * Le prix courrant en euros
	 */
	private double price = 0.0;
	
	
	/**
	 * Construit un objet prix en passant en paramètre une représentation String d'un prix en euro
	 * 
	 * @param strPrice Un prix en euros: 10.50 ou 10,50
	 */
	public Price(String strPrice){
		try{
			price = NumberFormat.getInstance(Locale.FRANCE).parse(strPrice.replaceAll(POINT_REGEX, 
			                                                                          VIRG))
			                                               .doubleValue();
		}catch(ParseException e){
			log.warn("Prix au mauvais format: " + strPrice, e.getMessage());
			price = 0.0;
		}
	}
	
	
	/**
	 * Construit un objet prix en passant en paramètre un prix en centimes d'euros
	 * 
	 * @param price Un prix en centimes d'euros: 1050
	 */
	public Price(long centPrice){
		BigDecimal amount = new BigDecimal(centPrice).movePointLeft(2);
        
      	price = amount.doubleValue();
	}
	
	
	/**
	 * Construit un objet prix en passant en paramètre une représentation décimale d'un prix en euro
	 * 
	 * @param price Un prix en euros: 10.50
	 */
	public Price(double decimalPrice){
		price = decimalPrice;
	}

	
	/**
	 * Indique si le prix correspond à un produit gratuit
	 */
	public boolean isGratuit(){
		return (price == 0);
	}
	
	
	/**
	 * Additionne deux prix
	 */
	public void add(Price addPrice){
		price += addPrice.getPrice();
	}
	
	
	/**
	 * Soustrait deux prix
	 */
	public void sub(Price addPrice){
		price -= addPrice.getPrice();
	}
	
	
	/**
	 * Retourne le nouveau prix après une division
	 */
	public Price dividedBy(long diviser){
		return new Price(price / diviser);
	}
	
	public Price multipledBy(long mul){
	    return new Price(price * mul);
	}
	
	/**
	 * Retourne le prix au format String.
	 * Exemple: 10.56
	 */
	public String getStringPrice(){
		String currentPrice = NumberFormat.getInstance(Locale.FRANCE).format(price).replaceAll(VIRG, 
                                                                                               POINT_REGEX);
		
		int positionPoint = currentPrice.indexOf(POINT_CHAR);
		
		if(positionPoint > 0 && currentPrice.substring(positionPoint).length() < 3){
			currentPrice = currentPrice + ZERO;
		}
		
		return currentPrice;
	}
	
	
	/**
	 * Retourne le prix en centimes au format int.
	 * Exemple: 1056 cents pour 10.50 €
	 */
	public long getCentsPrice(){
		return ((long)(price * 100));
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return getStringPrice();
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object otherPrice){
		if(!(otherPrice instanceof Price)){
			return false;
		}else{
			return (price == ((Price)otherPrice).getPrice());
		}
	}
	
	
	/**
	 * GETTER/SETTER
	 */
	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}
}