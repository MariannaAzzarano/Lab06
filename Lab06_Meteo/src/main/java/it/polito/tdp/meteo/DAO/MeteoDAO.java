package it.polito.tdp.meteo.DAO;

import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.polito.tdp.meteo.model.Citta;
import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {
	
	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {
		
		String sql = "SELECT Umidita , Data "
				+ "FROM situazione "
				+ "WHERE MONTH(DATA) = ? AND Localita = ? ";
		
		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(2, localita);
			st.setInt(1, mese);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				
				rilevamenti.add(new Rilevamento(localita, rs.getDate("Data"), rs.getInt("Umidita")));
			}
			conn.close();
			return rilevamenti;
		}catch(SQLException e) {
			throw new RuntimeException("Errore" ,e);
		}
	}
	
	public List<String> getLocalita(){
		String sql = "SELECT Localita "
				   + "FROM situazione "
				   + "GROUP BY Localita ";
		
		List<String> localita = new ArrayList<String> ();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				String citta = rs.getString("Localita");
				localita.add(citta);
			}
			conn.close();
			return localita;
		}catch(SQLException e) {
			throw new RuntimeException("Errore in getLocalita", e);
		}
	}

	
	public List<Rilevamento> getRilevazioni(String nomeCitta){
		String sql = "SELECT Umidita, Data " 
				   + "FROM situazione "
				   + "WHERE Localita = ?";
		
		List<Rilevamento> listaRilevamenti = new ArrayList<Rilevamento>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, nomeCitta);
			
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Integer umidita = rs.getInt("Umidita");
				Date data = rs.getDate("Data");
				listaRilevamenti.add(new Rilevamento(nomeCitta, data, umidita));
			}
			conn.close();
			return listaRilevamenti;
			
		}catch(SQLException e) {
			throw new RuntimeException("Errore in getCitta", e);
		}
		
		
		
		
	}

}
