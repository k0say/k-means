package server;
import java.net.*;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import java.nio.charset.MalformedInputException;
import data.Data;
import data.OutOfRangeSampleSize;
import database.EmptySetException;
import mining.KMeansMiner;

import java.io.*;

/**
 * Classe che estende la classe madre Thread.
 * ServerOneClient implementa l'interfaccia Runnable,
 * la quale la rende in grado di poter essere eseguita essa stessa come un thread,
 * in questa vengono gestite la richiesta di lettura e scoperta di cluster.
 * 
 * @author Giuseppe Pio Maffia, Giovanni Vittore
 *
 */

//classe eseguibile come un thread
public class ServerOneClient implements Runnable {
	
	Scanner tastiera = new Scanner(System.in);
	private Socket socket;
 	private ObjectInputStream in;
	private ObjectOutputStream out;
	private KMeansMiner kmeans;
	private ServerSocket s;

	public ServerOneClient(ServerSocket ss) throws IOException {
	
		socket = new Socket();
		System.out.println("Aspettando la connessione del client..");
		this.s = ss;
		
	}
	
	public void run() {
		
		
		try {
			this.socket = this.s.accept();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 

		BufferedReader input = null;

		try {
			
			PrintWriter output = new PrintWriter(this.socket.getOutputStream(),true);
			input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			int valoreDecisione = input.read();
	
				
				if(valoreDecisione==1) {
				
				ClusterAsker asker = new ClusterAsker();
				int nCluster = asker.Asking();
				TableAsker tableAsk = new TableAsker();
				String table = tableAsk.Asking();
				FileAsker fileAsk = new FileAsker();
				String nomeFile = fileAsk.Asking();
				
				kmeans = new KMeansMiner(nCluster);
				Data dati = new Data(table);
				int nIterazioni = kmeans.kmeans(dati);
				//serializzazione in un file
				FileOutputStream fileOutput = new FileOutputStream(nomeFile+".dat");
				this.out = new ObjectOutputStream(fileOutput);
				out.writeObject(dati);
				out.writeObject(kmeans);
				out.close();
			
			}
			
			if(valoreDecisione==2) {
				
				//FileNameOpener fnOpener = new FileNameOpener();
				//String lookupName = fnOpener.fileNameAsker();
				
				JFileChooser jfc = new JFileChooser();
				int n = jfc.showOpenDialog(jfc);
				File file = jfc.getSelectedFile();
				String lookupName = file.getName();
				FileInputStream fileInput = new FileInputStream(lookupName);
				this.in = new ObjectInputStream(fileInput);
				KMeansMiner read;
				Data readData;
				readData = (Data) this.in.readObject();
				read = (KMeansMiner) this.in.readObject();
				ObjectOutputStream trasferimento = new ObjectOutputStream(this.socket.getOutputStream());
				trasferimento.writeObject(readData);
				trasferimento.writeObject(read);
				this.in.close();
			}
			
	
			
			String terminato = "Programma lato-Server terminato!";
			output.print(terminato);
			output.flush();
			output.close();
			input.close();
			
				} catch (MalformedInputException ex) {
					ex.printStackTrace();
					System.out.println("skipping binary file");
			} catch (IOException | SQLException | EmptySetException | OutOfRangeSampleSize e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Errore di I/O");
		} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	
}
