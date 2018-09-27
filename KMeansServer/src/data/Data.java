package data;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import database.DatabaseConnectionException;
import database.DbAccess;
import database.EmptySetException;
import database.Example;
import database.TableData;
import utility.ArraySet;

public class Data implements Serializable {
	

	private static final long serialVersionUID = 1L;

	private Object data [][]; //MATRICE N X M IN CUI GLI ELEMENTI RAPPRESENTANO DEGLI ATTRIBUTI DISCRETI E OGNI RIGA DI
						      //TALE MATRICE RAPPRESENTA UNA TRANSAZIONE
	
	private int numberOfExamples; //CARDINALITÀ DELL'INSIEME DI TRANSAZIONI (OGNI RIGA CORRISPONDE AD UNA TRANSAZIONE)
	
	private Attribute explanatorySet[]; //VETTORE I CUI ELEMENTI SONO GLI ATTRIBUTI DI OGNI RIGA
	
	private int distinctTuples;
	
	//METODO CHE INIZIALIZZA I VALORI DELLA MATRICE COME FORNITO IN ESEMPIO
	public Data(String table) throws SQLException, EmptySetException{
		
		try {
			
			data = new Object[14][5];
			DbAccess Database = new DbAccess();
			TableData schema = new TableData(Database);
			List<Example> lista = new ArrayList<Example>();
			lista = schema.getDistinctTransazioni(table);
			int indiceEsempi = 0;
			while(indiceEsempi<14) {
				int indiceAttributi = 0;
				while(indiceAttributi<5) {
					data[indiceEsempi][indiceAttributi] = lista.get(indiceEsempi).get(indiceAttributi).toString();
					//System.out.print(data[indiceEsempi][indiceAttributi] + "| ");
					indiceAttributi++;
				}
				indiceEsempi++;
				System.out.println("");

			}
			
			
		} catch (DatabaseConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		//VETTORE DI ATTRIBUTI
		explanatorySet = new Attribute[5];
		
		//ATTRIBUTO "OUTLOOK" CHE DESCRIVE LA PERCEZIONE VISIVA DEL CLIMA
		//I SUOI VALORI SONO "OVERCAST","RAIN" E "SUNNY"
		String outLookValues[]=new String[3];
		outLookValues[0]="overcast";
		outLookValues[1]="rain";
		outLookValues[2]="sunny";
		explanatorySet[0] = new DiscreteAttribute("Outlook",0, outLookValues);
		
		//ATTRIBUTO "TEMPERATURE" CHE DESCRIVE LA PERCEZIONE DELLA TEMPERATURA
		//I SUOI VALORI SONO "COLD","MILD" E "HOT"
		
		String TemperatureValues[]=new String[3];		
		TemperatureValues[0]="cold";
		TemperatureValues[1]="hot";
		TemperatureValues[2]="mild";
		
		explanatorySet[1] = new DiscreteAttribute("Temp.",1, TemperatureValues);
		
		
		/*TemperatureValues[0]="30.30";
		TemperatureValues[1]="30.00";
		TemperatureValues[2]="29.21";
		TemperatureValues[3]="13.00";
		TemperatureValues[4]="12.50";
		TemperatureValues[5]="12.00";
		TemperatureValues[6]="0.10";
		TemperatureValues[7]="0.00";
		
		explanatorySet[1] = new DiscreteAttribute("Temp.",1, TemperatureValues);
		*/
		
		
		//ATTRIBUTO "HUMIDITY" CHE DESCRIVE LA QUANTITÀ DI UMIDITÀ PRESENTE
		//I SUOI VALORI SONO "NORMAL" E "HIGH"
		String HumidityValues[]=new String[2];
		HumidityValues[0]="normal";
		HumidityValues[1]="high";
		explanatorySet[2] = new DiscreteAttribute("Humidity",2, HumidityValues);
		
		//ATTRIBUTO "WIND" CHE DESCRIVE LA FORZA DEL VENTO
		//I SUOI VALORI SONO "WEAK" E "STRONG"
		String WindValues[]=new String[2];
		WindValues[0]="weak";
		WindValues[1]="strong";
		explanatorySet[3]= new DiscreteAttribute("Wind",3, WindValues);
		
		//ATTRIBUTO CHE DESCRIVE LA POSSIBILITÀ DI
		//SVOLGERE ATTIVITÀ ALL'APERTO
		//I SUOI VALORI SONO "NO" E "YES"
		String PlayTennisValues[]= new String[2];
		PlayTennisValues[0]="no";
		PlayTennisValues[1]="yes";
		explanatorySet[4]= new DiscreteAttribute("PlayTennis",4, PlayTennisValues);
		
		
		//INIZIALIZZAZIONE DEL VALORE DI RIGHE PRESENTI (TUPLE)
		numberOfExamples=14;		 
		distinctTuples=countDistinctTuples();
	}
	
	//METODO GETTER CHE RESTITUISCE IL NUMERO DI TUPLE PRESENTI
	public int getNumberOfExamples(){
		return numberOfExamples;
	}
	
	//METODO GETTER CHE RESTITUISCE LA LUNGHEZZA DELL'ARRAY DI ATTRIBUTI E QUINDI LA QUANTITÀ DI ATTRIBUTI MODELLATI
	public int getNumberOfExplanatoryAttributes(){
		return explanatorySet.length;
	}
	
	
	Attribute[] getAttributeSchema(){
		return explanatorySet;
	}
	
	//METODO GETTER CHE RESTITUISCE UN PARTICOLARE VALORE DELLA MATRICE ASSOCIATO AD UN INDICE DI RIGA(NUMERO DI TUPLA) E COLONNA(ATTRIBUTO ASSOCIATO)
	public Object getAttributeValue(int exampleIndex, int attributeIndex){
		return data[exampleIndex][attributeIndex];
	}
	
	public String toString(){
		
		String s = new String();
		System.out.println("\t" +explanatorySet[0] +"|" + explanatorySet[1] +"|" + explanatorySet[2] +"|" + explanatorySet[3] +"|" + explanatorySet[4]+"|");
		for(int i=0;i<getNumberOfExamples();i++) {
			s += (i+1) + ":\t";
			for(int j=0;j<getNumberOfExplanatoryAttributes();j++) {
				s += data[i][j] + "| ";
			}
			s += "\n";
		}
		return s;
	}
	
	public Tuple getItemSet(int index) {
		Tuple tuple=new Tuple(explanatorySet.length);
		for(int i=0;i<explanatorySet.length;i++) 
			tuple.add(new DiscreteItem((DiscreteAttribute) explanatorySet[i], (String)data[index][i]), i);
		return tuple;
	}

	public int[] sampling(int k) throws OutOfRangeSampleSize {
		if (k<=0 || k>distinctTuples) throw new OutOfRangeSampleSize();
		else {
			int centroidIndexes[]=new int[k];
			//choose k random different centroids in data.
			Random rand=new Random();
			rand.setSeed(System.currentTimeMillis());
			for(int i=0;i<k;i++) {
				boolean found= false;
				int c;
				do {
					found=false;
					c=rand.nextInt(getNumberOfExamples());
					//verify that centroid[c] is not equal to a centroide already stored in CentroidIndexes
					for(int j=0;j<i;j++) {
						if(compare(centroidIndexes[j],c)) {
							found=true;
							break;
						}
					}
				}while (found);
				centroidIndexes[i]=c;
			}
			return centroidIndexes;
		}
	}
	
	private boolean compare(int i,int j) { 	
		boolean equal= true;
		for(int c=0; c<getNumberOfExplanatoryAttributes(); c++) {
				if (data[i][c]!=data[j][c]) {
					equal=false;
				}
		}
		return equal;
	}
	

	Object computePrototype(ArraySet idList,Attribute attribute) {
		return computePrototype(idList, (DiscreteAttribute)attribute);
	}
	
	String computePrototype(ArraySet idList,DiscreteAttribute attribute){	
		String val = attribute.getValue(0);
		for(int i = 1; i < attribute.getNumberOfDistinctValues(); i++){
			int occ1 = attribute.frequency(this, idList, val);
			int occ2 = attribute.frequency(this, idList, attribute.getValue(i));
			
			if(occ2 > occ1)
				val = attribute.getValue(i);
			}
		return val;
	}
	/*
	private int countDistinctTuples() {
		int occ=0;
		boolean check=false;
		for(int i=0;i<getNumberOfExamples();i++) {
			check=true;
			for(int j=0;j<getNumberOfExamples();j++) {
				if(compare(i,j) && (i != j)) {
					check=false;
				}
			}
			if(check == true) {
				occ = occ +1;
			}
		}
		return occ;
		
	}
	*/
	
	private int countDistinctTuples() {
		int distinct=0;
		for(int i=0;i<getNumberOfExamples();i++) {
			for(int j=1;j<getNumberOfExamples();j++) {
				if(!compare(i, j)) {
					distinct++;
				}
			}
		}
		return distinct;
	}
}
