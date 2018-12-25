package esame_mazzotta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Logger;

public class QuizServerImpl extends UnicastRemoteObject implements QuizServer {
	
	
	private static final long serialVersionUID = 1L;
	private ArrayList <Iscritto> iscritti=new ArrayList <Iscritto>();
	private boolean partito=false;
	private int votosi=0;
	private int votono=0;
	private static QuizServerImpl se=null;
	//modifica: aggiunto logger con eventuali messaggi di log
	private static Logger log=Logger.getLogger("global");
	public QuizServerImpl() throws RemoteException {
		super();
	}
	
	synchronized public boolean iscrivi(String nome, QuizClient rif) throws RemoteException {
		log.info("sono in iscrivi");
		if(partito==false){
			//modifica:è stata cambiata la posizione di queste istruzioni
			se.diciAtutti("Server: si è iscritto " + nome);
			iscritti.add(new Iscritto(nome,rif));
			return false;
		}
		return true;
	}

	synchronized public void inviaAtutti(String messaggio, QuizClient rif) throws RemoteException {
		log.info("sto in inviaAtutti");
		for(int i=0;i<iscritti.size();i++){
			Iscritto s=iscritti.get(i);
			if(s.getRif().equals(rif)){
				if(s.getBloccato()){
					if(iscritti.size()>=2){
						for(Iscritto b:iscritti){
							//modificata condizione:verifica che non sia bloccato quando deve inviare il messaggio
							if((!(b.getRif().equals(rif)))&&(b.getBloccato())){
								b.getRif().visualizzaMessaggio(s.getNome() + " : " + messaggio);
							}
						}
					}
					else{
						rif.visualizzaMessaggio("non puoi chattare con meno di due iscritti");
					}
				}
				else{
					rif.visualizzaMessaggio("sei stato bloccato");
				}
			}
		}
	}

	synchronized public void logout(QuizClient rif) throws RemoteException {
		log.info("sto in logout");
		for(int i=0;i<iscritti.size();i++){
			Iscritto s=iscritti.get(i);
			if(s.getRif().equals(rif)){
				//Modifica:Aggiunta stringa per comporre un messaggio
				String messaggio=s.getNome() + " ha abbandonato la chat";
				iscritti.remove(i);
				se.diciAtutti(messaggio);
				break;
			}
		}
	}
	
	synchronized public void vota(String voto, QuizClient rif) throws RemoteException {
		log.info("sto in vota");
		for(Iscritto i:iscritti){
			if(i.getRif().equals(rif)){
				//Modifica:modificata condizione in modo tale da controllare anche se è bloccato
				if(((partito==true)&&(i.getVotato()==true))&&(i.getBloccato()==true)){
					if(voto.equals("!si")){
						votosi++;
					}
					else{
						votono++;
					}
					i.setVotato(false);
					se.faiVincitore();
				}
				else{
					rif.visualizzaMessaggio("Non puoi votare");
				}
			}
		}
	}

	private void blocca(String nome,boolean bloccato){
		//modifica:aggiunto try/catch
		try{
			for(int i=0;i<iscritti.size();i++){
				Iscritto s=iscritti.get(i);
				if(s.getNome().equals(nome)){
					s.setBloccato(bloccato);
					if(bloccato){
						s.getRif().visualizzaMessaggio("sei stato sbloccato");
					}
					else{
						s.getRif().visualizzaMessaggio("sei stato bloccato");
					}
				}
			}
		}
		catch(RemoteException e){
			e.printStackTrace();
		}
	}
	
	private void diciAtutti(String messaggio){
		//modifica:Aggiunto try/catch
		try{
			for(int i=0;i<iscritti.size();i++){
				Iscritto s=iscritti.get(i);
				if(s.getBloccato()){
					s.getRif().visualizzaMessaggio(messaggio);
				}
			}
		}
		catch(RemoteException e){
			e.printStackTrace();
		}
	}
	
	private void faiVincitore(){
		int somma=votosi+votono;
		if(somma==iscritti.size()){
			String messaggio=" Alla domanda proposta ci sono stati " + votosi + " si e " + votono + " no";
			se.diciAtutti(messaggio);
			votosi=0;
			votono=0;
			partito=false;
			//modifica:aggiunto for per settare a true i valori booleani dopo la votazione
			for(Iscritto i:iscritti){
				i.setVotato(true);
			}
		}
		
	}
	
	private static String ask(String prompt,BufferedReader in)throws IOException{
		System.out.print(prompt+" ");
		return(in.readLine());
	}
	
	public static void main(String[] args) {
		try{ 
			//modifica:aggiunto securitymanager
			System.setSecurityManager(new RMISecurityManager());
			se=new QuizServerImpl();
			Naming.rebind("Quiz",se);
			log.info("pronto per accettare connessioni");
			BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
			String cmd=null;
			while(!(cmd=ask("Comandi>",in)).equals("!quit")){
				if(cmd.startsWith("!")){
					if(cmd.equals("!blocco")){
						String nome=ask("inserisci il nome: ",in);
						se.blocca(nome,false);
					}
					if(cmd.equals("!sblocco")){
						String nome=ask("inserisci il nome:",in);
						se.blocca(nome,true);
					}
					if(cmd.equals("!quiz")){
						String domanda=ask("inserisci la domanda:",in);
						se.diciAtutti("Il server pone la seguente domanda: "+ domanda);
						se.partito=true;
					}
				}
				else{
					se.diciAtutti(" Server :" + cmd);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}