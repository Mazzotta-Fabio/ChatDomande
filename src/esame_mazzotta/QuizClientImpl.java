package esame_mazzotta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

public class QuizClientImpl extends UnicastRemoteObject implements QuizClient {
	
	private static Logger log=Logger.getLogger("global");
	public QuizClientImpl() throws RemoteException {
		super();
	}
	
	public void visualizzaMessaggio(String messaggio) throws RemoteException {
		System.out.println(messaggio);
	}
	
	private static String ask(String prompt,BufferedReader in)throws IOException{
		System.out.print(prompt+" ");
		return(in.readLine());
	}
	
	public static void main(String[] args) {
		try{
			String nome;
			if(args.length<1){
				System.out.println("Non hai inserito il nome");
				System.exit(0);
			}
			nome=args[0];
			QuizClientImpl c=new QuizClientImpl();
			BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
			String cmd=null;
			QuizServer s=(QuizServer)Naming.lookup("rmi://localhost/Quiz");
			log.info("connesso con il server " + nome);
			if(s.iscrivi(nome,c)){
				System.out.println("C'è un quiz in atto non puoi iscriverti");
			}
			else{
				while(!(cmd=ask("Comandi>",in)).equals("!quit")){
					if(cmd.startsWith("!")){
						if(cmd.equals("!si")){
							s.vota(cmd,c);
						}
						if(cmd.equals("!no")){
							s.vota(cmd, c);
						}
					}
					else{
						s.inviaAtutti(cmd, c);
					}
				}
				s.logout(c);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
