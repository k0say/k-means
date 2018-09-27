package mining;
import java.io.Serializable;

import data.Data;
import data.OutOfRangeSampleSize;
import data.Tuple;

/**
 * Classe che rappresenta un insieme di cluster determinati dal k-means
 * 
 * @author Giuseppe Pio Maffia, Giovanni Vittore
 *
 */

public class ClusterSet implements Serializable {
	
	private static final long serialVersionUID = 1L;
	Cluster C[];
	int i=0;
	
	public ClusterSet(int k){
		C = new Cluster[k];
	}
	
	void add(Cluster c) {
		this.C[i]=c;
		i++;
	}
	
	public Cluster get(int i) {
		return C[i];
	}
	
	public void initializeCentroids(Data data) throws OutOfRangeSampleSize{
			int centroidIndexes[]=data.sampling(C.length);
			
			for(int i=0;i<centroidIndexes.length;i++)
			{
				Tuple centroidI=data.getItemSet(centroidIndexes[i]);
				add(new Cluster(centroidI));
			}
	}
	
	Cluster nearestCluster(Tuple tuple) {
		Cluster nearest = C[0];
		double dist1=0.0;
		double dist2=0.0;
		
		for(int i=1; i<C.length; i++) {
			dist1=tuple.getDistance(nearest.getCentroid());
			dist2=tuple.getDistance(C[i].getCentroid());
			if(dist2<dist1) {
				nearest = C[i];
			}
		}
		
		return nearest;
	}
	
	Cluster currentCluster(int id) {
		Cluster current = null;
		//double contain=0.0;
		
		for(int i = 0;i<C.length; i++) {
			if(C[i].contain(id)) {
				current = C[i];
			}
		}
		
		return current;
	}
	
	public void updateCentroids(Data data) {
		for(int i=0;i<C.length;i++) {
			C[i].computeCentroid(data);
		}
	}
	
	public String toString(Data data) {
		String str="";
		for(int i=0;i<C.length;i++){
			if (C[i]!=null){
				str+=i+":"+C[i].toString(data)+"\n";		
			}
		}
		return str;		
	}

}
