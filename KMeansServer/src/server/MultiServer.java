package server;
import java.net.*;
import java.io.*;


/**
 * Questa classe nota come MultiServer viene impiegata per creare un Server 
 * in grado di gestire le richieste del client
 * 
 * @author Giuseppe Pio Maffia, Giovanni Vittore
 *
 */

public class MultiServer {

	private int PORT = 9090;
		
		
		//METODO COSTRUTTORE
		public MultiServer(int port) throws IOException {
			port = this.PORT;
			this.run();
			
		}
		
		//METODO ESECUTORE
		private void run() throws IOException {

			//Socket lato server
			
			ServerSocket ss = new ServerSocket(this.PORT);
			Thread thread = new Thread(new ServerOneClient(ss));
			thread.start();
			}
		
		public static void main(String[] args) throws IOException {
			MultiServer Server = new MultiServer(9090);
		}
}
