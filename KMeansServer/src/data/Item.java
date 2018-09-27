package data;
import java.io.Serializable;

import utility.ArraySet;

/**
 * Classe astratta che modella un generico item (coppia attributo-valore).
 * 
 * @author Giuseppe Pio Maffia, Giovanni Vittore
 *
 */

public abstract class Item implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Attribute attribute;	//NOME DELL'ATTRIBUTO
	protected Object value;			//VALORE DELL'ATTRIBUTO
	
	//METODO COSTRUTTORE DELL'OGGETTO ITEM COSTITUENTE LA COPPIA "NOME ATTRIBUTO = VALORE ATTRIBUTO"
	public Item(Attribute attribute, Object value) {
		this.attribute = attribute;
		this.value = value;
	}
	
	//METODO GETTER PER LA RESTITUZIONE DEL NOME DELL'ATTRIBUTO REFERENZIATO DALLA COPPIA IN ITEM
	Attribute getAttribute() {
		return attribute;
	}
	
	//METODO GETTER PER LA RESTITUZIONE DEL VALORE DELL'ATTRIBUTO REFERENZIATO DALLA COPPIA IN ITEM
	Object getValue() {
		return value;
	}
	
	
	public String toString() {
		return (String)value;
	}
	
	abstract double distance(Object a);
	
	
	public void update(Data data,ArraySet clusteredData) {
		this.value = data.computePrototype(clusteredData,attribute);
	}
	
}
