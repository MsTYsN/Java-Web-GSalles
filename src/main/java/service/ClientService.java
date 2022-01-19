package service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Client;
import connexion.Connexion;
import dao.IDao;

public class ClientService implements IDao<Client> {

	@Override
	public boolean create(Client o) {
		String sql = "insert into client values (null, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
			ps.setString(1, o.getNom());
			ps.setString(2, o.getPrenom());
			ps.setString(3, o.getCin());
			ps.setString(4, o.getPassword());
			if (ps.executeUpdate() == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return false;
	}

	@Override
	public boolean delete(Client o) {
		String sql = "delete from client where id  = ?";
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
	public boolean update(Client o) {
		String sql = "update client set nom  = ? , prenom = ? , cin = ? , password = ? where id  = ?";
		try {
			PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
			ps.setString(1, o.getNom());
			ps.setString(2, o.getPrenom());
			ps.setString(3, o.getCin());
			ps.setString(4, o.getPassword());
			ps.setInt(5, o.getId());
			if (ps.executeUpdate() == 1) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("update : erreur sql : " + e.getMessage());

		}
		return false;
	}

	@Override
	public Client findById(int id) {
		String sql = "select * from client where id  = ?";
		try {
			PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Client(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("cin"),
						rs.getString("password"));
			}

		} catch (SQLException e) {
			System.out.println("findById " + e.getMessage());
		}
		return null;
	}

	@Override
	public List<Client> findAll() {
		List<Client> clients = new ArrayList<Client>();
		String sql = "select * from client";
		try {
			PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				clients.add(new Client(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),
						rs.getString("cin"), rs.getString("password")));
			}

		} catch (SQLException e) {
			System.out.println("findAll " + e.getMessage());
		}
		return clients;
	}

	public List<Client> findWithoutReservation() {
		List<Client> clients = new ArrayList<Client>();
		String sql = "select * from client where id not in (select idClient from occupation)";
		try {
			PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				clients.add(new Client(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),
						rs.getString("cin"), rs.getString("password")));
			}

		} catch (SQLException e) {
			System.out.println("findAll " + e.getMessage());
		}
		return clients;
	}

	public Client findByCin(String cin) {
		String sql = "select * from client where cin  = ?";
		try {
			PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
			ps.setString(1, cin);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Client(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("cin"),
						rs.getString("password"));
			}

		} catch (SQLException e) {
			System.out.println("findById " + e.getMessage());
		}
		return null;
	}

	public List<Client> search(String champ, String recherche) {
		List<Client> clients = new ArrayList<Client>();
		String sql = "select * from client where " + champ + " like '%" + recherche + "%'";
		try {
			PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				clients.add(new Client(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),
						rs.getString("cin"), rs.getString("password")));
			}

		} catch (SQLException e) {
			System.out.println("Search " + e.getMessage());
		}
		return clients;
	}

}
