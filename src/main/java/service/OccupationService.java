package service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beans.Occupation;
import beans.Salle;
import connexion.Connexion;
import dao.IDao;

public class OccupationService implements IDao<Occupation> {

	private ClientService cs = new ClientService();
	private SalleService ss = new SalleService();
	private CreneauService cns = new CreneauService();

	@Override
	public boolean create(Occupation o) {
		String sql = "insert into occupation values (null, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
			ps.setDate(1, new Date(o.getDate().getTime()));
			ps.setInt(2, o.getClient().getId());
			ps.setInt(3, o.getSalle().getId());
			ps.setInt(4, o.getCreneau().getId());
			ps.setString(5, o.getEtat());
			if (ps.executeUpdate() == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return false;
	}

	@Override
	public boolean delete(Occupation o) {
		String sql = "delete from occupation where id  = ?";
		try {
			PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
			ps.setInt(1, o.getId());
			if (ps.executeUpdate() == 1) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("delete : erreur sql : " + e.getMessage());

		}
		return false;
	}

	@Override
	public boolean update(Occupation o) {
		String sql = "update occupation set date  = ? , idClient = ? , idSalle = ? , idCreneau = ? , etat = ? where id  = ?";
		try {
			PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
			ps.setDate(1, new Date(o.getDate().getTime()));
			ps.setInt(2, o.getClient().getId());
			ps.setInt(3, o.getSalle().getId());
			ps.setInt(4, o.getCreneau().getId());
			ps.setString(5, o.getEtat());
			ps.setInt(6, o.getId());
			if (ps.executeUpdate() == 1) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("update : erreur sql : " + e.getMessage());

		}
		return false;
	}

	@Override
	public Occupation findById(int id) {
		String sql = "select * from occupation where id  = ?";
		try {
			PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Occupation(rs.getInt("id"), rs.getDate("date"), cs.findById(rs.getInt("idClient")),
						ss.findById(rs.getInt("idSalle")), cns.findById(rs.getInt("idCreneau")), rs.getString("etat"));
			}

		} catch (SQLException e) {
			System.out.println("findById " + e.getMessage());
		}
		return null;
	}

	@Override
	public List<Occupation> findAll() {
		List<Occupation> occupations = new ArrayList<Occupation>();
		String sql = "select * from occupation";
		try {
			PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				occupations.add(new Occupation(rs.getInt("id"), rs.getDate("date"), cs.findById(rs.getInt("idClient")),
						ss.findById(rs.getInt("idSalle")), cns.findById(rs.getInt("idCreneau")), rs.getString("etat")));
			}

		} catch (SQLException e) {
			System.out.println("findAll " + e.getMessage());
		}
		return occupations;
	}

	public Map<String, Integer> findMostReserved() {
		Map<String, Integer> salles = new HashMap<>();
		String sql = "select idSalle, count(*) from occupation group by idSalle order by count(*)";
		try {
			PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Salle s = ss.findById(rs.getInt("idSalle"));
				salles.put(s.getCode(), rs.getInt("count(*)"));
			}

		} catch (SQLException e) {
			System.out.println("findMostReserved " + e.getMessage());
		}
		return salles;
	}

	public Map<String, Integer> findMonthReservation() {
		Map<String, Integer> months = new HashMap<>();
		String sql = "select month(date), count(*) from occupation group by month(date) order by month(date)";
		try {
			PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int month = rs.getInt("month(date)");
				int count = rs.getInt("count(*)");
				switch(month) {
				case 1:
					months.put("Janvier", count);
					break;
				case 2:
					months.put("Février", count);
					break;
				case 3:
					months.put("Mars", count);
					break;
				case 4:
					months.put("Avril", count);
					break;
				case 5:
					months.put("May", count);
					break;
				case 6:
					months.put("Juin", count);
					break;
				case 7:
					months.put("Juillet", count);
					break;
				case 8:
					months.put("Août", count);
					break;
				case 9:
					months.put("Septembre", count);
					break;
				case 10:
					months.put("Octobre", count);
					break;
				case 11:
					months.put("Novembre", count);
					break;
				case 12:
					months.put("Décembre", count);
					break;
				}
			}

		} catch (SQLException e) {
			System.out.println("findMostReserved " + e.getMessage());
		}
		return months;
	}

	public List<Occupation> findByClient(int id) {
		List<Occupation> occupations = new ArrayList<Occupation>();
		String sql = "select * from occupation where idClient = ?";
		try {
			PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				occupations.add(new Occupation(rs.getInt("id"), rs.getDate("date"), cs.findById(rs.getInt("idClient")),
						ss.findById(rs.getInt("idSalle")), cns.findById(rs.getInt("idCreneau")), rs.getString("etat")));
			}

		} catch (SQLException e) {
			System.out.println("findByClient " + e.getMessage());
		}
		return occupations;
	}

	public List<Occupation> findBySalle(int id, java.util.Date date) {
		List<Occupation> occupations = new ArrayList<Occupation>();
		String sql = "select * from occupation where idSalle = ? and date = ?";
		try {
			PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ps.setDate(2, new Date(date.getTime()));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				occupations.add(new Occupation(rs.getInt("id"), rs.getDate("date"), cs.findById(rs.getInt("idClient")),
						ss.findById(rs.getInt("idSalle")), cns.findById(rs.getInt("idCreneau")), rs.getString("etat")));
			}

		} catch (SQLException e) {
			System.out.println("findBySalle " + e.getMessage());
		}
		return occupations;
	}

	public List<Occupation> search(String champ, String recherche) {
		List<Occupation> occupations = new ArrayList<Occupation>();
		String sql = "select * from occupation where " + champ + " like '%" + recherche + "%'";
		try {
			PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				occupations.add(new Occupation(rs.getInt("id"), rs.getDate("date"), cs.findById(rs.getInt("idClient")),
						ss.findById(rs.getInt("idSalle")), cns.findById(rs.getInt("idCreneau")), rs.getString("etat")));
			}

		} catch (SQLException e) {
			System.out.println("Search " + e.getMessage());
		}
		return occupations;
	}

}
