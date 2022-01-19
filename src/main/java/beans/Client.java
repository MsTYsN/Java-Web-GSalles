package beans;

public class Client {
	private int id;
	private String nom;
	private String prenom;
	private String cin;
	private String password;
	public Client(int id, String nom, String prenom, String cin, String password) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.cin = cin;
		this.password = password;
	}
	
	public Client(String nom, String prenom, String cin, String password) {
		this.nom = nom;
		this.prenom = prenom;
		this.cin = cin;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", cin=" + cin + ", password=" + password
				+ "]";
	}
}
