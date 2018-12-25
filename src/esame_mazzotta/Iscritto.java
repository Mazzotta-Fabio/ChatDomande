package esame_mazzotta;

public class Iscritto {
	
	private String nome;
	private QuizClient rif;
	private boolean votato;
	private boolean bloccato;
	
	public Iscritto(String nome,QuizClient rif) {
		this.nome=nome;
		this.rif=rif;
		votato=true;
		bloccato=true;
	}
	
	public String getNome(){
		return nome;
	}
	
	public QuizClient getRif() {
		return rif;
	}
	
	public boolean getVotato() {
		return votato;
	}
	
	public boolean getBloccato(){
		return bloccato;
	}
	
	public void setVotato(boolean votato) {
		this.votato = votato;
	}
	
	public void setBloccato(boolean bloccato) {
		this.bloccato = bloccato;
	}
}
