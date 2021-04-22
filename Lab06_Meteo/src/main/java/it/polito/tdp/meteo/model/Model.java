package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	
	private MeteoDAO dao; 

	public Model() {
		dao = new MeteoDAO();
	}

	// of course you can change the String output with what you think works best
	public String getUmiditaMedia(int mese) {
		List<String> localita = dao.getLocalita();
		String stringaFinale = "";
		for(String s : localita) {
			List<Rilevamento> ril = dao.getAllRilevamentiLocalitaMese(mese, s);    //lista dei rilevamenti per il mese specifico nella localita specifica
			double media = 0;
			for(Rilevamento r : ril) {
				media += r.getUmidita();
			}
			media = media / ril.size();
			stringaFinale += "La media per la citta' di "+ s + " e' " + media +"\n";  
			
		
		}
		return stringaFinale;
	}
	
	// of course you can change the String output with what you think works best
	public String trovaSequenza(int mese) {
		List<List<Citta>> risultato = new ArrayList<List<Citta>>();  //creo la lista dei risultati
		List<String> listaNomiCitta = dao.getLocalita();       //mi segno i nomi delle citta
		List<Citta> listaCitta = new ArrayList<Citta>();     //creo una lista per le citta
		List<Citta> listaParziale = new ArrayList<Citta>();    //creo la lista per le soluzioni parziali che però è vuota
		for(String s : listaNomiCitta) {           //passo in rassegna le citta (solo i nomi)
			List<Rilevamento> listaRivelamenti = dao.getAllRilevamentiLocalitaMese(mese, s);   //prendo i rilevamenti per quella citta
			Citta citta = new Citta(s);  // creo un oggetto di tipo citta
			citta.setRilevamenti(listaRivelamenti);  //gli metto i rilevamenti
			listaCitta.add(citta);  //aggiungo la citta alla lista che passo al metodo ricorsivo
		}
		   //CONTROLLI DA AFRE PER TROVARE QUELLO CON IL COSTO MINORE
		
		soggiorno(risultato, 0, listaCitta, listaParziale);
		
		String stringa = "";
		for(List<Citta> lista : risultato) {
			stringa += "Soluzione  = ";
			for(Citta c : lista) {
				stringa += c.getNome()+ " "; 
			}
			stringa += "\n";
		}
		
		
		
		return stringa;
	}
	
	private void soggiorno(List<List<Citta>> risultato, int livello, List<Citta> listaCitta, List<Citta> parziale) {
		if(livello == 15) {
			risultato.add(parziale);
		}
		else {
			if((livello % 3) == 0) {  //se il livello è un multiplo di tre --> dobbiamo cambiare citta
				//List <Citta> nuovaListaCitta = listaCitta;
				
				
				for(Citta cit : listaCitta) { 
					List<Citta> nuovoParziale = parziale;  //creo un nuovo parziale per non fare non fare backtracking su parziale
					if(livello > 0 && !parziale.get(livello-1).equals(cit)) {
						if(cit.getCounter() < 6) {
							nuovoParziale.add(cit); //aggiungo la citta
							cit.increaseCounter();
							soggiorno(risultato, livello+1, listaCitta, nuovoParziale);
							//cit.setCounter(cit.getCounter()-1);     //BACKTRACKING
							//livello = livello -3;
							
						}
					}
					else if(livello == 0) {
						nuovoParziale.add(cit); //aggiungo la citta
						cit.increaseCounter();
						soggiorno(risultato, livello+1, listaCitta, nuovoParziale);
						//cit.setCounter(cit.getCounter()-1);     //BACKTRACKING
					}
					
					
					
					
						
				}
			}
			else {   //DEVO METTERE LA STESSA CITTA
				Citta cit = parziale.get(livello-1);
				List<Citta> nuovoParziale = parziale;
				nuovoParziale.add(cit);
				cit.increaseCounter();
				
				soggiorno(risultato, livello+1, listaCitta, nuovoParziale);
				//NON HO FATTO BACKTRACKING ---> MA SERVE QUI?
				}
			
			
		}
	}
	
	

}
