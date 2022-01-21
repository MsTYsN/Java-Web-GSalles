package service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Admin;
import connexion.Connexion;
import dao.IDao;

public class AdminService implements IDao<Admin>{

    @Override
    public boolean create(Admin o) {
        String sql = "insert into admin values (null, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
            ps.setString(1, o.getEmail());
            ps.setString(2, MD5(o.getPassword()));
            ps.setString(3, o.getNom());
            ps.setString(4, o.getPrenom());
            ps.setDate(5, new Date(o.getDateNaissance().getTime()));
            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }

    @Override
    public boolean delete(Admin o) {
        String sql = "delete from admin where id  = ?";
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
    public boolean update(Admin o) {
        String sql = "update admin set nom  = ? , prenom = ? , dateNaissance = ? where id  = ?";
        try {
            PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
            ps.setString(1, o.getNom());
            ps.setString(2, o.getPrenom());
            ps.setDate(3, new Date(o.getDateNaissance().getTime()));
            ps.setInt(4, o.getId());
            if (ps.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("update : erreur sql : " + e.getMessage());

        }
        return false;
    }

    @Override
    public Admin findById(int id) {
        String sql = "select * from admin where id  = ?";
        try {
            PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Admin(rs.getInt("id"), rs.getString("email"), rs.getString("password"),
                        rs.getString("nom"), rs.getString("prenom"), rs.getDate("dateNaissance"));
            }

        } catch (SQLException e) {
            System.out.println("findById " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Admin> findAll() {
        List<Admin> clients = new ArrayList<Admin>();

        String sql = "select * from admin";
        try {
            PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                clients.add(new Admin(rs.getInt("id"), rs.getString("email"), rs.getString("password"),
                        rs.getString("nom"), rs.getString("prenom"), rs.getDate("dateNaissance")));
            }

        } catch (SQLException e) {
            System.out.println("findAll " + e.getMessage());
        }
        return clients;
    }

    public Admin findByEmail(String email) {
        try {
            String sql = "select * from admin where email = ?";
            PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Admin(rs.getInt("id"), rs.getString("email"), rs.getString("password"),
                        rs.getString("nom"), rs.getString("prenom"), rs.getDate("dateNaissance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updatePass(Admin o) {
        try {
            String sql = "update admin set password = ? where id = ?";
            PreparedStatement ps = Connexion.getInstance().getConnection().prepareStatement(sql);
            ps.setString(1, MD5(o.getPassword()));
            ps.setInt(2, o.getId());
            if (ps.executeUpdate() != 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String MD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            BigInteger bi = new BigInteger(1, md.digest(s.getBytes()));
            return bi.toString(16);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
