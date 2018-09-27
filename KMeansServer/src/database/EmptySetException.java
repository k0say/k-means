package database;

/**
 * Classe che estende Exception per modellare la restituzione di un resultset vuoto.
 * 
 * @author Giuseppe Pio Maffia, Giovanni Vittore
 *
 */
public class EmptySetException extends Exception {

	private static final long serialVersionUID = 1L;
	String messaggio;
	
	public EmptySetException() {
		messaggio = "ResultSet vuoto!";
	}
	
}
