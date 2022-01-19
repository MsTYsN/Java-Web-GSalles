package beans;

import java.util.Date;

public class Occupation {
	private int id;
	private Date date;
	private Client client;
	private Salle salle;
	private Creneau creneau;
	private String etat;
	
	public Occupation(int id, Date date, Client client, Salle salle, Creneau creneau, String etat) {
		this.id = id;
		this.date = date;
		this.client = client;
		this.salle = salle;
		this.creneau = creneau;
		this.etat = etat;
	}
	
	public Occupation(Date date, Client client, Salle salle, Creneau creneau, String etat) {
		this.date = date;
		this.client = client;
		this.salle = salle;
		this.creneau = creneau;
		this.etat = etat;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Salle getSalle() {
		return salle;
	}

	public void setSalle(Salle salle) {
		this.salle = salle;
	}

	public Creneau getCreneau() {
		return creneau;
	}

	public void setCreneau(Creneau creneau) {
		this.creneau = creneau;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	@Override
	public String toString() {
		return "Occupation [id=" + id + ", date=" + date + ", client=" + client + ", salle=" + salle + ", creneau="
				+ creneau + ", etat=" + etat + "]";
	}
}
