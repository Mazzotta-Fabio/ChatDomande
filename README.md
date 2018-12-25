# ChatDomande
La repository contiene un programma client-server realizzato con Java RMI. In particolare si gestisce una chat in cui vengono effettuati dei quiz.
La chat si avvia e si conduce in maniera in maniera standard: ogni partecipante ha una shell di comandi ed ogni stringa che digita 
viene inviata a tutti i partecipanti. Ogni entrata/uscirta di un nuovo utente viene comunicata a tutti i partecipanti. Il server può chattare 
normalmente come ogni altro utente ma può effettuare il blocco di un utente, e condurrre il quiz. La chat si avvia solamente quando si sono iscritti
almeno 2 client(con un solo client, il client non può chattare(mentre il servere si)). Se il client prova a chattare va avvisato che non può farlo!
### BLOCCO
Il blocco di un utente lo può fare solamente il server: se digita "!blocco", viene richiesto il nickname dell'utente da bloccare. L'utente viene avvisato,
che non può chattare e da quel momento non può più chattare e se lo fa viene avvisato che non può farlo. Con il comando "!sblocco" e il nickname dell'utente 7
l'utente viene sbloccato.
### QUIZ
In aggiunta alla chat il server può avviare una votazione su delle domande a tema. Il server digita il comando "!quiz" e poi viene chiesto di inserire la domanda.
Ogni client può votare con si/no e lo può fare digitando il comando "!si" e "!no" che invia il risultato al server. Solo quando il quiz è partito i client
possono votare. Quando hanno votato tutti il server comunica a tutti i risultati delle votazioni.

Il progetto è stato realizzato per l'esame di Programmazione Distribuita da 6 CFU della Laurea Triennale in Informatica dell'università di Salerno nell'anno accademico 2015/2016.
