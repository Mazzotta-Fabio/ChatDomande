package esame_mazzotta;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface QuizClient extends Remote {
	void visualizzaMessaggio(String messaggio)throws RemoteException;
}
