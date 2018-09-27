package database;

/**
 * Classe che estende Exception per modellare l'assenza di un valore all'interno di un resultset.
 * 
 * @author Giuseppe Pio Maffia, Giovanni Vittore
 */
public class NoValueException extends Exception {
		
	private static final long serialVersionUID = 1L;
	String messaggio;
	
	public NoValueException() {
		messaggio = "Nessun contenuto";
	}
	
}
