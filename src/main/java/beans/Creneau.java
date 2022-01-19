package beans;

public class Creneau {
	private int id;
	private String heureDebut;
	private String heureFin;

	public Creneau(int id, String heureDebut, String heureFin) {
		this.id = id;
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
	}

	public Creneau(String heureDebut, String heureFin) {
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHeureDebut() {
		return heureDebut;
	}

	public void setHeureDebut(String heureDebut) {
		this.heureDebut = heureDebut;
	}

	public String getHeureFin() {
		return heureFin;
	}

	public void setHeureFin(String heureFin) {
		this.heureFin = heureFin;
	}

	@Override
	public String toString() {
		return "Creneau [id=" + id + ", heureDebut=" + heureDebut + ", heureFin=" + heureFin + "]";
	}

}
