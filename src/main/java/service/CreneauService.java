package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Creneau;
import connexion.Connexion;
import dao.IDao;

public class CreneauService implements IDao<Creneau> {

	@Override
	public boolean create(Creneau o) {
		String sql = "insert into creneau values (null, ?, ?)";
		try {
			PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
			ps.setString(1, o.getHeureDebut());
			ps.setString(2, o.getHeureFin());
			if (ps.executeUpdate() == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return false;
	}

	@Override
	public boolean delete(Creneau o) {
		String sql = "delete from creneau where id  = ?";
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
	public boolean update(Creneau o) {
		String sql = "update creneau set heureDebut  = ? , heureFin = ? where id  = ?";
		try {
			PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
			ps.setString(1, o.getHeureDebut());
			ps.setString(2, o.getHeureFin());
			ps.setInt(3, o.getId());
			if (ps.executeUpdate() == 1) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("update : erreur sql : " + e.getMessage());

		}
		return false;
	}

	@Override
	public Creneau findById(int id) {
		String sql = "select * from creneau where id  = ?";
		try {
			PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Creneau(rs.getInt("id"), rs.getString("heureDebut"), rs.getString("heureFin"));
			}

		} catch (SQLException e) {
			System.out.println("findById " + e.getMessage());
		}
		return null;
	}

	@Override
	public List<Creneau> findAll() {
		List<Creneau> creneaux = new ArrayList<Creneau>();
		String sql = "select * from creneau";
		try {
			PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				creneaux.add(new Creneau(rs.getInt("id"), rs.getString("heureDebut"), rs.getString("heureFin")));
			}

		} catch (SQLException e) {
			System.out.println("findAll " + e.getMessage());
		}
		return creneaux;
	}

	public List<Creneau> search(String champ, String recherche) {
		List<Creneau> creneaux = new ArrayList<Creneau>();
		String sql = "select * from creneau where " + champ + " like '%" + recherche + "%'";
		try {
			PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				creneaux.add(new Creneau(rs.getInt("id"), rs.getString("heureDebut"), rs.getString("heureFin")));
			}

		} catch (SQLException e) {
			System.out.println("Search " + e.getMessage());
		}
		return creneaux;
	}

}
