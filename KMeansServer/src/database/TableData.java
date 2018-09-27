package database;
import java.sql.*;
import java.util.ArrayList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.util.List;
import java.util.Set;
import java.util.TreeSet;


/**
 * Classe che modella l'insieme di transazione collezionate in una tabella.
 * La singola transazione è modellata dalla classe Example.
 * 
 * @author Giuseppe Pio Maffia, Giovanni Vittore
 *
 */
public class TableData {

	DbAccess db;

	public TableData(DbAccess db) {
		this.db=db;
	}

	public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException, DatabaseConnectionException {
		
		DbAccess.initConnection();
		Connection connessione = DbAccess.getConnection();
		Statement state = connessione.createStatement();
		ResultSet rs = state.executeQuery("select distinct * from " + table);
		ArrayList<Example> esempioLista = new ArrayList<Example>(14);
		int indice = 0;
		while(rs.next()) {
			/*
			System.out.println(rs.getString("outlook") + "| " + rs.getString("temperature") + 
				"| " + rs.getString("humidity") + "| " + rs.getString("wind") + "| " + rs.getString("play"));
			*/ //QUESTA È LA STAMPA DEI VALORI
			
			Example temp = new Example();
			temp.add(rs.getString("outlook"));
			temp.add(rs.getFloat("temperature"));
			temp.add(rs.getString("humidity"));
			temp.add(rs.getString("wind"));
			temp.add(rs.getString("play"));
			esempioLista.add(indice,temp);
			indice++;
		}
		
		return esempioLista;
		
	}

	public  Set<Object>getDistinctColumnValues(String table,String column) throws SQLException{
		
		TreeSet<Object> nuovoSet = new TreeSet<Object>();
		
		try {
			DbAccess.initConnection();
			Connection connessione = DbAccess.getConnection();
			Statement state = connessione.createStatement();
			ResultSet rs = state.executeQuery("select distinct " + column + "from " +table);
			while(rs.next()) {
				nuovoSet.add(rs.getObject(column));
			}
			
			return nuovoSet;
			
		} catch (DatabaseConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	return nuovoSet;

	}
	
	public  Object getAggregateColumnValue(String table,String column,QUERY_TYPE aggregate) throws SQLException,NoValueException{
		
		try {
			DbAccess.initConnection();
			Connection connessione = DbAccess.getConnection();
			Statement state = connessione.createStatement();
			ResultSet rs = state.executeQuery("select (" +aggregate+ ")" + column +  " from " +table);
			Object aggregato = rs.next();
			return aggregato;
			
		} catch (DatabaseConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	
	}



}
