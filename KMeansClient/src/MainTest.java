import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import data.*;
import utility.ArraySet;
import mining.*;

/**
 * 
 * Includere la classe MainTest che stabilisce la connessione al Server e, una volta avvenuta la connessione, invia e riceve messaggi, dipendentemente dalla scelta effettuata dall'utente. 
 * Attraverso un menu, l'utente del client seleziona la attività da svolgere, scoperta/lettura di cluster.  
 * Se la scelta è una attività di scoperta si invia al Server il numero di cluster da scoprire, il nome della tabella di database, il nome del file in cui serializzare i cluster scoperti. 
 * Se la scelta è una attività di lettura si invia al Server il nome del file in cui sono serializzati i cluster da recuperare. 
 * In entrambe le attività il cliente acquisisce il risultato trasmesso dal server o lo visualizza a video.
 * 
 * @author Giuseppe Pio Maffia, Giovanni Vittore
 * 
 */


public class MainTest {
	
	
	PrintWriter output;
	BufferedReader input;
	ObjectInputStream in;
	Socket sock;
	String text;
	
	public MainTest(String ipAddress,int porta) throws UnknownHostException, IOException {
		
		
		Scanner tastiera = new Scanner(System.in);
		String localHost = "localhost";
		int portaConnessione = porta;
		String indirizzoIp;
		if(localHost.equalsIgnoreCase(ipAddress)) {
			String locConnection = localHost;
			this.sock = new Socket(locConnection,porta);
		}
		else {
			indirizzoIp = ipAddress;
			this.sock = new Socket(indirizzoIp,porta);
		}
		
		this.output = new PrintWriter(this.sock.getOutputStream(),true);
		this.input = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));

	}
	
	public void Lettura() throws ClassNotFoundException, IOException {
		output.write(2);
		output.flush();
		in = new ObjectInputStream(this.sock.getInputStream());
		Data dati = (Data) this.in.readObject();
		KMeansMiner kmeans = (KMeansMiner) this.in.readObject();
		//System.out.println(kmeans.getC().toString(dati));       
		this.text = kmeans.getC().toString(dati);
	}
	
	public void Scoperta() throws IOException {
		output.write(1);
		output.flush();
		
	}
	
	
}