package App.Model;

public class Utilisateur {
	private String nom;
	private String motDePasse;
	private String login;
	
	private boolean abonneStatus;
	private double solde;
	private int nbPizzaCommandees;
	
	public Utilisateur(String nom, String mdp, String login) {
		this.nom = nom;
		this.motDePasse = mdp;
		this.login = login;
	}
	
	public Utilisateur(String nom, String mdp, String login, boolean abo, double s, int nbPizza) {
		this.nom = nom;
		this.motDePasse = mdp;
		this.login = login;
		this.abonneStatus = abo;
		this.solde = s;
		this.nbPizzaCommandees = nbPizza;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public boolean isAbonne() {
		return abonneStatus;
	}

	public void setAbonneStatus(boolean abonneStatus) {
		this.abonneStatus = abonneStatus;
	}

	public double getSolde() {
		return solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}

	public int getNbPizzaCommandees() {
		return nbPizzaCommandees;
	}

	public void setNbPizzaCommandees(int nbPizzaCommandees) {
		this.nbPizzaCommandees = nbPizzaCommandees;
	}
}
