package database;

/**
 * Classe che estende Exception per modellare il fallimento della connessione al database.
 * 
 * @author Giuseppe Pio Maffia e Giovanni Vittore
 *
 */
public class DatabaseConnectionException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String messaggio;
	
	public DatabaseConnectionException() {
		messaggio = "Errore nella connessione al database";
	}
	
}
