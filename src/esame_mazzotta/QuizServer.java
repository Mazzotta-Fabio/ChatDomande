package esame_mazzotta;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface QuizServer extends Remote {
	void inviaAtutti(String messaggio,QuizClient rif)throws RemoteException;
	boolean iscrivi(String nome,QuizClient rif)throws RemoteException;
	void vota(String voto,QuizClient rif)throws RemoteException;
	void logout(QuizClient rif)throws RemoteException;
}
