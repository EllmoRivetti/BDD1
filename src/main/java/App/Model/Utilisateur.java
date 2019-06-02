package App.Model;


/**
 * @author Billy
 *
 */
public class Utilisateur {
	private int id;
	
	private String nom;
	private String motDePasse;
	private String login;
	
	private boolean abonneStatus;
	private boolean admin;
	private double solde;
	private int nbPizzaCommandees;
	
	public Utilisateur(int id, String nom, String mdp, String login) {
		this.id = id;
		this.nom = nom;
		this.motDePasse = mdp;
		this.login = login;
	}
	
	public Utilisateur(int id, String nom, String mdp, String login, boolean abo, double s, int nbPizza, boolean admin) {
		this.id = id;
		this.nom = nom;
		this.motDePasse = mdp;
		this.login = login;
		this.abonneStatus = abo;
		this.solde = s;
		this.nbPizzaCommandees = nbPizza;
		this.admin = admin;
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

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	public void decreaseSolde(double n) {
		this.solde -= n;
		System.out.println("UPDATE client SET solde = '"+this.solde+"' WHERE client.idClient = "+id);
		DatabaseManager.executeUpdate("UPDATE client SET solde = '"+this.solde+"' WHERE client.idClient = "+id);
	}
	
	public void increaseSolde(double n) {
		this.solde += n;
		System.out.println("UPDATE client SET solde = '"+this.solde+"' WHERE client.idClient = "+id);
		DatabaseManager.executeUpdate("UPDATE client SET solde = '"+this.solde+"' WHERE client.idClient = "+id);
	}
	
	public void increaseNbPizzaCommande(int i) {
		this.nbPizzaCommandees += i;
		System.out.println("UPDATE client SET nb_pizza_commande = '"+this.nbPizzaCommandees+"' WHERE client.idClient = "+id);
		DatabaseManager.executeUpdate("UPDATE client SET nb_pizza_commande = '"+this.nbPizzaCommandees+"' WHERE client.idClient = "+id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
